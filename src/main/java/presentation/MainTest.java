package presentation;

import javax.swing.*;

public class MainTest {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new View().setVisible(true);
        });
    }
}
