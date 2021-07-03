package tools.mtsuite.core.common.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.common.model.integrations.BugReporterCredential;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.core.utils.AppConstants;
import tools.mtsuite.core.endpoint.profile.dto.UserDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = AppConstants.USERS)
public class User extends GenericObject {
	/****************
	 * Relationships**
	 *****************/
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	private List<UserProject> userProject = new ArrayList();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	private List<FileTokenAccess> fileTokenAccesses = new ArrayList();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	private List<BugReporterCredential> bugReporterCredentials = new ArrayList();


	@OneToMany(mappedBy = "creatorUser", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	private List<TestSuiteRunner> testSuitesRunnerCreated = new ArrayList();


	@OneToMany(mappedBy = "creatorUser", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	private List<TestSuite> testSuitesCreated = new ArrayList();

	@ManyToMany(mappedBy = "users")
	private List<TestSuiteRunner> testSuiteRunners = new ArrayList();


	/****************
	 * Attributes **
	 *****************/
	@Column(name = "NAME")
	private String name;

	@Column(name = "SURNAME")
	private String surname;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	@Email
	private String email;

	@Column(name = "ENABLE")
	private Boolean enable = true;

	@Column(name = "EMAIL_VERIFIED")
	private Boolean emailVerified = false;

	@Column(name = "KEYCLOAK_KEY")
	private String keycloakKey;


	/****************
	 * constructors **
	 *****************/

	public User() {}

	public User(UserDto userDto) {
		this.enable = true;
		this.emailVerified = false;

		if(userDto.getUsername() == null || userDto.getUsername().isEmpty())
			throw new BadRequestException("500","The username is mandatory.");

		if(userDto.getName() == null || userDto.getName().isEmpty())
			throw new BadRequestException("500","The name is mandatory.");

		if(userDto.getPassword() == null || userDto.getPassword().isEmpty())
			throw new BadRequestException("500","The password is mandatory.");

		if(userDto.getEmail() == null || userDto.getEmail().isEmpty())
			throw new BadRequestException("500","The email is mandatory.");


		this.username = userDto.getUsername();
		this.updateUser(userDto);
	}

	public void updateUser(UserDto userDto){
		if(userDto.getName() == null && !userDto.getName().isEmpty())
			this.name = userDto.getName();

		if(userDto.getSurname() == null && !userDto.getSurname().isEmpty())
			this.surname = userDto.getSurname();

		if(userDto.getEmail() == null && !userDto.getEmail().isEmpty())
			this.email = userDto.getEmail();
	}


	public void updateKCUser(tools.mtsuite.core.core.keycloakSync.dto.User user) {
		this.keycloakKey = user.getId();
		this.name = user.getFirstName();
		this.surname = user.getLastName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.enable = (user.getEnabled() != null && user.getEnabled()) ?true : false;
	}

	/****************
	 * getter&setter **
	 *****************/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserProject> getUserProject() {
		return userProject;
	}

	public void setUserProject(List<UserProject> userProject) {
		this.userProject = userProject;
	}

	public List<FileTokenAccess> getFileTokenAccesses() {
		return fileTokenAccesses;
	}

	public void setFileTokenAccesses(List<FileTokenAccess> fileTokenAccesses) {
		this.fileTokenAccesses = fileTokenAccesses;
	}

	public List<TestSuiteRunner> getTestSuitesRunnerCreated() {
		return testSuitesRunnerCreated;
	}

	public void setTestSuitesRunnerCreated(List<TestSuiteRunner> testSuitesRunnerCreated) {
		this.testSuitesRunnerCreated = testSuitesRunnerCreated;
	}

	public List<TestSuite> getTestSuitesCreated() {
		return testSuitesCreated;
	}

	public void setTestSuitesCreated(List<TestSuite> testSuitesCreated) {
		this.testSuitesCreated = testSuitesCreated;
	}

	public String getKeycloakKey() {
		return keycloakKey;
	}

	public void setKeycloakKey(String keycloakKey) {
		this.keycloakKey = keycloakKey;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<BugReporterCredential> getBugReporterCredentials() {
		return bugReporterCredentials;
	}

	public void setBugReporterCredentials(List<BugReporterCredential> bugReporterCredentials) {
		this.bugReporterCredentials = bugReporterCredentials;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<TestSuiteRunner> getTestSuiteRunners() { return testSuiteRunners; }

	public void setTestSuiteRunners(List<TestSuiteRunner> testSuiteRunners) { this.testSuiteRunners = testSuiteRunners; }
}
