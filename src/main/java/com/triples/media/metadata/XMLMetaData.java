package com.triples.media.metadata;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLMetaData extends MediaMetaData {
	private Element root;
	
	public XMLMetaData(File metaDataFile) {
		super(metaDataFile);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(metaDataFile);
			root = doc.getDocumentElement();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			//e.printStackTrace();
		}
	}

	public String getElementText(String tagName) {
		if (root != null) {
			NodeList nodeList = root.getElementsByTagName(tagName);
			if (nodeList != null) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					return node.getTextContent();
				}
			}
		}
		return null;
	}

	public Integer getElementInteger(String tagName) {
		String numberString = getElementText(tagName);
		if (numberString != null) {
			try {
				return Integer.parseInt(numberString);
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}
}
