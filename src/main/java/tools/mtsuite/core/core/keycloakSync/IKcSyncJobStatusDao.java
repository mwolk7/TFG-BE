package tools.mtsuite.core.core.keycloakSync;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface IKcSyncJobStatusDao extends CrudRepository<KcSyncJobStatus, Long>, PagingAndSortingRepository<KcSyncJobStatus, Long> {
    public KcSyncJobStatus findByJobName(String name);
}
