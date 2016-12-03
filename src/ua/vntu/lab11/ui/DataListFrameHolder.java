package ua.vntu.lab11.ui;

import ua.vntu.lab11.CarData;
import ua.vntu.lab11.jdbc.CarDataDao;
import ua.vntu.lab11.jdbc.RowAlreadyExistsException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

// клас, що містить усі елементи графічного інтерфейсу програми
public class DataListFrameHolder {

    // об'єкт для зберігання даних в БД
    private CarDataDao carDataDao;

    // головний контейнер
    private Container container;
    // таблиця у якій будуть відображатись дані
    private JTable table;
    // модель таблиці - в неї ми кладемо дані, а вона
    // ці дані кладе в таблицю і розміщає їх по стовпцям і рядкам
    private DefaultTableModel tableModel;
    // кнопка для додання нового запису
    private Button addButton;
    // кнопка для видалення виділених записів
    private Button deleteButton;

    // панель для вводу даних
    private Panel inputPanel;
    // поля для вводу відповідних даних
    private JTextField markField;
    private JTextField modelField;
    private JTextField volumeField;
    private JTextField bodyTypeField;
    private JTextField driveTypeField;
    // підписи до полів
    private JLabel labelMark = new JLabel("Mark:");
    private JLabel labelModel = new JLabel("Model:");
    private JLabel labelVolume = new JLabel("Volume:");
    private JLabel labelBodyType = new JLabel("Body type:");
    private JLabel labelDriveType = new JLabel("Drive type:");

    // конструктор приймає об'єкт для зберігання даних в БД
    public DataListFrameHolder(CarDataDao carDataDao) {
        this.carDataDao = carDataDao;
        prepareComponents();
        // у верхню частину головного контейнера поміщаємо таблицю (NORTH -
        // верхня частина у BorderLayout)
        container.add(new JScrollPane(table), BorderLayout.NORTH);
        // у нижню частину цього ж контейнера кладемо панель для вводу даних
        container.add(inputPanel, BorderLayout.SOUTH);
    }

    // в цьому методі усі компоненти створюються і займають свої місця
    private void prepareComponents() {
        // ініціалізуємо графічні елементи
        container = new Panel(new BorderLayout());
        tableModel = createTableModel();
        table = new JTable(tableModel);
        // створюємо кнопки і задаємо їм background - колір при натисканні
        // foreground - колір у дефолтному положенні (не натиснені)
        // також задаємо кнопкам ActionListener'и, це такі класи, які виконують певні
        // дії при натисканні на кнопки, які саме дії - див нижче, там де ці класи
        // об'явлені
        addButton = new Button("Add");
        addButton.setBackground(Color.GRAY);
        addButton.setForeground(Color.LIGHT_GRAY);
        addButton.addActionListener(new AddButtonActionListener());
        deleteButton = new Button("Delete selected");
        deleteButton.setBackground(Color.GRAY);
        deleteButton.setForeground(Color.LIGHT_GRAY);
        deleteButton.addActionListener(new DeleteButtonActionListener());
        // створюємо і заповнюємо панель для вхідних полів
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
        // панель для вводу даних
        inputPanel = new Panel(new GridLayout(2, 1));
        inputPanel.add(inputFieldsPanel);
        // панель для кнопок
        Panel buttonPanel = new Panel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        inputPanel.add(buttonPanel);


    }

    public Container getContainer() {
        return container;
    }

    // створення моделі таблиці
    private DefaultTableModel createTableModel() {
        // отримуємо всі дані по машинам у вигляді списку
        List<CarData> dataList = carDataDao.findAllCarData();
        // створюємо двувимірний масив розміром ДОВЖИНА_СПИСКУх6
        // шість, бо 5 це поля для даних про машину і одне поле
        // для галочки "Del" - видалення машини
        Object[][] preparedData = new Object[dataList.size()][6];
        // перетворюємо список у масив
        for (int i = 0; i < dataList.size(); i++) {
            preparedData[i] = convertToRow(dataList.get(i));
        }
        // на основі масиву створюємо модель таблиці
        return new DefaultTableModel(
                // дані для таблиці
                preparedData,
                // назви стовпчиків таблиці
                new Object[]{
                        "Mark",
                        "Model",
                        "Volume",
                        "Body type",
                        "Drive type",
                        "Del"
                }
        )
            // в моделі таблиці переписуємо метод getColumnClass, це потрібно для
            // того, щоб можна було отримувати номера виділених рядків таблиці
            // я точно не знаю як це працює, просто взяв код зі stackoverflow.com
        {
            public Class<?> getColumnClass(int colIndex) {
                return getValueAt(0, colIndex).getClass();
            }
        };
    }

    // клас, який буде реагувати на кнопку додання рядку
    private class AddButtonActionListener implements ActionListener {
        // при натисненні на цю кнопку буде викликатись наступний метод
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // створюємо об'єкт з даними машини, отримуючи ці дані від текстових полів вводу
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
                    // зберігаємо дані в БД
                    carDataDao.persist(carData);
                    // додаємо рядок з даними у модель таблиці
                    tableModel.addRow(convertToRow(carData));
                    // оновлюємо таблицю (з'являється рядок який було щойно додано)
                    tableModel.fireTableDataChanged();
                }
                // перехоплюємо виключення, що виникає, якщо така машина вже додана
                catch (RowAlreadyExistsException ex) {
                    // показуємо вікно з повідомленням про помилку
                    JOptionPane.showMessageDialog(
                            null,
                            "Row already exists",
                            "Data error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                // перехоплюємо виключення, що виникає при неправильному заповненні поля об'єму двигуна
                // або заповненні не всіх полів
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

    // клас що реагує на натиснення кнопки видалення
    private class DeleteButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // список, у якому будуть зберігатись дані, які треба видалити
            List<CarData> toDelete = new ArrayList<>();
            // список, у якому будуть зберігатись номера рядків, які треба видалити
            List<Integer> rowNums = new ArrayList<>();
            // пробігаємось по кожному рядку таблиці
            for (int i = 0; i < table.getRowCount(); i++) {
                // перевіряємо чекбокс поля для видалення цього рядка
                Boolean checkbox = (Boolean) table.getModel().getValueAt(i, 5);
                // якщо на чекбоксі стоїть "галочка"
                if (checkbox) {
                    // додаємо у список видалення машину, яку треба видалити
                    // у методі equals класу CarData присутні лише два поля - марка і модель,
                    // це означає, що при порівнянні машин порівнюються лише ці два поля,
                    // тому іншими полями можна знехтувати
                    toDelete.add(
                            new CarData()
                                    .withMark((String) table.getModel().getValueAt(i, 0))
                                    .withModel((String) table.getModel().getValueAt(i, 1))
                    );
                    // до списку номерів рядків для видалення додаємо номер рядка
                    rowNums.add(i);
                }
            }
            // проходимо по списку машин для видалення і видаляємо їх із БД
            for (CarData carData : toDelete) {
                carDataDao.remove(carData);
            }
            // сортуємо номера рядків для видалення (це необхідно для того щоб рядки видалялись правильно)
            Collections.sort(rowNums);
            // пробігаємось по списку рядків до видалення
            for (int i = rowNums.size() - 1; i >= 0; i--) {
                // видаляємо рядок з моделі таблиці
                tableModel.removeRow(rowNums.get(i));
                // оновлюємо таблицю
                tableModel.fireTableDataChanged();
            }
            // зверни увагу, що видалення ми проводимо із двох місць:
            // 1) із БД, де фактично зберігаються дані про машини
            // 2) із моделі таблиці, де ці самі дані відображаються
        }
    }

    // метод для конвертації об'єкту з даними про машину в масив
    private Object[] convertToRow(CarData data) {
        Object[] row = new Object[6];
        row[0] = data.getMark();
        row[1] = data.getModel();
        row[2] = data.getVolume();
        row[3] = data.getBodyType();
        row[4] = data.getDriveType();
        // останній елемент масиву - boolean, з якого побудується чекбокс
        // true - чекбокс з галочкою, false - чекбокс без галочки (у нас завжди "чистий" чекбокс)
        row[5] = false;
        return row;
    }
}
