package ua.vntu.lab11.ui;

import ua.vntu.lab11.jdbc.DataFetcher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataListFrameHolder {

    private Container container;
    private JTable table;
    private DefaultTableModel tableModel;
    private Button addButton;
    private DataFetcher dataFetcher;


    public DataListFrameHolder() {
        prepareComponents();
        container.add(new JScrollPane(table), BorderLayout.CENTER);
        container.add(addButton, BorderLayout.SOUTH);
    }

    private void prepareComponents() {
        container = new Panel(new BorderLayout());
        tableModel = createTableModel();
        table = new JTable(tableModel);
        addButton = new Button("Add");
        addButton.setBackground(Color.GRAY);
        addButton.setForeground(Color.LIGHT_GRAY);
    }
    public Container getContainer() {
        return container;
    }

    private DefaultTableModel createTableModel() {
        //return dataFetcher.getDataArray();
        return new DefaultTableModel(
                new Object[][]{
                        {"data1","data1q","data1w","data1e","data1r",},
                        {"data2","data2q","data2w","data2e","data2r",},
                        {"data3","data3q","data3w","data3e","data3r",},
                        {"data4","data4q","data4w","data4e","data4r",},
                        {"data5","data5q","data5w","data5e","data5r",},
                        
                },
                new Object[]{
                        "Col1",
                        "Col2",
                        "Col3",
                        "Col4",
                        "Col5"
                }
        );
    }
    private class AddButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
