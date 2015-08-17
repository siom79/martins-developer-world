package com.wordpress.mdw;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainTest {
	private static final String[] firstNames = new String[]{"JAMES", "JOHN", "ROBERT", "MICHAEL", "WILLIAM", "DAVID", "RICHARD", "CHARLES", "JOSEPH", "THOMAS"};
	private static final String[] lastNames = new String[]{"SMITH", "JOHNSON", "WILLIAMS BROWN", "JONES", "MILLER", "DAVIS", "GARCIA", "RODRIGUEZ", "WILSON"};
	private static final String[] cities = new String[]{"New York, N.Y.", "Los Angeles, Calif.", "Chicago, Ill.", "Houston, Tex.", "Philadelphia, Pa.", "Phoenix, Ariz.", "San Antonio, Tex.", "San Diego, Calif.", "Dallas, Tex.", "San Jose, Calif.", "Austin, Tex.", "Indianapolis, Ind.", "Jacksonville, Fla.", "San Francisco, Calif.", "Columbus, Ohio", "Charlotte, N.C.", "Fort Worth, Tex.", "Detroit, Mich.", "El Paso, Tex.", "Memphis, Tenn.", "Seattle, Wash.", "Denver, Colo.", "Washington, DC", "Boston, Mass.", "Nashville-Davidson, Tenn.1", "Baltimore, Md.", "Oklahoma City, Okla.", "Louisville-Jefferson County, Ky.2", "Portland, Ore.", "Las Vegas, Nev.", "Milwaukee, Wis.", "Albuquerque, N.M.", "Tucson, Ariz.", "Fresno, Calif.", "Sacramento, Calif.", "Long Beach, Calif.", "Kansas City, Mo.", "Mesa, Ariz.", "Virginia Beach, Va.", "Atlanta, Ga.", "Colorado Springs, Colo.", "Omaha, Nebr.", "Raleigh, N.C.", "Miami, Fla.", "Oakland, Calif.", "Minneapolis, Minn.", "Tulsa, Okla.", "Cleveland, Ohio", "Wichita, Kans.", "Arlington, Tex."};
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private Random random;

	@Before
	public void before() {
		this.random = new Random(System.currentTimeMillis());
	}

	@Test
	public void testSimple() throws IOException {
		Main main = new Main();
		Path csvPath = Paths.get(System.getProperty("user.dir"), "target", "test.cvs");
		Path parquetPath = Paths.get(System.getProperty("user.dir"), "target", "test.par");
		if (Files.exists(parquetPath)) {
			Files.delete(parquetPath);
		}
		Files.write(csvPath, createFileContent(), Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		main.run(new String[]{csvPath.toString(), parquetPath.toString()});
	}

	private List<String> createFileContent() {
		List<String> lines = new ArrayList<>();
		lines.add("message csv{required binary firstName = 1; required binary lastName = 2; required binary dateOfBirth = 3; required binary placeOfBirth = 4;}");
		for (int i = 0; i < 10000000; i++) {
			lines.add(getNextFirstName() + Main.SEPARATOR + getNextLastName() + Main.SEPARATOR + getNextDate() + Main.SEPARATOR + getNextCity());
		}
		return lines;
	}

	private String getNextFirstName() {
		return firstNames[random.nextInt(firstNames.length)];
	}

	private String getNextLastName() {
		return lastNames[random.nextInt(lastNames.length)];
	}

	private String getNextCity() {
		return cities[random.nextInt(cities.length)];
	}

	public String getNextDate() {
		int year = 1950 + random.nextInt(66);
		int month = random.nextInt(12);
		int day = random.nextInt(28) + 1;
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month);
		gc.set(Calendar.DAY_OF_MONTH, day);
		return simpleDateFormat.format(gc.getTime());
	}
}