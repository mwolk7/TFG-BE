package tools.mtsuite.core.core.interceptor.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.mtsuite.core.common.dao.ITestSuiteDao;
import tools.mtsuite.core.common.dao.ITestSuiteRunnerDao;
import tools.mtsuite.core.common.model.TestSuite;
import tools.mtsuite.core.common.model.TestSuiteRunner;
import tools.mtsuite.core.core.service.CommonService;

@Component
public class TestSuiteRunnerACL extends  BaseAcl{

    @Autowired
    private CommonService commonService;

    @Autowired
    private TestSuiteACL testSuiteACL;



    @Autowired
    private ITestSuiteRunnerDao testSuiteRunnerDao;

    @Override
    public Boolean checkEntityVisibility(Long id) {
        if(!testSuiteRunnerDao.existsById(id))
            return false;
        TestSuiteRunner testSuiteRunner = testSuiteRunnerDao.findById(id).get();
        return testSuiteACL.checkEntityVisibility(testSuiteRunner.getTestSuite().getId());
    }


    @Override
    public Boolean checkEntityVisibility(Object object) {
        return false;
    }

}
