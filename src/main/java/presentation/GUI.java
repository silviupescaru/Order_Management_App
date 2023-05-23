package presentation;

import dao.ClientDAO;
import model.Client;

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
    private JTextField numeTextFieldTextField;
    private JTextField adresaTextFieldTextField;
    private JTextField emailTextFieldTextField;
    private JButton ADDCLIENTButton;
    private JButton DELETECLIENTButton;
    private JButton EDITCLIENTButton;
    private JButton VIEWCLIENTSButton;
    private JPanel mainPanel;
    private JPanel insetPanelOrder;
    private JTable clientTable;
    private JTable productTable;
    private JTable ordersTable;
    private JFormattedTextField a2FormattedTextField;
    private JButton ADDTOORDERButton;
    private JButton DELETEORDERButton;


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
                // Get the values from the JTextFields
                String name = numeTextFieldTextField.getText();
                String address = adresaTextFieldTextField.getText();
                String email = emailTextFieldTextField.getText();

                // Create a new instance of the client object and set its properties
                Client client = new Client();
                client.setName(name);
                client.setAddress(address);
                client.setEmail(email);

                // Call the insert method to insert the client into the database
                ClientDAO clientDAO = new ClientDAO(); // Assuming ClientDAO is your DAO class for the Client entity
                clientDAO.insert(client);

                // Show success message or perform any other desired action
                //JOptionPane.showMessageDialog(null, "Client added successfully!");
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
