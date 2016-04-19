<!DOCTYPE HTML>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Media Server</title>
		<style type="text/css">
			.imageSpan {
				vertical-align: top;
				text-align: center;
				display: inline-block;
				width: 120px;
			}
		</style>
	</head>
	
	<body>
		<c:set var="sortedDirectories" value="${directory.directories}"/>
		<c:if test="${!empty sortedDirectories}">
			<h1>Directories</h1>
			<c:forEach items="${sortedDirectories}" var="subDir">
				<span class="imageSpan">
					<a href="/folder/${subDir.ID}">
						<img src="/images/${subDir.localMediaImageFileId}" width="80" height="80" />
					</a>
					<div>
						<a href="/folder/${subDir.ID}">${subDir.folderName}</a>
					</div>
				</span>
			</c:forEach>
		</c:if>
	
		<c:set var="myFiles" value="${directory.files}"/>
		<c:if test="${!empty myFiles}">
			<h1>Files</h1>
			<c:forEach items="${myFiles}" var="file">
				<span class="imageSpan">
					<a href="/video/${file.ID}/${file.mediaFile.name}">
						<img src="/images/${file.localMediaImageFileId}" width="80" height="80" />
					</a>
					<div>
						<a href="/video/${file.ID}/${file.mediaFile.name}">${file.mediaName}</a>
					</div>
				</span>
			</c:forEach>
		</c:if>
	</body>
</html>