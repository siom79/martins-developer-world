package jsonp;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class JsonPExample {
    private static final Logger LOGGER = Logger.getLogger(JsonPExample.class.getSimpleName());

    public static void main(String[] args) {
        createJsonFile();
        readJsonFile();
    }

    private static void readJsonFile() {
        try (JsonReader jsonReader = Json.createReader(new FileReader(Paths.get(System.getProperty("user.dir"), "target/myData.json").toString()))) {
            JsonStructure jsonStructure = jsonReader.read();
            JsonValue.ValueType valueType = jsonStructure.getValueType();
            if (valueType == JsonValue.ValueType.OBJECT) {
                JsonObject jsonObject = (JsonObject) jsonStructure;
                JsonValue firstName = jsonObject.get("firstName");
                LOGGER.info("firstName=" + firstName);
                JsonValue emailAddresses = jsonObject.get("phoneNumbers");
                if (emailAddresses.getValueType() == JsonValue.ValueType.ARRAY) {
                    JsonArray jsonArray = (JsonArray) emailAddresses;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonValue jsonValue = jsonArray.get(i);
                        LOGGER.info("emailAddress(" + i + "): " + jsonValue);
                    }
                }
            } else {
                LOGGER.warning("First object is not of type " + JsonValue.ValueType.OBJECT);
            }
        } catch (FileNotFoundException e) {
            LOGGER.severe("Failed to open file: " + e.getMessage());
        }
    }

    private static void createJsonFile() {
        JsonObject model = Json.createObjectBuilder()
                .add("firstName", "Martin")
                .add("phoneNumbers", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("mobile", "1234 56789"))
                        .add(Json.createObjectBuilder()
                                .add("home", "2345 67890")))
                .build();
        try (JsonWriter jsonWriter = Json.createWriter(new FileWriter(Paths.get(System.getProperty("user.dir"), "target/myData.json").toString()))) {
            jsonWriter.write(model);
        } catch (IOException e) {
            LOGGER.severe("Failed to create file: " + e.getMessage());
        }
    }
}
