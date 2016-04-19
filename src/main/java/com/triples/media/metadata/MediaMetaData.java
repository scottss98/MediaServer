package com.triples.media.metadata;

import java.io.File;

public class MediaMetaData {
	private File metaDataFile;
	private String title;
	private Integer year;
	private String shortDescription;
	private String longDescription;

	public MediaMetaData(File metaDataFile) {
		this.metaDataFile = metaDataFile;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getShortDescription() {
		return shortDescription;
	}
	
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
		if ((this.shortDescription != null)
			&& this.shortDescription.isEmpty()) {
			this.shortDescription = null;
		}
	}

	public String getLongDescription() {
		return longDescription;
	}
	
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
		if ((this.longDescription != null)
			&& this.longDescription.isEmpty()) {
			this.longDescription = null;
		}
	}
}
