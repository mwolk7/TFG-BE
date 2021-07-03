package tools.mtsuite.core.common.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tools.mtsuite.core.common.model.enums.TestCaseStatus;
import tools.mtsuite.core.common.vmodel.VModule;
import tools.mtsuite.core.common.vmodel.VTestCase;
import tools.mtsuite.core.common.vmodel.VTestSuiteRunner;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Entity
@Table(name = AppConstants.TEST_SUITE_RUNNER_STATISTIC)
public class TestSuiteRunnerStatistics extends GenericObject {
	/****************
	 * Relationships**
	 *****************/
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEST_SUITE_RUNNER_ID")
	@Cascade({CascadeType.SAVE_UPDATE})
	private TestSuiteRunner testSuiteRunner;


	/****************
	 * Attributes **
	 *****************/
	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "FINISH_DATE")
	private Date finishDate;

	@Column(name = "TOTAL_TIME")
	private Long totalTime;

	@Column(name = "TOTAL_MODULES")
	private Integer totalModules;

	@Column(name = "TOTAL_TEST_CASES")
	private Integer totalTestCases;

	@Column(name = "PASSED")
	private Integer passed;

	@Column(name = "FAILED")
	private Integer failed;

	@Column(name = "CNT")
	private Integer cnt;
	@Column(name = "NA")
	private Integer na;

	@Column(name = "BUGS_REPORTED")
	private Integer bugsReported;

	@Column(name = "REMAINING")
	private Double remaining;

	public void increaseBugCount() {
		this.bugsReported++;
	}

	public TestSuiteRunnerStatistics() {
	}

	public boolean setInitialValues() {
		this.totalTime = 0L;
		this.totalModules = 0;
		this.totalTestCases = 0;
		this.passed = 0;
		this.failed = 0;
		this.cnt = 0;
		this.na = 0;
		this.bugsReported = 0;
		this.remaining= 0.0;

		return true;
	}

	public Boolean calculateStatistics(TestSuiteRunner testSuiteRunner) {
		VTestSuiteRunner vTestSuiteRunner = new VTestSuiteRunner(testSuiteRunner);

		if(this.startDate != null && this.finishDate !=null) {
			//this.totalTime = this.startDate.to
		}

		this.startDate = testSuiteRunner.getStartDate();
		this.finishDate = testSuiteRunner.getFinishDate();

		List<VModule> _module = vTestSuiteRunner.getModules();

		// Set all in 0
		this.setInitialValues();

		// No module no data
		if(_module.isEmpty()) {
			return true;
		}


		for(VModule module : _module) {

			Boolean addCountModule = false;

			for(VTestCase testCase : module.getTestCases()) {
				if(testCase.getTestCaseStatus() == null) {
					testCase.setTestCaseStatus(TestCaseStatus.Pending);
				}

				if( testCase.getRun() == null || testCase.getRun() == false) {
					continue;
				}
				this.totalTestCases++;
				addCountModule = true;

				switch (testCase.getTestCaseStatus()) {
					case NA:
						this.na++;
						break;
					case CNT:
						this.cnt++;
						break;
					case Fail:
						this.failed++;
						break;
					case Pass:
						this.passed++;
						break;
					case Pending:
						break;
					default:
				}
			}

			if(addCountModule == true) {
				this.totalModules++;
			}

		}

		if(this.totalTestCases != 0) {
			this.remaining = 1.0 * (((this.na + this.cnt + this.failed + this.passed) * 100 ) / this.totalTestCases);
		}

		return true;
	}

	/****************
	 * Constructors **
	 *****************/



	public TestSuiteRunner getTestSuiteRunner() {
		return testSuiteRunner;
	}

	public void setTestSuiteRunner(TestSuiteRunner testSuiteRunner) {
		this.testSuiteRunner = testSuiteRunner;
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

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getTotalModules() {
		return totalModules;
	}

	public void setTotalModules(Integer totalModules) {
		this.totalModules = totalModules;
	}

	public Integer getTotalTestCases() {
		return totalTestCases;
	}

	public void setTotalTestCases(Integer totalTestCases) {
		this.totalTestCases = totalTestCases;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

	public Integer getFailed() {
		return failed;
	}

	public void setFailed(Integer failed) {
		this.failed = failed;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public Integer getNa() {
		return na;
	}

	public void setNa(Integer na) {
		this.na = na;
	}

	public Integer getBugsReported() {
		return bugsReported;
	}

	public void setBugsReported(Integer bugsReported) {
		this.bugsReported = bugsReported;
	}

	public Double getRemaining() {
		return remaining;
	}

	public void setRemaining(Double remaining) {
		this.remaining = remaining;
	}
}
