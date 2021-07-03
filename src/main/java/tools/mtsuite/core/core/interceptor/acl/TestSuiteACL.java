package tools.mtsuite.core.core.interceptor.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.mtsuite.core.common.dao.IProjectDao;
import tools.mtsuite.core.common.dao.ITestSuiteDao;
import tools.mtsuite.core.common.model.TestSuite;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.service.CommonService;

@Component
public class TestSuiteACL extends  BaseAcl{

    @Autowired
    private CommonService commonService;

    @Autowired
    private ProjectACL projectACL;



    @Autowired
    private ITestSuiteDao testSuiteDao;

    @Override
    public Boolean checkEntityVisibility(Long id) {
        if(!testSuiteDao.existsById(id))
            return false;
        TestSuite testSuite = testSuiteDao.findById(id).get();
        Long projectId = testSuite.getProject().getId();
        return projectACL.checkEntityVisibility(projectId);
    }

    @Override
    public Boolean checkEntityVisibility(Object object) {
        return false;
    }

}
