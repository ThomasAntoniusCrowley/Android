import java.awt.*;

/**
 * Class to create a Table object with relevant attributes for TablesGUI
 *
 * Created by jordan on 19/02/16.
 */
public class Table {

    private String peopleCount;
    private boolean reservedStatus;

    private int tableNumber;
    private int gridHeight;
    private int gridWidth;
    private int xLocation;
    private int yLocation;

    //Getters and Setters
    public String getPeopleCount() {
        return peopleCount;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public boolean getReservedStatus() {
        return reservedStatus;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getyLocation() {
        return yLocation;
    }

    public int getxLocation() {
        return xLocation;
    }

    public void setReservedStatus(boolean reservedStatus) {
        this.reservedStatus = reservedStatus;
    }

    //Constructor for Table class with parameters
    Table(String peopleCount, int xLocation, int yLocation, int tableNumber) {

        //Instantiates parameters
        this.peopleCount = peopleCount;
        this.tableNumber = tableNumber;
        this.xLocation = xLocation;
        this.yLocation = yLocation;

        //Decides how much visual window space each table is
        //allow based on the amount of people it seats
        if (peopleCount == "2") {
            this.gridWidth = 1;
            this.gridHeight = 1;
        }

        if (peopleCount == "4") {
            this.gridWidth = 1;
            this.gridHeight = 2;
        }

        else if (peopleCount == "6") {
            this.gridWidth = 1;
            this.gridHeight = 3;
        }

        else if (peopleCount == "8") {
            this.gridWidth = 2;
            this.gridHeight = 2;
        }

    }

    public static void main(String[] args) {
    }
}
