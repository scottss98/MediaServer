package com.triples.media.metadata;

import java.io.File;

// Mean for mymovies.xml
public class MyMoviesXMLMetaData extends XMLMetaData {
	public MyMoviesXMLMetaData(File metaDataFile) {
		super(metaDataFile);
		
		setTitle(getElementText("LocalTitle"));
		setYear(getElementInteger("ProductionYear"));
		setLongDescription(getElementText("Description"));
	}
}
