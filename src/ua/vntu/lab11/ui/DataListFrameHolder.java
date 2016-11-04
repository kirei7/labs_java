package ua.vntu.lab11.ui;

import ua.vntu.lab11.CarData;
import ua.vntu.lab11.jdbc.CarDataDao;
import ua.vntu.lab11.jdbc.DataFetcher;
import ua.vntu.lab11.jdbc.RowAlreadyExistsException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DataListFrameHolder {

    private CarDataDao carDataDao;

    private Container container;
    private JTable table;
    private DefaultTableModel tableModel;
    private Button addButton;
    private Button deleteButton;
    private DataFetcher dataFetcher;

    private Panel inputPanel;
    private JTextField markField;
    private JTextField modelField;
    private JTextField volumeField;
    private JTextField bodyTypeField;
    private JTextField driveTypeField;
    private JLabel labelMark = new JLabel("Mark:");
    private JLabel labelModel = new JLabel("Model:");
    private JLabel labelVolume = new JLabel("Volume:");
    private JLabel labelBodyType = new JLabel("Body type:");
    private JLabel labelDriveType = new JLabel("Drive type:");

    public DataListFrameHolder(CarDataDao carDataDao) {
        this.carDataDao = carDataDao;
        prepareComponents();
        container.add(new JScrollPane(table), BorderLayout.NORTH);
        container.add(inputPanel, BorderLayout.SOUTH);
    }

    private void prepareComponents() {
        container = new Panel(new BorderLayout());
        tableModel = createTableModel();
        table = new JTable(tableModel);
        addButton = new Button("Add");
        addButton.setBackground(Color.GRAY);
        addButton.setForeground(Color.LIGHT_GRAY);
        addButton.addActionListener(new AddButtonActionListener());
        deleteButton = new Button("Delete selected");
        deleteButton.setBackground(Color.GRAY);
        deleteButton.setForeground(Color.LIGHT_GRAY);
        deleteButton.addActionListener(new DeleteButtonActionListener());

        Panel inputFieldsPanel = new Panel(new GridLayout(2, 5));
        inputFieldsPanel.add(labelMark);
        inputFieldsPanel.add(labelModel);
        inputFieldsPanel.add(labelVolume);
        inputFieldsPanel.add(labelBodyType);
        inputFieldsPanel.add(labelDriveType);
        inputFieldsPanel.add(markField = new JTextField("", 5));
        inputFieldsPanel.add(modelField = new JTextField("", 5));
        inputFieldsPanel.add(volumeField = new JTextField("", 5));
        inputFieldsPanel.add(bodyTypeField = new JTextField("", 5));
        inputFieldsPanel.add(driveTypeField = new JTextField("", 5));
        inputPanel = new Panel(new GridLayout(2, 1));
        inputPanel.add(inputFieldsPanel);
        Panel buttonPanel = new Panel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        inputPanel.add(buttonPanel);


    }

    public Container getContainer() {
        return container;
    }

    private DefaultTableModel createTableModel() {
        List<CarData> dataList = carDataDao.findAllCarData();
        Object[][] preparedData = new Object[dataList.size()][6];
        for (int i = 0; i < dataList.size(); i++) {
            preparedData[i] = convertToRow(dataList.get(i));
        }
        return new DefaultTableModel(
                preparedData,
                new Object[]{
                        "Mark",
                        "Model",
                        "Volume",
                        "Body type",
                        "Drive type",
                        "Del"
                }
        ) {
            public Class<?> getColumnClass(int colIndex) {

                return getValueAt(0, colIndex).getClass();

            }
        };
    }

    private class AddButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                CarData carData = new CarData()
                        .withMark(
                                markField.getText().trim()
                        )
                        .withModel(
                                modelField.getText().trim()
                        )
                        .withBodyType(
                                bodyTypeField.getText().trim()
                        )
                        .withDriveType(
                                driveTypeField.getText().trim()
                        )
                        .withVolume(
                                Float.parseFloat(volumeField.getText().trim())
                        );
                try {
                    carDataDao.persist(carData);
                    tableModel.addRow(convertToRow(carData));
                    tableModel.fireTableDataChanged();
                }
                catch (RowAlreadyExistsException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Row already exists",
                            "Data error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Fill all fields",
                        "Input error",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private class DeleteButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            java.util.List<CarData> toDelete = new ArrayList<>();
            List<Integer> rowNums = new ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) {
                Boolean checkbox = (Boolean) table.getModel().getValueAt(i, 5);
                if (checkbox) {
                    toDelete.add(
                            new CarData()
                                    .withMark((String) table.getModel().getValueAt(i, 0))
                                    .withModel((String) table.getModel().getValueAt(i, 1))
                    );
                    rowNums.add(i);
                }
            }
            for (CarData carData : toDelete) {
                carDataDao.remove(carData);
            }
            for (int i : rowNums) {
                tableModel.removeRow(i);
            }
            tableModel.fireTableDataChanged();
        }
    }

    private Object[] convertToRow(CarData data) {
        Object[] row = new Object[6];
        row[0] = data.getMark();
        row[1] = data.getModel();
        row[2] = data.getVolume();
        row[3] = data.getBodyType();
        row[4] = data.getDriveType();
        row[5] = false;
        return row;
    }
}
