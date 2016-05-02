import javax.swing.*;
import java.awt.*;

/**
 * Created by Sean McCarthy on 02/05/2016.
 */
public class SalesTable extends JPanel {
    /*
    Class to generate a table of month on month sales for a specific menu item
     */

    DatabaseHandler dbHandler = new DatabaseHandler();

    SalesTable(String name) {

        //Start by getting the earliest month for which sales data is available, and the total number of months
        int[] firstMonth = dbHandler.getFirstMonthInData();
        int monthCount = dbHandler.getOrderDataPeriod();

        GridLayout layout = new GridLayout(monthCount+1, 3);
        this.setLayout(layout);

        //Add headers to the columns of the table
        JLabel yearLabel = new JLabel("Year: ");
        yearLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(yearLabel);
        JLabel monthLabel = new JLabel("Month: ");
        monthLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(monthLabel);
        JLabel salesLabel = new JLabel("Monthly Sales: ");
        salesLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(salesLabel);

        //Now, fill the rest of the table with the sales data for each month:
        int currentMonth = firstMonth[1];
        int currentYear = firstMonth[0];

        int[] salesData = dbHandler.getSalesData(name);

        for (int i = 0; i<monthCount; i++) {
            this.add(new JLabel(String.valueOf(currentYear)));
            this.add(new JLabel(String.valueOf(currentMonth)));
            this.add(new JLabel(String.valueOf(salesData[i])));

            if (currentMonth < 12) {
                currentMonth++;
            }
            else {
                currentYear++;
                currentMonth = 1;
            }

        }



    }


}
