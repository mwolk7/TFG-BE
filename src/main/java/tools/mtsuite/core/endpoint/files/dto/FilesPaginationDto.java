package tools.mtsuite.core.endpoint.files.dto;
import tools.mtsuite.core.endpoint.testRunner.dto.TestSuiteRunnerTableDto;

import java.util.ArrayList;
import java.util.List;


public class FilesPaginationDto {

    /******
     * Attributes **
     *******/

    private List<FileDto> data = new ArrayList();
    private Long totalElements = 0l;

    /******
     * Constructors **
     *******/

    public FilesPaginationDto() {
    }


    /******
     * getter & setter **
     *******/

    public List<FileDto> getData() {
        return data;
    }

    public void setData(List<FileDto> data) {
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
