//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Properties;
//import java.util.UUID;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.swing.*;
//
////made by Sean O'Connor
//public class DesktopGUI extends JFrame {
//
//    public DesktopGUI() {
//
//        initUI();
//    }
//
//    JLabel customerNameLabel = new JLabel("Customer Name: ");
//    JTextField customer = new JTextField();
//    JLabel dateLabel = new JLabel("Date: ");
//    JTextField dateField = new JTextField();
//    JLabel phoneNumberLabel = new JLabel("Phone Number: ");
//    JTextField phone = new JTextField();
//    JLabel emailAddressLabel = new JLabel("Email Address: ");
//    JTextField email = new JTextField();
//    JLabel partySizeLabel = new JLabel("Party Size: ");
//    JLabel timeLabel = new JLabel("Time: ");
//    JLabel bookingLengthLabel = new JLabel("Length of Time: ");
//    JLabel tableNumberLabel = new JLabel("Table Number: ");
//    JLabel refLabel = new JLabel("Reference Number: ");
//    JTextField referenceNumber = new JTextField();
//    JButton getReference = new JButton("Reference Number");
//    JButton sendEmailConfirmation = new JButton("Send Email Confirmation");
//    JButton bookTable = new JButton("Book Table");
//    final JComboBox timePick = new JComboBox();
//
//    private void initUI() {
//
//
//        setTitle("Restaurant Bookings");
//        setMinimumSize(new Dimension(700, 500));
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        //partySizeLabel
//        int max = 20;
//        int min = 0;
//        int step = 1;
//        int init = 0;
//        SpinnerNumberModel model = new SpinnerNumberModel(init, min, max, step);
//        final JSpinner size = new JSpinner(model);
//
//
//        //timeLabel
//
//        timePick.addItem("18:00");
//        timePick.addItem("18:30");
//        timePick.addItem("19:00");
//        timePick.addItem("19:30");
//        timePick.addItem("20:00");
//        timePick.addItem("20:30");
//        timePick.addItem("21:00");
//        timePick.addItem("21:30");
//        timePick.addItem("22:00");
//
//        //bookingLengthLabel
//        final JComboBox lengthTime = new JComboBox();
//        lengthTime.addItem("18:00-19:00");
//        lengthTime.addItem("18:30-19:30");
//        lengthTime.addItem("19:00-20:00");
//        lengthTime.addItem("19:30-20:30");
//        lengthTime.addItem("20:00-21:00");
//        lengthTime.addItem("20:30-21:30");
//        lengthTime.addItem("21:00-22:00");
//        lengthTime.addItem("21:30-22:30");
//        lengthTime.addItem("22:00-23:00");
//
//
//
//
//
//        //tableNumberLabel
//        final JComboBox tableNo = new JComboBox();
//        for (int i = 1; i <= 12; i++) {
//            tableNo.addItem(i);
//        }
//
//        //JPANEL
//
//        JPanel head = new JPanel();
//        head.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//
//        gbc.weighty = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(customerNameLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(dateLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(phoneNumberLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(emailAddressLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(partySizeLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 5;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(timeLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 6;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(bookingLengthLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 7;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(tableNumberLabel, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 8;
//        gbc.anchor = GridBagConstraints.CENTER;
//        head.add(refLabel, gbc);
//
//        //next column
//        gbc.weightx = 1;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        gbc.gridwidth = 3;
//        head.add(customer, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        gbc.gridwidth = 3;
//        head.add(dateField, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 2;
//        gbc.gridwidth = 3;
//        head.add(phone, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 3;
//        gbc.gridwidth = 3;
//        head.add(email, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 4;
//        gbc.gridwidth = 3;
//        head.add(size, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 5;
//        gbc.gridwidth = 3;
//        head.add(timePick, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 6;
//        gbc.gridwidth = 3;
//        head.add(lengthTime, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 7;
//        gbc.gridwidth = 3;
//        head.add(tableNo, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 1;
//        gbc.gridy = 8;
//        gbc.gridwidth = 2;
//        head.add(referenceNumber, gbc);
//
//
//        JPanel foot = new JPanel();
//        foot.setLayout(new BorderLayout());
//
//        foot.add(getReference, BorderLayout.WEST);
//        foot.add(bookTable, BorderLayout.CENTER);
//        foot.add(sendEmailConfirmation, BorderLayout.EAST);
//
//
//        add(head, BorderLayout.NORTH);
//        add(foot, BorderLayout.SOUTH);
//
//
//
//
//
//        class booktable implements ActionListener {
//            public void actionPerformed (ActionEvent a) {
//                customer.setText("");
//                dateField.setText("");
//                phone.setText("");
//                email.setText("");
//                referenceNumber.setText("");
//                timePick.setSelectedIndex(0);
//                lengthTime.setSelectedItem(0);
//                tableNo.setSelectedIndex(0);
//            }
//        }
//        bookTable.addActionListener(new booktable());
//
//
//        class refnumber implements ActionListener {
//            public void actionPerformed (ActionEvent a) {
//                String ref = UUID.randomUUID().toString();
//                referenceNumber.setText(ref);
//            }
//        }
//        getReference.addActionListener(new refnumber());
//
//
//
//        class email implements ActionListener {
//            public void actionPerformed (ActionEvent a) {
//                String to = email.getText();
//                String from = "softwaremongoose@gmail.com";
//                final String username = "softwaremongoose@gmail.com";
//                final String password = "mongoose123";
//
//
//                Properties properties = new Properties();
//                properties.put("mail.smtp.host", "smtp.gmail.com");
//                properties.put("mail.smtp.socketFactory.port", "465");
//                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//                properties.put("mail.smtp.auth", "true");
//                properties.put("mail.smtp.port", "465");
//
//
//                Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//
//                try {
//
//                    MimeMessage message = new MimeMessage(session);
//
//                    message.setFrom(new InternetAddress(from));
//
//                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//
//                    message.setSubject("Booking Confirmation at Mongoose Restaurant, Reference: "
//                            + referenceNumber.getText());
//                    message.setText("Hello " + customer.getText());
//                    message.setText("Your table is booked for " + timePick.getSelectedItem().toString()
//                            + " on: " + dateField.getText());
//                    message.setText("We look forward to seeing you, reply to this email if you have any queries!");
//
//
//                    Transport.send(message);
//                    System.out.println("Confirmation Sent");
//
//                } catch (MessagingException mex) {
//                    mex.printStackTrace();
//                }
//            }
//        }
//        sendEmailConfirmation.addActionListener(new email());
//
//
//
//
//
//
//        //MENU BAR
//        JMenuBar menubar = new JMenuBar();
//        setJMenuBar(menubar);
//
//        JMenu file = new JMenu("File");
//        menubar.add(file);
//        JMenuItem exit = new JMenuItem("Exit");
//        JMenuItem newOrder = new JMenuItem("New Order");
//        JMenuItem tableAvailability = new JMenuItem("Table Availability");
//        file.add(newOrder);
//        file.add(tableAvailability);
//        file.add(exit);
//
//        class neworder implements ActionListener {
//            public void actionPerformed (ActionEvent a) {
//                DesktopGUI desk = new DesktopGUI();
//                desk.setVisible(true);
//            }
//        }
//        newOrder.addActionListener(new neworder());
//
//        class tablesavilable implements ActionListener {
//            public void actionPerformed (ActionEvent a) {
//                //opens table display
//            }
//        }
//        tableAvailability.addActionListener(new tablesavilable());
//
//        class exitaction implements ActionListener {
//            public void actionPerformed (ActionEvent e) {
//                System.exit(0);
//            }
//        }
//        exit.addActionListener(new exitaction());
//
//        JMenu help = new JMenu("Help");
//        menubar.add(help);
//        JMenuItem about = new JMenuItem("About");
//        help.add(about);
//
//        class info implements ActionListener {
//            public void actionPerformed (ActionEvent a) {
//                JOptionPane.showMessageDialog(null, "Software Created By Team Mongoose",
//                        "About", JOptionPane.INFORMATION_MESSAGE);
//            }
//        }
//        about.addActionListener(new info());
//    }
//
//    public static void main(String[] args) {
//
//        EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                DesktopGUI ex = new DesktopGUI();
//                ex.pack();
//                ex.setVisible(true);
//            }
//        });
//    }
//}
//
