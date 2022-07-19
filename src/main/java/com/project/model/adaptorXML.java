package com.project.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class adaptorXML extends Adaptor{
    @Override
    public Map<String, List<Reading>> processFile(final File file) {

        Map<String, List<Reading>> mapReadings = new HashMap<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            // get readings
            NodeList list = doc.getElementsByTagName("reading");

            int linesCounter = 0;
            List<Reading> listReadings = new ArrayList<>();

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String clientID = element.getAttribute("clientID");
                    String period = element.getAttribute("period");
                    String readingValue = element.getTextContent();

                    Reading reading = new Reading(period, Integer.parseInt(readingValue));
                    listReadings.add(reading);
                    linesCounter++;
                    if (linesCounter == 12) {
                        mapReadings.put(clientID, listReadings);
                        listReadings = new ArrayList<>();
                        linesCounter = 0;
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return mapReadings;
    }
}
