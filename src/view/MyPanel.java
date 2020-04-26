package view;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public MyPanel() {
        setLayout(new GridLayout(2,1));
        SearchResultPanel searchResultPanel = new SearchResultPanel();
        add(searchResultPanel);
        add(new SearchQueryPanel(searchResultPanel));
    }
}
