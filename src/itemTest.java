import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas on 2/18/2016.
 *
 * jUnit tests for item class'
 */
public class itemTest {

    private Item beefBurger;

    @Before
    public void setUp()
    {
        beefBurger= new Item("Beef Burger", "Food", "Meat", 12.00 );
    }

    @Test
    public void getNameTest()
    {
        String name = beefBurger.getThisName();

        assertEquals("Name: ", "Beef Burger", name);
    }
}
