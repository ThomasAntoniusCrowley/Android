
import java.sql.*;

public class DatabaseHandler {
//safdsagfdsbafdsbv
	/**
	 * Class to handle connecting to and querying the database, then returning its results.
	 * All interaction with the database should go through one instance of this class.
	 */
	
	public static void main( String[] args) {
		/**
		 * Connects to a local database called Restaurant.db then checks if certain tables exist,
		 * if they don't then it creates them. 
		 */
		
		//Basic variables for connecting to the database
		Connection database = null;
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
		
		//Extract usable data from the ResultSet object
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
	
	public String returnItemName(int id) {
		/**
		 * Takes an id for a menu item and returns its name as a human readable string 
		 * eg returnItemName(7) might return "large coke".
		 */
		return "placeholder";
	}
	
	public int returnItemPrice(int id) {
		/**
		 * Takes an id for a menu item and returns the price of that item in pence.
		 */
		return 5;
	}
}
