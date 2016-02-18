
import java.sql.*;

public class DatabaseHandler {

	/**
	 * Class to handle connecting to and querying the database, then returning its results.
	 * All interaction with the database should go through one instance of this class.
	 */
	
	private DatabaseHandler() {
		/**
		 * Connects to a local database called Restaurant.db then checks if certain tables exist,
		 * if they don't then it creates them. 
		 */
		
		Connection database = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			database = DriverManager.getConnection("JDBC:SQL:Menu.db");
		}
		catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
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
