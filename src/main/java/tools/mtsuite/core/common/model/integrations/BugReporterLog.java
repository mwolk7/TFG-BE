package tools.mtsuite.core.common.model.integrations;

import org.hibernate.annotations.Cascade;
import tools.mtsuite.core.common.model.GenericObject;
import tools.mtsuite.core.common.model.TestSuiteRunner;
import tools.mtsuite.core.common.model.User;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;

@Entity
@Table(name = AppConstants.BUG_REPORTER_LOG)
public class BugReporterLog extends GenericObject {
    /****************
     * Relationships**
     *****************/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUG_REPORTER_CREDENTIAL_ID")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private BugReporterCredential bugReporterCredential;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_SUITE_RUNNER_ID")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TestSuiteRunner testSuiteRunner;

    /****************
     * Attributes **
     *****************/

    @Column(name = "BUG_REPORTER_PROJECT")
    private String bugReporterProject;


    @Column(name = "REQUEST_BODY")
    @Lob
    private String requestBody;

    @Column(name = "ISSUE")
    @Lob
    private String issue;

    /*****************
     * Functions *****
     *****************/


    /*****************
     * Getter & Setters**
     *****************/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BugReporterCredential getBugReporterCredential() {
        return bugReporterCredential;
    }

    public void setBugReporterCredential(BugReporterCredential bugReporterCredential) {
        this.bugReporterCredential = bugReporterCredential;
    }

    public TestSuiteRunner getTestSuiteRunner() {
        return testSuiteRunner;
    }

    public void setTestSuiteRunner(TestSuiteRunner testSuiteRunner) {
        this.testSuiteRunner = testSuiteRunner;
    }

    public String getBugReporterProject() {
        return bugReporterProject;
    }

    public void setBugReporterProject(String bugReporterProject) {
        this.bugReporterProject = bugReporterProject;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
