package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Admin extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Admin() {
        setTitle("Admin Login");
        setSize(1080, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
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

        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(300, 200, 480, 300);
        centerPanel.setBackground(new Color(240, 240, 240));
        centerPanel.setLayout(null);

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

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(180, 150, 100, 30);
        centerPanel.add(loginButton);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(300, 150, 150, 30);
        centerPanel.add(createAccountButton);

        add(centerPanel);

        loginButton.addActionListener(this);
        createAccountButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Database connection and query to check credentials
            try {
            	Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                PreparedStatement statement = conn
                        .prepareStatement("SELECT * FROM login WHERE username = ? AND password = ?");
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    // Redirect to Home page
                    Home homePage = new Home();
                    homePage.setVisible(true);
                    dispose(); // Close the current window
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Create Account")) {
            // Redirect to Admin1 page
            Admin1 admin1Page = new Admin1();
            admin1Page.setVisible(true);
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Admin adminPage = new Admin();
                adminPage.setVisible(true);
            }
        });
    }
}
