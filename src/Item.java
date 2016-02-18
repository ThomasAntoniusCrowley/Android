import java.text.DecimalFormat;
import java.util.Arrays;

public class Item {

	private String thisName;
	private String thisFoodOrDrink;
	private String thisCategory;
	private double thisPrice; 
	private int categoriesSize;
	private String[] categories;
	
	private DecimalFormat decimalTester;
	private double modulo;
	private int decimalLength;
	
	private String itemString;
	
	//Sort setters
	public String getThisName() {
		return thisName;
	}

	public String getThisFoodOrDrink() {
		return thisFoodOrDrink;
	}

	public String getThisCategory() {
		return thisCategory;
	}

	public double getThisPrice() {
		return thisPrice;
	}

//Setters not needed as any item input is final (if its done with it can be removed)
	
	Item(String name, String foodOrDrink, String category, double price) {
		thisName = name;
		thisFoodOrDrink = foodOrDrink;
		thisCategory = category;
		thisPrice = price;
		
		categoriesSize = 30;
		categories = new String[categoriesSize];
		
		categories[0] = "Fish";
		categories[1] = "Meat";
		categories[2] = "Starter";
		categories[3] = "Sharer";
		categories[4] = "Burger";
		categories[5] = "Sandwich";
		categories[6] = "Dessert";
		
		categories[7] = "Soft Drink";
		categories[8] = "Wine";
		categories[9] = "Cocktail";
		
		testCategory();
		testFoodOrDrink();
		testPrice();
	}
	
	public void testCategory() {
		if (!(Arrays.asList(categories).contains(thisCategory))) {
			throw new IllegalArgumentException("Error: invalid " +
					"category, ensure each word is capitalized.");
		}
	}
	
	public void testFoodOrDrink() {
		if (!(thisFoodOrDrink.equals("Food") || 
				thisFoodOrDrink.equals("Drink"))) {
			throw new IllegalArgumentException("Error: " +
					"foodOrDrink must be input as 'Food' " +
					"or 'Drink' only");
		}		
	}
	
	public void testPrice() {
		modulo = thisPrice % 1;
		decimalTester = new DecimalFormat("#.00");
		decimalLength = (decimalTester.format(modulo).length() - 1);
		
		if (decimalLength > 2) {		
			throw new IllegalArgumentException("Error: price " +
					"must be entered in the form xx.xx, with " +
					"up to 2 decimal places.");
		}
	}
	
	public void showCategories() {
		System.out.println("Valid Categories: ");
		System.out.println("");
		for (String thisString: categories) {
			System.out.println(thisString);
		}
	}
	
	@Override public String toString() {
		itemString = (thisName + " " + thisFoodOrDrink + " " + 
						thisCategory + " £" + thisPrice);
		return itemString;
	}
	
	public static void main(String[] args) {
		Item item = new Item("Chicken", "Food", "Meat", 10.66);
		item.showCategories();
	}
}

//http://stackoverflow.com/questions/21825862/validation-to-check-a-double-for-2-decimal-places
