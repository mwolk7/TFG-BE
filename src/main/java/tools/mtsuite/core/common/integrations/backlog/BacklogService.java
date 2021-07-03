package tools.mtsuite.core.common.integrations.backlog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.integrations.BugReporterType;
import tools.mtsuite.core.common.integrations.IBugReporter;
import tools.mtsuite.core.common.integrations.backlog.dto.*;
import tools.mtsuite.core.common.model.integrations.BacklogCredential;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.service.HttpClientService;
import tools.mtsuite.core.core.utils.JsonParserUtil;
import tools.mtsuite.core.endpoint.files.FileService;
import tools.mtsuite.core.endpoint.files.dto.FileDto;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterProjectDto;
import tools.mtsuite.core.endpoint.integrations.dto.ElementListDto;
import tools.mtsuite.core.endpoint.integrations.dto.backlog.BackLogInputDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BacklogService implements IBugReporter {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private BacklogUrlBuilder backlogUrlBuilder;

    @Autowired
    private FileService fileService;

    @Override
    public BugReporterType getType() {
        return BugReporterType.Backlog;
    }


    @Override
    public List<BugReporterProjectDto> getProjects(BugReporterCredential bugReporterCredential) {
        if(!(bugReporterCredential instanceof BacklogCredential))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"projects");
        ResponseEntity<Project[]> response = httpClientService.get(endpoint, Project[].class);

        List<BugReporterProjectDto> bugReporterProjectDtoList = new ArrayList();
        Arrays.asList(response.getBody()).forEach(project -> {
            BugReporterProjectDto bugReporterProjectDto = new BugReporterProjectDto();
            bugReporterProjectDto.setId(project.getId());
            bugReporterProjectDto.setName(project.getName());
            bugReporterProjectDto.setProjectKey(project.getProjectKey());
            bugReporterProjectDtoList.add(bugReporterProjectDto);
        });
        return bugReporterProjectDtoList;
    }

    @Override
    public List<ElementListDto> getIssuesType(BugReporterCredential bugReporterCredential,String projectId) {
        if(!(bugReporterCredential instanceof BacklogCredential))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"projects/"+projectId+"/issueTypes");
        ResponseEntity<IssueType[]> response = httpClientService.get(endpoint, IssueType[].class);

        List<ElementListDto> elementsListDto = new ArrayList();
        Arrays.asList(response.getBody()).forEach(issueType -> {
            ElementListDto elementListDto = new ElementListDto();
            elementListDto.setId(issueType.getId());
            elementListDto.setDescription(issueType.getName());
            elementListDto.setColor(issueType.getColor());
            elementsListDto.add(elementListDto);

        });
        return elementsListDto;
    }

    @Override
    public List<ElementListDto> getPriorities(BugReporterCredential bugReporterCredential) {
        if(!(bugReporterCredential instanceof BacklogCredential))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"priorities");
        ResponseEntity<Priority[]> response = httpClientService.get(endpoint, Priority[].class);

        List<ElementListDto> elementsListDto = new ArrayList();

        Arrays.asList(response.getBody()).forEach(priority -> {
            ElementListDto elementListDto = new ElementListDto();
            elementListDto.setId(priority.getId());
            elementListDto.setDescription(priority.getName());
            elementsListDto.add(elementListDto);

        });
        return elementsListDto;
    }

    @Override
    public List<ElementListDto> getMilestones(BugReporterCredential bugReporterCredential,String projectId)
    {
        if(!(bugReporterCredential instanceof BacklogCredential))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"projects/"+projectId+"/versions");
        ResponseEntity<Milestone[]> response = httpClientService.get(endpoint, Milestone[].class);

        List<ElementListDto> elementsListDto = new ArrayList();

        Arrays.asList(response.getBody()).forEach(milestone -> {
            ElementListDto elementListDto = new ElementListDto();
            elementListDto.setId(milestone.getId());
            elementListDto.setDescription(milestone.getName());
            elementListDto.setElementKey(milestone.getDescription());
            elementsListDto.add(elementListDto);

        });
        return elementsListDto;
    }

    @Override
    public List<ElementListDto> getUsersProject(BugReporterCredential bugReporterCredential,String projectId) {
        if(!(bugReporterCredential instanceof BacklogCredential))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"projects/"+projectId+"/users");
        ResponseEntity<User[]> response = httpClientService.get(endpoint, User[].class);

        List<ElementListDto> elementsListDto = new ArrayList();

        Arrays.asList(response.getBody()).forEach(user -> {
            ElementListDto elementListDto = new ElementListDto();
            elementListDto.setId(user.getId());
            elementListDto.setDescription(user.getName());
            elementListDto.setElementKey(user.getUserId());
            elementsListDto.add(elementListDto);

        });
        return elementsListDto;

    }

    @Override
    public ElementListDto getUserMySelf(BugReporterCredential bugReporterCredential) {
        if(!(bugReporterCredential instanceof BacklogCredential))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"users/myself");
        ResponseEntity<User> response = httpClientService.get(endpoint, User.class);

        ElementListDto elementListDto = new ElementListDto();
        elementListDto.setId(response.getBody().getId());
        elementListDto.setDescription(response.getBody().getName());
        elementListDto.setElementKey(response.getBody().getUserId());

        return elementListDto;

    }

    @Override
    public List<ElementListDto> getCategories(BugReporterCredential bugReporterCredential, String projectId) {
        if(!(bugReporterCredential instanceof BacklogCredential))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"projects/"+projectId+"/categories");
        ResponseEntity<Category[]> response = httpClientService.get(endpoint, Category[].class);

        List<ElementListDto> elementsListDto = new ArrayList();

        Arrays.asList(response.getBody()).forEach(category -> {
            ElementListDto elementListDto = new ElementListDto();
            elementListDto.setId(category.getId());
            elementListDto.setDescription(category.getName());
            elementListDto.setElementKey(category.getDisplayOrder());
            elementsListDto.add(elementListDto);

        });
        return elementsListDto;
    }

    @Override
    public List<ElementListDto> getVersions(BugReporterCredential bugReporterCredential,String projectId) {
        return this.getMilestones(bugReporterCredential,projectId);
    }

    @Override
    public String reportBug(BugReporterCredential bugReporterCredential,BugReporterInputDto bugInput) {
        if(!(bugInput instanceof BackLogInputDto))
            throw new BadRequestException("500"," The parameter received is not the type needed");

        BackLogInputDto backLogInputDto = (BackLogInputDto) bugInput;

        if(backLogInputDto.getAttachmentId() != null && backLogInputDto.getAttachmentId().length != 0) {
            List<Integer> remoteAttachmentsId = new ArrayList();
            for (Integer localAttachmentId : backLogInputDto.getAttachmentId()) {
                remoteAttachmentsId.add(uploadAttachment(bugReporterCredential,localAttachmentId));
            }
            Integer[] remoteAttachmentsIdArray = new Integer[remoteAttachmentsId.size()];
            backLogInputDto.setAttachmentId(remoteAttachmentsId.toArray(remoteAttachmentsIdArray));
        }

        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"issues");

        ResponseEntity<Object> response = httpClientService.post(endpoint,JsonParserUtil.objectToJson(bugInput), Object.class);

        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            return JsonParserUtil.objectToJson(response.getBody());
        }else{
            throw new BadRequestException("500","Error creating bug in backlog integration");
        }
    }

    public Integer uploadAttachment(BugReporterCredential bugReporterCredential,Integer localAttachmentId){
        FileDto fileDto = fileService.getFileObject(Long.valueOf(localAttachmentId));
        String endpoint = backlogUrlBuilder.generateUrl((BacklogCredential)bugReporterCredential,"space/attachment");
        ResponseEntity<Attachment> response = httpClientService.postUploadAttachment(endpoint,fileDto, Attachment.class);
        return response.getBody().getId();
    }
}
