package tools.mtsuite.core.common.model;


import org.hibernate.annotations.Cascade;
import tools.mtsuite.core.core.utils.AppConstants;
import tools.mtsuite.core.endpoint.files.dto.FileDto;

import javax.persistence.*;


@Entity
@Table(name = AppConstants.FILES)
public class File extends GenericObject {

	/****************
	 * Relationships**
	 *****************/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Project project;

	/****************
	 * Attributes **
	 *****************/
	@Column(name = "NAME")
	private String name;

	@Column(name = "BUCKET")
	private String bucket;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	@Column(name = "NAME_TO_STORAGE")
	private String nameToStorage;

	@Column(name = "THUMBNAIL_NAME")
	private String thumbnailName;

	@Column(name = "DATA_TYPE")
	private String dataType;

	@Column(name = "SHOW_IN_GALLERY")
	private Boolean showInGallery;

	public File() {
	}


	public File(FileDto fileDto) {
		this.name = fileDto.getName();
		this.bucket = fileDto.getBucket();
		this.nameToStorage = fileDto.getNameToStorage();
		this.dataType = fileDto.getDataType();
		this.thumbnailName = fileDto.getThumbnailName();
		this.showInGallery = fileDto.getShowInGallery();
	}

	/****************
	 * FUNCTIONS  **
	 *****************/

	public String getAbsolutePath() {
		return this.bucket+"/"+this.nameToStorage;
	}
	public String getThumbnailAbsolutePath() {
		return this.bucket+"/"+this.thumbnailName;
	}
	/*******************
	 * GETTES & SETTERS **
	 ********************/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getNameToStorage() {
		return nameToStorage;
	}

	public void setNameToStorage(String nameToStorage) {
		this.nameToStorage = nameToStorage;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getThumbnailName() {
		return thumbnailName;
	}

	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}

	public Boolean getShowInGallery() { return showInGallery; }

	public void setShowInGallery(Boolean showInGallery) { this.showInGallery = showInGallery; }
}
