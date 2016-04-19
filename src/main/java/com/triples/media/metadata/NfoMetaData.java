package com.triples.media.metadata;

import java.io.File;

// Meant for <file>.nfo
public class NfoMetaData extends XMLMetaData {
	public NfoMetaData(File metaDataFile) {
		super(metaDataFile);
		
		setTitle(getElementText("title"));
		setYear(getElementInteger("year"));
		setLongDescription(getElementText("plot"));
		setShortDescription(getElementText("outline"));
	}
}
