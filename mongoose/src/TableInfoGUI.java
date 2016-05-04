import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class to display relevant table information based on button clicked
 *
 * Created by jordan on 27/02/16.
 */
public class TableInfoGUI extends JFrame{

    JLabel tableInfo;
    int tableNumber;
    String tableCount;

    //Creates a separate GUI window based on the Table object
    TableInfoGUI(Table thisTable) {
        //Formats text appropriately
        tableNumber = thisTable.getTableNumber();
        tableCount = thisTable.getPeopleCount();
        tableInfo = new JLabel("<html>Table Number: " + tableNumber + "<br>" +
                                "Seats " + tableCount + " people" + "<br>" +
                                "Reserved From: " + "<br>" +
                                "Reserved Until: " + "<br>" + "</html>");

        //Formats window appropriately
        this.setPreferredSize(new Dimension(540, 480));
        this.setMinimumSize(this.getPreferredSize());
        setTitle("Table Number " + tableNumber);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(tableInfo);
        setVisible(true);
    }



    public static void main(String[] args) {
    }
}
