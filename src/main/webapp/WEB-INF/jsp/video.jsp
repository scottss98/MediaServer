<!DOCTYPE HTML>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Media Server</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<style type="text/css">
			.imageSpan {
				vertical-align: top;
				text-align: center;
				display: inline-block;
				width: 120px;
			}
			div.background {
				width: 100%;
				height: 100%;
				background-image: url("/images/${file.localMediaImageFileId}");
				opacity: 0.2;
				top: 0;
				bottom: 0;
				right : 0;
				position: absolute;
			}
			
			span {
				vertical-align: top;
				word-wrap: normal;
			}
			
			video {
				width:100%;
				max-width:500px;
				height:auto;
				float: left;
				margin-right: 10px;
				margin-bottom: 10px;
			}
			
			div {
				clear:left;
			}
		</style>
	</head>
	
	<body>
		<xdiv class="background"></xdiv>
		<h1>
			<c:if test="${file.metaData.title != null}">${file.metaData.title}</c:if>
			<c:if test="${file.metaData.title == null}">${fileName}</c:if>
		</h1>
		<div>
		<video controls preload="none" poster="/images/${file.localMediaImageFileId}">
			<source src="/file/${fileID}/${fileName}" type="${file.contentType}">
			<p>HTML5 video is not supported by your browser</p>
		</video>
		<c:if test="${file.metaData.shortDescription != null}">
			<span>
				<b>Overview:</b>
				${file.metaData.shortDescription}
			</span>
		</c:if>
		</div>
		<c:if test="${file.metaData.longDescription != null}">
			<div>
				<hr>
				<b>Description:</b>
				<p>${file.metaData.longDescription}</p>
			</div>
		</c:if>
	</body>
</html>