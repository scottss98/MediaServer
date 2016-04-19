<!DOCTYPE HTML>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Media Server - File Info</title>
		<style type="text/css">
		</style>
	</head>
	
	<body>
		<H1>File Info</H1>
		<table border="0">
			<tr><td>ID</td><td colspan="2">${fileID}</td></tr>
			<tr><td>Media Name</td><td colspan="2">${file.mediaName}</td></tr>
			<tr><td>Content Type</td><td colspan="2">${file.contentType}</td></tr>
			<tr><td>Video</td><td>${file.mediaFile.name}</td><td>${file.mediaFile.absolutePath}</td></tr>
			<tr><td>Image</td><td>${file.mediaImageFile.name}</td><td>${file.mediaImageFile.absolutePath}</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>Title</td><td colspan="2">${file.metaData.title}</td></tr>
			<tr><td>Year</td><td colspan="2">${file.metaData.year}</td></tr>
			<tr><td>Short Description</td><td colspan="2">${file.metaData.shortDescription}</td></tr>
			<tr><td>Long Description</td><td colspan="2">${file.metaData.longDescription}</td></tr>
		</table>
		
	</body>
</html>