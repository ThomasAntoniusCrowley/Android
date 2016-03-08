import java.awt.*;

/**
 * Created by jordan on 19/02/16.
 */
public class Table {

    private String peopleCount;
    private String reservedFrom;
    private String reservedTo;
    private String[] reservationTimes;

    private boolean reserveStatus;
    private int tableNumber;
    private int gridHeight;
    private int gridWidth;
    private int xLocation;
    private int yLocation;

    public String getPeopleCount() {
        return peopleCount;
    }

    public String getReservedFrom() {
        return reservedFrom;
    }

    public String getReservedTo() {
        return reservedTo;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public boolean getReserveStatus() {
        return reserveStatus;
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

    public void setReservedFrom(String reservedFrom) {
        this.reservedFrom = reservedFrom;
    }

    public void setReservedTo(String reservedTo) {
        this.reservedTo = reservedTo;
    }

    Table(String peopleCount, int xLocation, int yLocation, int tableNumber) {

        this.peopleCount = peopleCount;
        this.tableNumber = tableNumber;
        this.xLocation = xLocation;
        this.yLocation = yLocation;

        reservationTimes = new String[10];

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
        reserveStatus = true;
    }

    public void determineReservedStatus() {

    }

    public static void main(String[] args) {
        Table table = new Table("2", 0, 0, 1);
    }
}
