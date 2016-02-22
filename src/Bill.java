/**
 * Created by jordan.
 *
 * Class to represent a bill for a meal.
 */
public class Bill {

	private Item[] bill;
	private double finalPrice;
	
	//private Item currentItem;

	//Creates a bill object, which is a list of billSize Item objects
	public Bill(int billSize) {
		bill = new Item[billSize];
		//addVAT();
	}

	//Adds a new Item object at the next free element of the bill, if possible
	public void addItem(Item item) {
		boolean nextFree = false;
		int currentElement = 0;
		try {
			while (nextFree == false) {
				if (bill[currentElement] == null) {
					bill[currentElement] = item;
					nextFree = true;
				}
				
				else {
					currentElement += 1;
				}
			}
		}
		
		catch (IllegalArgumentException e) {
			System.out.println("Error: " + e);
		}
	}

	//Adds VAT to get a final price (to the penny)
	public void addVAT() {
		double itemTotal = 0;
		for (Item item : bill) {
			if (item != null) {
				itemTotal += item.getThisPrice();
			}
		}
		finalPrice = (itemTotal * 1.2);
		finalPrice = Math.round(finalPrice * 100.0) / 100.0;

		
	}

	//Removes an item based on the input number, reallocates affected Items
	public void removeItem(int itemNo) {
		try {
			for (int i = itemNo - 1; i < bill.length -1; ++i) {
				bill[i] = bill[i+1];
			}
			bill[bill.length - 1] = null;
		}
		
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error: could not remove item. " +
					"Please restart bill entries.");
		}
	}

	//Returns a string of each item in the bill, formatted
	//appropriately and the total price with VAT
	@Override public String toString() {
		String billString = "";
		for (Item thisItem : bill) {
			if (thisItem != null) {
				billString += thisItem.toString();
				billString += "\n";				
			}
			
			else {
				billString += "<Empty element>";
				billString += "\n";
			}
		}

		billString += "\n";
		billString += "Total including VAT: £";
		billString += finalPrice;

		return billString;
	}
	
	static public void main(String[] args) {
		//Don't forget to call addVAT(); on bill once finished creating it

	}
}

//http://stackoverflow.com/questions/5945867/how-to-round-the-double-value-to-2-decimal-points