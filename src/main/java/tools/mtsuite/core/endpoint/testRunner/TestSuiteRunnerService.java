package tools.mtsuite.core.endpoint.testRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.dao.*;
import tools.mtsuite.core.common.model.*;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.common.vmodel.VTestSuiteRunner;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.dto.LoginUserDto;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.interceptor.acl.ProjectACL;
import tools.mtsuite.core.core.service.CommonService;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerDto;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerPaginationDto;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerTableDto;
import tools.mtsuite.core.endpoint.testSuite.dto.ModuleDto;
import tools.mtsuite.core.endpoint.testSuite.dto.TestCaseDto;
import tools.mtsuite.core.endpoint.testSuite.dto.TestSuiteDto;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TestSuiteRunnerService {

	@Autowired
	private Mapeador mapeador;

	@Autowired
	private IProjectDao iProjectDao;

	@Autowired
	private ITestSuiteDao iTestSuiteDao;

	@Autowired
	private ITestSuiteRunnerDao iTestSuiteRunnerDao;

	@Autowired
	private IUserDao iUserDao;

	@Autowired
	private IProjectVersionDao iProjectVersionDao;

	@Autowired
	private ITestSuiteRunnerStatisticsDao iTestSuiteRunnerStatisticsDao;

	@Autowired
	private CommonService commonService;


    /**
     * Get test suites by project. If project id = 0, get all for user.
	 * @return
     */
	public TestSuiteRunnerPaginationDto getTestSuiteRunnersByTestSuite(Long testSuiteId, Integer page, Integer size,List<TestSuiteStatus> searchFilters) {

		List<TestSuiteRunnerTableDto> _testSuiteRunnersDto = new ArrayList<>();
		Page<TestSuiteRunner> _testSuiteRunner;

		if(testSuiteId == 0) {
			throw new BadRequestException("001", "TestSuite not found");
		}

		Optional<TestSuite> testSuite = iTestSuiteDao.findById(testSuiteId);
		if(testSuite.isEmpty()) {
			throw new BadRequestException("002", "TestSuite not found");
		}

		Pageable pageable = PageRequest.of(page, size);

		if (searchFilters == null) {
			_testSuiteRunner = iTestSuiteRunnerDao.findByTestSuite(testSuite.get(),pageable);
		} else {
			_testSuiteRunner = iTestSuiteRunnerDao.findByTestSuiteAndStatusIn(testSuite.get(),searchFilters,pageable);
		}

		for(TestSuiteRunner testSuiteRunner : _testSuiteRunner) {
			TestSuiteRunnerTableDto testSuiteRunnerTableDto = (TestSuiteRunnerTableDto) mapeador.map(testSuiteRunner, TestSuiteRunnerTableDto.class);
			testSuiteRunnerTableDto.setCreatorUserId(testSuiteRunner.getCreatorUser().getId());
			testSuiteRunnerTableDto.setTestSuiteId(testSuiteRunner.getTestSuite().getId());
			testSuiteRunnerTableDto.setTestSuiteName(testSuiteRunner.getTestSuite().getName());
			_testSuiteRunnersDto.add(testSuiteRunnerTableDto);
		}
		_testSuiteRunnersDto.sort( Comparator.comparing(TestSuiteRunnerTableDto::getLastModifiedDate).reversed());


		TestSuiteRunnerPaginationDto testSuiteRunnerPaginationDto = new TestSuiteRunnerPaginationDto();
		testSuiteRunnerPaginationDto.setData(_testSuiteRunnersDto);
		testSuiteRunnerPaginationDto.setTotalElements(_testSuiteRunner.getTotalElements());
		return testSuiteRunnerPaginationDto;
	}

	/**
	 * Return test suite by id
	 * @param id
	 * @return
	 */
	public TestSuiteRunnerDto getTestSuiteRunner(Long id) {
		Optional<TestSuiteRunner> testSuiteRunnerOpt = iTestSuiteRunnerDao.findById(id);

		if(testSuiteRunnerOpt.isEmpty()) {
			throw new BadRequestException("002", "TestSuiteRunner not found");
		}

		TestSuiteRunner testSuiteRunner = testSuiteRunnerOpt.get();

		VTestSuiteRunner vTestSuiteRunner = new VTestSuiteRunner(testSuiteRunner);

		TestSuiteRunnerDto testSuiteRunnerDto = (TestSuiteRunnerDto) mapeador.map(vTestSuiteRunner, TestSuiteRunnerDto.class);

		//testSuiteRunnerDto.setProjectId(vTestSuiteRunner.getProject().getId());
		testSuiteRunnerDto.setCreatorUserId(vTestSuiteRunner.getCreatorUser().getId());
		testSuiteRunnerDto.setTestSuiteId(testSuiteRunner.getTestSuite().getId());
		testSuiteRunnerDto.setTestSuiteName(testSuiteRunner.getTestSuite().getName());

		return testSuiteRunnerDto;
	}

	/**
	 * Set/Update testSuiteRunner
	 * @param testSuiteRunnerDto
	 * @return
	 */

	@Autowired
	private ProjectACL projectACL;
	public TestSuiteRunnerDto addTestSuiteRunner(TestSuiteRunnerDto testSuiteRunnerDto) {

		// validate valid input
		validateTestSuiteDto(testSuiteRunnerDto);
		// Get actual user
		User user = commonService.getCurrentUser();
		// map to vTestSuiteRunner
		VTestSuiteRunner vTestSuiteRunner = new VTestSuiteRunner();
		mapeador.map(testSuiteRunnerDto, vTestSuiteRunner);

		// map to testSuiteRunner
		TestSuiteRunner testSuiteRunner = vTestSuiteRunner.toTestSuiteRunner();

		// Add extra data
		Optional<TestSuite> testSuite = iTestSuiteDao.findById(testSuiteRunnerDto.getTestSuiteId());
		if(testSuite.isEmpty()) {
			throw new BadRequestException("001","No TestSuite");
		}
		projectACL.checkEntityVisibilityWithException(testSuite.get().getProject().getId());


		testSuiteRunner.setTestSuite(testSuite.get());
		testSuiteRunner.setCreatorUser(user);

		if(testSuiteRunnerDto.getProjectVersion() != null) {
			Optional<ProjectVersion> projectVersionOpt = iProjectVersionDao.findById(testSuiteRunnerDto.getProjectVersion().getId());

			if(projectVersionOpt.isEmpty()) {
				throw new BadRequestException("001","No ProjectVersion");
			}

			testSuiteRunner.setProjectVersion(projectVersionOpt.get());
		}

		// Create statics
		TestSuiteRunnerStatistics testSuiteRunnerStatistics = new TestSuiteRunnerStatistics();
		testSuiteRunnerStatistics.calculateStatistics(testSuiteRunner);
		testSuiteRunnerStatistics.setTestSuiteRunner(testSuiteRunner);
		testSuiteRunner.setStatistics(testSuiteRunnerStatistics);



		//create relation between test suite runner and users
		createOrUpdateTestSuiteRunnerUser(testSuiteRunner, testSuiteRunnerDto.getUsers());

		if(!testSuiteRunner.getUsers().contains(user)){
			// add user creator
			user.getTestSuiteRunners().add(testSuiteRunner);
			testSuiteRunner.getUsers().add(user);
			iTestSuiteRunnerDao.save(testSuiteRunner);
		}

		// Save
		iTestSuiteRunnerStatisticsDao.save(testSuiteRunnerStatistics);
		iTestSuiteRunnerDao.save(testSuiteRunner);

		// Map save to TestSuiteRunnerDto
		VTestSuiteRunner vTestSuiteRunnerRes = new VTestSuiteRunner(testSuiteRunner);
		TestSuiteRunnerDto testSuiteRunnerDtoRes = (TestSuiteRunnerDto) mapeador.map(vTestSuiteRunnerRes, TestSuiteRunnerDto.class);

		// Add extra data
		testSuiteRunnerDtoRes.setTestSuiteId(testSuiteRunner.getTestSuite().getId());
		testSuiteRunnerDtoRes.setTestSuiteName(testSuiteRunner.getTestSuite().getName());
		testSuiteRunnerDtoRes.setCreatorUserId(testSuiteRunner.getCreatorUser().getId());
		return testSuiteRunnerDtoRes;
	}

	public TestSuiteRunnerDto updateTestSuiteRunner(TestSuiteRunnerDto testSuiteRunnerDto) {

		// validate valid input
		validateTestSuiteDto(testSuiteRunnerDto);

		Optional<TestSuiteRunner> testSuiteRunnerOp = iTestSuiteRunnerDao.findById(testSuiteRunnerDto.getId());

		if(testSuiteRunnerOp.isEmpty()) {
			throw new BadRequestException("001", "testSuiteRunner Id not found");
		}

		projectACL.checkEntityVisibilityWithException(testSuiteRunnerOp.get().getTestSuite().getProject().getId());

		VTestSuiteRunner vTestSuiteRunner = new VTestSuiteRunner(testSuiteRunnerOp.get());

		mapeador.map(testSuiteRunnerDto, vTestSuiteRunner);

		TestSuiteRunner testSuiteRunner = vTestSuiteRunner.toTestSuiteRunner();

		// Add extra data
		Optional<TestSuite> testSuite = iTestSuiteDao.findById(testSuiteRunnerDto.getTestSuiteId());
		if(testSuite.isEmpty()) {
			throw new BadRequestException("001","No TestSuite");
		}
		testSuiteRunner.setTestSuite(testSuite.get());

		if(testSuiteRunnerDto.getProjectVersion() != null) {
			Optional<ProjectVersion> projectVersionOpt = iProjectVersionDao.findById(testSuiteRunnerDto.getProjectVersion().getId());

			if(projectVersionOpt.isEmpty()) {
				throw new BadRequestException("001","No ProjectVersion");
			}

			testSuiteRunner.setProjectVersion(projectVersionOpt.get());
		}

		// Caluclate statics
		if(testSuiteRunnerDto.getStatistics() == null) {
			throw new BadRequestException("002","No Statics");
		}
		Optional<TestSuiteRunnerStatistics> testSuiteRunnerStatisticsOpt = iTestSuiteRunnerStatisticsDao.findById(testSuiteRunnerDto.getStatistics().getId());

		if(testSuiteRunnerStatisticsOpt.isEmpty()) {
			throw new BadRequestException("003","No Statics");
		}

		TestSuiteRunnerStatistics testSuiteRunnerStatistics = testSuiteRunnerStatisticsOpt.get();
		testSuiteRunnerStatistics.calculateStatistics(testSuiteRunner);

		testSuiteRunner.setStatistics(testSuiteRunnerStatistics);

		//update users related to test suite runner
		createOrUpdateTestSuiteRunnerUser(testSuiteRunner, testSuiteRunnerDto.getUsers());

		iTestSuiteRunnerDao.save(testSuiteRunner);

		VTestSuiteRunner vTestSuiteRunnerResp = new VTestSuiteRunner(testSuiteRunner);

		TestSuiteRunnerDto testSuiteRunnerDtoRes = (TestSuiteRunnerDto) mapeador.map(vTestSuiteRunnerResp,TestSuiteRunnerDto.class);
		// Add extra data
		testSuiteRunnerDtoRes.setTestSuiteId(testSuiteRunner.getTestSuite().getId());
		testSuiteRunnerDtoRes.setTestSuiteName(testSuiteRunner.getTestSuite().getName());
		testSuiteRunnerDtoRes.setCreatorUserId(testSuiteRunner.getCreatorUser().getId());

		return testSuiteRunnerDtoRes;
	}

	public Boolean deleteTestSuiteRunner(Long id) {
		Optional<TestSuiteRunner> testSuiteRunnerOp = iTestSuiteRunnerDao.findById(id);

		if(testSuiteRunnerOp.isEmpty()) {
			throw new BadRequestException("001", "testSuiteRunner Id not found");
		}

		TestSuiteRunner testSuiteRunner = testSuiteRunnerOp.get();

		iTestSuiteRunnerDao.delete(testSuiteRunner);

		return true;
	}

	/* ---- TEST SUITE RUNNER USER(S) ----*/
	public void createOrUpdateTestSuiteRunnerUser(TestSuiteRunner testSuiteRunner, List<UserDto> users ) {


		// removes all associations
		if(!testSuiteRunner.getUsers().isEmpty()) {
			for (User user : testSuiteRunner.getUsers()) {
				user.getTestSuiteRunners().remove(testSuiteRunner);
			}
			testSuiteRunner.getUsers().clear();
			iTestSuiteRunnerDao.save(testSuiteRunner);
		}

		// adds the new associations
		for(UserDto userDto : users)
		{
			Optional<User> user = iUserDao.findById(userDto.getId());

			testSuiteRunner.getUsers().add(user.get());
			user.get().getTestSuiteRunners().add(testSuiteRunner);

		}
	}

	private boolean checkCurrentUserAccess(Long testSuiteRunnerDtoId) {
		User currentUser = commonService.getCurrentUser();
		if (iTestSuiteRunnerDao.getUserTestSuitRunnerByUserIdAndTestSuitRunnerId(currentUser.getId(), testSuiteRunnerDtoId) == null) {
			throw new BadRequestException("401", "Unauthorized user");
		}

		return true;
	}

	private boolean checkTestSuitRunnerStatus(TestSuiteRunner testSuiteRunner,TestSuiteStatus currentStatus, TestSuiteStatus updatedStatus) {
		//test suit runner restrictive check for transitory states
		switch (currentStatus) {
			case Pending:
				if (updatedStatus != TestSuiteStatus.Running) {
					throw new BadRequestException("001", "Invalid status update");
				}
				return true;
			case Running:
				if (updatedStatus == TestSuiteStatus.Pending | updatedStatus == TestSuiteStatus.Running) {
					throw new BadRequestException("001", "Invalid status update");
				}
				if(testSuiteRunner.getStartDate() != null){
					testSuiteRunner.setStartDate(new Date());
					testSuiteRunner.getStatistics().setStartDate(new Date());
				}
				return true;
			case Waiting:
				if (updatedStatus == TestSuiteStatus.Pending | updatedStatus == TestSuiteStatus.Waiting) {
					throw new BadRequestException("001", "Invalid status update");
				}
				return true;
			case Cancelled:
				if (updatedStatus != TestSuiteStatus.Cancelled) {
					throw new BadRequestException("001", "Invalid status update");
				}
				return true;
			case Finished:
				if (updatedStatus != TestSuiteStatus.Finished) {
					throw new BadRequestException("001", "Invalid status update");
				}

				if(testSuiteRunner.getFinishDate() != null){
					testSuiteRunner.setFinishDate(new Date());
					testSuiteRunner.getStatistics().setFinishDate(new Date());
				}
				return true;
			default:
				return false;

		}
	}

	public TestSuiteRunnerDto updateTestSuiteRunnerStatus(Long testSuiteRunnerId, TestSuiteStatus updatedStatus) {
		//check current user's access
		checkCurrentUserAccess(testSuiteRunnerId);

		Optional<TestSuiteRunner> testSuiteRunnerOp = iTestSuiteRunnerDao.findById(testSuiteRunnerId);
		if (testSuiteRunnerOp.isEmpty()) {
			throw new BadRequestException("001", "Test suit runner not found");
		}

		TestSuiteRunner testSuiteRunner = testSuiteRunnerOp.get();
		//check for a valid test suit runner status update
		if (!checkTestSuitRunnerStatus(testSuiteRunnerOp.get(),testSuiteRunnerOp.get().getStatus(), updatedStatus)) {
			throw new BadRequestException("001", "Invalid status update");
		}

		testSuiteRunner.setStatus(updatedStatus);
		iTestSuiteRunnerDao.save(testSuiteRunner);

		VTestSuiteRunner vTestSuiteRunner = new VTestSuiteRunner(testSuiteRunner);

		TestSuiteRunnerDto testSuiteRunnerDto = (TestSuiteRunnerDto) mapeador.map(vTestSuiteRunner, TestSuiteRunnerDto.class);

		//testSuiteRunnerDto.setProjectId(vTestSuiteRunner.getProject().getId());
		testSuiteRunnerDto.setCreatorUserId(vTestSuiteRunner.getCreatorUser().getId());
		testSuiteRunnerDto.setTestSuiteId(testSuiteRunner.getTestSuite().getId());
		testSuiteRunnerDto.setTestSuiteName(testSuiteRunner.getTestSuite().getName());

		return testSuiteRunnerDto;
	}

	/* Helper to delete unselected testCases */
	private TestSuiteRunnerDto filterUnRunTestCases(TestSuiteRunnerDto testSuiteRunnerDto) {

		List<ModuleDto> removeModuleList = new ArrayList<>();
		for(ModuleDto module : testSuiteRunnerDto.getModules()) {
			Boolean deleteModule = true;
			List<TestCaseDto> removeTestCaseList = new ArrayList<>();

			for(TestCaseDto testCase : module.getTestCases()) {
				if(testCase.getRun() == null || testCase.getRun() != true) {
					removeTestCaseList.add(testCase);
				}
				else
				{
					deleteModule = false;
				}
			}

			if( deleteModule ) {
				removeModuleList.add(module);
			}else {
				module.getTestCases().removeAll(removeTestCaseList);
			}
		}

		testSuiteRunnerDto.getModules().removeAll(removeModuleList);

		return testSuiteRunnerDto;
	}

	public void validateTestSuiteDto(TestSuiteRunnerDto testSuiteRunnerDto) {

		for (ModuleDto module : testSuiteRunnerDto.getModules()) {
			if (!module.validateModuleDto(module) || module.getName().isEmpty() || module.getTestCases().isEmpty()) {
				throw new BadRequestException("001","Modules are mandatory");
			}
		}
	}

}
