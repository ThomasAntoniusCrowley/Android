import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by jordan.
 *
 * Class to represent a bill for a meal.
 */
public class Bill {

    private Item[] bill;


    //private Item currentItem;

    //Creates a bill object, which is a list of billSize Item objects
    public Bill(int billSize) {
        bill = new Item[billSize];

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
                } else {
                    currentElement += 1;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e);
        }
    }

    public double getTotal()
    {
        double total = 0;

        for (Item i : bill)
        {
           double price =  i.getThisPrice();

            total = total + price;
        }


        return total;
    }

    //Adds VAT to get a final price (to the penny)
    public double addVAT()
    {
        double finalPrice;
        finalPrice = (getTotal() * 1.2);
        finalPrice = Math.round(finalPrice * 100.0) / 100.0;

        return finalPrice;


    }

    //Removes an item based on the input number, reallocates affected Items
    public void removeItem(int itemNo) {
        try {
            for (int i = itemNo - 1; i < bill.length - 1; ++i) {
                bill[i] = bill[i + 1];
            }
            bill[bill.length - 1] = null;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: could not remove item. " +
                    "Please restart bill entries.");
        }
    }

    //Returns a string of each item in the bill, formatted
    //appropriately and the total price with VAT
    @Override
    public String toString() {
        String billString = "";
        for (Item thisItem : bill) {
            if (thisItem != null) {
                billString += thisItem.toString();
                billString += "\n";
            } else {
                billString += "<Empty element>";
                billString += "\n";
            }
        }

        billString += "\n";
        billString += "Total including VAT: £";
        billString += addVAT();

        return billString;
    }

    static public void main(String[] args) {
        //Don't forget to call addVAT(); on bill once finished creating it

    }

    public void toFile(String file_name) throws FileNotFoundException
    /**
     * method to create printable version of the receipt
     */
    {
        ArrayList<String> billArray = new ArrayList<String>();


        for (Item i : bill)
        {
            String name = i.getThisName();
            double price = i.getThisPrice();
            String priceString = String.valueOf(price);
            String billString = name + " ........... " + priceString;

            billArray.add(billString);

        }
        String priceBeforeVat = String.valueOf(getTotal());
        String preVatString = "Total .......... " + priceBeforeVat;
        String finalPriceString = String.valueOf(addVAT());
        finalPriceString = "Total (inc Vat) ............ " + finalPriceString;
        billArray.add("");
        billArray.add(preVatString);
        billArray.add(finalPriceString);


        PrintWriter pw = new PrintWriter(new FileOutputStream(file_name));
        for (String s : billArray)
        {
            pw.println(s);
        }
        pw.close();
    }
}



//http://stackoverflow.com/questions/5945867/how-to-round-the-double-value-to-2-decimal-points