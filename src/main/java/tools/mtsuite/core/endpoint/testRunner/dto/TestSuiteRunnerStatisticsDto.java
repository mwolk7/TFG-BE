package tools.mtsuite.core.endpoint.testRunner.dto;

import tools.mtsuite.core.core.dto.GenericObjectDto;
import java.util.Date;

public class TestSuiteRunnerStatisticsDto extends GenericObjectDto {
	/****************
	 * Relationships**
	 *****************/

	/****************
	 * Attributes **
	 *****************/
	private Date startDate;
	private Date finishDate;
	private Long totalTime;
	private Integer totalModules;
	private Integer totalTestCases;
	private Integer passed;
	private Integer failed;
	private Integer cnt;
	private Integer na;
	private Integer bugsReported;
	private Double remaining;

	/****************
	 * Functions **
	 *****************/

	public void increaseBugCount() {
		this.bugsReported++;
	}

	/****************
	 * Constructors **
	 *****************/
	public TestSuiteRunnerStatisticsDto() {
	}

	/****************
	 * getter & setter **
	 *****************/

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

