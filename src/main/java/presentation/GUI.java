package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static java.lang.Integer.parseInt;

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
    private JTable productsTable;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton ADDPRODUCTButton;
    private JButton DELETEPRODUCTButton;
    private JButton EDITPRODUCTButton;
    private JButton VIEWPRODUCTSButton;
    private JButton VIEWORDERSButton;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModel1;
    private DefaultTableModel tableModel2;
    private DefaultTableModel model3;


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

                    tableModel = new ClientBLL().initClientsTable();

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

        DocumentListener documentListener = new DocumentListener() {

            void notCleared()
            {
                ADDCLIENTButton.setEnabled(!(numeTextFieldTextField.getText().equals("") || adresaTextFieldTextField.getText().equals("") || emailTextFieldTextField.getText().equals("")));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                notCleared();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                notCleared();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                notCleared();
            }
        };
        numeTextFieldTextField.getDocument().addDocumentListener(documentListener);
        adresaTextFieldTextField.getDocument().addDocumentListener(documentListener);
        emailTextFieldTextField.getDocument().addDocumentListener(documentListener);

        ADDCLIENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the JTextFields
                String name = numeTextFieldTextField.getText();
                String address = adresaTextFieldTextField.getText();
                String email = emailTextFieldTextField.getText();

                // Create a new instance of the client object and set its properties
                ClientDAO clientDAO = new ClientDAO(); // Assuming ClientDAO is your DAO class for the Client entity
                Client client = new Client();
                client.setId(parseInt(clientDAO.idCalc(client)));
                client.setName(name);
                client.setAddress(address);
                client.setEmail(email);

                // Call the insert method to insert the client into the database
                clientDAO.insert(client);

                // Show success message or perform any other desired action
                //JOptionPane.showMessageDialog(null, "Client added successfully!");
            }
        });

        table1.getSelectionModel().addListSelectionListener(e -> {
            int[] rows = table1.getSelectedRows();
            if(rows.length == 1)
            {
                EDITCLIENTButton.setEnabled(true);
                numeTextFieldTextField.setEditable(true);
                adresaTextFieldTextField.setEditable(true);
                emailTextFieldTextField.setEditable(true);
                numeTextFieldTextField.setText(table1.getValueAt(rows[0], 1).toString());
                adresaTextFieldTextField.setText(table1.getValueAt(rows[0], 2).toString());
                emailTextFieldTextField.setText(table1.getValueAt(rows[0], 3).toString());
            }
            else
            {
                EDITCLIENTButton.setEnabled(false);
                numeTextFieldTextField.setEditable(false);
                adresaTextFieldTextField.setEditable(false);
                emailTextFieldTextField.setEditable(false);
                numeTextFieldTextField.setText("");
                adresaTextFieldTextField.setText("");
                emailTextFieldTextField.setText("");
            }
            DELETECLIENTButton.setEnabled(rows.length >= 1);
        });

        DELETECLIENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] rows = table1.getSelectedRows();
                int id;
                ClientBLL clientBLL = new ClientBLL();
                for(int i = 0; i < rows.length; i++)
                {
                    id = Integer.parseInt(tableModel.getValueAt(rows[i] - i, 0).toString());
                    clientBLL.deleteClient(new Client(id));
                    tableModel.removeRow(rows[i] - i);
                }
            }
        });
        EDITCLIENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                String name = numeTextFieldTextField.getText();
                String address = adresaTextFieldTextField.getText();
                String email = emailTextFieldTextField.getText();
                ClientBLL clientBLL = new ClientBLL();
                clientBLL.modifyClient(new Client(id, name, address, email));
                tableModel.setValueAt(name, row, 1);
                tableModel.setValueAt(address, row, 2);
                tableModel.setValueAt(email, row, 3);
                numeTextFieldTextField.setText("");
                adresaTextFieldTextField.setText("");
                emailTextFieldTextField.setText("");
            }
        });


        VIEWPRODUCTSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Connect to the database
                    String url = "jdbc:postgresql://localhost:5432/postgres";
                    String username = "postgres";
                    String password = "root";
                    Connection connection = DriverManager.getConnection(url, username, password);

                    tableModel = new ProductBLL().initProductsTable();

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute the query
                    String query = "SELECT * FROM product";
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
                    productsTable.setModel(model);

                    // Close the connection
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        ADDPRODUCTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the JTextFields
                Double price = Double.parseDouble(textField1.getText());
                String name = textField2.getText();
                int quantity = Integer.parseInt(textField3.getText());

                // Create a new instance of the client object and set its properties
                ProductDAO productDAO = new ProductDAO(); // Assuming ClientDAO is your DAO class for the Client entity
                Product product = new Product();
                product.setId(parseInt(productDAO.idCalc(product)));
                product.setPrice(price);
                product.setName(name);
                product.setQuantity(quantity);

                // Call the insert method to insert the client into the database
                productDAO.insert(product);

                // Show success message or perform any other desired action
                //JOptionPane.showMessageDialog(null, "Client added successfully!");
            }
        });

        DELETEPRODUCTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] rows = productsTable.getSelectedRows();
                int id;
                ProductBLL productBLL = new ProductBLL();
                for(int i = 0; i < rows.length; i++)
                {
                    id = Integer.parseInt(tableModel.getValueAt(rows[i] - i, 0).toString());
                    productBLL.deleteProduct(new Product(id));
                    tableModel.removeRow(rows[i] - i);
                }
            }
        });
        EDITPRODUCTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = productsTable.getSelectedRow();
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                Double price = Double.parseDouble(textField1.getText());
                String name = textField2.getText();
                int quantity = Integer.parseInt(textField3.getText());
                ProductBLL productBLL = new ProductBLL();
                productBLL.modifyTable(new Product(id, price, name, quantity));
                tableModel.setValueAt(price, row, 1);
                tableModel.setValueAt(name, row, 2);
                tableModel.setValueAt(quantity, row, 3);
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
            }
        });

        VIEWORDERSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Connect to the database
                    String url = "jdbc:postgresql://localhost:5432/postgres";
                    String username = "postgres";
                    String password = "root";
                    Connection connection = DriverManager.getConnection(url, username, password);

                    // Fetch data for orders table
                    Statement statement1 = connection.createStatement();
                    String query1 = "SELECT * FROM orders";
                    ResultSet resultSet1 = statement1.executeQuery(query1);
                    DefaultTableModel model1 = new DefaultTableModel();
                    ResultSetMetaData metaData1 = resultSet1.getMetaData();
                    int columnCount1 = metaData1.getColumnCount();
                    for (int i = 1; i <= columnCount1; i++) {
                        model1.addColumn(metaData1.getColumnName(i));
                    }
                    while (resultSet1.next()) {
                        Object[] rowData = new Object[columnCount1];
                        for (int i = 1; i <= columnCount1; i++) {
                            rowData[i - 1] = resultSet1.getObject(i);
                        }
                        model1.addRow(rowData);
                    }
                    ordersTable.setModel(model1);

                    // Fetch data for clients table
                    Statement statement2 = connection.createStatement();
                    String query2 = "SELECT * FROM client";
                    ResultSet resultSet2 = statement2.executeQuery(query2);
                    DefaultTableModel model2 = new DefaultTableModel();
                    ResultSetMetaData metaData2 = resultSet2.getMetaData();
                    int columnCount2 = metaData2.getColumnCount();
                    for (int i = 1; i <= columnCount2; i++) {
                        model2.addColumn(metaData2.getColumnName(i));
                    }
                    while (resultSet2.next()) {
                        Object[] rowData = new Object[columnCount2];
                        for (int i = 1; i <= columnCount2; i++) {
                            rowData[i - 1] = resultSet2.getObject(i);
                        }
                        model2.addRow(rowData);
                    }
                    clientTable.setModel(model2);

                    // Fetch data for products table
                    Statement statement3 = connection.createStatement();
                    String query3 = "SELECT * FROM product";
                    ResultSet resultSet3 = statement3.executeQuery(query3);
                    model3 = new DefaultTableModel();
                    ResultSetMetaData metaData3 = resultSet3.getMetaData();
                    int columnCount3 = metaData3.getColumnCount();
                    for (int i = 1; i <= columnCount3; i++) {
                        model3.addColumn(metaData3.getColumnName(i));
                    }
                    while (resultSet3.next()) {
                        Object[] rowData = new Object[columnCount3];
                        for (int i = 1; i <= columnCount3; i++) {
                            rowData[i - 1] = resultSet3.getObject(i);
                        }
                        model3.addRow(rowData);
                    }
                    productTable.setModel(model3);

                    // Close the connection
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        ADDTOORDERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int clientRow = clientTable.getSelectedRow();
                int productRow = productTable.getSelectedRow();

                int clientID = Integer.parseInt(clientTable.getValueAt(clientRow, 0).toString());
                int productID = Integer.parseInt(productTable.getValueAt(productRow, 0).toString());
                int quantity = Integer.parseInt(a2FormattedTextField.getText());

                ProductBLL productBLL = new ProductBLL();
                Product product = productBLL.findProductById(productID);
                product.setQuantity(product.getQuantity() - quantity);

                productBLL.validateProduct(product);

                productBLL.modifyTable(product);
                OrderBLL orderBLL = new OrderBLL();
                Orders order = new Orders(quantity, clientID, productID);
                int idValue = orderBLL.insertOrder(order);
                idValue++;
                order.setId(idValue);
                productTable.setValueAt(product.getQuantity(), productTable.getSelectedRow(), 2);
                model3.addRow(new Object[]{idValue, quantity, clientID, productID});
            }
        });
        DELETEORDERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = ordersTable.getSelectedRows();
                int id;
                OrderBLL orderBLL = new OrderBLL();
                for(int i = 0; i < rows.length; i++){
                    id = Integer.parseInt(model3.getValueAt(rows[i] - i, 0).toString());
                    id--;
                    orderBLL.deleteOrder(new Orders(id));
                    model3.removeRow(rows[i] - i);
                }
            }
        });
    }

    public static void main(String[] args){
        GUI gui = new GUI();
    }
}
