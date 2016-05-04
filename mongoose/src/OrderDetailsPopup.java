import javax.swing.*;
import javax.swing.tree.ExpandVetoException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sean McCarthy on 03/05/2016.
 */
public class OrderDetailsPopup extends JFrame {
    /*
    Takes an order ID, creates a popup displaying the items ordered by that order as well as other key data
     */
    DatabaseHandler dbHandler = new DatabaseHandler();

    OrderDetailsPopup(final int id) {
        //Standard constructor

        this.setMinimumSize(new Dimension(650, 500));
        this.setTitle(String.format("Details for order id: %d",id));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Get order data from database
        String[] metadata = new String[2];
        String[][] itemData = null;

        try {
            metadata = dbHandler.getOrderMetadata(id);
            itemData = dbHandler.getOrderContents(id);
        }
        catch (Exception e) {
            System.out.println("error getting data from database");
        }

        //Set up the window elements
        JPanel mainPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        JLabel orderIdLabel = new JLabel("Order ID: " + String.valueOf(id));
        JLabel tableLabel = new JLabel("Table: " + metadata[0]);
        JLabel arrivedLabel = new JLabel("Time arrived: " + metadata[1]);
        mainPanel.setLayout(new BorderLayout());

        //Calculate total order price and place at bottom
        int total = 0;
        for (int i=0; i<itemData.length; i++) {
            total += Integer.parseInt(itemData[i][1]);
        }
        String formattedTotal = String.format("£%d.%02d", total/100, total%100);
        JLabel totalLabel = new JLabel(String.format("Order Total: %s", formattedTotal));
        bottomPanel.add(totalLabel);

        //Set up fonts used
        Font defaultFont = totalLabel.getFont();
        Font boldFont = new Font(defaultFont.getFontName(), Font.BOLD, defaultFont.getSize()+12);
        final Font largerFont = new Font(defaultFont.getFontName(), Font.BOLD, defaultFont.getSize()+5);
        final Font italicFont = new Font(defaultFont.getFontName(), Font.ITALIC, defaultFont.getSize());
        totalLabel.setFont(boldFont);


        //Put some buttons below order total
        JButton printButton = new JButton("Print paper bill");
        JButton confirmPaymentButton = new JButton("Confirm payment");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        buttonPanel.add(confirmPaymentButton);
        bottomPanel.add(buttonPanel);

        //Place the labels at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(orderIdLabel);
        topPanel.add(tableLabel);
        topPanel.add(arrivedLabel);


        //Class for item data
        class itemPanel extends JPanel {
            itemPanel(String name, String price, String status) {
                GridLayout gridLayout = new GridLayout(1,4);
                this.setLayout(gridLayout);

                //Label for item name
                JLabel nameLabel = new JLabel(name);
                nameLabel.setFont(largerFont);
                this.add(nameLabel);

                //Price label
                int priceInt = Integer.parseInt(price);
                String priceFormatted = String.format("£%d.%02d", priceInt / 100, priceInt % 100);
                JLabel priceLabel = new JLabel(priceFormatted);
                priceLabel.setFont(largerFont);
                this.add(priceLabel);

                //Status label
                final JLabel statusLabel = new JLabel("Status: " + status);
                statusLabel.setFont(italicFont);
                this.add(statusLabel);

                //Button for confirm delivery
                JButton confirmDeliveredButton = new JButton("Confirm delivered");
                confirmDeliveredButton.setAlignmentY(CENTER_ALIGNMENT);
                JPanel confirmButtonPanel = new JPanel();
                confirmButtonPanel.setLayout(new BoxLayout(confirmButtonPanel, BoxLayout.PAGE_AXIS));
                confirmButtonPanel.add(Box.createVerticalStrut(30));
                confirmButtonPanel.add(confirmDeliveredButton);
                confirmButtonPanel.setAlignmentY(BOTTOM_ALIGNMENT);
                this.add(confirmButtonPanel);

                class confirmDeliveredButtonListener implements ActionListener {
                    private String name = "";
                    private int id = 0;
                    confirmDeliveredButtonListener(int id, String name) {
                        this.id = id;
                        this.name = name;
                    }
                    public void actionPerformed(ActionEvent a) {
                        dbHandler.confirmDelivered(name, id);
                        statusLabel.setText("Status: delivered");
                    }
                }
                confirmDeliveredButton.addActionListener(new confirmDeliveredButtonListener(id, name));
            }
        }
        //Add the panel to the scroll panel


        //Read order contents into a scrollpane
        JPanel scrollableArea = new JPanel();
        BoxLayout layout = new BoxLayout(scrollableArea, BoxLayout.PAGE_AXIS);
        scrollableArea.setLayout(layout);
        for (int i = 0; i<itemData.length; i++) {
            scrollableArea.add(new itemPanel(itemData[i][0],itemData[i][1],itemData[i][2]));
        }


        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(new JScrollPane(scrollableArea), BorderLayout.CENTER);
        this.add(mainPanel);


        this.setVisible(true);
    }
}
