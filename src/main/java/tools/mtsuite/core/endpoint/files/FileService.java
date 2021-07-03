package tools.mtsuite.core.endpoint.files;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.mtsuite.core.common.dao.IFileDao;
import tools.mtsuite.core.common.dao.IFileTokenAccessDao;
import tools.mtsuite.core.common.dao.IProjectDao;
import tools.mtsuite.core.common.dao.IUserDao;
import tools.mtsuite.core.common.model.File;
import tools.mtsuite.core.common.model.FileTokenAccess;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.dto.ResponseStatus;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.service.CommonService;
import tools.mtsuite.core.core.utils.ExcelVariablesPosition;
import tools.mtsuite.core.endpoint.files.dto.FileDto;
import tools.mtsuite.core.endpoint.files.dto.FileTokenAccessDto;
import tools.mtsuite.core.endpoint.files.dto.FilesPaginationDto;
import tools.mtsuite.core.endpoint.testSuite.dto.ModuleDto;
import tools.mtsuite.core.endpoint.testSuite.dto.TestSuiteDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class FileService {

	@Autowired
	@Qualifier("initMinioClient")
	private MinioClient minioClient;

	@Autowired
	private IFileDao fileDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IFileTokenAccessDao fileTokenAccessDao;

	@Autowired
	private  Mapeador mapeador;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IProjectDao projectDao;

	public FileDto uploadFile(FileDto fileDto, Long projectId) {
		try {

			if (!projectDao.existsById(projectId))
				throw new BadRequestException("404", "Project not found");

			Project project = projectDao.findById(projectId).get();


			String bucket = URLDecoder.decode(projectId.toString()+project.getId().toString(), "UTF-8");
			createBucket(bucket);
			fileDto.setBucket(bucket);
			Map<String, String> headerMap = new HashMap<>();

			// Getting stream parsed
			String[] data = fileDto.getContent().split(",");
			if(data.length != 2) {
				throw new BadRequestException("300", "Bad stream format");
			}
			String dataType = data[0]+",";
			String base64Image = data[1];

			if(dataType == null || base64Image == null) {
				throw new BadRequestException("300", "Bad stream format");
			}

			fileDto.setDataType(dataType);

			// This method deletes data:image/png;base64, from the stream
			byte[] fileBytes;
			fileBytes = Base64.getDecoder().decode(base64Image);
			ByteArrayInputStream byteStream = new ByteArrayInputStream(fileBytes);


			Date now = new Date();
			Random r = new Random();
			int i = r.nextInt((100000 - 1) + 1) + 1;
			String nameForStorage = (now.getTime() * i) + "_" + fileDto.getName();
			fileDto.setNameToStorage(nameForStorage);
			fileDto.setThumbnailName("thumbnail_"+nameForStorage);
			minioClient.putObject(bucket, nameForStorage, byteStream, (long) byteStream.available(), headerMap);
			byteStream.close();


			ByteArrayInputStream thumbnailImage = this.generateThumbImage(fileBytes);
			minioClient.putObject(bucket, fileDto.getThumbnailName(), thumbnailImage, (long) thumbnailImage.available(), headerMap);
			thumbnailImage.close();

			File file = new File(fileDto);
			file.setProject(project);
			fileDao.save(file);

			return (FileDto) mapeador.map(file,FileDto.class);
		} catch (BadRequestException e) {
			throw new BadRequestException(e.getCode(), e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException("300", "Error creating file");
		}

	}

	private ByteArrayInputStream generateThumbImage(byte[] data) throws IOException {

		double percent           = 0.2;
		InputStream in           = new ByteArrayInputStream(data);
		BufferedImage inputImage = ImageIO.read(in);

		//Redimensionado
		int scaledWidth  = (int) (inputImage.getWidth() * percent);
		int scaledHeight = (int) (inputImage.getHeight() * percent);

		BufferedImage bufferimage  = resize(inputImage, scaledWidth, scaledHeight);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(bufferimage, "png", output );
		output.flush();
		byte[] dataOut   = output.toByteArray();
		output.close();
		bufferimage.flush();
		ByteArrayInputStream result = new ByteArrayInputStream(dataOut);

		return result;
	}

	private BufferedImage resize(BufferedImage inputImage ,  int scaledWidth, int scaledHeight){


		// Escalar
		Image img  = inputImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

		if (img instanceof BufferedImage){
			return (BufferedImage) img;
		}

		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bimage;
	}



	public ResponseEntity<?> getFile(Long id) {
		if (!fileDao.existsById(id))
			throw new BadRequestException("300", "File not founded");
		File file = fileDao.findById(id).get();
		return  getFileBucket(file.getBucket(), file.getNameToStorage(), file.getName());
	}

	public ResponseEntity<?> getFileWithApiKey(Long id,String apiKey) {
		validateToken(apiKey);

		if (!fileDao.existsById(id))
			throw new BadRequestException("300", "File not founded");

		File file = fileDao.findById(id).get();
		return  getFileBucket(file.getBucket(), file.getThumbnailName(), file.getName());
	}





	public FileDto getFileObject(Long id) {
		if (!fileDao.existsById(id))
			throw new BadRequestException("300", "File not founded");
		File file = fileDao.findById(id).get();
		FileDto fileDto = new FileDto();
		fileDto.setName(file.getName());
		fileDto.setContent(file.getDataType()+getFileBucketString(file.getBucket(), file.getNameToStorage(), file.getName()));
		return fileDto;
	}

	public FilesPaginationDto getFileObjectByProject(Long projectId,Integer page, Integer size) {
		if (!projectDao.existsById(projectId))
			throw new BadRequestException("300", "Project not found");

		List<FileDto> fileDtoList = new ArrayList();

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

		Page<File> files = fileDao.findByProject(projectDao.findById(projectId).get(),pageable);

		files.forEach(f ->{
			fileDtoList.add((FileDto) mapeador.map(f,FileDto.class));
		});

		FilesPaginationDto filesPaginationDto = new FilesPaginationDto();
		filesPaginationDto.setData(fileDtoList);
		filesPaginationDto.setTotalElements(files.getTotalElements());

		return filesPaginationDto;
	}

	public ResponseEntity<?> getFileBucket(String bucket, String file, String fileName) {
		try {
			ObjectStat is = minioClient.statObject(bucket, file);

			InputStream stream = minioClient.getObject(bucket, file);

			InputStreamResource inputStreamResource = new InputStreamResource(stream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(is.length());
			headers.setContentType(MediaType.valueOf(is.contentType()));
			ContentDisposition contentDisposition = ContentDisposition.builder("inline")
					.filename(fileName)
					.build();
			headers.setContentDispositionFormData("Content-Disposition", is.name());
			headers.setContentDisposition(contentDisposition);
			return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
		} catch (Exception e) {
			throw new BadRequestException("300", "File not founded in storage");
		}
	}

	@Transactional(readOnly = true)
	public String getFileBucketString(String bucket, String file, String fileName) {
		try {
			InputStream stream = minioClient.getObject(bucket, file);
			byte[] bytes = IOUtils.toByteArray(stream);
			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			throw new BadRequestException("300", "File not founded in storage");
		}
	}

	public ResponseStatus removeFile(Long id) {
		try {
			if (!fileDao.existsById(id))
				throw new BadRequestException("300", "File not founded");
			File file = fileDao.findById(id).get();
			file.setShowInGallery(false);
			fileDao.save(file);
			return new ResponseStatus("200", "File " + file.getName() + " has been deleted successfully");
		} catch (Exception e) {
			throw new BadRequestException("300", "Error deleting File");
		}
	}

	private ResponseStatus removeFileMinio(File file) {
		try {
			if (!minioClient.bucketExists(file.getBucket())) {
				throw new BadRequestException("300", "Bucket not found");
			}
			minioClient.removeObject(file.getBucket(), file.getNameToStorage());
			return new ResponseStatus("200", "File " + file.getName() + " has been deleted successfully");
		} catch (Exception e) {
			throw new BadRequestException("300", "File not founded in storage");
		}
	}


	public ResponseStatus createBucket(String name) {
		try {
			boolean found = minioClient.bucketExists(name);
			if (found) {
				return new ResponseStatus("200", "Bucket " + name + " has been created successfully");
			} else {
				minioClient.makeBucket(name);
				return new ResponseStatus("200", "Bucket " + name + " has been created successfully");
			}
		} catch (Exception e) {
			throw new BadRequestException("300", "Error creating bucket");
		}
	}

	public ResponseStatus removeBucket(String bucketName) {
		try {
			if (!minioClient.bucketExists(bucketName)) {
				return new ResponseStatus("200", "Bucket " + bucketName + " has been deleted successfully");
			}
			minioClient.removeBucket(bucketName);
			return new ResponseStatus("200", "Bucket " + bucketName + " has been deleted successfully");
		} catch (Exception e) {
			throw new BadRequestException("300", "Error deleting bucket");
		}
	}




	public TestSuiteDto createNewTestSuiteDtoFromExcel(FileDto fileDto) {
		try {


			// Getting stream parsed
			String[] data = fileDto.getContent().split(",");
			if(data.length != 2) {
				throw new BadRequestException("300", "Bad stream format");
			}
			String dataType = data[0]+",";
			String base64Image = data[1];

			if(dataType == null || base64Image == null) {
				throw new BadRequestException("300", "Bad stream format");
			}

			fileDto.setDataType(dataType);

			// This method deletes data:image/png;base64, from the stream
			byte[] fileBytes;
			fileBytes = Base64.getDecoder().decode(base64Image);
			ByteArrayInputStream byteStream = new ByteArrayInputStream(fileBytes);

			BufferedReader fileReader = new BufferedReader(new InputStreamReader(byteStream, "UTF-8"));
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();


			TestSuiteDto testSuiteDto = new TestSuiteDto();

			int i = 0;

			ModuleDto moduleDto = new ModuleDto();
			for (CSVRecord csvRecord : csvRecords) {
				i++;
				String module = csvRecord.get(ExcelVariablesPosition.C_MODULE);

				if (i == 1)
					continue;

				if (module.isEmpty() && i <= 2)
					continue;


				if (module.isEmpty() || csvRecord.getRecordNumber() == ((ArrayList) csvRecords).size()) {

					if (csvRecord.getRecordNumber() == ((ArrayList) csvRecords).size())
						moduleDto.addTestCaseFromExcelRow(csvRecord);

					testSuiteDto.getModules().add((ModuleDto) moduleDto.clone());
					moduleDto = new ModuleDto();
				} else {
					moduleDto.addTestCaseFromExcelRow(csvRecord);
				}
			}
			return testSuiteDto;
		} catch (Exception e) {
			throw new BadRequestException("300", "Error reading file");
		}

	}



	public FileTokenAccessDto getFileTokenAccess(Long projectId,Boolean refresh){
		User user = commonService.getCurrentUser();

		if (!projectDao.existsById(projectId))
			throw new BadRequestException("404", "Project not found");

		Project project = projectDao.findById(projectId).get();

		List<FileTokenAccess> fileTokenAccessList = fileTokenAccessDao.findByProjectAndUser(project,user);
		FileTokenAccess fileTokenAccess = new FileTokenAccess();
		if(fileTokenAccessList.isEmpty()){
			String token = generateToken(project,user);
			fileTokenAccess.setToken(token);
			fileTokenAccess.setProject(project);
			fileTokenAccess.setUser(user);
			fileTokenAccessDao.save(fileTokenAccess);
		}else{
			fileTokenAccess = fileTokenAccessList.get(0);
			if(refresh){
				String token = generateToken(project,user);
				fileTokenAccess.setToken(token);
				fileTokenAccessDao.save(fileTokenAccess);
			}
		}

		return new FileTokenAccessDto(fileTokenAccess.getToken());
	}


	public ResponseStatus uploadFileWithToken(FileDto fileDto,String token){
		String decodedToken = validateToken(token);

		String projectId = decodedToken.split("&")[0];
		String username  = decodedToken.split("&")[1];

		if (!projectDao.existsById(Long.valueOf(projectId)))
			throw new BadRequestException("404", "Project not found");

		Project project = projectDao.findById(Long.valueOf(projectId)).get();
		User user = userDao.findByUsername(username);

		if (user == null)
			throw new BadRequestException("404", "User not found");

		this.uploadFile(fileDto,project.getId());

		return new ResponseStatus("200","File saved successfully");
	}


	private String generateToken(Project project, User user){
		String token = JWT.create()
				.withSubject(project.getId()+"&"+user.getUsername()+"&"+ new Date().getTime())
				.sign(HMAC512(SECRET));
		return token;
	}

	public String validateToken(String token){

		try {
			String decodedToken = JWT.require(Algorithm.HMAC512(SECRET))
					.build()
					.verify(token)
					.getSubject();
			return decodedToken;
		}catch (Exception e) {
			throw  new BadRequestException("401","Invalid token");
		}
	}





	public static final String SECRET = "NotBugTokenS3cR3T-SHhhhHHh";

}
