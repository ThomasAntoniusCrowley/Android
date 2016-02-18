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

}


