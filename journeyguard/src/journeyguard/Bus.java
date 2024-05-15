package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class Bus extends JFrame implements ActionListener {

    // Dropdown menu for buses
    private JComboBox<String> busDropdown;

    public Bus() {
        setTitle("Bus");
        setSize(1080, 768);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Top rectangle with gradient
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
        busButton.setForeground(Color.green);
        busButton.setBackground(new Color(0, 119, 182));
        busButton.addActionListener(this);
        navPanel.add(busButton);

        JButton passengerButton = new JButton("Passenger");
        passengerButton.setForeground(Color.WHITE);
        passengerButton.setBackground(new Color(0, 119, 182));
        passengerButton.addActionListener(this);
        navPanel.add(passengerButton);

        JButton driverButton = new JButton("Driver");
        driverButton.setForeground(Color.WHITE);
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
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null);

        // Content inside center panel
        JLabel titleLabel2 = new JLabel("Bus Details");
        titleLabel2.setBounds(440, 50, 200, 30);
        titleLabel2.setForeground(Color.BLACK);
        titleLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel2.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(titleLabel2);

        // Dropdown menu for buses
        busDropdown = new JComboBox<String>();
        busDropdown.setBounds(200, 100, 240, 30);
        populateBusDropdown(); // Populate the dropdown with bus data
        centerPanel.add(busDropdown);

        // Add Bus button
        JButton addButton = new JButton("Add Bus");
        addButton.setBounds(470, 100, 120, 30);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Redirect to AddBus.java
                AddBus addBusPage = new AddBus();
                addBusPage.setVisible(true);
            }
        });
        centerPanel.add(addButton);
        
        

        // Panel to display selected bus details
        JPanel selectedBusDetailsPanel = new JPanel();
        selectedBusDetailsPanel.setBounds(200, 150, 680, 300);
        selectedBusDetailsPanel.setBorder(BorderFactory.createTitledBorder("Selected Bus Details"));
        selectedBusDetailsPanel.setLayout(new GridLayout(0, 2));
        centerPanel.add(selectedBusDetailsPanel);
        
     // Update Bus button
        JButton updateButton = new JButton("Update Bus");
        updateButton.setBounds(620, 100, 120, 30);
        updateButton.setEnabled(false); 
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to update.java
            	String selectedBus = (String) busDropdown.getSelectedItem();
                UpdateBus update = new UpdateBus((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource()),selectedBus);
                update.setVisible(true);
            }
        });
        centerPanel.add(updateButton);
        
     // Delete Bus button
        JButton deleteButton = new JButton("Delete Bus");
        deleteButton.setBounds(760, 100, 120, 30);
        deleteButton.setEnabled(false); 
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected bus number
                String selectedBus = (String) busDropdown.getSelectedItem();
                // Query to delete the bus_details and route for the selected bus
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                    // Delete from bus_details table
                    String deleteBusQuery = "DELETE FROM bus_details WHERE bnum = ?";
                    PreparedStatement deleteBusStatement = conn.prepareStatement(deleteBusQuery);
                    deleteBusStatement.setString(1, selectedBus);
                    deleteBusStatement.executeUpdate();
                    // Delete from route table
                    String deleteRouteQuery = "DELETE FROM route WHERE bnum = ?";
                    PreparedStatement deleteRouteStatement = conn.prepareStatement(deleteRouteQuery);
                    deleteRouteStatement.setString(1, selectedBus);
                    deleteRouteStatement.executeUpdate();
                    // Close connection
                    deleteBusStatement.close();
                    deleteRouteStatement.close();
                    conn.close();
                    JOptionPane.showMessageDialog(Bus.this, "Bus deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Repopulate the bus dropdown
                    populateBusDropdown();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Bus.this, "Error deleting bus", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        centerPanel.add(deleteButton);

        // Listener for bus dropdown selection
        busDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                // Get the selected bus number
                String selectedBus = (String) busDropdown.getSelectedItem();
                if (selectedBus != null && !selectedBus.isEmpty()) {
                    deleteButton.setEnabled(true);
                    updateButton.setEnabled(true);
                } else {
                    deleteButton.setEnabled(false);
                    updateButton.setEnabled(false);
                }
                // Query the database for details of the selected bus
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                    Statement stmt = conn.createStatement();
                    String query = "SELECT * FROM bus_details WHERE bnum = '" + selectedBus + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    // Clear previous details
                    selectedBusDetailsPanel.removeAll();
                    // Display details of the selected bus
                    if (rs.next()) {
                        selectedBusDetailsPanel.add(new JLabel("Bus Number:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("bnum")));
                        selectedBusDetailsPanel.add(new JLabel("Register Number:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("regno")));
                        selectedBusDetailsPanel.add(new JLabel("Date of Registration:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("datereg")));
                        selectedBusDetailsPanel.add(new JLabel("Test Date:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("testdate")));
                        selectedBusDetailsPanel.add(new JLabel("Pollution:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("pollution")));
                        selectedBusDetailsPanel.add(new JLabel("Insurance:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("insurance")));
                        selectedBusDetailsPanel.add(new JLabel("Model:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("model")));
                        selectedBusDetailsPanel.add(new JLabel("Seating Capacity:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("seating")));
                        selectedBusDetailsPanel.add(new JLabel(" status:"));
                        selectedBusDetailsPanel.add(new JLabel(rs.getString("status")));
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                    // Refresh the panel
                    selectedBusDetailsPanel.revalidate();
                    selectedBusDetailsPanel.repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(centerPanel);
    }

    // Method to populate the bus dropdown menu with data from the database
    private void populateBusDropdown() {
        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
            Statement stmt = conn.createStatement();
            // Query to retrieve bus data
            String query = "SELECT bnum FROM bus_details";
            // Execute the query
            ResultSet rs = stmt.executeQuery(query);
            // Populate the dropdown with bus numbers
            while (rs.next()) {
                String busNumber = rs.getString("bnum");
                busDropdown.addItem(busNumber);
            }
            // Close the connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ActionListener for navigation buttons
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Home")) {
            // Redirect to Home page
            Home homePage = new Home();
            homePage.setVisible(true);
            dispose(); // Close the current window
        } else if (command.equals("Bus")) {
            // Do nothing, already on Bus page
        } else if (command.equals("Passenger")) {
            // Redirect to Passenger page
            Passenger passengerPage = new Passenger();
            passengerPage.setVisible(true);
            dispose(); // Close the current window
        } else if (command.equals("Driver")) {
            // Redirect to Driver page
            Driver driverPage = new Driver();
            driverPage.setVisible(true);
            dispose(); // Close the current window
        } else if (command.equals("Maintenance")) {
            // Redirect to Maintenance page
            Maintenance maintenancePage = new Maintenance();
            maintenancePage.setVisible(true);
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Bus busPage = new Bus();
                busPage.setVisible(true);
            }
        });
    }
}
