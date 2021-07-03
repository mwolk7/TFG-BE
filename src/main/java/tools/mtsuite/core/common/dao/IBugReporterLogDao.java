package tools.mtsuite.core.common.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.TestSuiteRunner;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.common.model.integrations.BugReporterLog;

public interface IBugReporterLogDao extends CrudRepository<BugReporterLog, Long>, PagingAndSortingRepository<BugReporterLog, Long> {
    BugReporterLog findFirstByTestSuiteRunnerAndUserOrderByCreatedDateDesc(TestSuiteRunner testSuiteRunner, User user);

}
