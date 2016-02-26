import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Crowley on 2/18/2016.
 *
 * The Menu class creates a menu containing all the relevant food and drink information
 */
public class Menu {
    protected List<Item> menu = new ArrayList<Item>();

    public Menu() {
       // menu = new ArrayList<Item>();
    }

    public boolean containsFood(Item item) {
        return menu.contains(item);
    }


    public void addItem(Item item)
    {
        if (menu.contains(item)) {
            throw new IllegalArgumentException("item already present");
        } else {
            menu.add(item);
        }
    }

    public void removeMenuItem(Item item) {
        int menuSize = getMenuSize();
        if (menu.contains(item)) {
            for (int j =0; j<menuSize; j+=1)
            {
                if (item == menu.get(j))
                {
                    menu.remove(j);
                }
             }
        }
        else
        {
            throw new IllegalArgumentException("item not present");
        }
    }

    @Override
    public String toString()
    {
        String menuString = "";
        for (Item thisItem : menu) {
            if (thisItem != null) {
                menuString += thisItem.toString();
                menuString += "\n";
            } else {
                menuString += "<Empty element>";
                menuString += "\n";
            }
        }
        return menuString.trim();
    }

    public double getItemPrice(Item item)
    {
        double price = 0;
        if (menu.contains(item)) {

            for (Item i : menu) {
                if (i == item) {
                    price = i.getThisPrice();
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("item not present");
        }
        return price;
    }

    public Item getItem(Item item)
    {
        int menuSize = getMenuSize();
        Item desired =null;
        if (menu.contains(item)) {
            for (int j =0; j<menuSize; j+=1)
            {
                if (item == menu.get(j))
                {
                   desired = menu.get(j);
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("item not present");
        }
        return desired;
    }

    public int getMenuSize()
    {
        int menuCount = 0;
        for (Item i: menu)
        {
            menuCount += 1;
        }

        return menuCount;
    }

    public void toFile(String file_name) throws FileNotFoundException
    {
        // method for displaying the menu in a file
        ArrayList<String> drinkStringList = new ArrayList<String>();
        ArrayList<String> starterStringList = new ArrayList<String>();
        ArrayList<String> mainStringList = new ArrayList<String>();
        ArrayList<String> desertStringList = new ArrayList<String>();
        ArrayList<ArrayList<String>> menuArray = new ArrayList<ArrayList<String>>();

        drinkStringList.add("Drinks:");
        drinkStringList.add(" ");
        starterStringList.add("Starters:");
        starterStringList.add(" ");
        mainStringList.add("Mains:");
        mainStringList.add(" ");
        desertStringList.add("Deserts:");
        desertStringList.add(" ");

        for (Item i : menu)
        {
            String name = i.getThisName();
            double price = i.getThisPrice();
            String priceString = String.valueOf(price);
            String category = i.getThisCategory();
            String FOrD = i.getThisFoodOrDrink();
            String menuStringFormat = name + "............" + priceString;
            // put the different catorgories of food in different arrays
            if (FOrD == "Drink")
            {
                drinkStringList.add(menuStringFormat);

            }
            if (FOrD == "Food")
            {
                if (category == "Starter") {
                    starterStringList.add(menuStringFormat);
                }
                if (category == "Desert") {
                    desertStringList.add(menuStringFormat);

                } else {
                    mainStringList.add(menuStringFormat);
                }
            }
        }
        //write the info to file

        // make an array of the arrays

        drinkStringList.add(" ");
        starterStringList.add(" ");
        mainStringList.add(" ");
        desertStringList.add(" ");
        drinkStringList.add(" ");
        starterStringList.add(" ");
        mainStringList.add(" ");
        desertStringList.add(" ");

        menuArray = new ArrayList<ArrayList<String>>();
        menuArray.add(drinkStringList);
        menuArray.add(starterStringList);
        menuArray.add(mainStringList);
        menuArray.add(desertStringList);


        PrintWriter pw = new PrintWriter(new FileOutputStream(file_name));

        for (ArrayList<String> m : menuArray)
        {
            for (String n : m)
            {
                pw.println(n);
            }

        }
        pw.close();
    }
}




