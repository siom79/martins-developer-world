JSON Processing
===============

JSON (JavaScript Object Notation) is a compact text file format that can be used to store and transfer data. It has become very popular over the years as it is very simple to read and parse. Beyond that each JSON construct should be valid JavaScript and it should be possible to evaluate it using JavaScript eval() function. The latter has made it popular in the web developer community as JSON data received from the server can be easily evaluated on the client side.
The popularity of JSON has created the demand to use it also on the server-side. Over the time different Java libraries have been developed that support reading and writing JSON data (e.g. [google-gson](https://code.google.com/p/google-gson/), [flexjson](http://flexjson.sourceforge.net/) or [Jackson](http://jackson.codehaus.org/)). Hence it was only a question of time until JSON processing becomes a part of the Java EE specification.
In the last article I have thrown light on the new concurrency features of Java EE 7. In this article we want to explore the new feature JSON Processing.
After these introductory words it is time to put our hands on some code. The following code shows how to write a very simple JSON file:

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
	
The builder API makes it easy to append the next call to the previous one. I still need to get used to the fact that you have to call createArrayBuilder() or createObjectBuilder() everytime you want to create an array or an object.
When reading a JSON file you have to determine the type you are currently reading by calling getValueType():

	try (JsonReader jsonReader = Json.createReader(new FileReader(Paths.get(System.getProperty("user.dir"), "target/myData.json").toString()))) {
		JsonStructure jsonStructure = jsonReader.read();
		JsonValue.ValueType valueType = jsonStructure.getValueType();
		if (valueType == JsonValue.ValueType.OBJECT) {
			JsonObject jsonObject = (JsonObject) jsonStructure;
			JsonValue firstName = jsonObject.get("firstName");
			...
			
Functionality to map a complete JSON file to an existing structure of Java classes (someting like JAX-B for XML files) is not part of the standard. But instead of that JSON-P offers a complete streaming API for reading huge amount of JSON data:

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
	
The code for the streaming API reads very similar to the normal API. The main difference is that you have to mark the end of an array or object with writeEnd().
Reading a JSON file looks very similar to the [Streaming API for XML (StAX)](http://stax.codehaus.org/Home):

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
	...
	
Having just said that the above code looks very similar to StAX code, let's try to compare both implementations by the means of file size and runtime. The following diagram shows the average execution times for writing and reading a JSON/XML file with 100.000 entries. One entry consists of a simple object with a name and an array of two telephone numbers:

	{"firstName":"Martin","phoneNumbers":[{"mobile":123456789,"home":"2345 67890"}]}
	
This is how it looks in XML:

	<firstName>Martin</firstName><phoneNumbers><mobile>123456789</mobile><home>234567890</home></phoneNumbers>
	

It is surprising that the StAX implementation is this much slower when writing big files. When we take a closer look at the file size, we see that the XML file is about 1.31 times bigger than the JSON file, although it contains the same data.

Conclusion: JSON-P provides a standardized API for JSON processing together with a StAX like streaming API. Although it still lacks a binding to a Java class structure, it performs even better than the StAX implementation by a more compact file size.

PS: The implementations used in the comparison are listed below:
	<dependency>
		<groupId>org.glassfish</groupId>
		<artifactId>javax.json</artifactId>
		<version>1.0</version>
	</dependency>
	<dependency>
		<groupId>stax</groupId>
		<artifactId>stax</artifactId>
		<version>1.2.0</version>
	</dependency>

