import javax.swing.*;
import java.awt.*;

/**
 * Created by jordan on 19/02/16.
 */
public class TablesGUI extends JFrame {

    private Table[] allTables;
    private JButton[] tableButtons;

    private int tableCount = 9;
    
    TablesGUI(String time) {

        allTables = new Table[tableCount];
        createTables();

        tableButtons = new JButton[tableCount];
        for (int j = 0; j < tableCount; ++j) {
            tableButtons[j] = new JButton("<html>" + "Table " + (j+1) + "<br>"
                    + allTables[j].getPeopleCount() + " people" + "</html>");

            //Add Action Listener to each j element

            if (allTables[j].getReserveStatus() == true) {
                tableButtons[j].setBackground(Color.RED);
            }

            else {
                tableButtons[j].setBackground(Color.GREEN);
            }

        }
        setLayout();
    }

    public void setLayout() {



        GridBagLayout finalGrid = new GridBagLayout();
        this.setLayout(finalGrid);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 20, 20, 20);

        constraints.weightx = constraints.weighty = 1.0;

        for (int i = 0; i < allTables.length; ++i) {
            constraints.gridwidth = allTables[i].getGridWidth();
            constraints.gridheight = allTables[i].getGridHeight();
            constraints.gridx = allTables[i].getxLocation();
            constraints.gridy = allTables[i].getyLocation();
            /*
            if (allTables[i].getPeopleCount() == "2") {
                //tableButtons[i].setPreferredSize(new Dimension(340, 320));
            }
            else if (allTables[i].getPeopleCount() == "4") {

            }
            else if (allTables[i].getPeopleCount() == "6") {

            }
            else if (allTables[i].getPeopleCount() == "8") {

            }*/

            this.add(tableButtons[i], constraints);
        }

        this.setPreferredSize(new Dimension(540, 480));
        this.setMinimumSize(this.getPreferredSize());

        //setSize(320, 400);
        setTitle("Table Representation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createTables() {
        allTables[0] = new Table("2", 0, 0, 1);
        allTables[1] = new Table("2", 0, 1, 2);
        allTables[2] = new Table("2", 0, 2, 3);
        allTables[3] = new Table("2", 0, 3, 4);
        allTables[4] = new Table("8", 0, 4, 5);
        allTables[5] = new Table("4", 1, 0, 6);
        allTables[6] = new Table("4", 1, 2, 7);
        allTables[7] = new Table("6", 2, 0, 8);
        allTables[8] = new Table("6", 2, 3, 9);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TablesGUI tablesGUI = new TablesGUI("18:30");
                tablesGUI.setVisible(true);
            }
        });

    }
}

//http://stackoverflow.com/questions/5604188/how-to-make-java-swing-components-fill-available-space