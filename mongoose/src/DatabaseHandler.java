
import java.sql.*;

public class DatabaseHandler {

	/**
	 * Class to handle connecting to and querying the database, then returning its results.
	 * All interaction with the database should go through one instance of this class.
	 * 
	 * Made by Sean McCarthy
	 */
	
	//Declare database connection object here so its visible for all functions
	private static Connection database = null;

	public DatabaseHandler() {
		/**
		 * Connects to a local database called Restaurant.db then checks if certain tables exist,
		 * if they don't then it creates them. 
		 */
		//Basic variables for connecting to the database
		String databaseFilePath = "jdbc:mysql://localhost/restaurant";
		String databaseJDBCDriver = "com.mysql.jdbc.Driver";

		//Incredibly insecure username and password
		String mySQLUsername = "root";
		String mySQLPassword = "password";

		//Connect to the database on my local machine, displaying messages if it works
		try {
			Class.forName(databaseJDBCDriver);
			database = DriverManager.getConnection(databaseFilePath, mySQLUsername, mySQLPassword);
			
			if (database != null) {
				System.out.println("Successfully connected to database: " + databaseFilePath);
			}
		}
		catch ( Exception e ) {
			System.out.println("Error connecting to database, dummy functions still available.");
		}
	}
	
	//This class won't have a main function in the final version, it's just here for testing
	public static void main( String[] args) {
		/**
		 * Connects to a local database called Restaurant.db then checks if certain tables exist,
		 * if they don't then it creates them. 
		 */
		
		//Basic variables for connecting to the database
		String databaseFilePath = "jdbc:mysql://localhost/restaurant";
		String databaseJDBCDriver = "com.mysql.jdbc.Driver";
		
		//Incredibly insecure username and password
		String mySQLUsername = "root";
		String mySQLPassword = "password";
		
		//Connect to the database on my local machine, displaying messages if it works
		try {
			Class.forName(databaseJDBCDriver);
			database = DriverManager.getConnection(databaseFilePath, mySQLUsername, mySQLPassword);
			
			if (database != null) {
				System.out.println("Successfully connected to database: " + databaseFilePath);
			}
		}
		catch ( Exception e ) {
			System.out.println("Error connecting to database");
		      e.printStackTrace();
		}
		
//		//Create to execute a statement, storing the output in variable 'result'
//		ResultSet results = null;
//		try {
//			Statement statement = null;
//			System.out.println("Building SQL statement");
//			
//			statement = database.createStatement();
//			String queryText = "SELECT * FROM menu";
//			
//			results = statement.executeQuery(queryText);
//		}
//		catch (Exception e){
//			System.out.println("Error when creating and executing query");
//			e.printStackTrace();
//		}
//		
//		//Extract usable data from the ResultSet object, just reads everything on the menu for testing purposes.
//		try {
//			while (results.next()) {
//				int id = results.getInt("id");
//				String category = results.getString("category");
//				String name = results.getString("name");
//				int price = results.getInt("price");
//				System.out.printf("ID: %d, Category: %s, Name: %s, Price: %d \n", id, category, name, price);
//			}
//		}
//		catch (Exception e) {
//			System.out.println("Error reading data from result set");
//			e.printStackTrace();
//		}
//		//Testing code
//		System.out.println("THERE ARE " + returnMenuItemCount() + " ITEMS IN THE MENU");
//		System.out.println("Testing order id retrieval...");
//		int[] ordersResults = listActiveOrders();
//		for (int i = 0; i<ordersResults.length; i++){
//			System.out.println(ordersResults[i]);
//		}
//		System.out.println("THE ITEM WITH ID 5 IS: " + returnItemName(5));
//		getOrderInfo(1);
//		getOrderInfo(2);
		
		int[][] allOrderInfo = getAllOrderInfo();
		for (int i = 0; i < allOrderInfo.length; i++) {
			System.out.printf("Order id: %d | Table id: %d | Received: %d | Waiting: %d \n", allOrderInfo[i][0], allOrderInfo[i][1], allOrderInfo[i][2], allOrderInfo[i][3]);
		}
	}
	
	public static int[] listActiveOrders() {
		/**
		 * Returns the id's of all active orders in the database, these id's can then be handed to the returnBill function 
		 * to get info on the order.
		 */
		String countQueryText = "SELECT COUNT( DISTINCT order_id) FROM orders where status = 'open'";
		String listQueryText = "SELECT DISTINCT order_id FROM orders where status = 'open'";
		int[] orders = {0};
		int numberOfOrders = 0;
		
		try{
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(countQueryText);
			results.next();
			numberOfOrders = results.getInt("COUNT( DISTINCT order_id)");
			orders = new int[numberOfOrders];
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println( "Error retrieving number of orders");
			return orders;
		}
		
		try{
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(listQueryText);
			
			for (int i = 0; results.next(); i++) {
				orders[i] = results.getInt("order_id");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println( "Error reading results into array");
			return orders;
		}
		return orders;
	}

	public boolean createOrder(int table) {
		/**
		 * Accepts an id for the table_id and creates an order_id associated with it, as well as a time arrived in the database.
		 * Order id's are persistent so if they order 3 mains then later
		 *  order dessert the order id won't change)
		 *  
		 *  @returns Boolean, true means the order was placed properly, false means something went wrong.
		 */

		//Firstly, check there isn't an open order still associated with the table in question
		String queryText = String.format("SELECT COUNT(order_id) FROM orders WHERE table_id='%d' AND status = 'open';", table);

		try {
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);
			results.next();
			if (results.getInt("COUNT(order_id)") != 0) {
				return false;
			}
			else {
				String maxIdQuery = "SELECT MAX(order_id) FROM orders;";
				results = stmt.executeQuery(maxIdQuery);
				results.next();
				int maxOrderId = results.getInt("MAX(order_id)");
				String statementText = String.format("INSERT INTO orders (order_id, table_id, status, arrived) VALUES (%d, %d, 'open', NOW());", maxOrderId+1, table);
				stmt.execute(statementText);
				return true;
			}

		}
		catch (Exception e) {
			System.out.println("Error creating new order" + e);
			return false;
		}
	}

	public void addItemsToOrder(String[] items, int tableId) {
		/*
		Accepts an array of strings, each being an item name, and adds each one to items associated with a given orderId
		 */
		//if there is no open order associated with the table, make one
		int[] tableStatus = getTableStatus();
		if (tableStatus[tableId] == 0) {
			createOrder(tableId);
		}

		//Get an order id from the table id
		int orderId = 0;
		try {
			String orderQueryText = "SELECT * FROM orders WHERE tableId = %d AND status = 'open'";
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(orderQueryText);
			results.next();
			orderId = results.getInt("order_id");
		}
		catch (Exception e) {
			System.out.println("Error getting order id from table id");
		}

		//Place it in with the order id
		for (String item: items) {
			String statementText = String.format("INSERT INTO items (item_id, order_id, status) VALUES (%d, %d, 'waiting')", getIdFromName(item), orderId);
			try {
				Statement stmt = database.createStatement();
				stmt.execute(statementText);
			}
			catch (Exception e) {
				System.out.printf("Error inserting name : '%s' into table: 'items'");
			}
		}
	}
	
	public void closeOrder(int id) {
		/**
		 * Changes an orders status to 'closed'
		 */
		String queryText = String.format("UPDATE orders SET status = 'closed' WHERE order_id = %d", id);
		try {
			Statement stmt = database.createStatement();
			stmt.execute(queryText);
		}
		catch (Exception e) {
			System.out.println("Error closing order id: " + id);
		}
	}

	public String[] getOrderMetadata(int id) {
		/*
		Takes an id and returns time arrived and associated table for that order.
		Does not return the items ordered by that order, use getOrderInfo( ) for that.

		returns in the format {"table_id", "arrived"};
		 */

		String[] returnArray = {"",""};
		try {
			String queryText = String.format("SELECT * FROM orders WHERE order_id = %d;", id);
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);
			results.next();
			String table = String.valueOf(results.getInt("table_id"));
			String arrived = results.getTimestamp("arrived").toString();
			returnArray[0] = table;
			returnArray[1] = arrived;
		}
		catch (Exception e) {
			System.out.println("Error getting order metadata.");
			e.printStackTrace();
		}
		return returnArray;
	}

	public int getOrderItemCount(int id) {
		/*
		Returns the number of items associated with an order
		 */
		int count = 0;

		try {
			String queryText = String.format("SELECT count(item_id) FROM items WHERE order_id = %d;", id);
			Statement stmt = database.createStatement();

			ResultSet results = stmt.executeQuery(queryText);

			results.next();
			count = results.getInt("count(item_id)");
		}
		catch (Exception e) {
			System.out.println("Error getting item count for order: " + id);
		}
		return count;
	}

	public String[][] getOrderContents(int id) {
		/**
		 * Takes an integer id for an order or table, then reads all the relevant order info for that table from
		 *  the database and formats it into a string array then returns it.
		 *
		 *  Returns in the format {"name", "Price", "Status"}
		 *
		 *  Placeholder function until order class is finished
		 */

		String queryText = String.format(	"SELECT * FROM " +
											"items INNER JOIN menu ON items.item_id = menu.id " +
											"WHERE order_id = %d;", id);

		String[][] returnArray = new String[getOrderItemCount(id)][3];

		try {
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);

			int i = 0;
			while (results.next()) {
				returnArray[i][0] = results.getString("name");
				int priceInt = results.getInt("price");
				String priceString = String.format("£%d.%02d", priceInt/100, priceInt%100);
				returnArray[i][1] = String.valueOf(priceInt);
				returnArray[i][2] = results.getString("status");
				i++;
			}
		}
		catch (Exception e) {
			System.out.println("Error getting items for order id " + id);
		}
		return returnArray;
	}

	public String[] getOrderInfo(int id) {
		/**
		 * Takes an integer id for an order or table, then reads all the relevant order info for that table from
		 *  the database and formats it into a string array then returns it.
		 *
		 *  Returns in the format {"name", "Price", "Status"}
		 *  
		 *  Placeholder function until order class is finished
		 */
		String itemCountQueryText = String.format("SELECT item_id, COUNT(item_id) FROM items WHERE order_id = %d GROUP BY item_id", id);
		String tableIdQueryText = String.format("SELECT * FROM orders WHERE order_id = %d", id);
		int uniqueItemCount;
		int[][] itemArray;
		int table_id = -1;
		String status = "error";
		try {
			//Get the number of unique items in the order, and the quantity of each unique item
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(itemCountQueryText);
			results.next();
			uniqueItemCount = results.getInt("COUNT(item_id)");
			itemArray = new int[uniqueItemCount][2];
			results = statement.executeQuery(itemCountQueryText);
			
			//Read through the results and input them into a 2d array
			for (int i = 0; results.next(); i++) {
				itemArray[i][0] = results.getInt("item_id");
				//test code
				System.out.println("Item id: " + itemArray[i][0]);
				//test code
				itemArray[i][1] = results.getInt("COUNT(item_id)");
				System.out.println("Item quantity: "+ itemArray[i][1]);
			}
			
			//Get the order's table_id and status (open or closed)
			statement = database.createStatement();
			results = statement.executeQuery(tableIdQueryText);
			if (results.next()) {
				table_id = results.getInt("table_id");
				status = results.getString("status");
			}
			else {
				System.out.println("Order with associated entries in items table not found in orders table");
			}
			//Test code
			System.out.println("TABLE " + table_id + " STATUS: " + status);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new String[] {"",""};
		}
		
		//TEST CODE REMOVE
		return new String[] {"",""};
	}

	public int getIdFromName(String name) {
		/*
		Gets the item id from a name string
		 */
		String queryText = String.format("SELECT * FROM menu WHERE name = '%s'", name);
		int id = 0;
		try {
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);
			results.next();
			id = results.getInt("id");
		}
		catch (Exception e) {
			System.out.println("Error getting item id from name string");
		}

		return id;
	}

	public String getNameFromId(int id) {
		/*
		Gets an items name from its item id
		 */
		String name = "";
		String queryText = String.format("GET * FROM menu WHERE id = %d", id);
		try {
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);
			results.next();
			name = results.getString("name");
		}
		catch (Exception e) {
			System.out.println("Error getting name from ");
		}
		return name;
	}

	public void confirmDelivered(String name, int orderId) {
		/*
		Changes the status of one item in the items table with the name and orderId specified to delivered.
		 */

		int itemId = getIdFromName(name);
		try {
			String statementText = String.format("UPDATE items SET status = 'received' WHERE order_id = %d AND item_id = %d LIMIT 1",orderId, itemId);
			Statement stmt = database.createStatement();
			stmt.execute(statementText);
		}
		catch (Exception e) {
			System.out.println("Error confirming delivery of item.");
		}
	}
	
	public static int[][] getAllOrderInfo() {
		/**
	 * returns metadata about all orders (order id, table id, items received & items they are still waiting for)
	 */

	try {
		//First, find out how many orders there are
		String orderCountStatementText = "SELECT COUNT(order_id) FROM orders WHERE status = 'open'";
		Statement statement = database.createStatement();
		ResultSet results = statement.executeQuery(orderCountStatementText);
		results.next();
		int orderCount = results.getInt("COUNT(order_id)");

		//Now, create an array of the appropriate size to return
		int[][] returnArray = new int[orderCount][4];

		//Populate the first two columns of the array with order_id and table_id
		String orderIdTableIdStatementText = "SELECT order_id, table_id FROM orders WHERE status = 'open'";
		results = statement.executeQuery(orderIdTableIdStatementText);

		for (int i = 0; results.next(); i++) {
			returnArray[i][0] = results.getInt("order_id");
			returnArray[i][1] = results.getInt("table_id");
		}

		//Now, get the number of items each order have received / are waiting for from the items table
		for (int i = 0; i < returnArray.length; i++) {
			String receivedItemsStatementText = String.format("SELECT COUNT(item_id) FROM items WHERE order_id = %d AND status = 'received'", returnArray[i][0]);
			results = statement.executeQuery(receivedItemsStatementText);
			if (results.next()) {
				returnArray[i][2] = results.getInt("COUNT(item_id)");
			}
		}

		for (int i = 0; i < returnArray.length; i++) {
			String waitingItemsStatementText = String.format("SELECT COUNT(item_id) FROM items WHERE order_id = %d AND status = 'waiting'", returnArray[i][0]);
			results = statement.executeQuery(waitingItemsStatementText);
			if (results.next()) {
				returnArray[i][3] = results.getInt("COUNT(item_id)");
			}
		}

		//Finally, return the now populated array
		return returnArray;

	}
		catch (Exception e) {
			System.out.print(e);
			return new int[1][1];
		}
	}

	public static int[][] getAllClosedOrderInfo() {
		/**
		 * returns metadata about all orders (order id, table id, items received & items they are still waiting for)
		 */

		try {
			//First, find out how many orders there are
			String orderCountStatementText = "SELECT COUNT(order_id) FROM orders WHERE status = 'closed'";
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(orderCountStatementText);
			results.next();
			int orderCount = results.getInt("COUNT(order_id)");

			//Now, create an array of the appropriate size to return
			int[][] returnArray = new int[orderCount][4];

			//Populate the first two columns of the array with order_id and table_id
			String orderIdTableIdStatementText = "SELECT order_id, table_id FROM orders WHERE status = 'closed'";
			results = statement.executeQuery(orderIdTableIdStatementText);

			for (int i = 0; results.next(); i++) {
				returnArray[i][0] = results.getInt("order_id");
				returnArray[i][1] = results.getInt("table_id");
			}

			//Now, get the number of items each order have received / are waiting for from the items table
			for (int i = 0; i < returnArray.length; i++) {
				String receivedItemsStatementText = String.format("SELECT COUNT(item_id) FROM items WHERE order_id = %d AND status = 'received'", returnArray[i][0]);
				results = statement.executeQuery(receivedItemsStatementText);
				if (results.next()) {
					returnArray[i][2] = results.getInt("COUNT(item_id)");
				}
			}

			for (int i = 0; i < returnArray.length; i++) {
				String waitingItemsStatementText = String.format("SELECT COUNT(item_id) FROM items WHERE order_id = %d AND status = 'waiting'", returnArray[i][0]);
				results = statement.executeQuery(waitingItemsStatementText);
				if (results.next()) {
					returnArray[i][3] = results.getInt("COUNT(item_id)");
				}
			}

			//Finally, return the now populated array
			return returnArray;

		}
		catch (Exception e) {
			System.out.print(e);
			return new int[1][1];
		}
	}

	public static int returnMenuItemCount() {
		/**
		 * Returns the number of items in the menu
		 */
		String queryText = "SELECT COUNT(id) FROM MENU";
		
		try {
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(queryText);
			results.next();
			int count = results.getInt("COUNT(id)");
			return count;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public static String returnItemName(int id) {
		/**
		 * Takes an id for a menu item and returns its name as a human readable string 
		 * eg returnItemName(7) might return "large coke".
		 */
		String queryText = String.format("SELECT * FROM MENU WHERE id = %d", id);
		
		try {
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(queryText);
			results.next();
			String name = results.getString("name");
			return name;
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Error retrieving name";
		}
		
	}
	
	public int returnItemPrice(int id) {
		/**
		 * Takes an id for a menu item and returns the price of that item in pence.
		 */
		String queryText = String.format("SELECT * FROM MENU WHERE id = %d", id);
		
		try {
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(queryText);
			results.next();
			int price = results.getInt("price");
			return price;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String returnItemCategory(int id) {
		/**
		 * Takes an id for a menu item and returns its category as a human readable string 
		 * eg returnItemName(7) might return "drinks".
		 */
		String queryText = String.format("SELECT * FROM MENU WHERE id = %d", id);
		
		try {
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(queryText);
			results.next();
			String category = results.getString("category");
			return category;
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Error retrieving category";
		}
		
	}

	public int createBooking(String name, String date, String time, String phone, String email, String size, int table, String ref) {
		/*
		Accepts a number of strings and enters these into the database as a new booking
		 */
		String bookingStatement = String.format("INSERT INTO bookings (customer_name, date, time, phone_number, email, party_size, preferred_table, booking_reference)" +
				"VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d, '%s');", name, date, time, phone, email, size, table, ref);

		try {
			Statement stmt = database.createStatement();
			stmt.execute(bookingStatement);
			return 0;
		}
		catch (Exception e) {
			System.out.println(e + "Error adding booking to database");
			return -1;
		}
	}

	public int getBookingCount() {
		/*
		returns the number of distinct bookings in the bookings table
		 */
		String statementText = "SELECT COUNT(booking_reference) FROM bookings";
		try {
			Statement stmt = database.createStatement();
			ResultSet queryResults = stmt.executeQuery(statementText);
			queryResults.next();
			int bookingCount = queryResults.getInt("COUNT(booking_reference)");
			return bookingCount;
		}
		catch (Exception e) {
			System.out.println(e);
			System.out.println("Error getting booking count from database, exception thrown and zero returned");
			return 0;
		}
	}

	public String[] getBookingInfo(String reference) {
		/*
		Accepts a booking reference and returns all the relevant info for that booking in an array
		 */
		String statementText = String.format("SELECT * FROM bookings WHERE booking_reference = %s", reference);

		try {
			Statement stmt = database.createStatement();
			ResultSet queryResults = stmt.executeQuery(statementText);
			queryResults.next();

			String[] returnArray = new String[8];
			returnArray[0] = queryResults.getString("name");
			returnArray[1] = queryResults.getString("date");
			returnArray[2] = queryResults.getString("time");
			returnArray[3] = queryResults.getString("phone");
			returnArray[4] = queryResults.getString("email");
			returnArray[5] = queryResults.getString("party_size");
			returnArray[6] = String.valueOf(queryResults.getInt("preferred_table"));
			returnArray[7] = queryResults.getString("booking_reference");

			return returnArray;
		}
		catch (Exception e) {
			System.out.println(e);
			System.out.println("Error retriving booking info, returned empty array");
			String[] returnArray = new String[8];
			return returnArray;
		}
	}

	public String[][] getAllBookingInfo(){
		/*
		returns info on every booking in the bookings table, formatted into a 2d array
		 */

		String statementText = "SELECT * FROM bookings";
		String[][] returnArray = new String[getBookingCount()][8];

		try {
			Statement stmt = database.createStatement();
			ResultSet queryResults = stmt.executeQuery(statementText);
			queryResults.next();

			for (int i = 0; i < getBookingCount(); i++) {
				returnArray[i][0] = queryResults.getString("customer_name");
				returnArray[i][1] = queryResults.getString("date");
				returnArray[i][2] = queryResults.getString("time");
				returnArray[i][3] = queryResults.getString("phone_number");
				returnArray[i][4] = queryResults.getString("email");
				returnArray[i][5] = queryResults.getString("party_size");
				returnArray[i][6] = String.valueOf(queryResults.getInt("preferred_table"));
				returnArray[i][7] = queryResults.getString("booking_reference");
				queryResults.next();
			}
			return returnArray;
		}
		catch (Exception e) {
			System.out.println(e);
			System.out.println("Error getting all booking info");

			return returnArray;
		}

	}

	public void removeBooking(String reference) {
		/*
		Removes the booking with the specified reference
		 */
		String statementText = String.format("DELETE FROM bookings WHERE booking_reference = '%s'", reference);

		try {
			Statement stmt = database.createStatement();
			stmt.execute(statementText);
		}
		catch (Exception e) {
			System.out.println("Error removing entry from database");
		}
	}

	public int[] getTableStatus() {
		/*
		Returns a 9 int array describing the availability status of each table in the restaurant, 1 for in use, 0 for free
		 */

		String statementText = String.format("SELECT * FROM orders");
		int[] returnArray = {0,0,0,0,0,0,0,0,0};

		try {
			Statement stmt = database.createStatement();
			ResultSet queryResults = stmt.executeQuery(statementText);
			while (queryResults.next()) {
				int tableID = queryResults.getInt("table_id");
				String tableStatus = queryResults.getString("status");
				if (tableStatus.equals("open")) {
					if (tableID-1 < 10) {
						returnArray[tableID-1] = 1;
					}
				}
			}
			System.out.println(returnArray[0] + returnArray[1] + returnArray[2] + returnArray[3] + returnArray[4] + returnArray[5]);
			return returnArray;
		}
		catch (Exception e) {
			System.out.println(e);
			System.out.println("Error getting table availability info");

			return returnArray;
		}





	}

	public String[][] returnMenuAsArray() {
		/*
		Returns a 2d array of all menu items in the format {name, category, price}
		 */

		String queryText = "SELECT * FROM menu;";
		int itemCount = returnMenuItemCount();
		String[][] returnArray = new String[itemCount][3];

		try {
			Statement stmt = database.createStatement();
			ResultSet resultSet = stmt.executeQuery(queryText);

			int i = 0;
			while (resultSet.next()) {
				returnArray[i][0] = resultSet.getString("name");
				returnArray[i][1] = resultSet.getString("category");
				int priceInPence = resultSet.getInt("price");
				String priceString = String.format("£%d.%02d", priceInPence/100, priceInPence%100);
				returnArray[i][2] = priceString;

				i++;
			}
		}

		catch (Exception e) {
			System.out.println(e);
			System.out.println("Problem retrieving menu items");
		}

		return returnArray;
	}

	public String[][] returnDummyMenu() {
		/*
		Returns a dummy version of the menu which doesn't rely on having mySQL and the database available
		 */

		String[][] returnArray = {{"Small soft drink", "drinks", "£1.99"}, {"Onion rings", "starters", "£2.00"}, {"Steak and chips", "mains", "£11.00"}, {"Ice cream", "desserts", "£3.00"}};
		return returnArray;

	}

	public int getOrderDataPeriod() {
		/*
		Returns in months, the period for which order data is available
		 */
		//First, get all the order data
		String queryText = "SELECT * FROM orders;";
		ResultSet results = null;
		int[] firstDate = {Integer.MAX_VALUE,01};
		int[] lastDate = {0,01};

		try {
			Statement stmt = database.createStatement();
			results = stmt.executeQuery(queryText);

			//Now, find the first and last months in this data
			while (results.next()) {
				Timestamp dateTime = results.getTimestamp("arrived");
				String dateString = dateTime.toString();
				int[] dateYearMonth = {Integer.parseInt(dateString.split("-",3)[0]), Integer.parseInt(dateString.split("-",3)[1])};
				if (dateYearMonth[0] < firstDate[0]) {
					firstDate = dateYearMonth;
				}
				if (dateYearMonth[0] == firstDate[0]) {
					if (dateYearMonth[1] < firstDate[1]) {
						firstDate = dateYearMonth;
					}
				}
				if (dateYearMonth[0] > lastDate[0]){
					lastDate = dateYearMonth;
				}
				if (dateYearMonth[0] == lastDate[0]) {
					if (dateYearMonth[1] > lastDate[1]) {
						lastDate = dateYearMonth;
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error retrieving order data in method getOrderDataPeriod()" + e);
			return 0;
		}
		int monthCount = (12*(lastDate[0]-firstDate[0]) + (lastDate[1]-firstDate[1]) + 1);

		if (monthCount > 0) {
			return monthCount;
		}
		else {
			return 0;
		}


	}

	public int[] getFirstMonthInData() {
		/*
		Returns a 2d array of the first month in the database with order data available.

		returns in the format {year, month}
		 */
		//First, get all the order data
		String queryText = "SELECT * FROM orders;";
		ResultSet results = null;
		int[] firstDate = {Integer.MAX_VALUE,01};

		try {
			Statement stmt = database.createStatement();
			results = stmt.executeQuery(queryText);

			//Now, find the first month in this data
			while (results.next()) {
				Timestamp dateTime = results.getTimestamp("arrived");
				String dateString = dateTime.toString();
				int[] dateYearMonth = {Integer.parseInt(dateString.split("-",3)[0]), Integer.parseInt(dateString.split("-",3)[1])};
				if (dateYearMonth[0] < firstDate[0]) {
					firstDate = dateYearMonth;
				}
				if (dateYearMonth[0] == firstDate[0]) {
					if (dateYearMonth[1] < firstDate[1]) {
						firstDate = dateYearMonth;
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error retrieving order data in method getFirstMonthInData()" + e);
			return firstDate;
		}

		return firstDate;
	}

	public int[][] getRevenueData() {

		/*
		-Get all order info
		-Generate an empty array of year, month, revenue, food, drinks
		-for each piece of order info, find what month it is and increment the appropriate array entry

		returns in the format {year, month, revenue, food revenue, drinks revenue}
		 */


		//First, get the period for which orders are available
		int monthCount = getOrderDataPeriod();
		int[] firstDate = getFirstMonthInData();

		//Set the variables for year and month, knowing the first month and total number of months
		int[][] revenueArray = new int[monthCount][5];
		int currentYear = firstDate[0];
		int currentMonth = firstDate[1];
		for (int i = 0; i<monthCount; i++) {
			revenueArray[i][0] = currentYear;
			revenueArray[i][1] = currentMonth;

			if (currentMonth < 12) {
				currentMonth++;
			} else {
				currentMonth = 1;
				currentYear++;
			}
		}

		//Now, get all the order data
		String queryText =  "SELECT i.order_id, category, price, arrived " +
							"FROM items i " +
							"INNER JOIN menu " +
							"ON menu.id = i.item_id " +
							"INNER JOIN orders " +
							"ON orders.order_id = i.order_id;";

		try {
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);

			//Cycle through the results, reading the month and year then adding data to revenue variables
			while (results.next()) {
				String category = results.getString("category");
				int order_id = results.getInt("order_id");
				int price = results.getInt("price");
				Timestamp resultDateTime = results.getTimestamp("arrived");
				String dateString = resultDateTime.toString();

				int[] yearMonth = {Integer.parseInt(dateString.split("-",3)[0]),Integer.parseInt(dateString.split("-",3)[1])};

				//Now, knowing the year and month, as well as the first year and month in the data, place the revenue in the correct variable
				int monthIndex = ((12*(yearMonth[0]-firstDate[0]))+(yearMonth[1]-firstDate[1]));

				revenueArray[monthIndex][2]+=price;
				if (category.equals("drinks")) {
					revenueArray[monthIndex][4]+=price;
				}
				else {
					revenueArray[monthIndex][3] += price;
				}

			}
		}
		catch (Exception e) {
			System.out.println("error retrieving revenue data" + e);
		}
		return revenueArray;
	}

	public int[] getSalesData(String name) {
		/*
		Returns the month by month sales data for a given menu item, starting from the earliest month for which an order exists
		 */

		//Firstly get the total number of months and the first month
		int monthCount = getOrderDataPeriod();
		int[] firstMonth = getFirstMonthInData();

		int[] returnArray = new int[monthCount];

		//Now, get all items that have been ordered
		String queryText =  "SELECT name, arrived " +
							"FROM items i " +
							"INNER JOIN menu ON i.item_id = menu.id " +
							"INNER JOIN orders ON i.order_id = orders.order_id";

		try {
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);

			while (results.next()) {
				String resultDateTime = results.getTimestamp("arrived").toString();
				int[] resultYearMonth = {Integer.parseInt(resultDateTime.split("-",3)[0]),Integer.parseInt(resultDateTime.split("-",3)[1])};
				int monthIndex = (12*(resultYearMonth[0]-firstMonth[0])+(resultYearMonth[1]-firstMonth[1]));

				if (results.getString("name").equals(name)) {
					returnArray[monthIndex]++;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}

		return returnArray;
	}

	public void addMenuItem(String name, String category, int price){
		/*
		Adds a menu item from a set of arguments, automatically generating an id number 1 higher than the current highest in the table
		 */
		String queryText = "SELECT MAX(id) FROM menu;";
		int maxID = 0;

		try {
			Statement stmt = database.createStatement();
			ResultSet results = stmt.executeQuery(queryText);
			results.next();
			maxID = results.getInt("MAX(id)");
			String statementText = String.format("INSERT INTO menu (name, category, price, id) VALUES ('%s', '%s', %d, %d);", name, category, price, maxID+1);
			stmt.execute(statementText);
		}
		catch (Exception e) {
			System.out.println("Error calculating max id and inserting values into table: menu");
			System.out.println(e);
		}
	}


}
