package view;


import utility.AstronomicalObjectDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class SearchResultPanel extends JPanel {
    private JTable starTable;
    private JTable messierTable;
    private JTable planetTable;

    public JTable getStarTable() {
        return starTable;
    }

    public void setStarTable(JTable starTable) {
        this.starTable = starTable;
    }

    public JTable getMessierTable() {
        return messierTable;
    }

    public void setMessierTable(JTable messierTable) {
        this.messierTable = messierTable;
    }

    public JTable getPlanetTable() {
        return planetTable;
    }

    public void setPlanetTable(JTable planetTable) {
        this.planetTable = planetTable;
    }

    public SearchResultPanel() {
        Border border = BorderFactory.createTitledBorder("Search Result");
        setBorder(border);
        setLayout(new GridLayout(3,1));
        // Create panel
        add(myPanel("Star"));
        add(myPanel("Messier"));
        add(myPanel("Planet"));
    }

    public void updateTable(Map<String,Vector<Vector<String>>> map) {
        MyTableModel model;
        String[] starColNames = {"Number", "RA", "Declination", "Magnitude", "Distance", "Type", "Constellation"};
        String[] messierColNames = {"Number", "RA", "Declination", "Magnitude", "Distance", "Constellation", "Description"};
        String[] planetColNames = {"Name", "RA", "Declination", "Magnitude", "Distance", "Albedo"};
        Vector<String> starColName = new Vector<>(Arrays.asList(starColNames));
        Vector<String> messierColName = new Vector<>(Arrays.asList(messierColNames));
        Vector<String> planetColName = new Vector<>(Arrays.asList(planetColNames));
        // Update star model
        if (map.containsKey("Star")) {
            ((DefaultTableModel)starTable.getModel()).setDataVector(map.get("Star"), starColName);
        }else {
            ((DefaultTableModel)starTable.getModel()).setDataVector(new Vector<String>(), starColName);
        }
        // Update messier model
        if (map.containsKey("Messier")) {
            ((DefaultTableModel)messierTable.getModel()).setDataVector(map.get("Messier"), messierColName);
        }else {
            ((DefaultTableModel)messierTable.getModel()).setDataVector(new Vector<String>(), messierColName);
        }
        // Update planet model
        if (map.containsKey("Planet")) {
            ((DefaultTableModel)planetTable.getModel()).setDataVector(map.get("Planet"), planetColName);
        }else {
            ((DefaultTableModel)planetTable.getModel()).setDataVector(new Vector<String>(), planetColName);
        }
    }

    private Component myPanel(String name){
        // Get all the objects
        AstronomicalObjectDAO aoDao = new AstronomicalObjectDAO();
        Map<String,Vector<Vector<String>>> map = aoDao.get(null);
        JScrollPane scroll = null;
        MyTableModel model;
        Border border;
        // Create table
        switch (name){
            case "Star":
                starTable = new JTable();
                model = new MyTableModel(name , map);
                starTable.setModel(model);
                scroll = new JScrollPane(starTable);
                break;
            case "Messier":
                messierTable = new JTable();
                model = new MyTableModel(name , map);
                messierTable.setModel(model);
                scroll = new JScrollPane(messierTable);
                break;
            case "Planet":
                planetTable = new JTable();
                model = new MyTableModel(name , map);
                planetTable.setModel(model);
                scroll = new JScrollPane(planetTable);
                break;
        }
        // Set scroll panel
        assert scroll != null;
        scroll.setPreferredSize(new Dimension(1100,100));
        border = BorderFactory.createTitledBorder(name);
        scroll.setBorder(border);
        return scroll;
    }

    static class MyTableModel extends DefaultTableModel{
        public MyTableModel(String name, Map<String,Vector<Vector<String>>> map) {
            String[] starColNames = {"Number", "RA", "Declination", "Magnitude", "Distance", "Type", "Constellation"};
            String[] messierColNames = {"Number", "RA", "Declination", "Magnitude", "Distance", "Constellation", "Description"};
            String[] planetColNames = {"Name", "RA", "Declination", "Magnitude", "Distance", "Albedo"};
            Vector<String> starColName = new Vector<>(Arrays.asList(starColNames));
            Vector<String> messierColName = new Vector<>(Arrays.asList(messierColNames));
            Vector<String> planetColName = new Vector<>(Arrays.asList(planetColNames));
            switch (name) {
                case "Star":
                    setDataVector(map.get("Star"), starColName);
                    break;
                case "Messier":
                    setDataVector(map.get("Messier"), messierColName);
                    break;
                case "Planet":
                    setDataVector(map.get("Planet"), planetColName);
                    break;
            }
        }

        // Do not allow to edit
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    }
}
