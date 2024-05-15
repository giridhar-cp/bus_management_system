package journeyguard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Expenditure extends JFrame {
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public Expenditure() {
        setTitle("Expenditure");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 180, 216));
        JLabel titleLabel = new JLabel("Bus Expenditure");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel expenditurePanel = new JPanel();
        expenditurePanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(expenditurePanel);
        add(scrollPane, BorderLayout.CENTER);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/journeyguard", "root","");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT bnum, SUM(amount) AS total_expenditure FROM maintenance GROUP BY bnum");

            while (resultSet.next()) {
                String busNumber = resultSet.getString("bnum");
                int totalExpenditure = resultSet.getInt("total_expenditure");

                JPanel busPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel busLabel = new JLabel("Bus Number: " + busNumber + ", Total Expenditure: " + totalExpenditure);
                busPanel.add(busLabel);
                expenditurePanel.add(busPanel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Expenditure expenditurePage = new Expenditure();
                expenditurePage.setVisible(true);
            }
        });
    }
}
