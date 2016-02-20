import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.*;

//made by Sean O'Connor
public class DesktopGUI extends JFrame {

    public DesktopGUI() {

        initUI();
    }

    JLabel customerNameLabel = new JLabel("Customer Name: ");
    JTextField customer = new JTextField();
    JLabel dateLabel = new JLabel("Date: ");
    JTextField dateField = new JTextField();
    JLabel phoneNumberLabel = new JLabel("Phone Number: ");
    JTextField phone = new JTextField();
    JLabel emailAddressLabel = new JLabel("Email Address: ");
    JTextField email = new JTextField();
    JLabel refLabel = new JLabel();
    JTextField referenceNumber = new JTextField();
    JButton getReference = new JButton("Reference Number");
    JButton sendEmailConfirmation = new JButton("Send Email Confirmation");


    private void initUI() {


        setTitle("Restaurant Bookings");
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //JPANEL

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel partySizeLabel = new JLabel("Party Size: ");
        int max = 20;
        int min = 0;
        int step = 1;
        int init = 0;
        SpinnerNumberModel model = new SpinnerNumberModel(init, min, max, step);
        JSpinner size = new JSpinner(model);

        JLabel timeLabel = new JLabel("Time: ");
        JComboBox timePick = new JComboBox();
        timePick.addItem("18:00");
        timePick.addItem("18:30");
        timePick.addItem("19:00");
        timePick.addItem("19:30");
        timePick.addItem("20:00");
        timePick.addItem("20:30");
        timePick.addItem("21:00");
        timePick.addItem("21:30");
        timePick.addItem("22:00");

        JLabel bookingLengthLabel = new JLabel("Length of Time: ");
        JComboBox lengthTime = new JComboBox();
        lengthTime.addItem("18:00-19:00");
        lengthTime.addItem("18:30-19:30");
        lengthTime.addItem("19:00-20:00");
        lengthTime.addItem("19:30-20:30");
        lengthTime.addItem("20:00-21:00");
        lengthTime.addItem("20:30-21:30");
        lengthTime.addItem("21:00-22:00");
        lengthTime.addItem("21:30-22:30");
        lengthTime.addItem("22:00-23:00");





        JLabel tableNumberLabel = new JLabel("Table Number: ");
        JComboBox tableNo = new JComboBox();
        for (int i = 1; i <= 12; i++) {
            tableNo.addItem(i);
        }



        JButton bookTable = new JButton("Book Table");
        class booktable implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                customer.setText("");
                dateField.setText("");
                phone.setText("");
                email.setText("");
                referenceNumber.setText("");
            }
        }
        bookTable.addActionListener(new booktable());


        class refnumber implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                String ref = UUID.randomUUID().toString();
                referenceNumber.setText(ref);
            }
        }
        getReference.addActionListener(new refnumber());



        class email implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                //RUNS SEND EMAIL CLASS
            }
        }
        sendEmailConfirmation.addActionListener(new email());



        add(pane);


        //MENU BAR
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        menubar.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem newOrder = new JMenuItem("New Order");
        JMenuItem tableAvailability = new JMenuItem("Table Availability");
        file.add(newOrder);
        file.add(tableAvailability);
        file.add(exit);

        class neworder implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                DesktopGUI desk = new DesktopGUI();
                desk.setVisible(true);
            }
        }
        newOrder.addActionListener(new neworder());

        class tablesavilable implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                //opens table display
            }
        }
        tableAvailability.addActionListener(new tablesavilable());

        class exitaction implements ActionListener {
            public void actionPerformed (ActionEvent e) {
                System.exit(0);
            }
        }
        exit.addActionListener(new exitaction());

        JMenu help = new JMenu("Help");
        menubar.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);

        class info implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                JOptionPane.showMessageDialog(null, "Software Created By Team Mongoose",
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        about.addActionListener(new info());
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                DesktopGUI ex = new DesktopGUI();
                ex.pack();
                ex.setVisible(true);
            }
        });
    }
}

