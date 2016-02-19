import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 2/18/2016.
 */
public class Menu {
    protected List<Item> menu = new ArrayList<Item>();

    public Menu() {
       // menu = new ArrayList<Item>();
    }

    public boolean containsFood(Item item) {
        return menu.contains(item);
    }


    public void addItem(Item item) {
        if (menu.contains(item)) {
            throw new IllegalArgumentException("item already present");
        } else {
            menu.add(item);
        }
    }

    public void removeItem(Item item, int item_position) {
        if (menu.contains(item)) {
            menu.remove(item_position);
        } else {
            throw new IllegalArgumentException("item not present");
        }
    }

    @Override
    public String toString() {
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
        return menuString;
    }
}




