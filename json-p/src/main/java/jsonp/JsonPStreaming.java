package jsonp;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class JsonPStreaming {
    private static final Logger LOGGER = Logger.getLogger(JsonPStreaming.class.getSimpleName());

    public static void main(String[] args) {
        writeJsonStream();
        readJsonStream();
    }

    private static void readJsonStream() {
            try (FileReader fileReader = new FileReader(Paths.get(System.getProperty("user.dir"), "target/myStream.json").toString())) {
                JsonParser parser = Json.createParser(fileReader);
                while (parser.hasNext()) {
                    JsonParser.Event event = parser.next();
                    switch (event) {
                        case START_OBJECT:
                            LOGGER.info("{");
                            break;
                        case END_OBJECT:
                            LOGGER.info("}");
                            break;
                    case START_ARRAY:
                        LOGGER.info("[");
                        break;
                    case END_ARRAY:
                        LOGGER.info("]");
                        break;
                    case KEY_NAME:
                        LOGGER.info("\"" + parser.getString() + "\"");
                        break;
                    case VALUE_STRING:
                        LOGGER.info("\"" + parser.getString() + "\"");
                        break;
                    case VALUE_NUMBER:
                        LOGGER.info(String.valueOf(parser.getLong()));
                        break;
                    default:
                        LOGGER.warning("Unhandled parser event: " + event.name());
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Failed to read file: " + e.getMessage());
        }
    }

    private static void writeJsonStream() {
        try (FileWriter writer = new FileWriter(Paths.get(System.getProperty("user.dir"), "target/myStream.json").toString())) {
            JsonGenerator gen = Json.createGenerator(writer);
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
            gen.close();
        } catch (IOException e) {
            LOGGER.severe("Failed to write to file: " + e.getMessage());
        }
    }
}