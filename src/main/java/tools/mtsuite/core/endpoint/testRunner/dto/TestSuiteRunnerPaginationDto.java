package tools.mtsuite.core.endpoint.testRunner.dto;
import java.util.ArrayList;
import java.util.List;


public class TestSuiteRunnerPaginationDto {

    /******
     * Attributes **
     *******/

    private List<TestSuiteRunnerTableDto> data = new ArrayList();
    private Long totalElements = 0l;

    /******
     * Constructors **
     *******/

    public TestSuiteRunnerPaginationDto() {
    }

    /******
     * getter & setter **
     *******/

    public List<TestSuiteRunnerTableDto> getData() {
        return data;
    }

    public void setData(List<TestSuiteRunnerTableDto> data) {
        this.data = data;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public void increaseElements() {
        this.totalElements++;
    }
}
