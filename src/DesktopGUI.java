import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//made by sean
public class DesktopGUI extends JFrame {

    public DesktopGUI() {

        initUI();
    }

    private void initUI() {


        setTitle("Restaurant Orders");
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //JPANEL

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel customerName = new JLabel("Customer Name: ");
        JTextField customer = new JTextField();

        JLabel partySize = new JLabel("Party Size: ");
        int max = 20;
        int min = 0;
        int step = 1;
        int init = 0;
        SpinnerNumberModel model = new SpinnerNumberModel(init, min, max, step);
        JSpinner size = new JSpinner(model);


        JLabel date = new JLabel("Date: ");
        JTextField dateField = new JTextField();

        JLabel time = new JLabel("Time: ");
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


        JLabel phoneNumber = new JLabel("Phone Number: ");
        JTextField phone = new JTextField();

        JLabel emailAddress = new JLabel("Email Address: ");
        JTextField email = new JTextField();

        JTextArea referenceNumber = new JTextArea();

        JButton bookTable = new JButton("Book Table");

        JButton sendEmailConfirmation = new JButton("Send Email Confirmation");

        add(pane);


        //MENU BAR
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        menubar.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem newOrder = new JMenuItem("New Order");
        file.add(newOrder);
        file.add(exit);

        class neworder implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                DesktopGUI desk = new DesktopGUI();
                desk.setVisible(true);
            }
        }
        newOrder.addActionListener(new neworder());

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

