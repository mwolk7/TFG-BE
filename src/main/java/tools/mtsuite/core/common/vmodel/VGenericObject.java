package tools.mtsuite.core.common.vmodel;

import org.apache.commons.csv.CSVRecord;

import java.util.Date;
import java.util.UUID;


public abstract class VGenericObject {


	private String uid = UUID.randomUUID().toString();
	private Date createdDate = new Date();


	/**
	Getter & Setters
	**/
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUid() { return uid; }

	public void setUid(String uid) { this.uid = uid; }


}
