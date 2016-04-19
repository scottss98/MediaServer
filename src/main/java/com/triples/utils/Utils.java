package com.triples.utils;

import java.io.File;

public class Utils {
	public static String removeExtension(String s) {

		String separator = System.getProperty("file.separator");
		String filename;

		// Remove the path upto the filename.
		int lastSeparatorIndex = s.lastIndexOf(separator);
		if (lastSeparatorIndex == -1) {
			filename = s;
		} else {
			filename = s.substring(lastSeparatorIndex + 1);
		}

		// Remove the extension.
		int extensionIndex = filename.lastIndexOf(".");
		if (extensionIndex == -1)
			return filename;

		return filename.substring(0, extensionIndex);
	}

	public static String getExtension(String s) {

		String separator = System.getProperty("file.separator");
		String filename;

		// Remove the path upto the filename.
		int lastSeparatorIndex = s.lastIndexOf(separator);
		if (lastSeparatorIndex == -1) {
			filename = s;
		} else {
			filename = s.substring(lastSeparatorIndex + 1);
		}

		// Remove the extension.
		int extensionIndex = filename.lastIndexOf(".");
		if (extensionIndex == -1)
			return "";

		return filename.substring(extensionIndex);
	}
	
	public static String getContetType(File file) {
		String contentType = "application/octet-stream";
		
		if (file != null) {
			String fileExtension = Utils.getExtension(file.getName()).toLowerCase();
			switch (fileExtension) {
			case ".jpg":
			case ".jpeg":
				contentType = "image/jpeg";
				break;

			case ".png":
				contentType = "image/png";
				break;

			case ".avi":
				contentType = "video/x-msvideo";
				break;
			
			case ".mkv": 
				contentType = "video/mkv"; 
				break;

			case ".mov": 
				contentType = "video/mov"; 
				break;

			case ".mp4": 
				contentType = "video/mp4"; 
				break;
			}
		}
		return contentType;
	}
}
