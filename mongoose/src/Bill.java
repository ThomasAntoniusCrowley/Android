import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by jordan.
 *
 * Class to represent a bill for a meal.
 */
public class Bill {

    private Item[] bill;
    private String orderId;
    private String arrivedTime;
    private String tableNo;


    //private Item currentItem;

    //Creates a bill object, which is a list of billSize Item objects
    public Bill(int billSize, String orderId, String tableNo, String arrivedTime) {
        bill = new Item[billSize];
        this.orderId=orderId;
        this.tableNo = tableNo;
        this.arrivedTime = arrivedTime;

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

    public int getTotal()
    {
        int total = 0;

        for (Item i : bill)
        {
           int price =  i.getThisPrice();

            total = total + price;
        }


        return total;
    }

    //Adds VAT to get a final price (to the penny)
    public int addVAT()
    {
        int finalPrice;
        finalPrice = (getTotal() + getTotal()/5);

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
        billArray.add(String.format("Order ID: %s",orderId));
        billArray.add(String.format("Table: %s", tableNo));
        billArray.add(String.format("Time arrived: %s", arrivedTime));
        billArray.add("");

        for (Item i : bill)
        {
            String name = i.getThisName();
            int price = i.getThisPrice();
            String priceString = String.format("£%d.%02d",price/100, price%100);
            //Set the string to always be the right length
            String billString = name;
            for (int j = 0;j <30-name.length();j++){
            billString += ".";
            }
            billString += priceString;
            billArray.add(billString);

        }
        String priceBeforeVat = String.format("£%d.%02d", getTotal()/100, getTotal()%100);
        String preVatString = "Total ...................... " + priceBeforeVat;
        String finalPriceString = String.format("£%d.%02d", addVAT()/100, addVAT()%100);
        finalPriceString =    "Total (inc Vat) ............ " + finalPriceString;
        billArray.add("");
        billArray.add(preVatString);
        billArray.add(finalPriceString);


        PrintWriter pw = new PrintWriter(new FileOutputStream(file_name));
        for (String s : billArray)
        {
            pw.println(s);
        }
        pw.close();

        File billTextFile = new File(file_name);
        JTextPane paperPane = new JTextPane();
        paperPane.setContentType("text/html");
        String printContent = "";
        for (String i : billArray) {
            printContent += i+"\n";
        }
        paperPane.setText(printContent);
        try {
            paperPane.print();
        }
        catch (Exception e) {
            System.out.println("Error printing physical bill");
        }

    }
}



//http://stackoverflow.com/questions/5945867/how-to-round-the-double-value-to-2-decimal-points