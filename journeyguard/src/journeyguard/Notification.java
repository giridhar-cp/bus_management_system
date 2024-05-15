package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification extends JFrame {
    private JPanel contentPane;
    private JButton closeButton;

    public Notification() {
        setTitle("Notification");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Driver Licence Expiry Notification");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JTextArea notificationArea = new JTextArea();
        notificationArea.setEditable(false);
        contentPane.add(notificationArea, BorderLayout.CENTER);

        // Retrieve driver name from database based on expiry condition
        try {
        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard","root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT dname FROM driver_details WHERE lic_exp <= CURRENT_DATE");

            while (resultSet.next()) {
                String driverName = resultSet.getString("dname");
                String message = "Driver: " + driverName + " - Licence expiry notification";
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String dateTime = dtf.format(now);
                notificationArea.append(dateTime + " : " + message + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(closeButton, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Notification();
            }
        });
    }
}
