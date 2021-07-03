package tools.mtsuite.core.core.interceptor.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.mtsuite.core.common.dao.IProjectDao;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.service.CommonService;

@Component
public class ProjectACL extends  BaseAcl{

    @Autowired
    private CommonService commonService;

    @Autowired
    private IProjectDao projectDao;

    @Override
    public Boolean checkEntityVisibility(Long id) {
        return projectDao.hasAccess(id,commonService.getCurrentUser().getId()) != null ? true :false;
    }

    public Boolean checkEntityVisibilityWithException(Long id) {
        Boolean bool = checkEntityVisibility(id);
        if (bool)
            return true;
        throw new BadRequestException("500","Operation not allowed. You don't have access to the project ["+id+"]");
    }

    @Override
    public Boolean checkEntityVisibility(Object object) {
        return false;
    }

}
