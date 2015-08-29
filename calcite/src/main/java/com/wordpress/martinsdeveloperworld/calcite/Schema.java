package com.wordpress.martinsdeveloperworld.calcite;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Schema {
	private static final Logger LOGGER = Logger.getLogger(Schema.class.getName());
	public Person[] persons;
	public Address[] addresses;

	public static Schema getInstance() {
		LOGGER.log(Level.INFO, "Creating schema...");
		DataFactory dataFactory = new DataFactory(0);
		int numberOfPersons = 10000000;
		Schema schema = new Schema();
		schema.persons = new Person[numberOfPersons];
		schema.addresses = new Address[numberOfPersons];
		for (int i = 0; i < numberOfPersons; i++) {
			Person person = dataFactory.getNextPerson(i);
			schema.persons[i] = person;
			schema.addresses[i] = dataFactory.getNextAddress(person);
		}
		LOGGER.log(Level.INFO, "Created schema.");
		return schema;
	}
}
