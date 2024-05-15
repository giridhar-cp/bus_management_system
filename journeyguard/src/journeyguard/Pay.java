package journeyguard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class Pay extends JDialog {
    private JLabel admissionNumberLabel;
    private JLabel dueAmountLabel;
    private JTextField amountField;
    private JTextField transField;
    private JComboBox<String> transactionModeComboBox;
    private JButton payButton;

    private String admissionNumber;
    private int dueAmount;

    public Pay(JFrame parent, String admissionNumber) {
        super(parent, "Pay", true);
        setSize(400, 450);
        setLayout(null);

        this.admissionNumber = admissionNumber;

        // Fetch due amount from the database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root","");
            String query = "SELECT due FROM passenger_details WHERE admin_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, admissionNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                dueAmount = resultSet.getInt("due");
            } else {
                // Handle if admission number not found
                JOptionPane.showMessageDialog(this, "Admission number not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database error
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Admission Number
        admissionNumberLabel = new JLabel("Admission Number: " + admissionNumber);
        admissionNumberLabel.setBounds(50, 50, 200, 30);
        add(admissionNumberLabel);

        // Due Amount
        dueAmountLabel = new JLabel("Due Amount: " + dueAmount);
        dueAmountLabel.setBounds(50, 100, 200, 30);
        add(dueAmountLabel);

        // Amount Field
        JLabel amountLabel = new JLabel("Amount to Pay:");
        amountLabel.setBounds(50, 150, 100, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(160, 150, 150, 30);
        add(amountField);

        // Transaction Mode
        JLabel transactionModeLabel = new JLabel("Transaction Mode:");
        transactionModeLabel.setBounds(50, 200, 150, 30);
        add(transactionModeLabel);

        String[] transactionModes = { "UPI", "Card", "Cash" };
        transactionModeComboBox = new JComboBox<>(transactionModes);
        transactionModeComboBox.setBounds(160, 200, 150, 30);
        add(transactionModeComboBox);
        
        JLabel transLabel = new JLabel("trancaction id:");
        transLabel.setBounds(50, 250, 100, 30);
        add(transLabel);
        
        transField = new JTextField();
        transField.setBounds(160, 250, 150, 30);
        add(transField);

        // Pay Button
        payButton = new JButton("Pay");
        payButton.setBounds(150, 300, 100, 30);
        transField.setEditable(false);
        add(payButton);
        
        transactionModeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected mode of transaction
                String selectedMode = (String) transactionModeComboBox.getSelectedItem();
                if (selectedMode.equals("Cash")) {
                    transField.setText("0"); // Set modeTextField to "0" if Cash is selected
                } else {
                    transField.setEditable(true); // Clear modeTextField for other modes
                }
            }
        });

        // Pay Button Action Listener
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate amount field
                String amountText = amountField.getText().trim();
                if (amountText.isEmpty()) {
                    JOptionPane.showMessageDialog(Pay.this, "Please enter the amount to pay.", "Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse amount
                int amount;
                try {
                    amount = Integer.parseInt(amountText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Pay.this, "Please enter a valid amount.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate if amount is less than or equal to due amount
                if (amount > dueAmount) {
                    JOptionPane.showMessageDialog(Pay.this, "Amount cannot be greater than due amount.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String transactionMode = (String) transactionModeComboBox.getSelectedItem();
                String trans = transField.getText();
                
             // Insert transaction details into the database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
                    String insertQuery = "INSERT INTO transaction (admin_no, date_of_pay, mode,amount, trans_id) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, admissionNumber);
                    preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
                    preparedStatement.setString(3, transactionMode);
                    preparedStatement.setInt(4, amount);
                    preparedStatement.setString(5, trans);
                    preparedStatement.executeUpdate();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Pay.this, "Error inserting transaction details.", "Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update due amount in database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard","root", "");
                    String updateQuery = "UPDATE passenger_details SET due = due - ? WHERE admin_no = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, amount);
                    updateStatement.setString(2, admissionNumber);
                    updateStatement.executeUpdate();

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Pay.this, "Error updating due amount.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(Pay.this, "Payment successful.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Open Pay dialog
        Pay pay = new Pay(frame, "123456");
        pay.setVisible(true);
    }
}
