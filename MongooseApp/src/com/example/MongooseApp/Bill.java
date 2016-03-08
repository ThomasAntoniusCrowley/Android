package com.example.MongooseApp;

public class Bill {

	private Item[] bill;
	private double finalPrice;
	
	//private Item currentItem;
		
	public Bill(int billSize) {
		bill = new Item[billSize];
		//addVAT();
	}

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
	
	public void addVAT() {
		double itemTotal = 0;
		for (Item item : bill) {
			if (item != null) {
				itemTotal += item.getThisPrice();
			}
		}
		System.out.println(itemTotal);
		finalPrice = (itemTotal * 1.2);
		finalPrice = Math.round(finalPrice * 100.0) / 100.0;
		System.out.println(finalPrice);
		
	}

	public void removeItem(int itemNo) {
		try {
			for (int i = itemNo - 1; i < bill.length -1; ++i) {
				bill[i] = bill[i+1];
			}
			bill[bill.length - 1] = null;
		}
		
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error: could not remove item. Please restart bill entries.");
		}
	}
	
	@Override
	public String toString() {
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
		return billString.trim();
	}
	
	static public void main(String[] args) {
		Bill thisBill = new Bill(3);
		//System.out.println(thisBill.toString());
		
		Item item1 = new Item("chicken", "Food", "Meat", 12.23);
		Item item2 = new Item("tuna", "Food", "Fish", 12.24);
		Item item3 = new Item("rioja", "Drink", "Wine", 12.25);
		
		thisBill.addItem(item1);	
		System.out.println(thisBill.toString());
		
		thisBill.addItem(item2);
		thisBill.addItem(item3);	
		System.out.println(thisBill.toString());
		
		thisBill.addVAT();
	}
}

//http://stackoverflow.com/questions/5945867/how-to-round-the-double-value-to-2-decimal-points