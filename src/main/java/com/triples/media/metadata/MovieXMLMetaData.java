package com.triples.media.metadata;

import java.io.File;

//Mean for movie.xml
public class MovieXMLMetaData extends XMLMetaData {
	public MovieXMLMetaData(File metaDataFile) {
		super(metaDataFile);
		
		setTitle(getElementText("LocalTitle"));
		setYear(getElementInteger("ProductionYear"));
		setLongDescription(getElementText("Overview"));
		setShortDescription(getElementText("ShortOverview"));
	}
}
