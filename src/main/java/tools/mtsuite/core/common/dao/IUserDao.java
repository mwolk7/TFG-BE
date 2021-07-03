package tools.mtsuite.core.common.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.User;

import java.util.List;

public interface IUserDao extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);

    @Query(
            value = "SELECT *\n" +
                    "        FROM USERS\n" +
                    "        WHERE name LIKE :search\n" +
                    "        OR EMAIL LIKE :search\n" +
                    "        OR USERNAME LIKE :search\n" +
                    "        LIMIT 10;",
            nativeQuery = true)
    List<User> searchUser(String search);


    @Query(value = "SELECT U.ID FROM USERS U WHERE U.USERNAME != 'api-user-not-bug';", nativeQuery = true)
    List<Long> getUsersIdList();


    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET ENABLE = FALSE WHERE ID IN :ids",nativeQuery = true)
    Integer softDeleteAllUser(List<Long> ids);


}
