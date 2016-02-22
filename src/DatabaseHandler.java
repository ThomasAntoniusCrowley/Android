
import java.sql.*;

public class DatabaseHandler {

	/**
	 * Class to handle connecting to and querying the database, then returning its results.
	 * All interaction with the database should go through one instance of this class.
	 */
	
	//Declare database connection object here so it's visible for all functions.
	private static Connection database = null;
	
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
		
		//Create to execute a statement, storing the output in variable 'result'
		ResultSet results = null;
		try {
			Statement statement = null;
			System.out.println("Building SQL statement");
			
			statement = database.createStatement();
			String queryText = "SELECT * FROM menu";
			
			results = statement.executeQuery(queryText);
		}
		catch (Exception e){
			System.out.println("Error when creating and executing query");
			e.printStackTrace();
		}
		
		//Extract usable data from the ResultSet object, just reads everything on the menu for testing purposes.
		try {
			while (results.next()) {
				int id = results.getInt("id");
				String category = results.getString("category");
				String name = results.getString("name");
				int price = results.getInt("price");
				System.out.printf("ID: %d, Category: %s, Name: %s, Price: %d \n", id, category, name, price);
			}
		}
		catch (Exception e) {
			System.out.println("Error reading data from result set");
			e.printStackTrace();
		}
		//Testing code
		System.out.println("THERE ARE " + returnMenuItemCount() + " ITEMS IN THE MENU");
		System.out.println("Testing order id retrieval...");
		int[] ordersResults = listActiveOrders();
		for (int i = 0; i<ordersResults.length; i++){
			System.out.println(ordersResults[i]);
		}
		System.out.println("THE ITEM WITH ID 5 IS: " + returnItemName(5));
		getOrderInfo(1);
		getOrderInfo(2);
	}
	
	public static int[] listActiveOrders() {
		/**
		 * Returns the id's of all active orders in the database, these id's can then be handed to the returnBill function 
		 * to get info on the order.
		 */
		String countQueryText = "SELECT COUNT( DISTINCT order_id) FROM orders";
		String listQueryText = "SELECT DISTINCT order_id FROM orders";
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
		int table_id;
		String status;
		try {
			//Get the number of unique items in the order, and the quantity of each unique item - populate a 2d array with this info.
			Statement statement = database.createStatement();
			ResultSet results = statement.executeQuery(itemCountQueryText);
			results.next();
			uniqueItemCount = results.getInt("COUNT(item_id)");
			itemArray = new int[uniqueItemCount][2];
			results = statement.executeQuery(itemCountQueryText);
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
			results.next();
			table_id = results.getInt("table_id");
			status = results.getString("status");
			//Test code
			System.out.println("TABLE " + table_id + " STATUS: " + status);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
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
}
