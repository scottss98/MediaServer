package com.triples.media.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.triples.media.library.ImageMap;
import com.triples.media.library.MediaItem;
import com.triples.media.library.MediaLibrary;
import com.triples.utils.Utils;

@Controller
public class MediaController {
	private static SimpleDateFormat lastModifiedFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	private MediaLibrary mediaLibrary;
	private ImageMap imageMap;
	private Date controllerStartTime;

	@Autowired
	public MediaController(MediaLibrary mediaLibrary, ImageMap imageMap) {
		this.mediaLibrary = mediaLibrary;
		this.imageMap = imageMap;
		this.controllerStartTime = new Date();
	}

	@RequestMapping("/")
	public String showHome(Map<String, Object> model) {
		model.put("directory", mediaLibrary.getBaseFolder());
		return "home";
	}

	@RequestMapping("/folder/{folderId}")
	public String showDirectory(Map<String, Object> model, @PathVariable("folderId") String folderId) {
		model.put("directory", mediaLibrary.getFolder(folderId));
		return "home";
	}

	@RequestMapping("/info/{fileId}")
	public String getInfo(Map<String, Object> model, @PathVariable("fileId") String fileId) {
		MediaItem mediaFile = mediaLibrary.getFile(fileId);
		model.put("fileID", fileId);
		model.put("file", mediaFile);
		return "info";
	}

	@RequestMapping("/file/{fileId}/{fileName:.*}")
	public void getVideo(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileId") String fileId,
			@PathVariable("fileName") String fileName) {
		MediaItem mediaFile = mediaLibrary.getFile(fileId);
		//streamFile(response, mediaFile.getMediaFile());
		streamVideoFile(request, response, mediaFile.getMediaFile());
	}

	@RequestMapping("/video/{fileId}/{fileName:.*}")
	public String showVideo(HttpServletResponse response, Map<String, Object> model, @PathVariable("fileId") String fileId,
			@PathVariable("fileName") String fileName) {
		MediaItem item = mediaLibrary.getFile(fileId);
		model.put("fileID", fileId);
		model.put("fileName", fileName);
		model.put("file", item);
		response.setHeader("Accept-Ranges", "bytes");
		return "video";
	}

	@RequestMapping(value = "/images/{imageFileID:.*}", method = RequestMethod.GET)
	public void returnImageStream(HttpServletRequest request, HttpServletResponse response, @PathVariable("imageFileID") String imageFileID) {
		File imageFile = imageMap.getImageFile(imageFileID);
		streamFile(request, response, imageFile);
	}
	
	public void streamVideoFile(HttpServletRequest request, HttpServletResponse response, File file) {
		try {
			MultipartFileSender.fromFile(file)
			.with(request)
			.with(response)
			.serveResource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void streamFile(HttpServletRequest request, HttpServletResponse response, File file) {
		try {
			if (file != null) {
		        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		        long lastModified = controllerStartTime.getTime();
		        if (ifModifiedSince != -1 && ifModifiedSince + 1000 > lastModified) {
		            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
		            return;
		        }
				
				Long length = Files.size(file.toPath());
				String contentType = Utils.getContetType(file);

				// Set the content type and attachment header.
				response.addHeader("Content-disposition", "inline;filename=" + URLEncoder.encode(file.getName()));
				response.addHeader("Content-Length", String.valueOf(length));
				response.addHeader("Last-Modified", lastModifiedFormat.format(controllerStartTime));
				//response.addHeader("Content-Range", "bytes 0-" + String.valueOf(length - 1) + "/" + String.valueOf(length));

				if (contentType != null) {
					response.setContentType(contentType);
				}

				// Copy the stream to the response's output stream.
				Files.copy(file.toPath(), response.getOutputStream());
				response.flushBuffer();
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}
}