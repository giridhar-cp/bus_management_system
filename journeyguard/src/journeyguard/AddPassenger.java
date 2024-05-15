package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class AddPassenger extends JFrame {
    private JTextField admissionNumberField;
    private JTextField nameField;
    private JTextField semesterField;
    private JTextField branchField;
    private JTextField contactField;
    private JComboBox<String> busNoComboBox;
    private JComboBox<String> boardingPointComboBox;
    private JLabel feeLabel;
    private JButton addButton;
    private JButton cancelButton;

    public AddPassenger() {
        setTitle("Add Passenger");
        setSize(440, 550);
        setLayout(null);

        // Admission Number
        JLabel admissionNumberLabel = new JLabel("Admission Number:");
        admissionNumberLabel.setBounds(50, 50, 150, 30);
        add(admissionNumberLabel);

        admissionNumberField = new JTextField();
        admissionNumberField.setBounds(200, 50, 150, 30);
        add(admissionNumberField);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 150, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 100, 150, 30);
        add(nameField);

        // Semester
        JLabel semesterLabel = new JLabel("Semester:");
        semesterLabel.setBounds(50, 150, 150, 30);
        add(semesterLabel);

        semesterField = new JTextField();
        semesterField.setBounds(200, 150, 150, 30);
        add(semesterField);

        // Branch
        JLabel branchLabel = new JLabel("Branch:");
        branchLabel.setBounds(50, 200, 150, 30);
        add(branchLabel);

        branchField = new JTextField();
        branchField.setBounds(200, 200, 150, 30);
        add(branchField);

        // Contact
        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(50, 250, 150, 30);
        add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(200, 250, 150, 30);
        add(contactField);

        // Bus No
        JLabel busNoLabel = new JLabel("Bus No:");
        busNoLabel.setBounds(50, 300, 150, 30);
        add(busNoLabel);

        busNoComboBox = new JComboBox<>();
        busNoComboBox.setBounds(200, 300, 150, 30);
        populateBusNoComboBox();
        add(busNoComboBox);
        busNoComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addBoardingPoints();
				
			}
		});

        // Boarding Point
        JLabel boardingPointLabel = new JLabel("Boarding Point:");
        boardingPointLabel.setBounds(50, 350, 150, 30);
        add(boardingPointLabel);

        boardingPointComboBox = new JComboBox<>();
        boardingPointComboBox.setBounds(200, 350, 150, 30);
        addBoardingPoints(); // Populate boarding point dropdown based on selected bus
        add(boardingPointComboBox);
        boardingPointComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				calculateFee((String) boardingPointComboBox.getSelectedItem());
				
			}
		});

        // Fee Label
        JLabel feeLabelLabel = new JLabel("Fee:");
        feeLabelLabel.setBounds(50, 400, 150, 30);
        add(feeLabelLabel);

        feeLabel = new JLabel("0");
        feeLabel.setBounds(200, 400, 150, 30);
        add(feeLabel);

        // addButton
        addButton = new JButton("Add Passenger");
        addButton.setBounds(50, 450, 150, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPassenger();
            }
        });
        add(addButton);

        cancelButton = new JButton("cancel");
        cancelButton.setBounds(250, 450, 150, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);
    }

    private void populateBusNoComboBox() {
        // Connect to the database and populate the bus number dropdown from the bus
        // table
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root",
                    "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT bnum FROM bus_details");
            while (resultSet.next()) {
                busNoComboBox.addItem(resultSet.getString("bnum"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBoardingPoints() {
        // Connect to the database and populate the boarding point dropdown based on
        // selected 
    	int i = 0;
    	String[] boardingPoints;
        String[] routecols = {"p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9", "p10", "p11", "p12", "p13", "p14", "p15", "p16", "p17", "p18", "p19", "p20"};
        String selectedBus = (String) busNoComboBox.getSelectedItem();
        if (selectedBus != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard","root", "");
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20 FROM route WHERE bnum = ?");
                preparedStatement.setString(1, selectedBus);
                ResultSet resultSet = preparedStatement.executeQuery();
                boardingPointComboBox.removeAllItems();
                if (resultSet.next()) {
                	for(String point : routecols) {
                		if(!resultSet.getString(point).trim().equals("")) {
                			boardingPointComboBox.addItem(resultSet.getString(point).trim());
                		}
                	}
                    //String route = resultSet.getString();
                    //String[] boardingPoints = route.split(",");
//                    for (String boardingPoint : boardingPoints) {
//                        boardingPointComboBox.addItem(boardingPoint.trim());
//                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPassenger() {
        String admissionNumber = admissionNumberField.getText();
        String name = nameField.getText();
        String semester = semesterField.getText();
        String branch = branchField.getText();
        String contact = contactField.getText();
        String busNo = (String) busNoComboBox.getSelectedItem();
        String boardingPoint = (String) boardingPointComboBox.getSelectedItem();
        // Fee calculation
        int fee = calculateFee(boardingPoint);
        // Insert passenger details into the database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root","");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO passenger_details (name, admin_no, sem, branch, contact, boardingpt, fee, due,  bnum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, admissionNumber);
            preparedStatement.setString(3, semester);
            preparedStatement.setString(4, branch);
            preparedStatement.setString(5, contact);
            preparedStatement.setString(6, boardingPoint);
            preparedStatement.setInt(7, fee);
            preparedStatement.setInt(8, fee); // Initially due is set to fee
            preparedStatement.setString(9, busNo);
            preparedStatement.executeUpdate();
            connection.close();
            JOptionPane.showMessageDialog(this, "Passenger added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "passenger already exist! Please try again.");
        }
    }

    private int calculateFee(String boardingPoint) {
        int fee = 5000;
        int i = 0;
        int index = 0;
        ArrayList<String> points = new ArrayList<>();
        String[] routecols = {"p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9", "p10", "p11", "p12", "p13", "p14", "p15", "p16", "p17", "p18", "p19", "p20"};
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root","");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20 FROM route WHERE bnum = '" + busNoComboBox.getSelectedItem() + "'");
            if (resultSet.next()) {
                //String[] route = resultSet.getString("route").split(",");
            	for(i=0;i<20 && !(resultSet.getString(routecols[i]).equals(boardingPoint));i++) {
            		System.out.println(routecols[i] + " : " + resultSet.getString(routecols[i])+" : "+boardingPoint);
            	}
            	//points.add(resultSet.getString(routecols[i]));
            }
            index = i;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //int index = points.indexOf(boardingPoint);
        for (i = 1; i <= index; i++) {
            fee += 500;
        }
        feeLabel.setText(String.valueOf(fee));
        return fee;
    }

    public static void main(String[] args) {
        // Run the AddPassenger frame
        AddPassenger addPassenger = new AddPassenger();
        addPassenger.setVisible(true);
    }
}
