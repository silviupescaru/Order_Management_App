package presentation;

import connection.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUI extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel TabelPanel;
    private JPanel InsertPanel;
    private JPanel ModifyPanel;
    private JTable table1;
    private JTextField IDTextFieldTextField;
    private JTextField numeTextFieldTextField;
    private JTextField adresaTextFieldTextField;
    private JTextField emailTextFieldTextField;
    private JButton ADDCLIENTButton;
    private JButton DELETECLIENTButton;
    private JButton EDITCLIENTButton;
    private JButton VIEWCLIENTSButton;
    private JPanel mainPanel;


    public GUI() {
        setContentPane(mainPanel);
        setTitle("Queue Simulation");
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        VIEWCLIENTSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Connect to the database
                    String url = "jdbc:postgresql://localhost:5432/postgres";
                    String username = "postgres";
                    String password = "root";
                    Connection connection = DriverManager.getConnection(url, username, password);

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute the query
                    String query = "SELECT * FROM Client";
                    ResultSet resultSet = statement.executeQuery(query);

                    // Create a table model to hold the data
                    DefaultTableModel model = new DefaultTableModel();

                    // Get the metadata of the result set
                    ResultSetMetaData metaData = resultSet.getMetaData();

                    // Get the number of columns
                    int columnCount = metaData.getColumnCount();

                    // Add column names to the model
                    for (int i = 1; i <= columnCount; i++) {
                        model.addColumn(metaData.getColumnName(i));
                    }

                    // Add rows to the model
                    while (resultSet.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = resultSet.getObject(i);
                        }
                        model.addRow(rowData);
                    }

                    // Set the model to the table
                    table1.setModel(model);

                    // Close the connection
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ADDCLIENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args){
        GUI gui = new GUI();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
         }
}
