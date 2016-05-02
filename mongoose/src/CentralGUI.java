import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import javax.xml.crypto.Data;

public class CentralGUI extends JFrame {

	/*
	 * Class for the main GUI to display orders, bookings, table availability and menu contents.
	 * Each of these can be placed in its own tab when it's finished.
	 *
	 * Made by Sean McCarthy
	 */

    private		JTabbedPane tabbedPane;
    private		JPanel		bookingsTab;
    private 	JPanel		menuTab;
    private 	JLabel 		ipLabel;
    private 	JLabel 		portLabel;
    private 	JLabel 		connectionsLabel;
    private     JPanel      ordersTab;
    private 	JPanel 		ordersTabScrollableArea;
    private     JPanel      bookingsTabScrollableArea;
    private 	JPanel 		menuTabScrollableArea;

    public CentralGUI() {

        //Basic setup
        setTitle("Server GUI");
        setSize( 1000, 600 );
        setBackground( Color.lightGray );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );

        //TEST CODE Create the tabs
        bookingsTab = new JPanel();
        menuTab = new JPanel();

        //Create the tabbed pane, and add the tabs to it - code for creating each tab is contained in its own function.
        tabbedPane = new JTabbedPane();
        createOrdersTab();
        createBookingsTab();
        createTablesTab();
        createMenuTab();
        populateMenuTab();
        createRevenueTab();
        createSalesTab();
        topPanel.add( tabbedPane, BorderLayout.CENTER );

        //Create the connection info box at the top
        JLabel ipLabelTag = new JLabel("Current IP address:");
        ipLabel = new JLabel("xxx.xxx.xxx.xxx");
        JLabel portLabelTag = new JLabel("Current port:");
        portLabel = new JLabel("xx");

        JPanel ipLayout = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ipLayout.add(ipLabelTag);
        ipLayout.add(ipLabel);

        JPanel portLayout = new JPanel(new FlowLayout(FlowLayout.LEFT));
        portLayout.add(portLabelTag);
        portLayout.add(portLabel);

        JPanel connectionLayout = new JPanel();
        connectionLayout.setLayout(new BoxLayout(connectionLayout, BoxLayout.PAGE_AXIS));
        connectionLayout.add(ipLayout);
        ipLayout.setAlignmentX( Component.LEFT_ALIGNMENT );
        connectionLayout.add(portLayout);
        portLayout.setAlignmentX( Component.LEFT_ALIGNMENT );

        connectionLayout.setBorder(BorderFactory.createTitledBorder("Current connection info:"));

        topPanel.add(connectionLayout, BorderLayout.NORTH);
    }

    private void createOrdersTab() {

        //Create the scrollable orders tab
        ordersTabScrollableArea = new JPanel(new BorderLayout());
        ordersTab = new JPanel(new BorderLayout());
        ordersTab.add(new JScrollPane(ordersTabScrollableArea), BorderLayout.CENTER);
        tabbedPane.add(ordersTab, "Orders");

        //Set up the two buttons at the bottom
        JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addOrderButton = new JButton("Add order");
        JButton refreshButton = new JButton("Refresh");
        buttonArea.add(addOrderButton);
        buttonArea.add(refreshButton);
        ordersTab.add(buttonArea, BorderLayout.SOUTH);

        //Add button listeners for the buttons
        class refreshButtonListener implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                populateOrdersTab();
                ordersTabScrollableArea.updateUI();
                ordersTab.updateUI();
                ordersTab.repaint();
            }
        }
        refreshButton.addActionListener(new refreshButtonListener());

        class addOrderPopup extends JFrame {
            /*
            popup for when the add order button is pressed
             */
            addOrderPopup() {
                this.setMinimumSize(new Dimension(300,300));
                this.setTitle("Create a new order");
                this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
                JLabel messageLabel = new JLabel("Choose table:");
                final JComboBox tableComboBox = new JComboBox();
                for (int i = 1; i<10; i++) {
                    tableComboBox.addItem(i);
                }
                JButton okayButton = new JButton("Create order");
                class okayButtonListener implements ActionListener {
                    public void actionPerformed (ActionEvent a) {
                        DatabaseHandler dbHandler = new DatabaseHandler();
                        dbHandler.createOrder(Integer.parseInt(tableComboBox.getSelectedItem().toString()));
                        ordersTabScrollableArea.updateUI();
                        ordersTab.updateUI();
                        ordersTab.repaint();
                    }
                }
                okayButton.addActionListener(new okayButtonListener());

                JPanel chooserPanel = new JPanel(new GridLayout(2,1));
                chooserPanel.add(messageLabel);
                chooserPanel.add(tableComboBox);
                mainPanel.add(chooserPanel);
                mainPanel.add(okayButton);
                this.add(mainPanel);
            }
        }

        class addButtonListener implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                JFrame popup = new addOrderPopup();
                popup.setVisible(true);
            }
        }
        addOrderButton.addActionListener(new addButtonListener());


        //Populate the orders tab with order info
        populateOrdersTab();
    }

    private void populateOrdersTab() {
		/*
		 * Method to read order info from the database, and fill the orders tab with this OrderPanel objects containing this info
		 */

        class OrderPanel extends JPanel {
            /*
             * A class for the panel that contains all the info on an order, in the orders tab
             */
            OrderPanel(int id, int table, int received, int waiting) {
				/*
				 * Basic setup of the panel elements
				 */

                //Create swing components
                JLabel orderIDLabel = new JLabel(String.format("Order id: %d", id));
                JLabel tableIDLabel = new JLabel(String.format("Table: %d", table));
                JLabel waitingLabel = new JLabel(String.format("Waiting: %d", waiting));
                JLabel receivedLabel = new JLabel(String.format("Received: %d", received));
                JButton detailsButton = new JButton("Details");
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(detailsButton);

                //Layout the components
                this.setAlignmentX(LEFT_ALIGNMENT);
                this.setMaximumSize(new Dimension(99999, 65));
                this.setBorder(BorderFactory.createDashedBorder(getForeground()));
                this.setLayout(new GridLayout(2,3));

                //Add components to layout
                this.add(orderIDLabel);
                this.add(tableIDLabel);
                this.add(buttonPanel);
                this.add(receivedLabel);
                this.add(waitingLabel);
            }
        }

        //Create a database handler and use its methods to pull info from the database
        DatabaseHandler dbHandler = new DatabaseHandler();
        int[][] allOrderInfo = dbHandler.getAllOrderInfo();

        //Format the info into OrderPanel objects and place them in the orders tab
        JPanel ordersTabCentralArea = new JPanel();
        ordersTabCentralArea.setLayout(new BoxLayout(ordersTabCentralArea, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < allOrderInfo.length; i++) {
            OrderPanel generatedOrderPanel = new OrderPanel(allOrderInfo[i][0], allOrderInfo[i][1], allOrderInfo[i][2], allOrderInfo[i][3]);
            ordersTabCentralArea.add(generatedOrderPanel, BorderLayout.CENTER);
        }

        ordersTabScrollableArea.add(ordersTabCentralArea, BorderLayout.CENTER);
        ordersTab.updateUI();

    }

    private void createBookingsTab() {
        /*
        Creates a tab to contain all the bookings info, with 2 buttons
         */

        //Create the scrollable area for the tab
        bookingsTabScrollableArea = new JPanel(new BorderLayout());
        JPanel bookingsTab = new JPanel(new BorderLayout());
        bookingsTab.add(new JScrollPane(bookingsTabScrollableArea), BorderLayout.CENTER);
        tabbedPane.add(bookingsTab, "Bookings");

        //Set up the button at the bottom
        JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton newBookingButton = new JButton("New booking");
        JButton refreshBookingsButton = new JButton("Refresh");
        buttonArea.add(newBookingButton);
        buttonArea.add(refreshBookingsButton);
        bookingsTab.add(buttonArea, BorderLayout.SOUTH);

        //Add button listeners to the buttons
        class newBookingButtonListener implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                NewBookingPopup popup = new NewBookingPopup();
                popup.run();
            }
        }
        newBookingButton.addActionListener(new newBookingButtonListener());

        //add a listener to refresh the bookings tab
        class refreshButtonListener implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                populateBookingsTab();
            }
        }
        refreshBookingsButton.addActionListener(new refreshButtonListener());

        //Populate the bookings tab
        populateBookingsTab();

    }

    public void populateBookingsTab() {
       /*
        Fills the bookings tab with info read from the database

         */

        class BookingPanel extends JPanel {
            /*
            A JPanel containing all the info for a single booking, with buttons to get more info

            There is a details button to display more info for one specific booking, and a remove button
            which removes the booking
             */
            private String name;
            private String date;
            private String time;
            private String phone;
            private String email;
            private String partySize;
            private int table;
            public String reference;

            BookingPanel(String name, String date, String time, String phone, String email, String partySize, int table, String reference) {
                this.name = name;
                this.date = date;
                this.time = time;
                this.phone = phone;
                this.email = email;
                this.partySize = partySize;
                this.table = table;
                this.reference = reference;


                JButton detailsButton = new JButton("Details");
                JButton removeButton = new JButton("Remove");
                JPanel buttonsPanel = new JPanel();
                buttonsPanel.add(detailsButton);
                buttonsPanel.add(removeButton);

                //Add button listeners for the "details" and "remove" buttons
                class removeBookingButtonListener implements ActionListener {
                    private String reference;
                    removeBookingButtonListener(String reference){
                        this.reference = reference;
                    }
                    public void actionPerformed (ActionEvent a) {
                        DatabaseHandler dbHandler = new DatabaseHandler();
                        dbHandler.removeBooking(reference);
                        populateBookingsTab();
                    }
                }
                removeButton.addActionListener(new removeBookingButtonListener(reference));

                class bookingDetailsButtonListener implements ActionListener {
                    public void actionPerformed (ActionEvent a) {
                        displayDetails();
                    }
                }
                detailsButton.addActionListener(new bookingDetailsButtonListener());



                this.setAlignmentX(LEFT_ALIGNMENT);
                this.setMaximumSize(new Dimension(99999, 65));
                this.setBorder(BorderFactory.createDashedBorder(getForeground()));
                this.setLayout(new GridLayout(1,3));

                JLabel bookingInfoLabel = new JLabel(String.format("   '%s',    party of %s at:    %s on %s", this.name, this.partySize, this.time, this.date));

                this.add(bookingInfoLabel);
                this.add(new JPanel());
                this.add(buttonsPanel);
            }

            public void displayDetails() {
                /*
                Creates a small popup to show further details about the booking
                 */
                JFrame detailsFrame = new JFrame();
                detailsFrame.setTitle("Booking details");
                detailsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                detailsFrame.setMinimumSize(new Dimension(500,500));

                JPanel labelsPanel = new JPanel();
                labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.PAGE_AXIS));

                JLabel nameLabel = new JLabel("Name: " + this.name);
                labelsPanel.add(nameLabel);
                JLabel dateLabel = new JLabel("Date: "+ this.date);
                labelsPanel.add(dateLabel);
                JLabel timeLabel = new JLabel("Time: " + this.time);
                labelsPanel.add(timeLabel);
                JLabel phoneLabel = new JLabel("Contact phone: " + this.phone);
                labelsPanel.add(phoneLabel);
                JLabel emailLabel = new JLabel("Contact EMail: " + this.email);
                labelsPanel.add(emailLabel);
                JLabel partySizeLabel = new JLabel("Party size: " + this.partySize);
                labelsPanel.add(partySizeLabel);
                JLabel tableLabel = new JLabel("Preferred table: " + String.valueOf(this.table));
                labelsPanel.add(tableLabel);
                JLabel referenceLabel = new JLabel("Booking reference: " + this.reference);
                labelsPanel.add(referenceLabel);

                detailsFrame.add(labelsPanel);
                detailsFrame.setVisible(true);
            }
        }

        //Create a JPanel to hold all the bookings before they are added to the scrollable area
        JPanel bookingsTabCentralArea = new JPanel();
        bookingsTabCentralArea.setLayout(new BoxLayout(bookingsTabCentralArea, BoxLayout.PAGE_AXIS));
        bookingsTabScrollableArea.add(bookingsTabCentralArea);

        //Now, read through the bookings in the database and format them into BookingPanel objects, adding them to bookingTabCentralArea for
        DatabaseHandler dbHandler = new DatabaseHandler();
        if (dbHandler.getBookingCount() == 0) {
            bookingsTabCentralArea.add(new JPanel());
        }
        else {
            String[][] allBookingInfo = dbHandler.getAllBookingInfo();
            for (int i = 0; i < dbHandler.getBookingCount(); i++) {
                BookingPanel bookingPanelToAdd = new BookingPanel(allBookingInfo[i][0], allBookingInfo[i][1], allBookingInfo[i][2], allBookingInfo[i][3],
                        allBookingInfo[i][4], allBookingInfo[i][5], Integer.parseInt(allBookingInfo[i][6]), allBookingInfo[i][7]);
                bookingsTabCentralArea.add(bookingPanelToAdd);

            }
        }
        bookingsTabCentralArea.updateUI();
    }

    public void createTablesTab() {
        /*
        Adds a tab to the tabbed layout with the table availability visualiser in it
         */
        TablesGUI tablesTab = new TablesGUI("whatever");
        tabbedPane.add(tablesTab, "Tables");

    }

    public void createMenuTab() {
        /*
        Creates a menu tab in the tabbed layout, another function fills it with menu item descriptions
         */

        //Create the scrollable menu tab
        menuTabScrollableArea = new JPanel(new BorderLayout());
        final JPanel menuTab = new JPanel(new BorderLayout());
        menuTab.add(new JScrollPane(menuTabScrollableArea), BorderLayout.CENTER);
        tabbedPane.add(menuTab, "Menu");

        //Set up the two buttons at the bottom
        JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addItemButton = new JButton("Add menu item");
        JButton refreshButton = new JButton("Refresh");
        buttonArea.add(addItemButton);
        buttonArea.add(refreshButton);
        menuTab.add(buttonArea, BorderLayout.SOUTH);

        //Define the function for adding menu items
        class AddMenuItemPopup extends JFrame {
            /*
            class for the popup through which items are added to the menu
             */

            JFormattedTextField priceField;
            JTextField nameField;
            JComboBox categoryComboBox;

            AddMenuItemPopup() {
                this.setTitle("Add new menu item");
                this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                this.setMinimumSize(new Dimension(300,300));

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new GridLayout(4,1));

                JLabel nameLabel = new JLabel("Name: ");
                nameField = new JTextField();
                JPanel namePanel = new JPanel(new GridLayout(2,1));
                namePanel.add(nameLabel);
                namePanel.add(nameField);

                JLabel categoryLabel = new JLabel("Category: ");
                DefaultComboBoxModel categoriesModel = new DefaultComboBoxModel();
                categoriesModel.addElement("drinks");
                categoriesModel.addElement("starters");
                categoriesModel.addElement("mains");
                categoriesModel.addElement("desserts");
                categoryComboBox = new JComboBox(categoriesModel);
                JPanel categoriesPanel = new JPanel(new GridLayout(2,1));
                categoriesPanel.add(categoryLabel);
                categoriesPanel.add(categoryComboBox);

                JLabel priceLabel = new JLabel("Price (in pence):");
                NumberFormat numberFormat = NumberFormat.getIntegerInstance();
                NumberFormatter formatter = new NumberFormatter(numberFormat);
                numberFormat.setGroupingUsed(false);
                formatter.setValueClass(Integer.class);
                formatter.setMinimum(0);
                formatter.setCommitsOnValidEdit(true);
                priceField = new JFormattedTextField(formatter);
                JPanel pricePanel = new JPanel(new GridLayout(2,1));
                pricePanel.add(priceLabel);
                pricePanel.add(priceField);

                JButton okayButton = new JButton("Okay");
                class okayButtonListener implements ActionListener {
                    public void actionPerformed (ActionEvent a) {
                        submitFieldEntries();
                    }
                }
                okayButton.addActionListener(new okayButtonListener());
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(okayButton);

                mainPanel.add(namePanel);
                mainPanel.add(categoriesPanel);
                mainPanel.add(pricePanel);
                mainPanel.add(buttonPanel);

                this.add(mainPanel);
                this.setVisible(true);
            }

            public void submitFieldEntries() {
                /*
                read price, category and name then store them in database
                 */

                String name = nameField.getText();
                String category = categoryComboBox.getSelectedItem().toString();
                String priceString = priceField.getText();
                int price = 0;
                if (!priceString.equals("")) {
                    price = Integer.parseInt(priceString);
                }

                if (!name.equals("")) {
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    dbHandler.addMenuItem(name, category, price);
                    System.out.printf("Added '%s' to menu. \n", name);
                    populateMenuTab();
                    menuTab.updateUI();
                    this.dispose();
                }
                else {
                    System.out.println("Name field cannot be left empty, try again.");
                }
            }
        }

        //Add button listeners for the buttons
        class refreshButtonListener implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                populateMenuTab();
                menuTab.updateUI();
            }
        }
        refreshButton.addActionListener(new refreshButtonListener());

        class addItemButtonListener implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                new AddMenuItemPopup();
            }
        }
        addItemButton.addActionListener(new addItemButtonListener());
        ordersTab.updateUI();
    }

    public void populateMenuTab() {
        /*
        Fills the menu tab with menu items and prices, placing them in menuTabScrollableArea that was created by createMenuTab()
         */
        DatabaseHandler dbHandler = new DatabaseHandler();


        //Firstly, create an area for the menu items to be displayed in
        JPanel menuTabCentralArea = new JPanel();
        menuTabCentralArea.setLayout(new BoxLayout(menuTabCentralArea, BoxLayout.PAGE_AXIS));
        menuTabCentralArea.setAlignmentX(LEFT_ALIGNMENT);

        //Now, get the menu items from the database
        String[][] menuArray = dbHandler.returnMenuAsArray();
        int menuCount = menuArray.length;

        //Place menu items and category tags into the central area
        class CategoryPanel extends JPanel {

            CategoryPanel(String categoryName) {
                JLabel categoryLabel = new JLabel(categoryName);
                Font defaultFont = categoryLabel.getFont();
                Font boldFont = new Font(defaultFont.getFontName(), Font.BOLD, defaultFont.getSize()+12);
                categoryLabel.setFont(boldFont);
                this.setLayout(new GridLayout(1,2));
                this.setMaximumSize(new Dimension(99999, 40));
                this.add(categoryLabel);
                this.setAlignmentX(LEFT_ALIGNMENT);
            }
        }

        class MenuItemPanel extends JPanel {

            MenuItemPanel(String name, String price) {
                JLabel nameLabel = new JLabel(name);
                JLabel priceLabel = new JLabel(price);
                this.setLayout(new GridLayout(1,3));
                this.setMaximumSize(new Dimension(99999, 120));
                this.setMinimumSize(new Dimension(1, 100));
                this.add(nameLabel);
                this.add(new JLabel(""));
                this.add(priceLabel);
            }

        }

        //Populate each category in turn:
        menuTabCentralArea.add(new CategoryPanel(" Drinks:"));
        for (int i = 0; i<menuCount; i++) {
            if (menuArray[i][1].equals("drinks")) {
                menuTabCentralArea.add(new MenuItemPanel(menuArray[i][0], menuArray[i][2]));
            }
        }

        menuTabCentralArea.add(new CategoryPanel(" Starters:"));
        for (int i = 0; i<menuCount; i++) {
            if (menuArray[i][1].equals("starters")) {
                menuTabCentralArea.add(new MenuItemPanel(menuArray[i][0], menuArray[i][2]));
            }
        }

        menuTabCentralArea.add(new CategoryPanel(" Mains:"));
        for (int i = 0; i<menuCount; i++) {
            if (menuArray[i][1].equals("mains")) {
                menuTabCentralArea.add(new MenuItemPanel(menuArray[i][0], menuArray[i][2]));
            }
        }

        menuTabCentralArea.add(new CategoryPanel(" Desserts:"));
        for (int i = 0; i<menuCount; i++) {
            if (menuArray[i][1].equals("desserts")) {
                menuTabCentralArea.add(new MenuItemPanel(menuArray[i][0], menuArray[i][2]));
            }
        }

        //Now, add the area to the tab
        menuTabScrollableArea.removeAll();
        menuTabScrollableArea.add(menuTabCentralArea);
        menuTab.updateUI();

    }

    public void createRevenueTab() {

        JPanel revenueTabScrollableArea = new RevenueTable();
        JPanel revenueTab = new JPanel(new BorderLayout());
        revenueTab.add(new JScrollPane(revenueTabScrollableArea), BorderLayout.CENTER);
        tabbedPane.addTab("Monthly Revenue", revenueTab);
    }

    public void createSalesTab() {

        //Create the scrollable area for the tab
        final JPanel salesTab = new JPanel(new BorderLayout());
        JPanel salesTabScrollableArea = new JPanel();
        salesTab.add(new JScrollPane(salesTabScrollableArea), BorderLayout.CENTER);
        tabbedPane.addTab("Sales", salesTab);

        //Create a JLabel to sit at the top
        final JLabel salesMessageLabel = new JLabel("Choose a menu item to view sales data");
        salesTab.add(salesMessageLabel, BorderLayout.NORTH);

        //Create the combo box and button that sit at the bottom
        DatabaseHandler dbHandler = new DatabaseHandler();
        final JComboBox menuItemComboBox = new JComboBox();
        String[][] menuArray = dbHandler.returnMenuAsArray();
        for (int i=0; i< menuArray.length; i++) {
            menuItemComboBox.addItem(menuArray[i][0]);
        }
        JButton displayButton = new JButton("Display data");

        JPanel comboButtonPanel = new JPanel();
        comboButtonPanel.add(menuItemComboBox);
        comboButtonPanel.add(displayButton);
        salesTab.add(comboButtonPanel, BorderLayout.SOUTH);


        class displayButtonListener implements ActionListener {
            /*
            button listener for the display sales data button
             */
            public void actionPerformed (ActionEvent a) {
                String comboSelection = menuItemComboBox.getSelectedItem().toString();
                salesTab.add(new SalesTable(comboSelection), BorderLayout.CENTER);
                salesMessageLabel.setText(String.format("Sales data for: %s", comboSelection));

            }
        }
        displayButton.addActionListener(new displayButtonListener());
    }


    public static void main(String[] args) {

        //create and make visible a new gui

        CentralGUI mainFrame = new CentralGUI();
        mainFrame.setVisible(true);
    }



}
