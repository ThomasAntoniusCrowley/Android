import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by Thomas on 2/18/2016.
 *
 * jUnit tests for item class
 */
public class ItemTest {

    private Item beefBurger;

    @Before
    public void setUp()
    {
        beefBurger= new Item("Beef Burger", "Burger", 1200 );
    }

    @Test
    public void getNameTest()
    {
        String name = beefBurger.getThisName();

        assertEquals("Name: ", "Beef Burger", name);
    }


//    @Test
//    public void foodOrDrinkTest()
//    {
//        String foodOrDrink1 = beefBurger.getThisFoodOrDrink();
//
//        assertEquals("Food or Drink: ", "Food", foodOrDrink1);
//    }

    @Test
    public void getTypeTest()
    {
        String type = beefBurger.getThisCategory();

        assertEquals("Type: ", "Burger", type);
    }


    @Test
    public void priceTest()
    {
        double price1 = beefBurger.getThisPrice();
        boolean priceCheck = false;
        if (price1 == 12.00) {
            priceCheck = true;
        }
        assertEquals(priceCheck, true);
    }
}