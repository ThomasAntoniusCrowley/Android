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

    public int getMenuSize()
    {
        int menuCount = 0;
        for (Item i: menu)
        {
            menuCount += 1;
        }

        return menuCount;
    }
}




