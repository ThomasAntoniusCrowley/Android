
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
			System.out.println("Error connecting to database");
		      e.printStackTrace();
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

	public boolean createOrder() {
		/**
		 * Accepts an id for the order_id (an order id relates to an entire groups meal, so if they order 3 mains then later
		 *  order dessert then the order id won't change) and an array of id's for menu items, then adds these to the orders table
		 *  in the database, automatically including the time that the order was placed.
		 *  
		 *  @returns Boolean, true means the order was placed properly, false means something went wrong.
		 */
		
		return false;
		
	}
	
	public void closeOrder(int id) {
		/**
		 * Changes an orders status to 'closed'
		 */
		String queryText = String.format("UPDATE orders SET status = 'closed' WHERE order_id = %d", id);
	}
	
	public static void getOrderInfo(int id) {
		/**
		 * Takes an integer id for an order or table, then reads all the relevant order info for that table from
		 *  the database and formats it into an order object then returns it.
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
			return;
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
}
