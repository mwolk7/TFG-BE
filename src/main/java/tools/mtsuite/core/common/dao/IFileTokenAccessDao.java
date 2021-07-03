package tools.mtsuite.core.common.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.FileTokenAccess;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.User;

import java.util.List;

public interface IFileTokenAccessDao extends CrudRepository<FileTokenAccess, Long>, PagingAndSortingRepository<FileTokenAccess, Long> {
    List<FileTokenAccess> findByProjectAndUser(Project project, User user);
}
