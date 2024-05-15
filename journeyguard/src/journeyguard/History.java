package journeyguard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class History extends JFrame {

    private JPanel contentPane;

    public History(String admissionNumber) {
        setTitle("Payment History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblAdmissionNumber = new JLabel("Admission Number: " + admissionNumber);
        lblAdmissionNumber.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblAdmissionNumber, BorderLayout.NORTH);

        JPanel panel = new JPanel(); // Define the panel
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1, 0, 10));

        // Fetch payment history from the database and add details to the panel
        fetchAndDisplayPaymentHistory(admissionNumber, panel);
    }

    private void fetchAndDisplayPaymentHistory(String admissionNumber, JPanel panel) {
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root","");

            // Prepare SQL query
            String query = "SELECT * FROM transaction WHERE admin_no = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, admissionNumber);

            // Execute query
            ResultSet rs = pst.executeQuery();

            // Iterate through the result set and add payment details to the panel
            while (rs.next()) {
                String dateOfPayment = rs.getString("date_of_pay");
                int amount = rs.getInt("amount");
                String mode = rs.getString("mode");

                JLabel lblPayment = new JLabel("Date of Payment: " + dateOfPayment + ", Amount: " + amount + ", Mode: " + mode);
                panel.add(lblPayment);
            }

            // Close resources
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
                History history = new History("admissionNumber");
                history.setVisible(true);
    }
}
