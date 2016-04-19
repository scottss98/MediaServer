package com.triples.media.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MediaFolder extends MediaItem {
	private static List<String> videoExtensions;

	public static void setVideoExtensions(List<String> videoExtensions) {
		MediaFolder.videoExtensions = videoExtensions;
	}

	private Set<MediaItem> mediaList;
	private boolean bEmpty = true;

	public MediaFolder(File folder) {
		super(folder);
		mediaList = findMedia();
	}

	public MediaFolder(String folderPath) {
		this(new File(folderPath));
	}

	public String getFolderName() {
		return super.getMediaName();
	}

	public File getFolder() {
		return super.getMediaFile();
	}

	public File getFolderImageFile() {
		return super.getMediaImageFile();
	}

	public boolean isEmpty() {
		return bEmpty;
	}
	
	public List<MediaItem> getFiles() {
		List<MediaItem> list = new ArrayList<>();
		for (MediaItem item : mediaList) {
			if (item instanceof MediaFolder) {
				// Skipping folders
			}
			else {
				list.add(item);
			}
		}
		return list;
	}

	public List<MediaItem> getSortedFiles() {
		List<MediaItem> list = getFiles();
		Collections.sort(list);
		return list;
	}

	public List<MediaFolder> getDirectories() {
		List<MediaFolder> list = new ArrayList<>();
		for (MediaItem item : mediaList) {
			if (item instanceof MediaFolder) {
				MediaFolder folder = (MediaFolder)item;
				if (!folder.isEmpty()) {
					list.add(folder);
				}
			}
			else {
				// Skipping files
			}
		}
		return list;
	}
	
	public List<MediaFolder> getSortedDirectories() {
		List<MediaFolder> list = getDirectories();
		Collections.sort(list);
		return list;
	}

	@Override
	public String toString() {
		return toString(0);
	}

	protected String toString(int indentLevel) {
		if (!bEmpty) {
			StringBuilder sb = new StringBuilder();
			sb.append(super.toString(indentLevel));

			if (mediaList != null) {
				for (MediaItem item : mediaList) {
					if (item != null) {
						sb.append(item.toString(indentLevel + 1));
					}
				}
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	private Set<MediaItem> findMedia() {
		Set<MediaItem> mediaItemList = new TreeSet<>();
		File[] fileList = getFolder().listFiles();
		if (fileList != null) {
			for (File file : fileList) {
				MediaItem item = null;
				if (file.isDirectory()) {
					MediaFolder mediaFolder = new MediaFolder(file);
					if (bEmpty) {
						bEmpty = mediaFolder.isEmpty();
					}
					item = mediaFolder;
				} else if (videoExtensions != null) {
					String fileName = file.getName();
					for (String videoExtension : videoExtensions) {
						if (fileName.endsWith(videoExtension)) {
							item = new MediaItem(file);
							bEmpty = false;
						}
					}
				} else {
					item = new MediaItem(file);
				}

				if (item != null) {
					mediaItemList.add(item);
				}
			}
		}
		return mediaItemList;
	}
	
	public static MediaFolder getFolder(String folderID) {
		MediaItem item = MediaItem.getFile(folderID);
		if (item instanceof MediaFolder) {
			return (MediaFolder)item;
		}
		else {
			return null;
		}
	}
}
