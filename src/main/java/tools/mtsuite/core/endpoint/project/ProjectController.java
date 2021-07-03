package tools.mtsuite.core.endpoint.project;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.ProjectVersion;
import tools.mtsuite.core.core.interceptor.acl.ProjectACL;
import tools.mtsuite.core.core.interceptor.visibility.Visibility;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;
import tools.mtsuite.core.endpoint.project.dto.*;

import java.util.List;

@RestController
@Tag(name = "project")
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = ProjectACL.class,injectable = "injectableFilter")
    public ProjectDto getProject(@PathVariable("id") Long id,
                                 @RequestAttribute(value = "injectableFilter", required = false) Boolean injectableFilter) {
        ProjectDto projectDto = projectService.getProject(id);
        projectDto.setReadOnly(Boolean.TRUE.equals(injectableFilter)? true :false);
        return projectDto;
    }

    /**
     * Return user projects (Project by actual user)
     * @return
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TableProjectDashboardDto> getProjects() {
        return projectService.getProjects();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto createOrUpdateProject(@RequestBody ProjectDto projectDto) {
        if (projectDto.getId() == null) {
            return projectService.createProject(projectDto);
        } else {
            return projectService.updateProject(projectDto);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = ProjectACL.class)
    public Boolean deleteProject(@PathVariable("id") Long id) {
        return projectService.deleteProject(id);
    }

    /**
     * Return All project users  (Users by project)
     * @param id
     * @return
     */
    @GetMapping("/{id}/AllUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectUserDto> getProjectAllUsers(@PathVariable("id") Long id) {
        return projectService.getProjectAllUsers(id);
    }



    /* PROJECT VERSION */

    @GetMapping("/{id}/versions")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = ProjectACL.class)
    public List<ProjectVersionDto> getProjectVersion(@PathVariable("id") Long id) {
        return projectService.getProjectVersions(id);
    }

    @PostMapping("/{id}/version")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = ProjectACL.class)
    public ProjectVersionDto createOrUpdateProjectVersion(@PathVariable("id") Long id,
                                                          @RequestBody ProjectVersionDto projectVersionDto) {
        if (projectVersionDto.getId() == null) {
            return projectService.createProjectVersion(id,projectVersionDto);
        } else {
            return projectService.updateProjectVersion(id,projectVersionDto);
        }
    }

    @DeleteMapping("/{id}/version/{versionId}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = ProjectACL.class)
    public Boolean deleteProjectVersion(@PathVariable("id") Long id, @PathVariable("versionId") Long versionId) {
        return projectService.deleteProjectVersion(versionId);
    }


    /* ---- PROJECT USER ----*/

    /**
     * Search users
     * @param search
     * @return
     */
    @GetMapping("/systemsUsers/{search}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> searchByUser(@PathVariable("search") String search) {
        return projectService.searchUser(search);
    }

    /**
     * Add user to project
     * @param id
     * @param userProjectDto
     * @return
     */
    @PostMapping("/{id}/users")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = ProjectACL.class)
    public UserProjectDto addUserToProject(@PathVariable("id") Long id, @RequestBody UserProjectDto userProjectDto) {
        if (userProjectDto.getId() == null) {
            return projectService.createUserProject(userProjectDto);
        } else {
            return projectService.updateUserProject(userProjectDto);
        }
    }

    /**
     * Delete user to project
     * @param id
     * @param usersId
     * @return
     */
    @DeleteMapping("/{id}/users/{usersId}")
    @ResponseStatus(HttpStatus.OK)
    @Visibility(attr = "id",acl = ProjectACL.class)
    public Boolean deleteUserFromProject(@PathVariable("id") Long id, @PathVariable("usersId") Long usersId) {
        return projectService.deleteUserProject(usersId);
    }


}
