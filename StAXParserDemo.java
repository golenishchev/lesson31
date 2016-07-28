package com.example.lesson31;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StAXParserDemo {
    public void parseXMLDocument() {
        boolean bTitle = false;
        boolean bType = false;
        boolean bAmount = false;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(
                    new FileReader("src\\com\\example\\lesson31\\computerStore.xml"));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("computer")) {
                            System.out.print("Computer: ");
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            String id = attributes.next().getValue();
                            System.out.print("ID=" + id + " ");
                        } else if (qName.equalsIgnoreCase("title")) {
                            bTitle = true;
                        } else if (qName.equalsIgnoreCase("type")) {
                            bType = true;
                        } else if (qName.equalsIgnoreCase("amount")) {
                            bAmount = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bTitle) {
                            System.out.print("Title=" + characters.getData() + " ");
                            bTitle = false;
                        }
                        if (bType) {
                            System.out.print("Type=" + characters.getData() + " ");
                            bType = false;
                        }
                        if (bAmount) {
                            System.out.print("Amount=" + characters.getData() + " ");
                            bAmount = false;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equalsIgnoreCase("computer")) {
                            System.out.println();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
