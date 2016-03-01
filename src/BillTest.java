import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Thomas on 26/02/2016.
 */
public class BillTest
{
    private Bill billTest;
    private Item beefBurger;
    private Item mojito;
    private Item guiness;
    private Item veggieBurger;


    @Before
    public void setUp()
    {
        billTest = new Bill(4);
        beefBurger = new Item("Beef Burger", "Food", "Burger", 12.00);
        veggieBurger = new Item("Veggie Burger", "Food", "Burger", 11.50);
        mojito = new Item("Mojito", "Drink", "Cocktail", 4.50);
        guiness = new Item("Guiness", "Drink", "Beer", 2.80);
        billTest.addItem(beefBurger);
        billTest.addItem(veggieBurger);
        billTest.addItem(mojito);
        billTest.addItem(guiness);

    }

    @Test
    public void addTest()
    {

    }

    @Test
    public void toFileTest() throws FileNotFoundException
    {

        billTest.toFile("billTestFile.txt");
    }

    @Test
    public void getTotalTest()
    {
        double tot = billTest.getTotal();

        assertTrue(tot == 30.80);

    }

}
