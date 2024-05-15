package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Maintenance extends JFrame {

    private JTextField busNoField, dateField, complaintField, amountField, remarksField,workshop1Field;
    private JButton saveButton;
    private JButton findExpenditureButton;

    public Maintenance() {
        setTitle("Maintenance");
        setSize(1080, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 1080, 100);
        topPanel.setBackground(new Color(0, 180, 216));
        topPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("JOURNEY GUARD");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        topPanel.add(titleLabel);
        add(topPanel);

        // Navigation Bar
        JPanel navPanel = new JPanel();
        navPanel.setBackground(Color.decode("#0077B6"));
        navPanel.setBounds(0, 100, 1080, 20);
        navPanel.setLayout(new GridLayout(1, 5));

        String[] menuItems = { "Home", "Bus", "Passenger", "Driver", "Maintenance" };
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setBackground(Color.decode("#0077B6"));
            if ("Maintenance".equals(item)) {
                button.setForeground(Color.green);
            } else {
                button.setForeground(Color.white);
            }
            button.addActionListener(new NavButtonListener());
            navPanel.add(button);
        }
        add(navPanel);

        JLabel busNoLabel = new JLabel("Bus No:");
        busNoLabel.setBounds(50, 150, 100, 30);
        add(busNoLabel);

        busNoField = new JTextField();
        busNoField.setBounds(200, 150, 200, 30);
        add(busNoField);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(50, 200, 100, 30);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(200, 200, 200, 30);
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        add(dateField);
        
        JLabel workshop1Label = new JLabel("Workshop:");
        workshop1Label.setBounds(50, 250, 100, 30);
        add(workshop1Label);

        workshop1Field = new JTextField();
        workshop1Field.setBounds(200, 250, 200, 30);
        add(workshop1Field);

        JLabel complaintLabel = new JLabel("Complaint:");
        complaintLabel.setBounds(50, 300, 100, 30);
        add(complaintLabel);

        complaintField = new JTextField();
        complaintField.setBounds(200, 300, 200, 30);
        add(complaintField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50, 350, 100, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(200, 350, 200, 30);
        add(amountField);

        JLabel remarksLabel = new JLabel("Remarks:");
        remarksLabel.setBounds(50, 400, 100, 30);
        add(remarksLabel);

        remarksField = new JTextField();
        remarksField.setBounds(200, 400, 200, 30);
        add(remarksField);

        saveButton = new JButton("Save");
        saveButton.setBounds(200, 450, 200, 50);
        saveButton.addActionListener(new SaveButtonListener());
        add(saveButton);
        JButton findExpenditureButton = new JButton("Find Expenditure");
        findExpenditureButton.setBounds(200, 525, 200, 50);
        findExpenditureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate to Expenditure.java
                Expenditure expenditurePage = new Expenditure();
                expenditurePage.setVisible(true);
            }
        });
        add(findExpenditureButton);
    }

    private class NavButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();
            switch (buttonText) {
                case "Home":
                    // Navigate to Home.java
                    dispose(); // Close the current frame
                    Home home = new Home();
                    home.setVisible(true);
                    break;
                case "Bus":
                    // Navigate to Bus.java
                    dispose(); // Close the current frame
                    Bus bus = new Bus();
                    bus.setVisible(true);
                    break;
                case "Passenger":
                    // Navigate to Passenger.java
                    dispose(); // Close the current frame
                    Passenger passenger = new Passenger();
                    passenger.setVisible(true);
                    break;
                case "Driver":
                    // Navigate to Driver.java
                    dispose(); // Close the current frame
                    Driver driver = new Driver();
                    driver.setVisible(true);
                    break;
                case "Maintenance":
                    // Already on Maintenance.java
                    break;
            }
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String busNo = busNoField.getText();
            LocalDate date = LocalDate.parse(dateField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String workshop= workshop1Field.getText();
            String complaint = complaintField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String remarks = remarksField.getText();


            try {
            	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                String query = "INSERT INTO maintenance (bnum, date, complaints,workshop, amount, remarks) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, busNo);
                preparedStatement.setDate(2, java.sql.Date.valueOf(date));
                preparedStatement.setString(3, workshop);
                preparedStatement.setString(4, complaint);
                preparedStatement.setDouble(5, amount);
                preparedStatement.setString(6, remarks);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Maintenance details saved successfully.");

                // Clear fields after saving
                busNoField.setText("");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dateField.setText(LocalDate.now().format(dateFormatter));
                workshop1Field.setText("");
                complaintField.setText("");
                amountField.setText("");
                remarksField.setText("");

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Maintenance maintenance = new Maintenance();
                maintenance.setVisible(true);
            }
        });
    }
}
