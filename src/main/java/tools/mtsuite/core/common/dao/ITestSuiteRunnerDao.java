package tools.mtsuite.core.common.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.TestSuite;
import tools.mtsuite.core.common.model.TestSuiteRunner;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import java.util.List;

public interface ITestSuiteRunnerDao extends CrudRepository<TestSuiteRunner, Long>, PagingAndSortingRepository<TestSuiteRunner, Long> {

    @Query(
            value = "select if(USER_ID is NULL, false, true)\n" +
                    "from USERS_TEST_SUITE_RUNNERS \n" +
                    "where USER_ID = :userId \n" +
                    "and TEST_SUIT_RUNNER_ID = :projectId \n" +
                    ";",
            nativeQuery = true)
    Long getUserTestSuitRunnerByUserIdAndTestSuitRunnerId(Long userId, Long projectId);

    Page<TestSuiteRunner> findByTestSuiteAndStatusIn(TestSuite testSuite, List<TestSuiteStatus> filters, Pageable pageable);

    Page<TestSuiteRunner> findByTestSuite(TestSuite testSuite, Pageable pageable);

}
