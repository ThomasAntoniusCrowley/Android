/**
 * Created by Thomas on 2/18/2016.
 */
public class Menu
{
    private Item[] menu;

    public Menu(int menuSize)
    {
        menu =new Item[menuSize];
    }

    public void addItem(Item item)
    {
        boolean nextFree = false;
        int currentElement = 0;
        try
        {
            while (nextFree == false)
            {
                if (menu[currentElement] == null)
                {
                    menu[currentElement] = item;
                    nextFree= true;
                }

                else
                {
                    currentElement +=1;
                }
            }
        }

        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e);
        }
    }

    public void removeItem(int itemNo) {
        try {
            for (int i = itemNo - 1; i < menu.length -1; ++i) {
                menu[i] = menu[i+1];
            }
            menu[menu.length - 1] = null;
        }

        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: could not remove item. Please restart menu entries.");
        }
    }

    @Override public String toString() {
        String menuString = "";
        for (Item thisItem : menu) {
            if (thisItem != null) {
                menuString += thisItem.toString();
                menuString += "\n";
            }

            else {
                menuString += "<Empty element>";
                menuString += "\n";
            }
        }
        return menuString;
    }

}


