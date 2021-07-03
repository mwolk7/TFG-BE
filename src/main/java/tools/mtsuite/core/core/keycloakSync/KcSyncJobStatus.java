package tools.mtsuite.core.core.keycloakSync;


import tools.mtsuite.core.common.model.GenericObject;
import tools.mtsuite.core.core.utils.AppConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = AppConstants.KC_SYNC_JOB_STATUS)
public class KcSyncJobStatus extends GenericObject {
    /****************
     * Attributes **
     ****************/

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "REFERENCE_DATE")
    private String referenceDate;

    @Column(name = "JOB_STATUS")
    private String status;



    public KcSyncJobStatus(){

    }

    public KcSyncJobStatus(String jobName,String referenceDate,String status){
        this.jobName = jobName;
        this.status = status;
        this.referenceDate = referenceDate;
    }
    /********************
     * Getter & Setter **
     ********************/


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
