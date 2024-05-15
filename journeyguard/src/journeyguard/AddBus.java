package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddBus extends JFrame {

    private JTextField busNumberField;
    private JTextField registerNumberField;
    private JTextField dateOfRegistrationField;
    private JTextField testDateField;
    private JTextField pollutionField;
    private JTextField insuranceField;
    private JTextField modelField;
    private JTextField seatingCapacityField;
    private JTextField p1Field;
    private JTextField p2Field;
    private JTextField p3Field;
    private JTextField p4Field;
    private JTextField p5Field;
    private JTextField p6Field;
    private JTextField p7Field;
    private JTextField p8Field;
    private JTextField p9Field;
    private JTextField p10Field;
    private JTextField p11Field;
    private JTextField p12Field;
    private JTextField p13Field;
    private JTextField p14Field;
    private JTextField p15Field;
    private JTextField p16Field;
    private JTextField p17Field;
    private JTextField p18Field;
    private JTextField p19Field;
    private JTextField p20Field;
    private JButton addButton;

    public AddBus() {
        setTitle("Add Bus");
        setSize(600, 768);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 180, 216));
        JLabel titleLabel = new JLabel("Add Bus");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 2));

        JLabel busNumberLabel = new JLabel("Bus Number:");
        busNumberField = new JTextField();

        JLabel registerNumberLabel = new JLabel("Reg Number:");
        registerNumberField = new JTextField();

        JLabel dateOfRegistrationLabel = new JLabel("Date of Reg:");
        dateOfRegistrationField = new JTextField();

        JLabel testDateLabel = new JLabel("Test Date:");
        testDateField = new JTextField();

        JLabel pollutionLabel = new JLabel("Pollution:");
        pollutionField = new JTextField();

        JLabel insuranceLabel = new JLabel("Insurance:");
        insuranceField = new JTextField();

        JLabel modelLabel = new JLabel("Model:");
        modelField = new JTextField();

        JLabel seatingCapacityLabel = new JLabel("capacity:");
        seatingCapacityField = new JTextField();
        
        JLabel p1Label = new JLabel("point 1:");
        p1Field = new JTextField();
        
        JLabel p2Label = new JLabel("point 2:");
        p2Field = new JTextField();

        JLabel p3Label = new JLabel("point 3:");
        p3Field = new JTextField();

        JLabel p4Label = new JLabel("point 4:");
        p4Field = new JTextField();

        JLabel p5Label = new JLabel("point 5:");
        p5Field = new JTextField();

        JLabel p6Label = new JLabel("point 6:");
        p6Field = new JTextField();
        
        JLabel p7Label = new JLabel("point 7:");
        p7Field = new JTextField();

        JLabel p8Label = new JLabel("point 8:");
        p8Field = new JTextField();

        JLabel p9Label = new JLabel("point 9:");
        p9Field = new JTextField();

        JLabel p10Label = new JLabel("point 10:");
        p10Field = new JTextField();

        JLabel p11Label = new JLabel("point 11:");
        p11Field = new JTextField();
        
        JLabel p12Label = new JLabel("point 12:");
        p12Field = new JTextField();

        JLabel p13Label = new JLabel("point 13:");
        p13Field = new JTextField();

        JLabel p14Label = new JLabel("point 14:");
        p14Field = new JTextField();

        JLabel p15Label = new JLabel("point 15:");
        p15Field = new JTextField();

        JLabel p16Label = new JLabel("point 16:");
        p16Field = new JTextField();
        
        JLabel p17Label = new JLabel("point 17:");
        p17Field = new JTextField();

        JLabel p18Label = new JLabel("point 18:");
        p18Field = new JTextField();

        JLabel p19Label = new JLabel("point 19:");
        p19Field = new JTextField();

        JLabel p20Label = new JLabel("point 20:");
        p20Field = new JTextField();


        centerPanel.add(busNumberLabel);
        centerPanel.add(busNumberField);
        centerPanel.add(registerNumberLabel);
        centerPanel.add(registerNumberField);
        centerPanel.add(dateOfRegistrationLabel);
        centerPanel.add(dateOfRegistrationField);
        centerPanel.add(testDateLabel);
        centerPanel.add(testDateField);
        centerPanel.add(pollutionLabel);
        centerPanel.add(pollutionField);
        centerPanel.add(insuranceLabel);
        centerPanel.add(insuranceField);
        centerPanel.add(modelLabel);
        centerPanel.add(modelField);
        centerPanel.add(seatingCapacityLabel);
        centerPanel.add(seatingCapacityField);
        centerPanel.add(p1Label);
        centerPanel.add(p1Field);
        centerPanel.add(p2Label);
        centerPanel.add(p2Field);
        centerPanel.add(p3Label);
        centerPanel.add(p3Field);
        centerPanel.add(p4Label);
        centerPanel.add(p4Field);
        centerPanel.add(p5Label);
        centerPanel.add(p5Field);
        centerPanel.add(p6Label);
        centerPanel.add(p6Field);
        centerPanel.add(p7Label);
        centerPanel.add(p7Field);
        centerPanel.add(p8Label);
        centerPanel.add(p8Field);
        centerPanel.add(p9Label);
        centerPanel.add(p9Field);
        centerPanel.add(p10Label);
        centerPanel.add(p10Field);
        centerPanel.add(p11Label);
        centerPanel.add(p11Field);
        centerPanel.add(p12Label);
        centerPanel.add(p12Field);
        centerPanel.add(p13Label);
        centerPanel.add(p13Field);
        centerPanel.add(p14Label);
        centerPanel.add(p14Field);
        centerPanel.add(p15Label);
        centerPanel.add(p15Field);
        centerPanel.add(p16Label);
        centerPanel.add(p16Field);
        centerPanel.add(p17Label);
        centerPanel.add(p17Field);
        centerPanel.add(p18Label);
        centerPanel.add(p18Field);
        centerPanel.add(p19Label);
        centerPanel.add(p19Field);
        centerPanel.add(p20Label);
        centerPanel.add(p20Field);
        add(centerPanel, BorderLayout.CENTER);

        addButton = new JButton("Add Bus");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    addBusToDatabase();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddBus.this, "Please fill all fields.");
                }
            }
        });
        add(addButton, BorderLayout.SOUTH);
    }

    // Method to validate fields
    private boolean validateFields() {
        if (busNumberField.getText().isEmpty() ||
                registerNumberField.getText().isEmpty() ||
                dateOfRegistrationField.getText().isEmpty() ||
                testDateField.getText().isEmpty() ||
                pollutionField.getText().isEmpty() ||
                insuranceField.getText().isEmpty() ||
                modelField.getText().isEmpty() ||
                seatingCapacityField.getText().isEmpty()||
                p1Field.getText().isEmpty()||
                p2Field.getText().isEmpty()||
                p3Field.getText().isEmpty()||
                p4Field.getText().isEmpty()||
                p5Field.getText().isEmpty()||
                p6Field.getText().isEmpty()||
                p7Field.getText().isEmpty()||
                p8Field.getText().isEmpty()||
                p9Field.getText().isEmpty()||
                p10Field.getText().isEmpty()||
                p11Field.getText().isEmpty()||
                p12Field.getText().isEmpty()||
                p13Field.getText().isEmpty()||
                p14Field.getText().isEmpty()||
                p15Field.getText().isEmpty()||
                p16Field.getText().isEmpty()||
                p17Field.getText().isEmpty()||
                p18Field.getText().isEmpty()||
                p19Field.getText().isEmpty()||
                p20Field.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void addBusToDatabase() {
        String busNumber = busNumberField.getText();
        String registerNumber = registerNumberField.getText();
        LocalDate dateofRegistration = LocalDate.parse(dateOfRegistrationField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate testDate = LocalDate.parse(testDateField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate pollution = LocalDate.parse(pollutionField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate insurance = LocalDate.parse(insuranceField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String model = modelField.getText();
        String seatingCapacity = seatingCapacityField.getText();
        String p1=p1Field.getText();
        String p2=p2Field.getText();
        String p3=p3Field.getText();
        String p4=p4Field.getText();
        String p5=p5Field.getText();
        String p6=p6Field.getText();
        String p7=p7Field.getText();
        String p8=p8Field.getText();
        String p9=p9Field.getText();
        String p10=p10Field.getText();
        String p11=p11Field.getText();
        String p12=p12Field.getText();
        String p13=p13Field.getText();
        String p14=p14Field.getText();
        String p15=p15Field.getText();
        String p16=p16Field.getText();
        String p17=p17Field.getText();
        String p18=p18Field.getText();
        String p19=p19Field.getText();
        String p20=p20Field.getText();
        
        
        try {
            // Connect to database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root", "");
            // Insert data into bus table
            String sql = "INSERT INTO bus_details (bnum, regno, datereg, testdate, pollution, insurance, model, seating,status) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?,'available')";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, busNumber);
            preparedStatement.setString(2, registerNumber);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateofRegistration));
            preparedStatement.setDate(4, java.sql.Date.valueOf(testDate));
            preparedStatement.setDate(5, java.sql.Date.valueOf(pollution));
            preparedStatement.setDate(6, java.sql.Date.valueOf(insurance));
            preparedStatement.setString(7, model);
            preparedStatement.setString(8, seatingCapacity);
            preparedStatement.executeUpdate();

       
           
            // Insert route points into route table
            PreparedStatement addRouteStatement = conn.prepareStatement("INSERT INTO route (bnum, " +
                    "p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, " +
                    "p11, p12, p13, p14, p15, p16, p17, p18, p19, p20) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            addRouteStatement.setString(1, busNumber);
            addRouteStatement.setString(2, p1);
            addRouteStatement.setString(3, p2);
            addRouteStatement.setString(4, p3);
            addRouteStatement.setString(5, p4);
            addRouteStatement.setString(6, p5);
            addRouteStatement.setString(7, p6);
            addRouteStatement.setString(8, p7);
            addRouteStatement.setString(9, p8);
            addRouteStatement.setString(10, p9);
            addRouteStatement.setString(11, p10);
            addRouteStatement.setString(12, p11);
            addRouteStatement.setString(13, p12);
            addRouteStatement.setString(14, p13);
            addRouteStatement.setString(15, p14);
            addRouteStatement.setString(16, p15);
            addRouteStatement.setString(17, p16);
            addRouteStatement.setString(18, p17);
            addRouteStatement.setString(19, p18);
            addRouteStatement.setString(20, p19);
            addRouteStatement.setString(21, p20);
            addRouteStatement.executeUpdate();

            conn.close();
            JOptionPane.showMessageDialog(this, "Bus added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding bus", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AddBus addBusPage = new AddBus();
                addBusPage.setVisible(true);
            }
        });
    }
}
