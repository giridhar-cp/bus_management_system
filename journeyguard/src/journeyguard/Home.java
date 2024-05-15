package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {
    public Home() {
        setTitle("Home");
        setSize(1094, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        // Notification button
        JButton notificationButton = new JButton("Notification");
        notificationButton.setForeground(Color.WHITE);
        notificationButton.setBackground(Color.GREEN);
        notificationButton.setBounds(800, 25, 150, 40);
        notificationButton.setFont(new Font("Arial", Font.PLAIN, 16));
        notificationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action here to navigate to Notification.java
                Notification notificationPage = new Notification();
                notificationPage.setVisible(true);
            }
        });
        topPanel.add(notificationButton, BorderLayout.EAST);

        // Sign out button
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setBackground(Color.RED);
        signOutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        signOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action here to logout the current user and navigate to Admin.java
                Admin adminPage = new Admin();
                adminPage.setVisible(true);
                dispose();
            }
        });
        topPanel.add(signOutButton, BorderLayout.EAST);

        add(topPanel);
        // Navigation bar
        JPanel navPanel = new JPanel();
        navPanel.setBounds(0, 100, 1080, 20);
        navPanel.setBackground(new Color(0, 119, 182));
        navPanel.setLayout(new GridLayout(1, 5));

        JButton homeButton = new JButton("Home");
        homeButton.setForeground(Color.green);
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
        centerPanel.setBackground(Color.white);
        centerPanel.setLayout(null);

        // Content inside center panel
        JLabel titleLabel2 = new JLabel("Welcome to JOURNEY GUARD");
        titleLabel2.setBounds(300, 200, 500, 50);
        titleLabel2.setForeground(Color.BLACK);
        titleLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel2.setFont(new Font("Arial", Font.BOLD, 30));
        centerPanel.add(titleLabel2);

        JLabel descriptionLabel = new JLabel("Your ultimate college bus management solution");
        descriptionLabel.setBounds(250, 300, 600, 30);
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        centerPanel.add(descriptionLabel);

        add(centerPanel);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Home")) {
            // Do nothing, already on Home page
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
            Driver driverPage = new Driver();
            driverPage.setVisible(true);
            dispose();
        } else if (command.equals("Maintenance")) {

            Maintenance maintenancePage = new Maintenance();
            maintenancePage.setVisible(true);
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Home homePage = new Home();
                homePage.setVisible(true);
            }
        });
    }
}
