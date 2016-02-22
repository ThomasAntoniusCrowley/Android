import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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


    @Before
    public void setUp()
    {
         menuTest = new Menu();
        beefBurger= new Item("Beef Burger", "Food", "Burger", 12.00 );
        mojito = new Item("Mojito", "Drink", "Cocktail", 4.50);
    }

    @Test
    public void addTest()
    {
//        menuTest.addItem(beefBurger);
//
//        int menuLength = menuTest.size();
//
//        assertEquals("Menu size: ", 1, menuLength);
//
//
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

}
