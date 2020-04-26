package view;

import domain.AstronomicalObject;
import utility.DAO;
import utility.AstronomicalObjectDAO;
import vo.Condition;
import vo.Query;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class SearchQueryPanel extends JPanel {
    private SearchResultPanel srp;

    public SearchQueryPanel(SearchResultPanel srp) {
        Border border = BorderFactory.createTitledBorder("Search Query");
        setBorder(border);
        this.srp = srp;
        add(createSearchQueryPanel());
    }
    private Component createSearchQueryPanel(){
        JPanel searchQueryPanel = new JPanel();
        searchQueryPanel.setLayout(new GridLayout(2,1));
        /*
        Create query panel
         */
        // Create query table
        JTable queryTable = new JTable();
        // Create table header
        Vector<String> header = new Vector<>();
        header.add("Type");
        header.add("Property");
        header.add("Operator");
        header.add("Value");
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(header);
        queryTable.setModel(model);

        // Set scroll
        JScrollPane queryPanel = new JScrollPane(queryTable);
        Border border = BorderFactory.createTitledBorder("All Queries");
        queryPanel.setBorder(border);
        queryPanel.setPreferredSize(new Dimension(1190,200));

        /*
        Create search panel
         */
        // Components design
        JPanel searchPanel = new JPanel();
        Border border2 = BorderFactory.createTitledBorder("Create Queries");
        searchPanel.setBorder(border2);
        // searchPanel.setPreferredSize(new Dimension(1190,50));
        searchPanel.setLayout(new GridLayout(3,1));
        // Create tip
        JLabel warn = new JLabel();
        // Create select module
        JPanel selectPane = new JPanel();
        selectPane.setLayout(new GridLayout(2,3));
        // Create properties and operators
        String[] starProperties = new String[]{"number", "ra", "decl", "magn", "distance", "type", "constellation"};
        String[] messierProperties = new String[]{"number", "ra", "decl", "magn", "distance", "constellation", "description"};
        String[] planetProperties = new String[]{"name", "ra", "decl", "magn", "distance", "albedo"};
        String[] numericalOperators = new String[]{">", ">=", "=", "<", "<=", "!="};
        String[] nonNumericOperators = new String[]{"=", "!="};
        // Create radio buttons
        ButtonGroup group = new ButtonGroup();
        JRadioButton star = new JRadioButton("Star",false);
        JRadioButton messier = new JRadioButton("Messier",false);
        JRadioButton planet = new JRadioButton("Planet",false);
        group.add(star);
        group.add(messier);
        group.add(planet);
        selectPane.add(star);
        selectPane.add(messier);
        selectPane.add(planet);
        // Create properties combo box
        JComboBox<String> properties = new JComboBox<>();
        // Create operators combo box
        JComboBox<String> operators = new JComboBox<String>();
        // Create value textfield
        JTextField value = new JTextField();
        selectPane.add(properties);
        selectPane.add(operators);
        selectPane.add(value);
        // Create submit module
        JPanel submitPane = new JPanel();
        submitPane.setLayout(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");
        submitPane.add(addButton);
        submitPane.add(deleteButton);
        submitPane.add(searchButton);
        // Add all components into panel
        searchPanel.add(warn);
        searchPanel.add(selectPane);
        searchPanel.add(submitPane);
        searchQueryPanel.add(queryPanel);
        searchQueryPanel.add(searchPanel);

        /*
        Event design
         */
        // Add actionListener to JRadioButton for updating properties according to type
        star.addActionListener(e -> {
            if (star.isSelected()){
                properties.removeAllItems();
                operators.removeAllItems();
                for (String starProperty : starProperties) {
                    properties.addItem(starProperty);
                }
                properties.setSelectedIndex(0);
                warn.setText("");
            }
        });
        messier.addActionListener(e -> {
            if (messier.isSelected()){
                properties.removeAllItems();
                operators.removeAllItems();
                for (String messierProperty : messierProperties) {
                    properties.addItem(messierProperty);
                }
                properties.setSelectedIndex(0);
                warn.setText("");
            }
        });
        planet.addActionListener(e -> {
            if (planet.isSelected()){
                properties.removeAllItems();
                operators.removeAllItems();
                for (String planetProperty : planetProperties) {
                    properties.addItem(planetProperty);
                }
                properties.setSelectedIndex(0);
                warn.setText("");
            }
        });
        // Add ItemListener to properties for updating operators according to properties
        properties.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                switch ((String) (Objects.requireNonNull(properties.getSelectedItem()))){
                    case "ra": case "decl": case "magn": case "distance": case "albedo":
                        operators.removeAllItems();
                        for (String numericalOperator : numericalOperators) {
                            operators.addItem(numericalOperator);
                        }
                        break;
                    default:
                        operators.removeAllItems();
                        for (String nonNumericOperator : nonNumericOperators) {
                            operators.addItem(nonNumericOperator);
                        }
                        break;
                }
            }
        });
        // Add actionListener to add button
        addButton.addActionListener(e -> {
            // Get information about query
            String newType = null;
            if (star.isSelected()){
                newType = "Star";
            }else if (messier.isSelected()){
                newType = "Messier";
            }else if (planet.isSelected()){
                newType = "Planet";
            }
            String newProperty = (String) properties.getSelectedItem();
            String newOperator = (String) operators.getSelectedItem();
            String newValue = value.getText();
            // Add query to query table
            if (newType != null && !newValue.equals("")){
                Vector<String> newRow = new Vector<>();
                newRow.add(newType);
                newRow.add(newProperty);
                newRow.add(newOperator);
                newRow.add(newValue);
                model.addRow(newRow);
            }else {
                warn.setText("Please complete search query");
            }
        });
        // Add actionListener to delete button
        deleteButton.addActionListener(e -> {
            int selectedRow = queryTable.getSelectedRow();
            if (selectedRow != -1){
                model.removeRow(selectedRow);
            }
            if (model.getRowCount() == 0){
                AstronomicalObjectDAO astronomicalObjectDAO = new AstronomicalObjectDAO();
                Map<String,Vector<Vector<String>>> map = astronomicalObjectDAO.get(null);
                srp.updateTable(map);
            }
        });
        // Add actionListener to search button
        searchButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                int sumRowCount = model.getRowCount();
                if (sumRowCount > 0){
                    Query query = new Query();
                    query.setType((String) model.getValueAt(0, 0));
                    Condition condition;
                    for (int i = 0 ; i < sumRowCount ; i++){
                        condition = new Condition();
                        condition.setProperty((String) model.getValueAt(i, 1));
                        condition.setOperator((String) model.getValueAt(i, 2));
                        condition.setValue((String) model.getValueAt(i, 3));
                        query.getConditions().add(condition);
                    }
                    warn.setText("");
                    // Update table
                    AstronomicalObjectDAO astronomicalObjectDAO = new AstronomicalObjectDAO();
                    Map<String,Vector<Vector<String>>> map = astronomicalObjectDAO.get(query);
                    srp.updateTable(map);
                }else {
                    warn.setText("Please add query first");
                }

            }
        });

        return searchQueryPanel;
    }
}
