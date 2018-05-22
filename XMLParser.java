import org.w3c.dom.Attr;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class XMLParser {
    static final String PROGRAM = "program";
    static final String PID = "pid";
    static final String TIME_REMAINING = "timeRemaining";
    static final String TIME_OF_ARRIVAL = "timeOfArrival";

    XMLEventReader eventReader;

    public XMLParser(String file) {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(file);
            this.eventReader = inputFactory.createXMLEventReader(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public List<Program> readFile() {
        List<Program> list = null;
        while (this.eventReader.hasNext()) {

        }
        return list;
    }

    public Program readNextProgram() {
        try {
            if (this.eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(PROGRAM)) {
                        int pid = 0;
                        int timeRemaining = 0;
                        int timeOfArrival = 0;
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(PID)){
                                pid = Integer.valueOf(attribute.getValue());
                            }
                            if (attribute.getName().toString().equals(TIME_REMAINING)) {
                                timeRemaining = Integer.valueOf(attribute.getValue());
                            }
                            if (attribute.getName().toString().equals(TIME_OF_ARRIVAL)) {
                                timeOfArrival = Integer.valueOf(attribute.getValue());
                            }
                        }
                        return new Program(pid, timeRemaining, timeOfArrival);
                    }
                }
            }
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

}
