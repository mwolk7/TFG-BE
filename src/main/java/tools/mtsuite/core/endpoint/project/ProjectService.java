package tools.mtsuite.core.endpoint.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.dao.IProjectDao;
import tools.mtsuite.core.common.dao.IProjectVersionDao;
import tools.mtsuite.core.common.dao.IUserDao;
import tools.mtsuite.core.common.dao.IUserProjectDao;
import tools.mtsuite.core.common.model.Project;
import tools.mtsuite.core.common.model.ProjectVersion;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.common.model.UserProject;
import tools.mtsuite.core.common.model.enums.Roles;
import tools.mtsuite.core.core.context.Mapeador;
import tools.mtsuite.core.core.dto.LoginUserDto;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.interceptor.acl.ProjectACL;
import tools.mtsuite.core.core.interceptor.visibility.Visibility;
import tools.mtsuite.core.core.service.CommonService;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;
import tools.mtsuite.core.endpoint.project.dto.*;
import tools.mtsuite.core.endpoint.project.model.ProjectList;


import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class ProjectService {

    @Autowired
    private IProjectDao projectDao;

    @Autowired
    private ProjectACL projectACL;

    @Autowired
    private Mapeador mapeador;

    @Autowired
    private IProjectVersionDao projectVersionDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IUserProjectDao iUserProjectDao;

    @Autowired
    private IUserDao iUserDao;
    /**
     * Get project by ID
     **/
    public ProjectDto getProject(Long id) {
        Project project = this.getProjectFromDB(id);

        ProjectDto projectDto = (ProjectDto) mapeador.map(project, ProjectDto.class);
        projectDto = addExtraDataToDto(projectDto, project);

        return projectDto;
    }
    public ProjectDto getMinProject(Long id) {
        Project project = this.getProjectFromDB(id);

        ProjectDto projectDto = (ProjectDto) mapeador.map(project, ProjectDto.class);
        projectDto = addExtraDataToDto(projectDto, project);

        return projectDto;
    }


    /**
     * Get user project by visibility cone
     * @param id
     * @return
     */
    public List<ProjectUserDto> getProjectAllUsers(Long id) {

        // Get project users
        List<ProjectUserDto> _projecActualUserDto = new ArrayList<>();
        _projecActualUserDto.addAll(getProjectUsers(id, true));

        // Add user from top project
        List<ProjectUserDto> _projecParentUserDto = new ArrayList<>();
        List<Project> parentProjects = projectDao.getParentProjects(id);

        for(Project project : parentProjects) {
            _projecParentUserDto.addAll(getProjectUsers(project.getId(), false));
        }

        List<ProjectUserDto> _projecUserDto = new ArrayList<>();

        // Busca si el usuario no esta en los aprents para saber si agregarlo o no.

        for(ProjectUserDto projectActualUser : _projecActualUserDto) {
            Boolean userFound = false;
            for(ProjectUserDto projectUser : _projecParentUserDto) {
                if(projectActualUser.getId() == projectUser.getId()) {
                    userFound = true;
                    break;
                }
            }
            if(!userFound) {
                _projecUserDto.add(projectActualUser);
            }
        }

        _projecUserDto.addAll(_projecParentUserDto);

        return _projecUserDto;
    }

    public List<ProjectUserDto> getProjectUsers(Long id, Boolean actualProject) {
        // Get project users
        Project project = this.getProjectFromDB(id);

        List<ProjectUserDto> _projectUserDto = new ArrayList<>();

        for(UserProject userProject: project.getUserProjects()) {
            ProjectUserDto projectUserDto = (ProjectUserDto) mapeador.map(userProject,ProjectUserDto.class);
            projectUserDto = new ProjectUserDto(userProject);
            projectUserDto.setActualUserProject(actualProject);
            _projectUserDto.add(projectUserDto);
        }
        return _projectUserDto;
    }


    /**
     * Get list of project
     **/
    public List<TableProjectDashboardDto> getProjects() {
        // Get current user
        User userDB = commonService.getCurrentUser();

        List<TableProjectDashboardDto> _projectsDto = new ArrayList();

        projectDao.getUserProjects(userDB.getId()).forEach(p -> {
            TableProjectDashboardDto tableProjectDashboardDto = (TableProjectDashboardDto) mapeador.map(p, TableProjectDashboardDto.class);
            tableProjectDashboardDto.setProjectPath(getProjectPath(p.getId()));
            _projectsDto.add(tableProjectDashboardDto);
        });

        _projectsDto.sort(Comparator.comparing(TableProjectDashboardDto::getLast_Modified_Date).reversed());
        return _projectsDto;
    }
    @Visibility(attr = "projectDto")
    public ProjectDto createProject(ProjectDto projectDto) {
        if(projectDto.getParentProjectId() != null) {
            projectACL.checkEntityVisibilityWithException(projectDto.getParentProjectId());
        }
               Project project = new Project();
        mapeador.map(projectDto, project);

        if(projectDto.getTag() == null)
            project.setTag(project.getName());
        // IF HAS PARENT, ADD PARENT PROJECT, ELSE ADD USER RELATION AS OWNER
        if(projectDto.getParentProjectId() != null) {
            Project projectParent = this.getProjectFromDB(projectDto.getParentProjectId());
            project.setParentProject(projectParent);
        }
        else
        {
            //TODO: add restriction: if user has parent with role, NOT ADD is a UI error.

            // Get current user
            User userDB = commonService.getCurrentUser();
            UserProject userProject = new UserProject();
            userProject.setProject(project);
            project.getUserProjects().add(userProject);
            userProject.setUser(userDB);
            userDB.getUserProject().add(userProject);

            userProject.setRol(Roles.Owner);

            iUserDao.save(userDB);
            iUserProjectDao.save(userProject);
        }

        projectDao.save(project);
        ProjectDto projectDtoRsp = (ProjectDto) mapeador.map(project, ProjectDto.class);
        projectDtoRsp = addExtraDataToDto(projectDtoRsp, project);
        return projectDtoRsp;

    }

    public ProjectDto updateProject(ProjectDto projectDto) {
        projectACL.checkEntityVisibilityWithException(projectDto.getId());

        Project project = this.getProjectFromDB(projectDto.getId());

        if(projectDto.getName() != null)
            project.setName(projectDto.getName());

        if(projectDto.getTag() != null)
            project.setTag(projectDto.getTag());
      /*  mapeador.map(projectDto, project);

        if(projectDto.getParentProjectId() != null) {
            Project projectParent = this.getProjectFromDB(projectDto.getId());
            project.setParentProject(projectParent);
        }*/

        projectDao.save(project);
        projectDto = addExtraDataToDto(projectDto, project);
        return projectDto;
    }

    public Boolean deleteProject(Long id) {
        try {
            projectDao.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new BadRequestException("500", "Error deleting project [" + id + "].");
        }
    }

    /********
     * Private functions ***
     *********/

    /**
     * Get project from the database, check if existe. If not throw an exception
     **/
    private Project getProjectFromDB(Long id) {
        if (!projectDao.existsById(id))
            throw new BadRequestException("404", "Project not found");
        return projectDao.findById(id).get();
    }

    private ProjectDto addExtraDataToDto(ProjectDto projectDto, Project project) {
        projectDto.setChildProjectsCount(project.getChildProjects().size());

        // Child projects
        List<ProjectDto> _childProjectsDto = new ArrayList<>();
        for(Project childProject : project.getChildProjects()) {
                _childProjectsDto.add( (ProjectDto) mapeador.map(childProject, ProjectDto.class) );
        }

        projectDto.setChildNestedProjects(_childProjectsDto);

        if(project.getParentProject() != null) {
            projectDto.setParentProjectId(project.getParentProject().getId());
            projectDto.setParentProjectName(project.getParentProject().getTag());
        }

        List<Project> _parentProjects = projectDao.getParentProjects(project.getId());
        List<ProjectDto> _parentProjectsDto = new ArrayList<>();

        for(Project parentProject : _parentProjects) {
                 _parentProjectsDto.add( (ProjectDto) mapeador.map(parentProject, ProjectDto.class) );
        }

        projectDto.setParentProjects(_parentProjectsDto);

        projectDto.setProjectPath( getProjectPath(projectDto.getId()) );

        return projectDto;
    }

    private String getProjectPath(Long projectId) {
        List<Project> _parentProjects = projectDao.getParentProjects(projectId);

        // Size 0 y 1
        if(_parentProjects.size() == 0) {
            System.out.println("SIZE 0 PARENT PROJECT");
            return "/";
        }

        if(_parentProjects.size() == 1) {
            return "/";
        }

        if(_parentProjects.size() == 2) {
            return _parentProjects.get(0).getName();
        }

        // Size
        if(_parentProjects.size() == 3) {
            return _parentProjects.get(0).getName() + "/" + _parentProjects.get(1).getName();
        }

        if(_parentProjects.size()>3) {
            return _parentProjects.get(0).getName() + "/..." + "/" + _parentProjects.get( _parentProjects.size()-2 ).getName();
        }

        return "-";

    }



    /* PROJECT VERSION */
    public List<ProjectVersionDto> getProjectVersions(Long projectId) {

        List<ProjectVersionDto> _projectVersionDto = new ArrayList<>();

        Optional<Project> projectOpt = projectDao.findById(projectId);

        if(projectOpt.isEmpty()) {
            throw new BadRequestException("001", "Project not found");
        }

        Project project = projectOpt.get();

        project.getProjectVersions().forEach(p -> {
            ProjectVersionDto projectVersionDto = (ProjectVersionDto) mapeador.map(p, ProjectVersionDto.class);
            _projectVersionDto.add(projectVersionDto);
        });

        _projectVersionDto.sort( Comparator.comparing(ProjectVersionDto::getLastModifiedDate).reversed());

        return _projectVersionDto;
    }

    public ProjectVersionDto createProjectVersion(Long projectId,ProjectVersionDto projectDto) {
        ProjectVersion projectVersion = new ProjectVersion();

        Optional<Project> projectOpt = projectDao.findById(projectId);

        if(projectOpt.isEmpty()) {
            throw new BadRequestException("001", "Project not found");
        }

        mapeador.map(projectDto, projectVersion);

        projectVersion.setProject(projectOpt.get());

        projectVersionDao.save(projectVersion);


        return (ProjectVersionDto) mapeador.map(projectVersion, ProjectVersionDto.class);
    }

    public ProjectVersionDto updateProjectVersion(Long id,ProjectVersionDto projectDto) {
        Optional<ProjectVersion> projectVersionOpt = projectVersionDao.findById(id);
        if(projectVersionOpt.isEmpty()) {
            throw new BadRequestException("001", "Projet version not found");
        }

        ProjectVersion projectVersion = projectVersionOpt.get();

        mapeador.map(projectDto, projectVersion);
        projectVersionDao.save(projectVersion);

        return (ProjectVersionDto) mapeador.map(projectVersion, ProjectVersionDto.class);
    }

    public Boolean deleteProjectVersion(Long id) {

        if (projectVersionDao.getTestSuiteRunnerCountByProjectVersion(id) > 0) {
            throw new BadRequestException("500", "Error deleting projectVersion [" + id + "].");
        }

        projectVersionDao.deleteById(id);
        return true;
    }


    /* ---- PROJECT USER ----*/

    public List<UserDto> searchUser(String input) {

        List<UserDto> _userDto = new ArrayList<>();

        String search = input+'%';

        iUserDao.searchUser(search).forEach(u -> {
            UserDto userDto = (UserDto) mapeador.map(u, UserDto.class);
            _userDto.add(userDto);
        });

        return _userDto;
    }

    public UserProjectDto createUserProject(UserProjectDto userProjectDto) {

        // Check if exist
        UserProject userCheckProject = iUserProjectDao.getUserProjectByUserAndProject(userProjectDto.getProjectId(), userProjectDto.getUserId());

        if(userCheckProject != null) {
            throw new BadRequestException("001", "User Project relation found");
        }

        Optional<Project> project = projectDao.findById(userProjectDto.getProjectId());

        if(project.isEmpty()) {
            throw new BadRequestException("001", "Project not found");
        }

        Optional<User> user = iUserDao.findById(userProjectDto.getUserId());

        if(user.isEmpty()) {
            throw new BadRequestException("001", "User not found");
        }

        UserProject userProject = new UserProject();
        userProject.setRol(userProjectDto.getRol());
        userProject.setProject(project.get());
        userProject.setUser(user.get());

        iUserProjectDao.save(userProject);

        return (UserProjectDto) mapeador.map(userProject, UserProjectDto.class);
    }

    public UserProjectDto updateUserProject(UserProjectDto userProjectDto) {

        Optional<UserProject> userProjectOpt = iUserProjectDao.findById(userProjectDto.getId());

        if(userProjectOpt.isEmpty()) {
            throw new BadRequestException("001", "Relation not found not found");
        }

        UserProject userProject = userProjectOpt.get();

        mapeador.map(userProjectDto, userProject);
        iUserProjectDao.save(userProject);

        return (UserProjectDto) mapeador.map(userProject, UserProjectDto.class);
    }

    public Boolean deleteUserProject(Long id) {
        try {
            iUserProjectDao.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new BadRequestException("500", "Error deleting projectVersion [" + id + "].");
        }
    }



}
