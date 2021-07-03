package tools.mtsuite.core.endpoint.files.dto;


import tools.mtsuite.core.core.dto.GenericObjectDto;

import javax.validation.constraints.NotNull;


public class FileDto extends GenericObjectDto {
	/****************
	 * Relationships**
	 *****************/

	/****************
	 * Attributes **
	 *****************/
	private String name;
	private String bucket;
	private String contentType;
	private String nameToStorage;
	private String dataType;
	private String content;
	private String thumbnailName;
	private Boolean showInGallery;


	public FileDto() {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
