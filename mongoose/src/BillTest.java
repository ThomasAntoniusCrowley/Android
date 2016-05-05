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
        billTest = new Bill(4,"code", "2", "3");
        beefBurger = new Item("Beef Burger", "Burger", 1200);
        veggieBurger = new Item("Veggie Burger", "Burger", 1150);
        mojito = new Item("Mojito", "Drink",  450);
        guiness = new Item("Guiness", "Drink", 280);
        billTest.addItem(beefBurger);
        billTest.addItem(veggieBurger);
        billTest.addItem(mojito);
        billTest.addItem(guiness);

    }

//tests the add function
    @Test
    public void addTest()
    {

    }

//tests the to file function. creating a text file with dummy data
    @Test
    public void toFileTest() throws FileNotFoundException
    {

        billTest.toFile("billTestFile.txt");
    }

//checks that the get total function works
    @Test
    public void getTotalTest()
    {
        double tot = billTest.getTotal();

        assertTrue(tot == 3080);

    }

}
