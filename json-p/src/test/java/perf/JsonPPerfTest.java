package perf;

import org.junit.Test;

import javax.json.*;
import javax.json.stream.JsonGenerator;
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
        try (JsonReader jsonReader = Json.createReader(new FileReader(Paths.get(System.getProperty("user.dir"), "target/myStreamPerf.json").toString()))) {
            JsonStructure jsonStructure = jsonReader.read();
            JsonValue.ValueType valueType = jsonStructure.getValueType();
            if (valueType == JsonValue.ValueType.ARRAY) {
                JsonArray jsonArray = (JsonArray) jsonStructure;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonValue jsonValue = jsonArray.get(i);
                    if (jsonValue.getValueType() == JsonValue.ValueType.OBJECT) {
                        JsonObject jsonObject = (JsonObject) jsonValue;
                        JsonValue firstName = jsonObject.get("firstName");
                        JsonValue emailAddresses = jsonObject.get("phoneNumbers");
                        if (emailAddresses.getValueType() == JsonValue.ValueType.ARRAY) {
                            JsonArray emailArray = (JsonArray) emailAddresses;
                            for (int j = 0; j < emailArray.size(); j++) {
                                jsonArray.get(j);
                            }
                        }
                    } else {
                        LOGGER.warning("Object is not of type " + JsonValue.ValueType.OBJECT + ": " + valueType);
                    }
                }
            } else {
                LOGGER.warning("First object is not of type " + JsonValue.ValueType.ARRAY);
            }
        } catch (FileNotFoundException e) {
            LOGGER.severe("Failed to open file: " + e.getMessage());
        }
        LOGGER.info("read duration=" + (System.currentTimeMillis() - start));
    }
}
