package journeyguard;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Admin1 extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public Admin1() {
        setTitle("Admin Sign Up");
        setSize(1080, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        // Center panel for sign up form
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(300, 200, 480, 350);
        centerPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        centerPanel.setLayout(null); // Using absolute positioning

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        centerPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 50, 200, 30);
        centerPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        centerPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 100, 200, 30);
        centerPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(10, 150, 140, 30);
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        centerPanel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(180, 150, 200, 30);
        centerPanel.add(confirmPasswordField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(180, 200, 100, 30);
        centerPanel.add(signUpButton);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(300, 200, 100, 30);
        centerPanel.add(loginButton);

        add(centerPanel);

        signUpButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sign Up")) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Database connection and query to insert new user
            try {
                
            	Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                PreparedStatement statement = conn.prepareStatement("INSERT INTO login (username, password) VALUES (?, ?)");
                statement.setString(1, username);
                statement.setString(2, password);
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Account created successfully", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Redirect to Admin page
                    Admin adminPage = new Admin();
                    adminPage.setVisible(true);
                    dispose(); // Close the current window
                }

                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Login")) {
            // Redirect to Admin page
            Admin adminPage = new Admin();
            adminPage.setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Admin1 admin1Page = new Admin1();
                admin1Page.setVisible(true);
            }
        });
    }
}