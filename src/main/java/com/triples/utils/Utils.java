package com.triples.utils;

import java.io.File;

import com.google.common.io.Files;

public class Utils {
	public static String removeExtension(String s) {
		return Files.getNameWithoutExtension(s);
	}

	public static String getExtension(String s) {
		return Files.getFileExtension(s);
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
