package perf;

import org.junit.Test;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class StaxPerfTest {
    private static final Logger LOGGER = Logger.getLogger(StaxPerfTest.class.getSimpleName());

    @Test
    public void writeXml() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        FileOutputStream out = new FileOutputStream(Paths.get(System.getProperty("user.dir"), "target/myStreamPerf.xml").toFile());
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = null;
        try {
            writer = factory.createXMLStreamWriter(out, "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("contacts");
            for (int i = 0; i < 100000; i++) {
                writer.writeStartElement("firstName");
                writer.writeCharacters("Martin");
                writer.writeEndElement();
                writer.writeStartElement("phoneNumbers");
                writer.writeStartElement("mobile");
                writer.writeCharacters("123456789");
                writer.writeEndElement();
                writer.writeStartElement("home");
                writer.writeCharacters("234567890");
                writer.writeEndElement();
                writer.writeEndElement();
                writer.flush();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
        } catch (XMLStreamException e) {
            LOGGER.severe("Failed to create xml file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (XMLStreamException e) {
                    LOGGER.severe("Failed to close file: " + e.getMessage());
                }
            }
        }
        LOGGER.info("write duration=" + (System.currentTimeMillis() - start));
    }

    @Test
    public void readXml() throws FileNotFoundException, XMLStreamException {
        long start = System.currentTimeMillis();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = null;
        try {
            parser = factory.createXMLStreamReader(new FileInputStream(Paths.get(System.getProperty("user.dir"), "target/myStreamPerf.xml").toFile()));
            while (true) {
                int event = parser.next();
                if (event == XMLStreamConstants.END_DOCUMENT) {
                    parser.close();
                    break;
                }
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
        LOGGER.info("read duration=" + (System.currentTimeMillis() - start));
    }
}
