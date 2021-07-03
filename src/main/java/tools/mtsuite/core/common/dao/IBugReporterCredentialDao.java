package tools.mtsuite.core.common.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;

import java.util.List;

public interface IBugReporterCredentialDao extends CrudRepository<BugReporterCredential, Long>, PagingAndSortingRepository<BugReporterCredential, Long> {
    List<BugReporterCredential> findByUser(User user);
}
