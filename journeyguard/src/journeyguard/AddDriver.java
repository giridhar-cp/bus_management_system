package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddDriver extends JFrame {

    private JLabel titleLabel, nameLabel, licenseLabel, contactLabel, expLabel, busLabel;
    private JTextField nameField, licenseField, contactField, expField, busField;
    private JButton addButton;
    public AddDriver() {
        setTitle("Add Driver");
        setSize(500, 400);
        setLayout(null);

       
        titleLabel = new JLabel("Add Driver");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(200, 20, 500, 30);
        add(titleLabel);

        licenseLabel = new JLabel("License Number:");
        licenseLabel.setBounds(50, 110, 100, 30);
        add(licenseLabel);
        licenseField = new JTextField();
        licenseField.setBounds(150, 110, 200, 30);
        add(licenseField);
        

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 70, 80, 30);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(150, 70, 200, 30);
        add(nameField);

        contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(50, 150, 80, 30);
        add(contactLabel);
        contactField = new JTextField();
        contactField.setBounds(150, 150, 200, 30);
        add(contactField);

        
        expLabel = new JLabel("Lic_Expiry:");
        expLabel.setBounds(50, 190, 80, 30);
        add(expLabel);
        expField = new JTextField();
        expField.setBounds(150, 190, 200, 30);
        add(expField);

        
        busLabel = new JLabel("Bus Number:");
        busLabel.setBounds(50, 230, 80, 30);
        add(busLabel);
        busField = new JTextField();
        busField.setBounds(150, 230, 200, 30);
        add(busField);

   
        addButton = new JButton("Add");
        addButton.setBounds(200, 270, 100, 30);
        add(addButton);

     
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String licenseNumber = licenseField.getText();
                String name = nameField.getText();
                String contact = contactField.getText();
                String expiry1 = contactField.getText();
                String busNumber = busField.getText();

                if (name.isEmpty() || licenseNumber.isEmpty() || contact.isEmpty() || expiry1.isEmpty()
                        || busNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                } else {
                	LocalDate expiry = LocalDate.parse(expField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    // Add driver to database
                    addDriverToDatabase( licenseNumber, name, contact, busNumber, expiry);

                    // Navigate back to Driver.java
                    dispose();
                    Driver driver = new Driver();
                    driver.setVisible(true);
                }
            }
        });
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(200, 320, 100, 30);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the addDriver.java page
            }
        });
        add(closeButton);
    }

    private void addDriverToDatabase(String licenseNumber, String name, String contact, String busNumberStr, LocalDate expiry) {
        try {
            int busNumber = Integer.parseInt(busNumberStr);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
            String query = "INSERT INTO driver_details (lic_id, dname, phone, bnum, lic_exp) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, licenseNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, contact);
            preparedStatement.setInt(4, busNumber);
            preparedStatement.setDate(5, java.sql.Date.valueOf(expiry));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("Driver added successfully to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid bus number format");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AddDriver addDriver = new AddDriver();
                addDriver.setVisible(true);
            }
        });
    }
}
