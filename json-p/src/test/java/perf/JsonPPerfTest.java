package perf;

import org.junit.Test;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class JsonPPerfTest {
    private static final Logger LOGGER = Logger.getLogger(JsonPPerfTest.class.getSimpleName());

    @Test
    public void writeJson() {
        long start = System.currentTimeMillis();
        try (FileWriter writer = new FileWriter(Paths.get(System.getProperty("user.dir"), "target/myStreamPerf.json").toString())) {
            JsonGenerator gen = Json.createGenerator(writer);
            gen.writeStartArray();
            for (int i = 0; i < 100000; i++) {
                gen
                        .writeStartObject()
                        .write("firstName", "Martin")
                        .writeStartArray("phoneNumbers")
                        .writeStartObject()
                        .write("mobile", 123456789)
                        .write("home", "2345 67890")
                        .writeEnd()
                        .writeEnd()
                        .writeEnd();
            }
            gen.writeEnd();
            gen.close();
        } catch (IOException e) {
            LOGGER.severe("Failed to write to file: " + e.getMessage());
        }
        LOGGER.info("write duration=" + (System.currentTimeMillis() - start));
    }

    @Test
    public void readJson() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        try (FileReader fileReader = new FileReader(Paths.get(System.getProperty("user.dir"), "target/myStreamPerf.json").toString())) {
            JsonParser parser = Json.createParser(fileReader);
            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                        break;
                    case END_OBJECT:
                        break;
                    case START_ARRAY:
                        break;
                    case END_ARRAY:
                        break;
                    case KEY_NAME:
                        break;
                    case VALUE_STRING:
                        break;
                    case VALUE_NUMBER:
                        break;
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Failed to read file: " + e.getMessage());
        }
        LOGGER.info("read duration=" + (System.currentTimeMillis() - start));
    }
}
