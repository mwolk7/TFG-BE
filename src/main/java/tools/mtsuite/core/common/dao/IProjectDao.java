package tools.mtsuite.core.common.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.endpoint.project.dto.ProjectDto;
import tools.mtsuite.core.endpoint.project.model.ProjectList;

import java.util.List;

public interface IProjectDao extends CrudRepository<Project, Long>, PagingAndSortingRepository<Project, Long> {
    @Query(
            value = "WITH RECURSIVE tree  AS ( \n" +
                    "    SELECT * \n" +
                    "    FROM PROJECTS \n" +
                    "    WHERE ID = ?1 \n" +
                    "\n" +
                    "    UNION ALL \n" +
                    "\n" +
                    "    SELECT c.* FROM PROJECTS c \n" +
                    "    JOIN tree t ON t.PARENT_PROJECT_ID = c.ID \n" +
                    ") \n" +
                    "SELECT * FROM tree ORDER BY ID ASC;",
            nativeQuery = true)
    List<Project> getParentProjects(Long projectId);


    @Query(
            value = "/* Recursive DESC */\n" +
                    "    with recursive cte as (\n" +
                    "            select     *\n" +
                    "            from       PROJECTS\n" +
                    "            where      ID = ?1\n" +
                    "            union all\n" +
                    "            select     p.*\n" +
                    "            from       PROJECTS p\n" +
                    "            inner join cte\n" +
                    "            on p.PARENT_PROJECT_ID = cte.ID\n" +
                    "    )\n" +
                    "    select * from cte ORDER BY ID ASC;",
            nativeQuery = true)
    List<Project> getChildProjects(Long projectId);


    /* GET USER ROLE BY PROJECT */
    @Query(
            value = "/* GET USER ROLE BY PROYECT, RECURSIVE ASC */\n" +
                    "WITH RECURSIVE tree AS (\n" +
                    "    SELECT p.*, us.`ROLE`\n" +
                    "    FROM PROJECTS p\n" +
                    "    left join USER_PROJECTS us on us.PROJECT_ID = p.ID AND us.USER_ID = :userId\n" +
                    "    WHERE p.ID = :projectId\n" +
                    "\n" +
                    "    UNION ALL\n" +
                    "\n" +
                    "    SELECT c.*, sus.`ROLE`\n" +
                    "    FROM PROJECTS c\n" +
                    "    JOIN tree t ON t.PARENT_PROJECT_ID = c.ID\n" +
                    "    left join USER_PROJECTS sus on sus.PROJECT_ID = c.ID AND sus.USER_ID = :userId\n" +
                    "\n" +
                    ")\n" +
                    "SELECT ROLE FROM tree WHERE ROLE IS NOT NULL ORDER BY ID ASC;",
            nativeQuery = true)
    String getUserRoleByProjectAndUser(Long userId, Long projectId);




    /************************************************/
    /*                     WARNING                  */
    /*               TOO MUCH MAGIC HERE            */
    /*                 WEAR SUNGLASSES              */
    /************************************************/

    /* GET USER PROJECT */
    @Query(
            value = "/* GET USER ROLE BY PROJECT TRY GET ALL */\n" +
                    "/* CTE PART, DESC RECURSIVE */\n" +
                    "with recursive cte as (\n" +
                    "  select     *\n" +
                    "  from       PROJECTS pc\n" +
                    "  where      pc.ID IN (\n" +
                    "  SELECT pF.ID\n" +
                    "  FROM USER_PROJECTS upF\n" +
                    "  LEFT JOIN PROJECTS pF ON pF.ID = upF.PROJECT_ID\n" +
                    "  WHERE upF.USER_ID = :userId\n" +
                    "  )\n" +
                    "  union all\n" +
                    "  select     p.*\n" +
                    "  from       PROJECTS p\n" +
                    "  inner join cte\n" +
                    "          on cte.ID = p.PARENT_PROJECT_ID\n" +
                    ")\n" +
                    "/* FILTER CTE */\n" +
                    "select *,\n" +
                    "/* ADD ROLE TO RESULT FROM CTE */\n" +
                    "  (\n" +
                    "/* ASC recursive */\n" +
                    "    WITH RECURSIVE tree AS (\n" +
                    "        SELECT ps.*, us.`ROLE`\n" +
                    "        FROM PROJECTS ps\n" +
                    "       left join USER_PROJECTS us on us.PROJECT_ID = ps.ID AND us.USER_ID = :userId\n" +
                    "       WHERE ps.ID = cte.ID /* USE CTE PROJECT ID */\n" +
                    "\n" +
                    "    UNION ALL\n" +
                    "\n" +
                    "    SELECT c.*, sus.`ROLE`\n" +
                    "    FROM PROJECTS c\n" +
                    "    JOIN tree t ON t.PARENT_PROJECT_ID = c.ID\n" +
                    "    left join USER_PROJECTS sus on sus.PROJECT_ID = c.ID AND sus.USER_ID = :userId\n" +
                    "\n" +
                    ")\n" +
                    "SELECT ROLE FROM tree WHERE ROLE IS NOT NULL ORDER BY ID ASC\n" +
                    ") ACTUAL_USER_ROLE\n" +
                    "from cte  \n" +
                    "ORDER BY LAST_MODIFIED_DATE DESC;",
            nativeQuery = true)
    List<ProjectList> getUserProjects(Long userId);




    /* GET IF THE USER HAS VISIBILITY IN THE GIVEN PROJECT */
    @Query(value = "    WITH RECURSIVE tree  AS (\n" +
                    "            SELECT *\n" +
                    "            FROM PROJECTS\n" +
                    "            WHERE ID = :projectId\n" +
                    "\n" +
                    "            UNION ALL\n" +
                    "\n" +
                    "            SELECT c.* FROM PROJECTS c\n" +
                    "            INNER JOIN tree t ON t.PARENT_PROJECT_ID = c.ID)\n" +
                    "\n" +
                    "    SELECT IFNULL(tree.ID,0) FROM tree INNER JOIN USER_PROJECTS UP\n" +
                    "    ON tree.ID = UP.PROJECT_ID\n" +
                    "    WHERE UP.USER_ID = :userId\n" +
                    "    ORDER BY tree.ID ASC;",
            nativeQuery = true)
    Integer hasAccess(Long projectId,Long userId);

}
