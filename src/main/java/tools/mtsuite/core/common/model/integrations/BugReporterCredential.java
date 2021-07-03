package tools.mtsuite.core.common.model.integrations;


import tools.mtsuite.core.common.integrations.BugReporterType;
import tools.mtsuite.core.common.model.GenericObject;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.core.utils.AppConstants;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterCredentialDto;

import javax.persistence.*;


@Entity
@Table(name = AppConstants.BUG_REPORTER_CREDENTIALS)
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class BugReporterCredential extends GenericObject {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "NAME")
    private String name;


    public abstract BugReporterCredentialDto getDto();
    public abstract void update(BugReporterCredentialDto bugReporterCredentialDto);
    public abstract BugReporterType getType();


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
