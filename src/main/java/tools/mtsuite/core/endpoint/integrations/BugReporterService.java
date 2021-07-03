package tools.mtsuite.core.endpoint.integrations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.dao.IBugReporterCredentialDao;
import tools.mtsuite.core.common.dao.IBugReporterLogDao;
import tools.mtsuite.core.common.dao.ITestSuiteRunnerDao;
import tools.mtsuite.core.common.integrations.BugReporterFactory;
import tools.mtsuite.core.common.integrations.IBugReporter;
import tools.mtsuite.core.common.model.TestSuiteRunner;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.common.model.integrations.BugReporterLog;
import tools.mtsuite.core.common.vmodel.VTestSuiteRunner;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.dto.ResponseStatus;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.service.CommonService;
import tools.mtsuite.core.core.utils.JsonParserUtil;
import tools.mtsuite.core.endpoint.integrations.dto.*;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Ing.Federico Gonzalez
 * @Since v1.0.0
 * Bug Reporter service
 * High level abstraction management with the third party the integration
 *  - C.R.U.D. action over BugReporterCredential
 *  - Fetch bug reporter project list
 *  - Fetch bug reporter model
 *  - Report a bug
 *  - Get last bug reported
 *  -
 **/

@Service
public class BugReporterService {

    @Autowired
    private Mapeador mapeador;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IBugReporterLogDao bugReporterLogDao;

    @Autowired
    private IBugReporterCredentialDao bugReporterCredentialDao;

    @Autowired
    private BugReporterFactory bugReporterFactory;

    @Autowired
    private ITestSuiteRunnerDao testSuiteRunnerDao;


    /**
     * @Param Long id
     * @Return BugReporterCredentialDto
     * Get bug reporter by ID.
     **/
    public BugReporterCredentialDto getBugReporter(Long id){
        if(!bugReporterCredentialDao.existsById(id))
            throw new BadRequestException("404","Bug reporter not found ["+id+"]");

        return bugReporterCredentialDao.findById(id).get().getDto();
    }

    /**
     * @Return List<BugReporterCredentialDto></BugReporterCredentialDto>
     * Get bug reporter by current user. Taking the current user from
     * the application context in the security context.
     **/
    public List<BugReporterCredentialDto> getBugReporterByUser(){
        List<BugReporterCredentialDto> bugReporterCredentialList = new ArrayList();
        User user = commonService.getCurrentUser();

        bugReporterCredentialDao.findByUser(user).forEach(bugReporterCredential -> {
            bugReporterCredentialList.add(bugReporterCredential.getDto());
        });

        return bugReporterCredentialList;
    }

    /**
     * @Param BugReporterCredentialDto
     * @Return BugReporterCredentialDto
     * Create bug reporter. The user will be automatically added
     * from the security context.
     **/
    public BugReporterCredentialDto createBugReporterCredential(BugReporterCredentialDto bugReporterCredentialDto){
        User user = commonService.getCurrentUser();

        BugReporterCredential bugReporterCredential = bugReporterCredentialDto.modelInstance();
        bugReporterCredential.setUser(user);

        bugReporterCredentialDao.save(bugReporterCredential);

        return bugReporterCredential.getDto();
    }

    /**
     * @Param BugReporterCredentialDto
     * @Return BugReporterCredentialDto
     * Modify buf reporter basic information. This function call the abstact
     * method from BugReporterCredentialDto update() to modify the values.
     **/
    public BugReporterCredentialDto modifyBugReporterCredential(BugReporterCredentialDto bugReporterCredentialDto){
        if(!bugReporterCredentialDao.existsById(bugReporterCredentialDto.getId()))
            throw new BadRequestException("404","Bug reporter not found ["+bugReporterCredentialDto.getId()+"]");

        BugReporterCredential bugReporterCredential = bugReporterCredentialDao.findById(bugReporterCredentialDto.getId()).get();

        bugReporterCredential.update(bugReporterCredentialDto);

        bugReporterCredentialDao.save(bugReporterCredential);
        return bugReporterCredential.getDto();
    }



    /**
     * @Param Long bugReporterCredentialId
     * @Return ResponseStatus
     * Delete bug reporter by ID
     **/
    public ResponseStatus deleteBugReporter(Long id) {
        if(!bugReporterCredentialDao.existsById(id))
            throw new BadRequestException("404","Bug reporter not found ["+id+"]");
        bugReporterCredentialDao.deleteById(id);
        return new ResponseStatus("200","Bug reporter deleted");
    }


    /**
     * @Param List<BugReporterProjectDto>
     * @Return Long bugReporterCredentialId
     * Get project list from the bug reporter API. Using the BugReporterFactory to get
     * the concrete bug reporter service.
     **/
    public List<BugReporterProjectDto> getProjectsList(Long bugReporterCredentialId){
        if(!bugReporterCredentialDao.existsById(bugReporterCredentialId))
            throw new BadRequestException("404","Bug reporter not found ["+bugReporterCredentialId+"]");

        BugReporterCredential bugReporterCredential = bugReporterCredentialDao.findById(bugReporterCredentialId).get();


        IBugReporter concreteBugReporter =  bugReporterFactory.getStrategy(bugReporterCredential.getType());

        return  concreteBugReporter.getProjects(bugReporterCredential);
    }


    /**
     * @Param Long bugReporterCredentialId
     * @Param String bugReporterProjectId
     * @Return BugReporterModelDto
     * Fetch all the models from the bug reporter API and complete the
     * BugReporterModelDto with the information received.
     **/
    public BugReporterModelDto getBugReporterModels(Long bugReporterCredentialId, String bugReporterProjectId){
        if(!bugReporterCredentialDao.existsById(bugReporterCredentialId))
            throw new BadRequestException("404","Bug reporter not found ["+bugReporterCredentialId+"]");

        BugReporterCredential bugReporterCredential = bugReporterCredentialDao.findById(bugReporterCredentialId).get();


        IBugReporter concreteBugReporter =  bugReporterFactory.getStrategy(bugReporterCredential.getType());


        BugReporterModelDto bugReporterModelDto = new BugReporterModelDto();
        bugReporterModelDto.setCategories(concreteBugReporter.getCategories(bugReporterCredential,bugReporterProjectId));
        bugReporterModelDto.setCurrentUser(concreteBugReporter.getUserMySelf(bugReporterCredential));
        bugReporterModelDto.setIssuesType(concreteBugReporter.getIssuesType(bugReporterCredential,bugReporterProjectId));
        bugReporterModelDto.setPriorities(concreteBugReporter.getPriorities(bugReporterCredential));
        bugReporterModelDto.setMilestones(concreteBugReporter.getMilestones(bugReporterCredential,bugReporterProjectId));
        bugReporterModelDto.setProjectUsers(concreteBugReporter.getUsersProject(bugReporterCredential,bugReporterProjectId));
        bugReporterModelDto.setVersions(concreteBugReporter.getVersions(bugReporterCredential,bugReporterProjectId));

        return bugReporterModelDto;
    }


    /**
     * @Param Long bugReporterCredentialId
     * @Param BugReporterInputDto bugReporterInputDto
     * @Return TestSuiteRunnerDto
     * Report a new bug using the bug reporter API and save the request body and
     * the new issue (response body) as a BugReporterLog
     **/
    public TestSuiteRunnerDto reportBug(Long bugReporterCredentialId,
                                        Long testRunnerId,
                                        String moduleUId,
                                        String testSuiteUId,
                                        BugReporterInputDto bugReporterInputDto){

        if(!bugReporterCredentialDao.existsById(bugReporterCredentialId))
            throw new BadRequestException("404","Bug reporter not found ["+bugReporterCredentialId+"]");

        if(!testSuiteRunnerDao.existsById(testRunnerId))
            throw new BadRequestException("404","Test Suite runner not found ["+testRunnerId+"]");

        BugReporterCredential bugReporterCredential = bugReporterCredentialDao.findById(bugReporterCredentialId).get();
        TestSuiteRunner  testSuiteRunner = testSuiteRunnerDao.findById(testRunnerId).get();

        IBugReporter concreteBugReporter =  bugReporterFactory.getStrategy(bugReporterCredential.getType());

        String issue =  concreteBugReporter.reportBug(bugReporterCredential,bugReporterInputDto);

        BugReporterLog bugReporterLog = new BugReporterLog();
        bugReporterLog.setBugReporterCredential(bugReporterCredential);
        bugReporterLog.setTestSuiteRunner(testSuiteRunner);
        bugReporterLog.setBugReporterProject(bugReporterInputDto.getProjectIdentificator());
        bugReporterLog.setUser(commonService.getCurrentUser());
        bugReporterLog.setIssue(issue);
        bugReporterLog.setRequestBody(JsonParserUtil.objectToJson(bugReporterInputDto));

        bugReporterLogDao.save(bugReporterLog);


        // Map to vTestSuiteRunner to increase the bug count.
        VTestSuiteRunner vTestSuiteRunner = new VTestSuiteRunner(testSuiteRunner);
        vTestSuiteRunner.increaseBugCountModule(moduleUId,testSuiteUId,bugReporterLog.getId());


        testSuiteRunner = vTestSuiteRunner.toTestSuiteRunner();
        // save changes
        testSuiteRunnerDao.save(testSuiteRunner);

        return (TestSuiteRunnerDto)mapeador.map(testSuiteRunner, TestSuiteRunnerDto.class);
    }



    public BugReporterLogDto getLastBugReporter(Long testRunnerId){

        if(!testSuiteRunnerDao.existsById(testRunnerId))
            throw new BadRequestException("404","Test Suite runner not found ["+testRunnerId+"]");

        TestSuiteRunner  testSuiteRunner = testSuiteRunnerDao.findById(testRunnerId).get();
        User user = commonService.getCurrentUser();

        BugReporterLog bugReporterLog = bugReporterLogDao.findFirstByTestSuiteRunnerAndUserOrderByCreatedDateDesc(testSuiteRunner,user);

        if(bugReporterLog == null)
            return new BugReporterLogDto();

        return (BugReporterLogDto) mapeador.map(bugReporterLog,BugReporterLogDto.class);
    }




}
