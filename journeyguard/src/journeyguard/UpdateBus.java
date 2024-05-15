package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateBus extends JFrame {
    private JTextField busNumberField;
    private JTextField pollutionDateField;
    private JTextField insuranceDateField;
    private JTextField testDateField;
    private JTextField statusField;;

    public UpdateBus(JFrame parent, String busNumber) {
        setTitle("Update Bus Details");
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2));

        JLabel busNumberLabel = new JLabel("Bus Number:");
        busNumberField = new JTextField(busNumber);
        busNumberField.setEditable(false);
        add(busNumberLabel);
        add(busNumberField);

        JLabel pollutionDateLabel = new JLabel("Pollution Date:");
        pollutionDateField = new JTextField();
        add(pollutionDateLabel);
        add(pollutionDateField);

        JLabel insuranceDateLabel = new JLabel("Insurance Date:");
        insuranceDateField = new JTextField();
        add(insuranceDateLabel);
        add(insuranceDateField);

        JLabel testDateLabel = new JLabel("Test Date:");
        testDateField = new JTextField();
        add(testDateLabel);
        add(testDateField);
        
        JLabel statusLabel = new JLabel("Status:");
        statusField = new JTextField();
        add(statusLabel);
        add(statusField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBusDetails(busNumber, pollutionDateField.getText(), insuranceDateField.getText(), testDateField.getText(), statusField.getText());
                dispose(); // Close the update window after updating
            }
        });
        add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the update window without updating
            }
        });
        add(cancelButton);

        setVisible(true);
    }

    private void updateBusDetails(String busNumber, String pollutionDate, String insuranceDate, String testDate, String status) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
            String query = "UPDATE bus_details SET pollution = ?, insurance = ?, testdate = ?,status = ? WHERE bnum = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, pollutionDate);
            statement.setString(2, insuranceDate);
            statement.setString(3, testDate);
            statement.setString(4, status);
            statement.setString(5, busNumber);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Bus details updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating bus details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
