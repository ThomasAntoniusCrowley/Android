
import java.sql.*;

public class DatabaseHandler {
//safdsagfdsbafdsbv
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
		System.out.println("THERE ARE " + returnMenuItemCount() + " ITEMS IN THE MENU");
		System.out.println("THE ITEM WITH ID 5 IS: " + returnItemName(5));
	}
	
	public int[] listActiveOrders() {
		/**
		 * Returns the id's of all active orders in the database, these id's can then be handed to the returnBill function 
		 * to get info on the order.
		 */
		int[] placeholder = {1,2,3};
		return placeholder;
	}
	
	public void closeOrder(int id) {
		/**
		 * Removes an order from the database, should be called once a bill is printed for an order.
		 */
	}
	
	public void returnBill(int id) {
		/**
		 * Takes an integer id for an order or table, then reads all the relevant order info for that table from
		 *  the database and formats it into a bill object then returns it.
		 *  
		 *  Placeholder function until bill class is finished
		 */
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
