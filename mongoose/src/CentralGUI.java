import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.*;

public class CentralGUI extends JFrame {
	
	/*
	 * Class for the main GUI to display orders, bookings, table availability and menu contents.
	 * Each of these can be placed in its own tab when it's finished.
	 * 
	 * Made by Sean McCarthy
	 */
	
	private		JTabbedPane tabbedPane;
	private		JPanel		bookingsTab;
	private		JPanel		tablesTab;
	private 	JPanel		menuTab;
	private 	JLabel 		ipLabel;
	private 	JLabel 		portLabel;
	private 	JLabel 		connectionsLabel;
	private 	JPanel 		ordersTabScrollableArea;
	
	public CentralGUI() {
		
		//Basic setup
		setTitle("Server GUI");
		setSize( 800, 500 );
		setBackground( Color.lightGray );
	
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );
		
		//TEST CODE Create the tabs
		bookingsTab = new JPanel();
		tablesTab = new JPanel();
		menuTab = new JPanel();
		
		//Create the tabbed pane, and add the tabs to it - code for creating each tab is contained in its own function.
		tabbedPane = new JTabbedPane();
		createOrdersTab();
		populateOrdersTab();
		tabbedPane.addTab( "Bookings", bookingsTab );
		tabbedPane.addTab( "Tables", tablesTab );
		tabbedPane.addTab( "Menu", menuTab);
		createOrdersTab();
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
		JPanel ordersTab = new JPanel(new BorderLayout());
		ordersTab.add(new JScrollPane(ordersTabScrollableArea), BorderLayout.CENTER);
		tabbedPane.add(ordersTab, "Orders");
		
		//Set up the two buttons at the bottom
		JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton addOrderButton = new JButton("Add order");
		JButton refreshButton = new JButton("Refresh");
		buttonArea.add(addOrderButton);
		buttonArea.add(refreshButton);
		ordersTab.add(buttonArea, BorderLayout.SOUTH);

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
		
	}
	
	
	public static void main(String[] args) {
		
		//create and make visible a new gui
		
		CentralGUI mainFrame = new CentralGUI();
		mainFrame.setVisible(true);
	}
	

	
}
