package tools.mtsuite.core.core.interceptor.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.mtsuite.core.common.dao.IFileDao;
import tools.mtsuite.core.common.dao.IProjectDao;
import tools.mtsuite.core.common.model.File;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.service.CommonService;

@Component
public class FileACL extends  BaseAcl{

    @Autowired
    private CommonService commonService;

    @Autowired
    private ProjectACL projectACL;

    @Autowired
    private IFileDao fileDao;

    @Override
    public Boolean checkEntityVisibility(Long id) {
        if (!fileDao.existsById(id))
            throw new BadRequestException("300", "File not allowed.");
        File file = fileDao.findById(id).get();
        return projectACL.checkEntityVisibility(file.getProject().getId());
    }

    @Override
    public Boolean checkEntityVisibility(Object object) {
        return false;
    }

}
