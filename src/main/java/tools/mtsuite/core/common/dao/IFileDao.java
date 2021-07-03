package tools.mtsuite.core.common.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.File;
import tools.mtsuite.core.common.model.Project;

import java.util.List;

public interface IFileDao  extends CrudRepository<File, Long>, PagingAndSortingRepository<File, Long>{
    Page<File> findByProject(Project project, Pageable pageable);
}


