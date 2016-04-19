package com.triples.media.library;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.triples.media.metadata.MediaMetaData;
import com.triples.media.metadata.MovieXMLMetaData;
import com.triples.media.metadata.MyMoviesXMLMetaData;
import com.triples.media.metadata.NfoMetaData;
import com.triples.utils.Utils;

public class MediaItem implements Comparable<MediaItem> {
	private static final Logger log = LoggerFactory.getLogger(MediaItem.class);
	private static List<String> folderImageFileNames;
	private static ImageMap imageMap;
	private static Integer nextId = 1;
	private static Map<String, MediaItem> fileMap = new HashMap<>();
	private static File unknownImageFile = null;

	public static void setFolderImageFileNames(List<String> folderImageFileNames) {
		MediaItem.folderImageFileNames = folderImageFileNames;
	}

	public static void setImageMap(ImageMap imageMap) {
		MediaItem.imageMap = imageMap;
	}

	private String ID;
	private File mediaFile;
	private String mediaName;
	private File mediaImageFile;
	//private File localTempMediaImageFile;
	private String localTempMediaImageFileId;
	private MediaMetaData metaData;

	protected MediaItem() {
		synchronized (nextId) {
			ID = nextId.toString();
			nextId++;
			
			fileMap.put(ID, this);
		}
	}
	
	public MediaItem(File mediaFile) {
		this(mediaFile, mediaFile.getName(), findFolderImage(mediaFile), findMetaData(mediaFile));
	}

	public MediaItem(File mediaFile, String mediaName, File mediaImageFile, MediaMetaData metaData) {
		this();
		this.mediaFile = mediaFile;
		this.mediaName = mediaName;
		this.mediaImageFile = mediaImageFile;
		this.metaData = metaData;

		if (this.metaData != null) {
			String title = this.metaData.getTitle();
			if ((title != null) && !title.isEmpty()) {
				this.mediaName = title;
			}
		}
	}

	public String getID() {
		return ID;
	}

	public File getMediaFile() {
		return this.mediaFile;
	}

	public String getMediaName() {
		return this.mediaName;
	}
	
	public MediaMetaData getMetaData() {
		return this.metaData;
	}

	public File getMediaImageFile() {
		return this.mediaImageFile;
	}
	
	public String getContentType() {
		return Utils.getContetType(this.mediaFile);
	}

	public File getLocalMediaImageFile() {
		localTempMediaImageFileId = imageMap.addImageFile(mediaImageFile);
		return mediaImageFile;
		/*
		final String prefix = "mediaServerImg";
		if (mediaImageFile != null) {
			if (localTempMediaImageFile == null) {
				String ext = getExtension(mediaImageFile.getName());
				try {
					localTempMediaImageFile = File.createTempFile(prefix, ext);
					localTempMediaImageFile.deleteOnExit();
					log.info("Created temp image file: " + localTempMediaImageFile.getAbsolutePath());
					Files.copy(mediaImageFile, localTempMediaImageFile);
					log.info("Copied " + mediaImageFile.getAbsolutePath() + " to "
							+ localTempMediaImageFile.getAbsolutePath());
					localTempMediaImageFileId = localTempMediaImageFile.getName();
					imageMap.addImageFile(localTempMediaImageFileId, mediaImageFile);
				} catch (IOException e) {
					// e.printStackTrace();
					localTempMediaImageFile = null;
					log.error(e.toString(), e);
				}
			} else {
				return localTempMediaImageFile;
			}
		}

		return null;
		*/
	}

	public String getLocalMediaImageFileId() {
		if (localTempMediaImageFileId != null) {
			return localTempMediaImageFileId;
		} else if (getLocalMediaImageFile() != null) {
			return localTempMediaImageFileId;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return toString(0);
	}

	protected String toString(int indentLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append(getIndent(indentLevel)).append(ID).append(": ").append(getMediaName());

		if (metaData != null) {
			String title = metaData.getTitle();
			Integer year = metaData.getYear();
			if (title != null) {
				sb.append(" (").append(title);

				if (year != null) {
					sb.append(" [").append(year).append("]");
				}

				sb.append(")");
			}
		}

		if (mediaImageFile != null) {
			sb.append(" (").append(mediaImageFile.getName()).append(")");
		}

		sb.append("\n");

		return sb.toString();
	}

	protected static String getIndent(int indentLevel) {
		StringBuilder indent = new StringBuilder(indentLevel);
		for (int i = 0; i < indentLevel; i++) {
			indent.append(" ");
		}
		return indent.toString();
	}

	private static File findFolderImage(String folderPath) {
		return findFolderImage(new File(folderPath));
	}

	private static File findFolderImage(File folder) {
		if (folder.isFile()) {
			File file = folder;
			folder = folder.getParentFile();
			String fileBaseName = Utils.removeExtension(file.getName());
			File imageFile = doesFileExist(folder, fileBaseName + ".jpg");
			if (imageFile == null) {
				imageFile = doesFileExist(folder, fileBaseName + "-poster.jpg");
			}
			if (imageFile == null) {
				imageFile = doesFileExist(folder, fileBaseName + "-thumb.jpg");
			}
			if (imageFile != null) {
				return imageFile;
			}
		}
		
		if (folderImageFileNames != null) {
			for (String folderImageFileName : folderImageFileNames) {
				File folderImageFile = new File(folder, folderImageFileName);
				if (folderImageFile.exists() && folderImageFile.isFile()) {
					return folderImageFile;
				}
			}
		}
		
		if (unknownImageFile == null) {
			ClassLoader classLoader = MediaItem.class.getClassLoader();
			unknownImageFile = new File(classLoader.getResource("images/matt-icons_file-x-unknown.png").getFile());
		}
		return unknownImageFile;
	}
	
	private static File doesFileExist(File folder, String fileName) {
		File file = new File(folder, fileName);
		if (file.exists() && file.isFile()) {
			return file;
		}
		else {
			return null;
		}
	}

	private static MediaMetaData findMetaData(File folder) {
		File movieFile = null;
		if (folder.isFile()) {
			movieFile = folder;
			folder = folder.getParentFile();
		}

		File metaDataFile = new File(folder, "movie.xml");
		if (metaDataFile.exists() && metaDataFile.isFile()) {
			try {
				return new MovieXMLMetaData(metaDataFile);
			} catch (Throwable t) {
			}
		}

		metaDataFile = new File(folder, "mymovies.xml");
		if (metaDataFile.exists() && metaDataFile.isFile()) {
			try {
				return new MyMoviesXMLMetaData(metaDataFile);
			} catch (Throwable t) {
			}
		}

		if (movieFile != null) {
			metaDataFile = new File(folder, Utils.removeExtension(movieFile.getName()) + ".nfo");
			if (metaDataFile.exists() && metaDataFile.isFile()) {
				try {
					return new NfoMetaData(metaDataFile);
				} catch (Throwable t) {
				}
			}
		}

		return null;
	}

	@Override
	public int compareTo(MediaItem o) {
		if (mediaFile.getName().matches(".*[Ss]\\d\\d[Ee]\\d\\d.*")) {
			// TV series. eg "Rome s01e01.mkv" so need to sort by file name to get episodes in order
			return mediaFile.getName().compareTo(o.mediaFile.getName());
		}
		else {
			// Movie so just sort by title
			int result = mediaName.compareTo(o.mediaName);
			if ((result == 0) && (metaData != null) && (o.metaData != null)) {
				Integer thisYear = metaData.getYear();
				Integer thatYear = o.metaData.getYear();
				if (thisYear != null) {
					result = metaData.getYear().compareTo(o.metaData.getYear());
				}
			}
			return result;
		}
	}
	
	public static MediaItem getFile(String fileID) {
		return fileMap.get(fileID);
	}
}
