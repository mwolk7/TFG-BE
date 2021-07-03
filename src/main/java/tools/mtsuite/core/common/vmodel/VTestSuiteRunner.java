package tools.mtsuite.core.common.vmodel;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import tools.mtsuite.core.common.model.*;
import tools.mtsuite.core.common.model.enums.Browsers;
import tools.mtsuite.core.common.model.enums.Devices;
import tools.mtsuite.core.common.model.enums.OS;
import tools.mtsuite.core.common.model.enums.TestSuiteStatus;
import tools.mtsuite.core.endpoint.integrations.dto.BugReporterInputDto;
import tools.mtsuite.core.endpoint.integrations.dto.backlog.BackLogInputDto;

import java.lang.reflect.Type;
import java.util.*;


public class VTestSuiteRunner extends GenericObject {


	private User creatorUser;
	private List<User> users = new ArrayList();
	private ProjectVersion projectVersion;
	private TestSuite testSuite;
	private TestSuiteRunnerStatistics statistics;

	private List<VModule> modules = new ArrayList();;

	/****************
	 * Attributes **
	 *****************/

	private String name;
	private Date startDate;
	private Date finishDate;
	private Date dueDate;
	private TestSuiteStatus status;
	private List<Devices> devices = new ArrayList() ;
	private List<OS> os = new ArrayList() ;
	private List<Browsers> browsers = new ArrayList() ;


	/****************
	 * Functions **
	 *****************/

	public void increaseBugCountModule(String moduleUId,String testCaseUId, Long bugLogId){
		increaseBugCountStatistic();

		this.modules.forEach(m ->{
			if(m.getUid().equals(moduleUId)) {
				m.increaseBugCount(testCaseUId, bugLogId);
				return;
			}
		});
	}

	private void increaseBugCountStatistic() {
		this.statistics.increaseBugCount();
	}

	/****************
	 * Constructors **
	 *****************/
	public VTestSuiteRunner() {
	}

	public VTestSuiteRunner(TestSuiteRunner testSuiteRunner) {
		// Generic object
		this.setId(testSuiteRunner.getId());
		this.setCreatedDate(testSuiteRunner.getCreatedDate());
		this.setLastModifiedDate(testSuiteRunner.getLastModifiedDate());
		this.setVersion(testSuiteRunner.getVersion());
		this.setUsers(new ArrayList(testSuiteRunner.getUsers()));


		// Extra data
		this.creatorUser = testSuiteRunner.getCreatorUser();
		this.projectVersion = testSuiteRunner.getProjectVersion();
		this.testSuite = testSuiteRunner.getTestSuite();
		this.statistics=testSuiteRunner.getStatistics();


		/****************
		 * Attributes **
		 *****************/
		this.name=testSuiteRunner.getName();
		this.startDate=testSuiteRunner.getStartDate();
		this.finishDate=testSuiteRunner.getFinishDate();
		this.dueDate=testSuiteRunner.getDueDate();
		this.status=testSuiteRunner.getStatus();
		this.devices= new ArrayList(testSuiteRunner.getDevices());
		this.os= new ArrayList(testSuiteRunner.getOs());
		this.browsers= new ArrayList(testSuiteRunner.getBrowsers());

		Type listType = new TypeToken<List<VModule>>() {}.getType();
		Gson g = new Gson();

		this.modules =  g.fromJson(testSuiteRunner.getModules(), listType);
	}

	public TestSuiteRunner toTestSuiteRunner() {
		TestSuiteRunner testSuiteRunner = new TestSuiteRunner();

		// Generic object
		testSuiteRunner.setId(this.getId());
		testSuiteRunner.setCreatedDate(this.getCreatedDate());
		testSuiteRunner.setLastModifiedDate(this.getLastModifiedDate());
		testSuiteRunner.setVersion(this.getVersion());
		testSuiteRunner.setUsers(new ArrayList<>(this.getUsers()));

		// Extra data
		testSuiteRunner.setCreatorUser(this.creatorUser);
		testSuiteRunner.setProjectVersion(this.projectVersion);
		testSuiteRunner.setTestSuite(this.testSuite);
		testSuiteRunner.setStatistics(this.statistics);

		/****************
		 * Attributes **
		 *****************/
		testSuiteRunner.setName(this.name);
		testSuiteRunner.setStartDate(this.startDate);
		testSuiteRunner.setFinishDate(this.finishDate);
		testSuiteRunner.setDueDate(this.dueDate);
		testSuiteRunner.setStatus(this.status);
		testSuiteRunner.setDevices(new ArrayList(this.devices));
		testSuiteRunner.setOs(new ArrayList(this.os));
		testSuiteRunner.setBrowsers(new ArrayList(this.browsers));

		Gson g = new Gson();
		testSuiteRunner.setModules(g.toJson(this.modules));

		return testSuiteRunner;
	}

	/****************
	 * getter & setter **
	 *****************/
	public User getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(User creatorUser) {
		this.creatorUser = creatorUser;
	}

	public ProjectVersion getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(ProjectVersion projectVersion) {
		this.projectVersion = projectVersion;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	public TestSuiteRunnerStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(TestSuiteRunnerStatistics statistics) {
		this.statistics = statistics;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public TestSuiteStatus getStatus() {
		return status;
	}

	public void setStatus(TestSuiteStatus status) {
		this.status = status;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<VModule> getModules() {
		return modules;
	}

	public void setModules(List<VModule> modules) {
		this.modules = modules;
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
}
