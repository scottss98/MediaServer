package com.triples;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "media")
public class MediaConfig {
	private String baseDirectory;
	private List<String> baseDirectories;
	private List<String> videoExtensions;
	private List<String> folderImageFileNames;

	public MediaConfig() {
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public List<String> getBaseDirectories() {
		return baseDirectories;
	}

	public void setBaseDirectories(List<String> baseDirectories) {
		this.baseDirectories = baseDirectories;
	}

	public List<String> getVideoExtensions() {
		return videoExtensions;
	}

	public void setVideoExtensions(List<String> videoExtensions) {
		this.videoExtensions = videoExtensions;
	}

	public List<String> getFolderImageFileNames() {
		return folderImageFileNames;
	}

	public void setFolderImageFileNames(List<String> folderImageFileNames) {
		this.folderImageFileNames = folderImageFileNames;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Base Directory: ").append(baseDirectory).append("\n");
		
		if (videoExtensions != null) {
		sb.append("Video Extensions:\n");
			for (String ext : videoExtensions) {
				sb.append(ext).append("\n");
			}
		}

		if (folderImageFileNames != null) {
			sb.append("Folder Image Names:\n");
			for (String name : folderImageFileNames) {
				sb.append(name).append("\n");
			}
		}

		return sb.toString();
	}
}
