package tools.mtsuite.core.endpoint.testSuite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.dao.*;
import tools.mtsuite.core.common.model.*;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.common.vmodel.VModule;
import tools.mtsuite.core.common.vmodel.VTestCase;
import tools.mtsuite.core.common.vmodel.VTestSuite;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.dto.LoginUserDto;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.interceptor.acl.ProjectACL;
import tools.mtsuite.core.core.service.CommonService;
import tools.mtsuite.core.endpoint.testSuite.dto.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Service
@Transactional
public class TestSuiteService {

	@Autowired
	private Mapeador mapeador;

	@Autowired
	private IProjectDao iProjectDao;

	@Autowired
	private ITestSuiteDao iTestSuiteDao;

	@Autowired
	private IUserDao iUserDao;

	@Autowired
	private ProjectACL projectACL;


	@Autowired
	private CommonService commonService;


    /**
     * Get test suites by project. If project id = 0, get all for user.
	 * @return
     */
	public List<TestSuiteDto> getTestSuitesByProject(Long projectId) {

		List<TestSuiteDto> _testSuitesDto = new ArrayList<>();
		List<TestSuite> _testSuite = new ArrayList();

		if (projectId == 0) {
			throw new BadRequestException("001","Project not found");
		}

		Optional<Project> project = iProjectDao.findById(projectId);
		if(project.isEmpty()) {
			throw new BadRequestException("001","Project not found");
		}

		// TODO: order by modifydate
		_testSuite = project.get().getTestSuites();

		for(TestSuite testSuite : _testSuite) {
			_testSuitesDto.add( getTestSuite(testSuite.getId()) );
		}

		_testSuitesDto.sort( Comparator.comparing(TestSuiteDto::getLastModifiedDate).reversed());

		return _testSuitesDto;
	}

	/**
	 * Return test suite by id
	 * @param id
	 * @return
	 */
	public TestSuiteDto getTestSuite(Long id) {
		TestSuite testSuite = iTestSuiteDao.findById(id).get();

		if(testSuite == null) {
			throw new BadRequestException("002", "TestSuite not found");
		}

		VTestSuite vTestSuite = new VTestSuite(testSuite);

		TestSuiteDto testSuiteDto = (TestSuiteDto) mapeador.map(vTestSuite, TestSuiteDto.class);

		testSuiteDto.setProjectId(vTestSuite.getProject().getId());
		testSuiteDto.setCreatorUserId(vTestSuite.getCreatorUser().getId());

		// Set total testRunners
		if(testSuite.getTestSuiteRunners() != null) {
			Integer testRunningRunning = 0;
			for(TestSuiteRunner testSuiteRunner : testSuite.getTestSuiteRunners()) {
				if(testSuiteRunner.getStatus() == TestSuiteStatus.Running) {
					testRunningRunning++;
				}
			}
			testSuiteDto.setTotalTestRunnersRunning(testRunningRunning);
			testSuiteDto.setTotalTestRunners( testSuite.getTestSuiteRunners().size() );
		}else{
			testSuiteDto.setTotalTestRunners(0);
			testSuiteDto.setTotalTestRunnersRunning(0);
		}

		// Set testCases statistics
		Integer totalTestCases = 0;
		Integer totalTestCasesCritical = 0;
		Integer totalTestCasesHigh = 0;
		Integer totalTestCasesMedium = 0;
		Integer totalTestCasesLow = 0;

		for(VModule vmodule : vTestSuite.getModules()) {
			for(VTestCase vTestCase : vmodule.getTestCases()) {

				if(vTestCase.getPriority() == null) {
					continue;
				}

				switch (vTestCase.getPriority()) {
					case Critical:
						totalTestCasesCritical++;
						break;
					case High:
						totalTestCasesHigh++;
						break;
					case Medium:
						totalTestCasesMedium++;
						break;
					case Low:
						totalTestCasesLow++;
						break;
					default:
				}
			}
			totalTestCases += vmodule.getTestCases().size();
		}

		testSuiteDto.setTotalTestCases(totalTestCases);
		testSuiteDto.setTotalTestCasesCritical(totalTestCasesCritical);
		testSuiteDto.setTotalTestCasesHigh(totalTestCasesHigh);
		testSuiteDto.setTotalTestCasesMedium(totalTestCasesMedium);
		testSuiteDto.setTotalTestCasesLow(totalTestCasesLow);

		return testSuiteDto;
	}

	/**
	 * Set/Update testSuite
	 * @param testSuiteDto
	 * @return
	 */
	public TestSuiteDto addTestSuite(TestSuiteDto testSuiteDto) {
		projectACL.checkEntityVisibilityWithException(testSuiteDto.getProjectId());
		// Get actual user
		User user = commonService.getCurrentUser();
		validateTestSuiteDto(testSuiteDto);


		// map to vTestSuite
		VTestSuite vTestSuite = new VTestSuite();
		mapeador.map(testSuiteDto, vTestSuite);

		// map to testSuite
		TestSuite testSuite = vTestSuite.toTestSuite();

		// Add extra data
		Optional<Project> project = iProjectDao.findById(testSuiteDto.getProjectId());
		if(project.isEmpty()) {
			throw new BadRequestException("001","No project");
		}
		testSuite.setProject(project.get());
		testSuite.setCreatorUser(user);

		// Save
		iTestSuiteDao.save(testSuite);

		// Map save to TestSuiteDto
		TestSuiteDto testSuiteDtoRes = (TestSuiteDto) mapeador.map(new VTestSuite(testSuite), TestSuiteDto.class);

		// Add extra data
		testSuiteDtoRes.setProjectId(testSuite.getProject().getId());
		testSuiteDtoRes.setCreatorUserId(testSuite.getCreatorUser().getId());

		return testSuiteDtoRes;
	}

	public TestSuiteDto updateTestSuite(TestSuiteDto testSuiteDto) {

		validateTestSuiteDto(testSuiteDto);

		Optional<TestSuite> testSuiteOp = iTestSuiteDao.findById(testSuiteDto.getId());

		if(testSuiteOp.isEmpty()) {
			throw new BadRequestException("001", "testSuite Id not found");
		}

		projectACL.checkEntityVisibilityWithException(testSuiteOp.get().getProject().getId());


		VTestSuite vTestSuite = new VTestSuite(testSuiteOp.get());

		// clear lists to avoid duplicates
		vTestSuite.getModules().clear();
		vTestSuite.getBrowsers().clear();
		vTestSuite.getOs().clear();
		vTestSuite.getDevices().clear();

		mapeador.map(testSuiteDto, vTestSuite);

		TestSuite testSuite = vTestSuite.toTestSuite();

		iTestSuiteDao.save(testSuite);

		VTestSuite vTestSuiteResp = new VTestSuite(testSuite);

		return (TestSuiteDto) mapeador.map(vTestSuiteResp,TestSuiteDto.class);
	}

	public Boolean deleteTestSuite(Long id) {
		Optional<TestSuite> testSuiteOp = iTestSuiteDao.findById(id);

		if(testSuiteOp.isEmpty()) {
			throw new BadRequestException("001", "testSuite Id not found");
		}

		TestSuite testSuite = testSuiteOp.get();

		iTestSuiteDao.delete(testSuite);

		return true;
	}

	public void validateTestSuiteDto(TestSuiteDto testSuiteDto) {

		for (ModuleDto module : testSuiteDto.getModules()) {
			if (!module.validateModuleDto(module) || module.getName().isEmpty() || module.getTestCases().isEmpty()) {
				throw new BadRequestException("001","Modules are mandatory");
			}
		}
	}

}
