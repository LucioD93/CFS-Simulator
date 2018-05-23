import org.w3c.dom.Attr;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLParser {
    static final String PROGRAM = "Process";
    static final String PID = "Id";
    static final String TIME_REMAINING = "ExecutionTime";

    public ArrayList<Program> readXMLFile(String file) {
        ArrayList<Program> programs = new ArrayList<Program>();
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(file);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);
            int pid = 0;
            int timeRemaining = 0;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(PROGRAM)) {

                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(PID)) {
                                pid = Integer.valueOf(attribute.getValue());
                            }
                        }
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart().equals(TIME_REMAINING)) {
                            event = eventReader.nextEvent();
                            timeRemaining = Integer.valueOf(event.asCharacters().getData());
                            continue;
                        }
                    }
                }

                    if (event.isEndElement()) {
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equals(PROGRAM)) {
                            programs.add(new Program(pid, timeRemaining));
                        }
                    }

                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return programs;
    }


}
