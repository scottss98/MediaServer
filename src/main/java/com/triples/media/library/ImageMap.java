package com.triples.media.library;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import org.springframework.stereotype.Component;

import com.triples.utils.Utils;

@Component
public class ImageMap {
	private Map<String, File> imageMap = new HashMap<>();
	Integer nextId = 1;

	public void addImageFile(String id, File file) {
		imageMap.put(id, file);
	}

	public String addImageFile(File file) {
		if (file != null) {
			String id;
			synchronized (nextId) {
				id = nextId.toString() + Utils.getExtension(file.getName());
				nextId++;
			}
			imageMap.put(id, file);
			return id;
		} else {
			return null;
		}
	}

	public File getImageFile(String id) {
		return imageMap.get(id);
	}
}
