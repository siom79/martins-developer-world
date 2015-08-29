package com.wordpress.martinsdeveloperworld.calcite;

import java.util.Random;

public class DataFactory {
	private Random random;
	private String[] cities = new String[] { "New York", "Los Angeles", "Chicago", "Houston", "Philadelphia", "Phoenix", "San Antonio", "San Diego", "Dallas", "San Jose",
			"Austin", "Jacksonville", "San Francisco", "Indianapolis", "Columbus", "Fort Worth", "Charlotte", "Detroit", "El Paso", "Seattle", "Denver", "Washington", "Memphis",
			"Boston", "Nashville", "Baltimore", "Oklahoma City", "Portland", "Las Vegas", "Louisville", "Milwaukee", "Albuquerque", "Tucson", "Fresno", "Sacramento", "Long Beach",
			"Kansas City", "Mesa", "Atlanta", "Virginia Beach", "Omaha", "Colorado Springs", "Raleigh", "Miami", "Oakland", "Minneapolis", "Tulsa", "Cleveland", "Wichita",
			"New Orleans", "Arlington", "Bakersfield", "Tampa", "Aurora", "Honolul", "Anaheim", "Santa Ana", "Corpus Christi", "Riverside", "St. Louis", "Lexington", "Pittsburgh",
			"Stockton", "Anchorage", "Cincinnati", "Saint Paul", "Greensboro", "Toledo", "Newark", "Plano", "Henderson", "Lincoln", "Orlando", "Jersey City", "Chula Vista",
			"Buffalo", "Fort Wayne", "Chandler", "St. Petersburg", "Laredo", "Durham", "Irvine", "Madison", "Norfolk", "Lubbock", "Gilbert", "Winston Salem", "Glendale", "Reno",
			"Hialeah", "Garland", "Chesapeake", "Irving", "North Las Vegas", "Scottsdale", "Baton Rouge", "Fremont", "Richmond", "Boise", "San Bernardino" };
	private String firstNames[] = new String[] { "Noah", "Liam", "Mason", "Jacob", "William", "Ethan", "Michael", "Alexander", "James", "Daniel", "Elijah", "Benjamin", "Logan",
			"Aiden", "Jayden", "Matthew", "Jackson", "David", "Lucas", "Joseph", "Anthony", "Andrew", "Samuel", "Gabriel", "Joshua", "John", "Carter", "Luke", "Dylan",
			"Christopher", "Isaac", "Oliver", "Henry", "Sebastian", "Caleb", "Owen", "Ryan", "Nathan", "Wyatt", "Hunter", "Jack", "Christian", "Landon", "Jonathan", "Levi",
			"Jaxon", "Julian", "Isaiah", "Eli", "Aaron", "Charles", "Connor", "Cameron", "Thomas", "Jordan", "Jeremiah", "Nicholas", "Evan", "Adrian", "Gavin", "Robert",
			"Brayden", "Grayson", "Josiah", "Colton", "Austin", "Angel", "Jace", "Dominic", "Kevin", "Brandon", "Tyler", "Parker", "Ayden", "Jason", "Jose", "Ian", "Chase",
			"Adam", "Hudson", "Nolan", "Zachary", "Easton", "Blake", "Jaxson", "Cooper", "Lincoln", "Xavier", "Bentley", "Kayden", "Carson", "Brody", "Asher", "Nathaniel",
			"Ryder", "Justin", "Leo", "Juan", "Luis", "Camden", "Tristan", "Emma", "Olivia", "Sophia", "Isabella", "Ava", "Mia", "Emily", "Abigail", "Madison", "Charlotte",
			"Harper", "Sofia", "Avery", "Elizabeth", "Amelia", "Evelyn", "Ella", "Chloe", "Victoria", "Aubrey", "Grace", "Zoey", "Natalie", "Addison", "Lillian", "Brooklyn",
			"Lily", "Hannah", "Layla", "Scarlett", "Aria", "Zoe", "Samantha", "Anna", "Leah", "Audrey", "Ariana", "Allison", "Savannah", "Arianna", "Camila", "Penelope",
			"Gabriella", "Claire", "Aaliyah", "Sadie", "Riley", "Skylar", "Nora", "Sarah", "Hailey", "Kaylee", "Paisley", "Kennedy", "Ellie", "Peyton", "Annabelle", "Caroline",
			"Madelyn", "Serenity", "Aubree", "Lucy", "Alexa", "Alexis", "Nevaeh", "Stella", "Violet", "Genesis", "Mackenzie", "Bella", "Autumn", "Mila", "Kylie", "Maya", "Piper",
			"Alyssa", "Taylor", "Eleanor", "Melanie", "Naomi", "Faith", "Eva", "Katherine", "Lydia", "Brianna", "Julia", "Ashley", "Khloe", "Madeline", "Ruby", "Sophie",
			"Alexandra", "London", "Lauren", "Gianna", "Isabelle", "Alice", "Vivian", "Hadley", "Jasmine" };
	private String lastNames[] = new String[] { "Smith", "Brown", "Johnson", "Jones", "Williams", "Davis", "Miller", "Wilson", "Taylor", "Clark", "White", "Moore", "Thompson",
			"Allen", "Martin", "Hall", "Adams", "Thomas", "Wright", "Baker", "Walker", "Anderson", "Lewis", "Harris", "Hill", "King", "Jackson", "Lee", "Green", "Wood", "Parker",
			"Campbell", "Young", "Robinson", "Stewart", "Scott", "Rogers", "Roberts", "Cook", "Phillips", "Turner", "Carter", "Ward", "Foster", "Morgan", "Howard", "Cox", "Jr",
			"Bailey", "Richardson", "Reed", "Russell", "Edwards", "Morris", "Wells", "Palmer", "Ann", "Mitchell", "Evans", "Gray", "Wheeler", "Warren", "Cooper", "Bell",
			"Collins", "Carpenter", "Stone", "Cole", "Ellis", "Bennett", "Harrison", "Fisher", "Henry", "Spencer", "Watson", "Porter", "Nelson", "James", "Marshall", "Butler",
			"Hamilton", "Tucker", "Stevens", "Webb", "May", "West", "Reynolds", "Hunt", "Barnes", "Perkins", "Brooks", "Long", "Price", "Fuller", "Powell", "Perry", "Alexander",
			"Rice", "Hart", "Ross", "Arnold", "Shaw", "Ford", "Pierce", "Lawrence", "Henderson", "Freeman", "Mason", "Andrews", "Graham", "Chapman", "Hughes", "Mills", "Gardner",
			"Jordan", "Ball", "Nichols", "Gibson", "Greene", "Wallace", "Baldwin", "Day", "Deaver", "Sherman", "Murphy", "Lane", "Knight", "Holmes", "Bishop", "Kelly", "French",
			"Myers", "Mentioned", "Patterson", "Elizabeth", "Case", "Curtis", "Simmons", "Jenkins", "Berry", "Hopkins", "Clarke", "Fox", "Gordon", "Hunter", "Robertson", "Payne",
			"Johnston", "Barker", "Grant", "Murray", "Church", "Webster", "Richards", "Sanders", "Page", "Crawford", "Duncan", "Warner", "Hale", "Kennedy", "Rose", "Carr",
			"Black", "Bates", "Chase", "Pratt", "Austin", "Hawkins", "Stephens", "Ferguson", "Parsons", "Simpson", "Armstrong", "Fowler", "Potter", "Hayes", "Griffin", "Bryant",
			"Weaver", "Boyd", "Townsend", "Coleman", "Holland", "Stanley", "Hicks", "Gilbert", "Bradley", "Chandler", "Barber", "Bartlett", "Woods", "Sutton", "Montgomery",
			"Dean", "Morse", "Brewer", "Newton", "Sullivan", "Jane", "Graves", "Phelps", "Hubbard", "Fletcher", "Drake", "Douglas", "Dunn", "Burton", "Sharp", "Mcdonald",
			"Elliott", "Eaton", "Harvey", "Peterson", "Franklin", "Morrison", "George", "Lincoln", "Snyder", "Hudson", "Snow", "Cobb", "England", "Gregory", "Wilcox", "Bowen",
			"Howell", "Cunningham", "Bowman", "Norton", "Lord", "Willis", "Holt", "Little", "Williamson", "Davidson", "Harrington", "Marsh", "County", "Daigle", "Leonard",
			"Harper", "Dixon", "Matthews", "Ray", "Mary", "Whitney", "Burns", "Boone", "Peck", "Bradford", "Owen", "Garrett", "Barrett", "Hammond", "Oliver", "John", "Mann",
			"Stuart", "Peters", "Welch", "Reeves", "Hull", "Caldwell", "Rhodes", "Howe", "Owens", "Gates", "Bush", "Pearson", "Newman", "Frost", "Wagner", "Bruce", "Kimball",
			"Abbott", "Plantagenet", "Robbins", "Briggs", "Wade", "Mullins", "Woodward", "Stafford", "Barton", "Todd", "Goodwin", "Dyer", "Horton", "Watkins", "Cummings",
			"Sparks", "Bacon", "Gould", "Sawyer", "Neal", "Kelley", "Reid", "Cooke", "Osborne", "Hancock" };

	public DataFactory(int threadId) {
		random = new Random(System.currentTimeMillis() + threadId);
	}

	public Person getNextPerson(int i) {
		Person person = new Person();
		person.id = i;
		person.firstName = firstNames[random.nextInt(firstNames.length)];
		person.lastName = lastNames[random.nextInt(lastNames.length)];
		return person;
	}

	public Address getNextAddress(Person person) {
		Address address = new Address();
		address.city = cities[random.nextInt(cities.length)];
		address.personId = person.id;
		return address;
	}

	public String getNextCity() {
		return cities[random.nextInt(cities.length)];
	}

	public String getNextLastName() {
		return lastNames[random.nextInt(lastNames.length)];
	}
}
