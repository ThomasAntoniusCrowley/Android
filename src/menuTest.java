import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thomas on 2/18/2016.
 *
 * test for Menu.java class
 */
public class MenuTest {

    private Menu menuTest;
    private Item beefBurger;
    private Item mojito;
    private List<String> testList = new ArrayList<String>();
    private Item veggieBurger;


    @Before
    public void setUp()
    {
        menuTest = new Menu();
        beefBurger= new Item("Beef Burger", "Food", "Burger", 12.00 );
        veggieBurger= new Item("Veggie Burger", "Food", "Burger", 11.50);
        mojito = new Item("Mojito", "Drink", "Cocktail", 4.50);
    }

    @Test
    public void addTest()
    {
        menuTest.addItem(beefBurger);

        int testSize = menuTest.getMenuSize();

        assertTrue(testSize==1);
    }

    @Test
    public void removeTest()
    {
        menuTest.addItem(beefBurger);
        menuTest.addItem(mojito);
        int startSize = menuTest.getMenuSize();
        menuTest.removeMenuItem(mojito);
        int endSize = menuTest.getMenuSize();
        int diff = startSize - endSize;

        assertTrue(diff == 1);
        String menuString = menuTest.toString();
        assertEquals("check the correct item has been removed: ", menuString, "Beef Burger Food Burger £12.0");

    }

    @Test
    public void toStringTest()
    {
        menuTest.addItem(beefBurger);
        menuTest.addItem(mojito);
        String testString = menuTest.toString();


        System.out.println(testString);

        assertEquals("toString?: ", "Beef Burger Food Burger £12.0" + "\n" + "Mojito Drink Cocktail £4.5", testString);
    }

    @Test
    public void getPriceTest()
    {
        menuTest.addItem(beefBurger);
        menuTest.addItem(mojito);

        double price1 = menuTest.getItemPrice(mojito);
        double price2 = menuTest.getItemPrice(beefBurger);
        boolean priceCheck = false;
        if (price1 == 4.50 && price2 == 12.00)
        {
            priceCheck = true;
        }


        assertEquals(priceCheck, true);
    }

    @Test
    public void getItemTest()
    {
        menuTest.addItem(beefBurger);
        menuTest.addItem(mojito);

        Item desiredItem = menuTest.getItem(beefBurger);

        assertTrue(desiredItem==beefBurger);
    }

    @Test
    public void toFileTest() throws FileNotFoundException {
        menuTest.addItem(beefBurger);
        menuTest.addItem(mojito);
        menuTest.addItem(veggieBurger);

        menuTest.toFile("testfile.txt");
    }


}
