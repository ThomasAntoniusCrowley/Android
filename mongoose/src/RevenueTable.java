import javax.swing.*;
import java.awt.*;

/**
 * Created by Sean McCarthy on 02/05/2016.
 */
public class RevenueTable extends JPanel {
    /*
    Class that reads revenue data from the database and displays it broken down by month in a table of variable size.

    Table has columns: "Month","Total Revenue", "Food Revenue", "Drinks Revenue", "Month on month change"
     */

    GridLayout tableLayout;

    RevenueTable() {

        DatabaseHandler dbHandler = new DatabaseHandler();

        //Firstly, count the number of months that order data is available for
        int monthCount = dbHandler.getOrderDataPeriod();

        //Now, set the layout for the table
        tableLayout = new GridLayout(monthCount+1, 6);
        this.setLayout(tableLayout);

        //Fill in the boxes at the top
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(yearLabel);
        JLabel monthLabel = new JLabel("Month: ");
        monthLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(monthLabel);
        JLabel totalRevenueLabel = new JLabel("Total Revenue:");
        totalRevenueLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(totalRevenueLabel);
        JLabel foodRevenueLabel = new JLabel("Food Revenue:");
        foodRevenueLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(foodRevenueLabel);
        JLabel drinksRevenueLabel = new JLabel("Drinks Revenue:");
        drinksRevenueLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(drinksRevenueLabel);
        JLabel changeLabel = new JLabel("Monthly Change:");
        changeLabel.setBorder(BorderFactory.createDashedBorder(getForeground()));
        this.add(changeLabel);

        //Now, fill in the actual data
        int[][] revenueData = dbHandler.getRevenueData();
        int lastMonthsRevenue = 0;

        for (int i=0; i<revenueData.length; i++) {
            this.add(new JLabel(String.valueOf(revenueData[i][0])));
            this.add(new JLabel(String.valueOf(revenueData[i][1])));
            this.add(new JLabel(String.format("£%d.%02d", revenueData[i][2]/100, revenueData[i][2]%100)));
            this.add(new JLabel(String.format("£%d.%02d", revenueData[i][3]/100, revenueData[i][3]%100)));
            this.add(new JLabel(String.format("£%d.%02d", revenueData[i][4]/100, revenueData[i][4]%100)));
            //Calculations for the month on month revenue change.
            String revenueChange;
            if (lastMonthsRevenue == 0) {
                revenueChange = "N/A";
            }
            else {
                revenueChange = String.format("%02.1f %%",(100*((float)revenueData[i][2]/lastMonthsRevenue) - 100));
            }
            this.add(new JLabel(revenueChange));
            lastMonthsRevenue = revenueData[i][2];

        }



    }

    public static void main(String[] argv) {
        RevenueTable revTable = new RevenueTable();

        JFrame newFrame = new JFrame();
        newFrame.setMinimumSize(new Dimension(500,500));
        newFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newFrame.add(revTable);

        newFrame.setVisible(true);
    }

}
