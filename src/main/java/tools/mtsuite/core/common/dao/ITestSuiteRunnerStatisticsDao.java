package tools.mtsuite.core.common.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.TestSuiteRunnerStatistics;

public interface ITestSuiteRunnerStatisticsDao extends CrudRepository<TestSuiteRunnerStatistics, Long>, PagingAndSortingRepository<TestSuiteRunnerStatistics, Long> {

}
