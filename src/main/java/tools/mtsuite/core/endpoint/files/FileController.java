package tools.mtsuite.core.endpoint.files;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.mtsuite.core.core.interceptor.acl.FileACL;
import tools.mtsuite.core.core.interceptor.acl.ProjectACL;
import tools.mtsuite.core.core.interceptor.visibility.Visibility;
import tools.mtsuite.core.endpoint.files.dto.FileDto;
import tools.mtsuite.core.endpoint.files.dto.FileTokenAccessDto;
import tools.mtsuite.core.endpoint.files.dto.FilesPaginationDto;
import tools.mtsuite.core.endpoint.testSuite.dto.TestSuiteDto;

import java.util.List;

@RestController
@Tag(name = "files")
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@GetMapping(value = "/{id}")
	@Visibility(attr = "id",acl = FileACL.class)
	public ResponseEntity<?> getFile(@PathVariable("id") Long id) {
			return fileService.getFile(id);
	}

	@GetMapping(value = "/content/{id}")
	public ResponseEntity<?> getFileContentWithApiKey(@PathVariable("id") Long id,
													  @RequestParam(value = "apiKey", required = true) String apiKey) {
		return fileService.getFileWithApiKey(id,apiKey);
	}


	@GetMapping(value = "/project/{projectId}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "projectId",acl = ProjectACL.class)
	public FilesPaginationDto getFileEntityByProject(@PathVariable("projectId") Long projectId,
													 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
													 @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
		return fileService.getFileObjectByProject(projectId,page,size);
	}

	@GetMapping(value = "/entity/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "id",acl = FileACL.class)
	public FileDto getFileEntity(@PathVariable("id") Long id) {
			return fileService.getFileObject(id);
	}
	

	@PostMapping(value = "/file/project/{projectId}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "projectId",acl = ProjectACL.class)
	public FileDto uploadFile(@RequestBody FileDto fileDto,@PathVariable("projectId") Long projectId) {
			return fileService.uploadFile(fileDto,projectId);
	}

	@PostMapping(value = "/file/mapTestSuite/project/{projectId}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "projectId",acl = ProjectACL.class)
	public TestSuiteDto mapExcelToTestSuite(@RequestBody FileDto fileDto, @PathVariable("projectId") Long projectId) {
		return fileService.createNewTestSuiteDtoFromExcel(fileDto);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "id",acl = FileACL.class)
	public tools.mtsuite.core.core.dto.ResponseStatus deleteFile(@PathVariable("id") Long id) {
		return fileService.removeFile(id);
	}

	/*****  TOKEN  ****/

	@GetMapping(value = "/token/project/{projectId}")
	@ResponseStatus(HttpStatus.OK)
	@Visibility(attr = "projectId",acl = ProjectACL.class)
	public FileTokenAccessDto getFileTokenAccess(@PathVariable("projectId") Long projectId,
												 @RequestParam(value = "refresh", defaultValue = "false") Boolean refresh) {
		return fileService.getFileTokenAccess(projectId,refresh);
	}


	@PostMapping(value = "/token/integration")
	@ResponseStatus(HttpStatus.OK)
	public tools.mtsuite.core.core.dto.ResponseStatus uploadFileByToken(@RequestBody FileDto fileDto,
																		@RequestParam(value = "apiKey", required = true) String apiKey) {
		return fileService.uploadFileWithToken(fileDto,apiKey);
	}


}
