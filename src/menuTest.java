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
public class menuTest {

    private Menu menuTest;
    private Item beefBurger;
    private List<String> testList = new ArrayList<String>();


    @Before
    public void setUp()
    {
         menuTest = new Menu();
        beefBurger= new Item("Beef Burger", "Food", "Burger", 12.00 );
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

}
