package tools.mtsuite.core.core.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.core.interceptor.acl.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class AclFactory {

    @Autowired
    private ProjectACL projectACL;

    @Autowired
    private TestSuiteACL testSuiteACL;

    @Autowired
    private FileACL fileACL;


    @Autowired
    private TestSuiteRunnerACL testSuiteRunnerACL;

    private HashMap<Class<? extends BaseAcl>, BaseAcl> acls = new HashMap<Class<? extends BaseAcl>, BaseAcl>();


    @PostConstruct
    public void init(){
           this.initAcl();
    }

    private void initAcl() {
            acls.put(ProjectACL.class, projectACL);
            acls.put(TestSuiteACL.class, testSuiteACL);
            acls.put(TestSuiteRunnerACL.class, testSuiteRunnerACL);
            acls.put(FileACL.class, fileACL);
    }

    public <T> T getAcl(Class<T> classObj){
        return (T) acls.get(classObj);
    }
}
