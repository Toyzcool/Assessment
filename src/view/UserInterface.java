package view;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    public UserInterface() throws HeadlessException {
        setTitle("Astronomical Object Museum");
        setSize(1200,1000);
        // Fix: Create components
        addPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    void addPanel(){
        MyPanel myPanel = new MyPanel();
        add(myPanel);
    }
}
