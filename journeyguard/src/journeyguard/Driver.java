package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver extends JFrame implements ActionListener {
    
	private JComboBox<String> driverDropdown;

    public Driver() {
        setTitle("Driver");
        setSize(1080, 768);
        getContentPane().setBackground(Color.CYAN);
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

        // Navigation bar
        JPanel navPanel = new JPanel();
        navPanel.setBounds(0, 100, 1080, 20);
        navPanel.setBackground(new Color(0, 119, 182));
        navPanel.setLayout(new GridLayout(1, 5));

        JButton homeButton = new JButton("Home");
        homeButton.setForeground(Color.WHITE);
        homeButton.setBackground(new Color(0, 119, 182));
        homeButton.addActionListener(this);
        navPanel.add(homeButton);

        JButton busButton = new JButton("Bus");
        busButton.setForeground(Color.WHITE);
        busButton.setBackground(new Color(0, 119, 182));
        busButton.addActionListener(this);
        navPanel.add(busButton);

        JButton passengerButton = new JButton("Passenger");
        passengerButton.setForeground(Color.WHITE);
        passengerButton.setBackground(new Color(0, 119, 182));
        passengerButton.addActionListener(this);
        navPanel.add(passengerButton);

        JButton driverButton = new JButton("Driver");
        driverButton.setForeground(Color.GREEN); // Set active page button color
        driverButton.setBackground(new Color(0, 119, 182));
        driverButton.addActionListener(this);
        navPanel.add(driverButton);

        JButton maintenanceButton = new JButton("Maintenance");
        maintenanceButton.setForeground(Color.WHITE);
        maintenanceButton.setBackground(new Color(0, 119, 182));
        maintenanceButton.addActionListener(this);
        navPanel.add(maintenanceButton);

        add(navPanel);

        // Center panel with main content
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(0, 120, 1080, 648);
        centerPanel.setBackground(Color.white);
        centerPanel.setLayout(null);
        
        // Dropdown menu for buses
        driverDropdown = new JComboBox<String>();
        driverDropdown.setBounds(300, 100, 240, 30);
        populateDriverDropdown(); // Populate the dropdown with bus data
        centerPanel.add(driverDropdown);

        JButton addDriverButton = new JButton("Add Driver");
        addDriverButton.setBounds(600, 100, 120, 30);
        addDriverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // navigate to AddDriver.java
                AddDriver addDriver = new AddDriver();
                addDriver.setVisible(true);
            }
        });
        centerPanel.add(addDriverButton);
        
     // Panel to display selected bus details
        JPanel selectedDriverDetailsPanel = new JPanel();
        selectedDriverDetailsPanel.setBounds(200, 150, 680, 300);
        selectedDriverDetailsPanel.setBorder(BorderFactory.createTitledBorder("Selected driver Details"));
        selectedDriverDetailsPanel.setLayout(new GridLayout(0, 2));
        centerPanel.add(selectedDriverDetailsPanel);
        
     // Delete Bus button
        JButton deleteButton = new JButton("Delete Driver");
        deleteButton.setBounds(750, 100, 120, 30);
        deleteButton.setEnabled(false); // Initially disabled
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected Driver number
                String selectedDriver = (String) driverDropdown.getSelectedItem();
                // Query to delete the driver details
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                    // Delete from bus_details table
                    String deleteDriverQuery = "DELETE FROM driver_details WHERE dname = ?";
                    PreparedStatement deleteDriverStatement = conn.prepareStatement(deleteDriverQuery);
                    deleteDriverStatement.setString(1, selectedDriver);
                    deleteDriverStatement.executeUpdate();
                    // Close connection
                    deleteDriverStatement.close();
                    conn.close();
                    JOptionPane.showMessageDialog(Driver.this, "Driver details deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Repopulate the bus dropdown
                    populateDriverDropdown();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Driver.this, "Error deleting bus", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        centerPanel.add(deleteButton);
        
        // Listener for bus dropdown selection
        driverDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                // Get the selected bus number
                String selectedDriver = (String) driverDropdown.getSelectedItem();
                if (selectedDriver != null && !selectedDriver.isEmpty()) {
                    deleteButton.setEnabled(true); // Enable the delete button when a bus is selected
                } else {
                    deleteButton.setEnabled(false); // Disable the delete button when no bus is selected
                }
                // Query the database for details of the selected bus
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                    Statement stmt = conn.createStatement();
                    String query = "SELECT * FROM driver_details WHERE dname = '" + selectedDriver + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    // Clear previous details
                    selectedDriverDetailsPanel.removeAll();
                    // Display details of the selected bus
                    if (rs.next()) {
                        selectedDriverDetailsPanel.add(new JLabel("licence Number:"));
                        selectedDriverDetailsPanel.add(new JLabel(rs.getString("lic_id")));
                        selectedDriverDetailsPanel.add(new JLabel("Name:"));
                        selectedDriverDetailsPanel.add(new JLabel(rs.getString("dname")));
                        selectedDriverDetailsPanel.add(new JLabel("contact:"));
                        selectedDriverDetailsPanel.add(new JLabel(rs.getString("phone")));
                        selectedDriverDetailsPanel.add(new JLabel("bus num:"));
                        selectedDriverDetailsPanel.add(new JLabel(rs.getString("bnum")));
                        selectedDriverDetailsPanel.add(new JLabel("licence expiry:"));
                        selectedDriverDetailsPanel.add(new JLabel(rs.getString("lic_exp")));
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                    // Refresh the panel
                    selectedDriverDetailsPanel.revalidate();
                    selectedDriverDetailsPanel.repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(centerPanel);
    }
    
 // Method to populate the bus dropdown menu with data from the database
    private void populateDriverDropdown() {
        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
            Statement stmt = conn.createStatement();
            // Query to retrieve bus data
            String query = "SELECT dname FROM driver_details";
            // Execute the query
            ResultSet rs = stmt.executeQuery(query);
            // Populate the dropdown with bus numbers
            while (rs.next()) {
                String dr = rs.getString("dname");
                driverDropdown.addItem(dr);
            }
            // Close the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Home")) {
            // Do nothing, already on Home page
            Home homePage = new Home();
            homePage.setVisible(true);
            dispose();
        } else if (command.equals("Bus")) {
            // navigate to Bus page
            Bus busPage = new Bus();
            busPage.setVisible(true);
            dispose(); // Close the current window
        } else if (command.equals("Passenger")) {
            Passenger passengerPage = new Passenger();
            passengerPage.setVisible(true);
            dispose();
        } else if (command.equals("Driver")) {

        } else if (command.equals("Maintenance")) {

            Maintenance maintenancePage = new Maintenance();
            maintenancePage.setVisible(true);
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Driver driverPage = new Driver();
                driverPage.setVisible(true);
            }
        });
    }
}
