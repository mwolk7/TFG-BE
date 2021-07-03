package tools.mtsuite.core.common.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.UserProject;

import java.util.List;

public interface IUserProjectDao extends CrudRepository<UserProject, Long>, PagingAndSortingRepository<UserProject, Long> {

    @Query(
            value = "SELECT *\n" +
                    "FROM USER_PROJECTS\n" +
                    "WHERE USER_ID = ?2 AND PROJECT_ID = ?1 ;",
            nativeQuery = true)
    UserProject getUserProjectByUserAndProject(Long projectId, Long userId);

}
