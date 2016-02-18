import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class DesktopGUI extends JFrame {

    public DesktopGUI() {

        initUI();
    }

    private void initUI() {


        setTitle("Restaurant Orders");
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



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

