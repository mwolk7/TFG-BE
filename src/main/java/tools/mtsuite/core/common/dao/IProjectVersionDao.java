package tools.mtsuite.core.common.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.ProjectVersion;
import tools.mtsuite.core.common.model.TestSuiteRunner;

import java.util.List;

public interface IProjectVersionDao extends CrudRepository<ProjectVersion, Long>, PagingAndSortingRepository<ProjectVersion, Long> {

    @Query(
            value = "SELECT COUNT(*)\n" +
                    "FROM PROJECT_VERSIONS AS PV \n" +
                    "INNER JOIN TEST_SUITE_RUNNERS TSR ON PV.ID = TSR.PROJECT_VERSION_ID\n" +
                    "WHERE PV.ID = :projectVersion\n" +
                    ";",
            nativeQuery = true)
    Integer getTestSuiteRunnerCountByProjectVersion(Long projectVersion);

}
