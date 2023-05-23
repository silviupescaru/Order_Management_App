package presentation;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class View extends JFrame{
    private JTabbedPane tabbedPane1;
    //private JPanel TabelPanel;
    private JPanel InsertPanel;
    private JPanel ModifyPanel;
    private JTable table1;
    private JTextField IDTextFieldTextField;
    private JTextField numeTextFieldTextField;
    private JTextField adresaTextFieldTextField;
    private JTextField emailTextFieldTextField;


    public View() {
        setTitle("Queue Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 700);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JPanel TabelPanel = new JPanel();

        TabelPanel.setBackground(new Color(66, 92, 90));
        TabelPanel.setLayout(new GridLayout(2, 2, 5, 5));

        JLabel idTextLabel = new JLabel("ID");
        JTextField idTextField = new JTextField();
        TabelPanel.add(idTextLabel);
        TabelPanel.add(idTextField);

        /*
        TabelPanel.add(IDTextFieldTextField);
        TabelPanel.add(numeTextFieldTextField);
        TabelPanel.add(adresaTextFieldTextField);
        TabelPanel.add(emailTextFieldTextField);
        */


        contentPanel.add(TabelPanel, BorderLayout.WEST);

        setContentPane(contentPanel);
        setVisible(true);
    }

}
