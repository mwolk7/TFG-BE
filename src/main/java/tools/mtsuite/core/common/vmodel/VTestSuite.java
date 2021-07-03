package tools.mtsuite.core.common.vmodel;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.aspectj.weaver.ast.Test;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import tools.mtsuite.core.common.model.*;
import tools.mtsuite.core.common.model.enums.Browsers;
import tools.mtsuite.core.common.model.enums.Devices;
import tools.mtsuite.core.common.model.enums.OS;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;



public class VTestSuite extends GenericObject {
	/****************
	 * Relationships**
	 *****************/
	private List<VModule> modules;
	private Project project;
	private User creatorUser;


	/****************
	 * Attributes **
	 *****************/
	private String name;
	private String description;
	private String testSuiteVersion;
	private List<Devices> devices = new ArrayList();
	private List<OS> os = new ArrayList();
	private List<Browsers> browsers = new ArrayList();

	/****************
	 * Constructors **
	 *****************/
	public VTestSuite() {
	}

	public VTestSuite(TestSuite testSuite) {
		// Generic object
		this.setId(testSuite.getId());
		this.setCreatedDate(testSuite.getCreatedDate());
		this.setLastModifiedDate(testSuite.getLastModifiedDate());
		this.setVersion(testSuite.getVersion());

		// Extra data
		this.project = testSuite.getProject();
		this.creatorUser = testSuite.getCreatorUser();
		this.name = testSuite.getName();
		this.description = testSuite.getDescription();
		this.testSuiteVersion = testSuite.getTestSuiteVersion();
		this.devices = testSuite.getDevices();
		this.os = testSuite.getOs();
		this.browsers = testSuite.getBrowsers();

		Type listType = new TypeToken<List<VModule>>() {}.getType();
		Gson g = new Gson();
		List<VModule> _vModule = g.fromJson(testSuite.getModules(), listType);

		List<VModule> hSet = new ArrayList();
		hSet.addAll(_vModule);

		this.modules = hSet;

	}

	public TestSuite toTestSuite() {
		TestSuite testSuite = new TestSuite();
		// Generi obj
		testSuite.setId(this.getId());
		testSuite.setCreatedDate(this.getCreatedDate());
		testSuite.setLastModifiedDate(this.getLastModifiedDate());
		testSuite.setVersion(this.getVersion());

		// Extra data
		testSuite.setProject(this.project);
		testSuite.setCreatorUser(this.creatorUser);
		testSuite.setName(this.name);
		testSuite.setDescription(this.description);
		testSuite.setTestSuiteVersion(this.testSuiteVersion);
		testSuite.setDevices(this.devices);
		testSuite.setOs(this.os);
		testSuite.setBrowsers(this.browsers);

		Gson g = new Gson();
		testSuite.setModules(g.toJson(this.modules));

		return testSuite;
	}

	/****************
	 * getter & setter **
	 *****************/

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(User creatorUser) {
		this.creatorUser = creatorUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTestSuiteVersion() {
		return testSuiteVersion;
	}

	public void setTestSuiteVersion(String testSuiteVersion) {
		this.testSuiteVersion = testSuiteVersion;
	}

	public List<Devices> getDevices() {
		return devices;
	}

	public void setDevices(List<Devices> devices) {
		this.devices = devices;
	}

	public List<OS> getOs() {
		return os;
	}

	public void setOs(List<OS> os) {
		this.os = os;
	}

	public List<Browsers> getBrowsers() {
		return browsers;
	}

	public void setBrowsers(List<Browsers> browsers) {
		this.browsers = browsers;
	}

	public List<VModule> getModules() {
		return modules;
	}

	public void setModules(List<VModule> modules) {
		this.modules = modules;
	}
}
