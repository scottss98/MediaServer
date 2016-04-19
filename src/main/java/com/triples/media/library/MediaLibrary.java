package com.triples.media.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.triples.MediaConfig;

@Component
public class MediaLibrary {
	private MediaFolder baseFolder;
	private ImageMap imageMap;

	private final MediaConfig mediaConfig;

	@Autowired
	public MediaLibrary(MediaConfig mediaConfig, ImageMap imageMap) {
		this.mediaConfig = mediaConfig;
		this.imageMap = imageMap;

		MediaItem.setFolderImageFileNames(mediaConfig.getFolderImageFileNames());
		MediaItem.setImageMap(imageMap);
		MediaFolder.setVideoExtensions(mediaConfig.getVideoExtensions());
		
		String baseDirectory = mediaConfig.getBaseDirectory();
		if (baseDirectory != null) {
			this.baseFolder = new MediaFolder(baseDirectory);
		}
		else {
			List<String> directories = mediaConfig.getBaseDirectories();
			if (directories != null) {
				this.baseFolder = new MediaFolder();
				for (String directory : directories) {
					this.baseFolder.addFolder(new MediaFolder(directory));
				}
			}
		}

		System.out.println(baseFolder.toString());
	}

	public MediaFolder getBaseFolder() {
		return baseFolder;
	}

	public MediaFolder getFolder(String folderID) {
		return MediaFolder.getFolder(folderID);
	}
/*
	private void addSubFoldersToMap(MediaFolder mainFolder) {
		for (MediaFolder folder : mainFolder.getDirectories()) {
			folderMap.put(folder.getID(), folder);

			// Recurse
			addSubFoldersToMap(folder);
		}
	}
*/
	public MediaItem getFile(String fileID) {
		return MediaItem.getFile(fileID);
	}
/*
	private void addSubFolderItemssToMap(MediaFolder mainFolder) {
		for (MediaItem file : mainFolder.getFiles()) {
			fileMap.put(file.getID(), file);
			
		for (MediaFolder folder : mainFolder.getDirectories()) {
				addSubFolderItemssToMap(folder);
			}
		}
	}
*/
}
