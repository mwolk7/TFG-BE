package tools.mtsuite.core.common.dao;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.TestSuite;

import java.util.List;

public interface ITestSuiteDao extends CrudRepository<TestSuite, Long>, PagingAndSortingRepository<TestSuite, Long> {

}
