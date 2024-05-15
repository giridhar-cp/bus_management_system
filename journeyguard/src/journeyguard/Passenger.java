package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Passenger extends JFrame {
    private JTextField searchField;
    private JButton addButton;
    private JButton payButton;
    private JButton historyButton;
    private JTextArea detailsArea;

    public Passenger() {
        setTitle("Passenger Details");
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
        JPanel navBar = new JPanel();
        navBar.setBounds(0, 100, 1080, 20);
        navBar.setBackground(Color.decode("#0077B6"));

        navBar.setLayout(new GridLayout(1, 5));
        String[] navButtonNames = { "Home", "Bus", "Passenger", "Driver","Maintenance" };
        for (String buttonName : navButtonNames) {
            JButton button = new JButton(buttonName);
            button.setBackground(Color.decode("#0077B6"));
            if ("Passenger".equals(buttonName)) {
                button.setForeground(Color.green);
            } else {
                button.setForeground(Color.white);
            }
            button.addActionListener(new NavButtonListener());
            navBar.add(button);
        }

        add(navBar);

        // Add Passenger Button
        addButton = new JButton("Add Passenger");
        addButton.setBounds(450, 150, 200, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to AddPassenger.java
                AddPassenger addPassenger = new AddPassenger();
                addPassenger.setVisible(true);
            }
        });
        add(addButton);

        // Search Field
        searchField = new JTextField();
        searchField.setBounds(50, 200, 300, 30);
        add(searchField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(360, 200, 100, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String admissionNumber = searchField.getText();
                // Fetch passenger details from the database based on admission number
                String passengerDetails = getPassengerDetails(admissionNumber);
                detailsArea.setText(passengerDetails);
                // Enable pay and history buttons only if details are found
                payButton.setEnabled(!passengerDetails.isEmpty());
                historyButton.setEnabled(!passengerDetails.isEmpty());
            }
        });
        add(searchButton);

        // Passenger Details Area
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(50, 250, 980, 300);
        add(scrollPane);

        // Pay Button
        payButton = new JButton("Pay");
        String admissionNumber = searchField.getText();
        payButton.setBounds(400, 580, 100, 30);
        payButton.setEnabled(false);
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to Pay.java
            	String admissionNumber = searchField.getText().trim();
            	
                Pay pay = new Pay((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource()),admissionNumber);
                pay.setVisible(true);
            }
        });
        add(payButton);

        // History Button
        historyButton = new JButton("History");
        historyButton.setBounds(600, 580, 100, 30);
        historyButton.setEnabled(false); // Disabled by default
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String admissionNumber = searchField.getText().trim();
                History history = new History(admissionNumber);
                history.setVisible(true);
            }
        });
        add(historyButton);
    }

    private String getPassengerDetails(String admissionNumber) {
        // Connect to the database and fetch passenger details based on admission number
        String details = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root","");
            String query = "SELECT * FROM passenger_details WHERE admin_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, admissionNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                details += "Admission Number: " + resultSet.getString("admin_no") + "\n";
                details += "Name: " + resultSet.getString("name") + "\n";
                details += "Semester: " + resultSet.getString("sem") + "\n";
                details += "Branch: " + resultSet.getString("branch") + "\n";
                details += "Contact: " + resultSet.getString("contact") + "\n";
                details += "Bus No: " + resultSet.getString("bnum") + "\n";
                details += "Fee: " + resultSet.getString("fee") + "\n";
                details += "Due: " + resultSet.getString("due") + "\n";
            } else {
                details = "No details found for admission number: " + admissionNumber;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    private class NavButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();
            switch (buttonText) {
                case "Home":
                    // Navigate to Home.java
                    dispose();
                    Home home = new Home();
                    home.setVisible(true);
                    break;
                case "Bus":
                    // Navigate to Bus.java
                    dispose();
                    Bus bus = new Bus();
                    bus.setVisible(true);
                    break;
                case "Passenger":
                    // Already on Passenger.java
                    break;
                case "Driver":
                    // Navigate to Driver.java
                    dispose();
                    Driver driver = new Driver();
                    driver.setVisible(true);
                    break;
                case "Maintenance":
                    // Navigate to Maintenance.java
                    dispose();
                    Maintenance maintenance = new Maintenance();
                    maintenance.setVisible(true);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        // Run the Passenger frame
        Passenger passenger = new Passenger();
        passenger.setVisible(true);
    }
}
