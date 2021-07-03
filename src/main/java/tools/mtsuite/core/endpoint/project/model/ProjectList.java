package tools.mtsuite.core.endpoint.project.model;

import tools.mtsuite.core.common.model.GenericObject;

import java.util.Date;

public interface ProjectList  {
    Long getId();
    Date getCreated_Date();
    Date getLast_Modified_Date();
    Long getVersion();
    String getDescription();
    String getName();
    String getParent_Project_Id();
    String getActual_User_Role();
}
