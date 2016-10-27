package ua.vntu.lab9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class GuiApp extends JFrame {

    private Container container;

    //об'єкти що представляють кнопки для
    //обрахування формули
    private JButton calcButton = new JButton("Calc");
    //скидання даних в усіх полях
    private JButton resetButton = new JButton("Reset");

    //поля вводу для
    //ширина, висота, довжина
    private JTextField inputWidth = new JTextField("", 5);
    private JTextField inputHeight = new JTextField("", 5);
    private JTextField inputLength = new JTextField("", 5);
    //виводу
    //об'єм, площа
    private JTextField outputVolume = new JTextField("", 5);
    private JTextField outputArea = new JTextField("", 5);
    //хоч призначення полів різне - ввід і вивід, використовуємо ми
    //той самий елемент - поле для текстового вводу, різниця в тому
    //що в поля вводу користувач сам заносить текст, а в поля виводу
    //текст заносить сама програма, після того як користувач
    //натискає кнопку "Обчислити"("Calc")


    //підписи до полів, щоб користувач бачив що куди вводити
    private JLabel labelWidth = new JLabel("Width:");
    private JLabel labelHeight = new JLabel("Height:");
    private JLabel labelLength = new JLabel("Length:");
    private JLabel labelVolume = new JLabel("Volume:");
    private JLabel labelArea = new JLabel("Area:");

    //заносимо усі наші текстові поля в набір, щоби пізніше
    //можна було їх обробити в ондому циклі, наприклад [1]
    Set<JTextField> textFields = new HashSet<>();
    {
        textFields.add(inputHeight);
        textFields.add(inputLength);
        textFields.add(inputWidth);
        textFields.add(outputArea);
        textFields.add(outputVolume);
    }

    public GuiApp() {
        //викликаємо конструктор класа-предка(JFrame)
        //і передаємо у нього назву для графічного вікна
        super("Parallelepiped app");
        this.setBounds(
                100, 100,   //координати x,y лівого верхнього кута вікна
                400, 150    //ширина і висота вікна
        );
        //завершуємо програму після закриття вікна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //отримуємо загальний контейнер вікна
        container = this.getContentPane();
        //робимо його сіткою 2x2, таким чином він розділений на 4 частини,
        //іншими словами - може містити 4 елементи
        //він і буде містити 4 елементи:
        //1) сітка з полями для вводу
        //2) сітка з полями для виводу
        //3) кнопку для обрахування
        //4) кнопку для скидання вмісту полів
        container.setLayout(new GridLayout(2,2));

        //нижче ми якраз створимо ці елементи і додамо їх у загальний контейнер
        //створюємо контейнер-сітку для полів вводу, розміром 3х2,
        //т.я. нам необхідно помістити у нього 3 поля для вводу і по одному
        //підпису для кожного поля, тобто 6 елементів
        Container inPanel = new Panel(new GridLayout(3,2));
        //те саме для виводу - сітка для 2 полів виводу і по одному
        //підпису для кожного поля
        Container outPanel = new Panel(new GridLayout(2,2));

        //кладемо в сітку для вводу спочатку підпис для поля
        //і одразу після нього саме поле
        inPanel.add(labelWidth);
        inPanel.add(inputWidth);
        //так само для інших полів
        inPanel.add(labelHeight);
        inPanel.add(inputHeight);

        inPanel.add(labelLength);
        inPanel.add(inputLength);

        //і так само для сітки виводу
        outPanel.add(labelVolume);
        outPanel.add(outputVolume);

        outPanel.add(labelArea);
        outPanel.add(outputArea);

        //прив'язуєм до кнопок обробників подій, які будуть реагувати на натиск кнопки
        calcButton.addActionListener(new GuiApp.CalcButtonEventListener());
        resetButton.addActionListener(new GuiApp.ResetButtonEventListener());

        //додаємо в загальний контейнер сітку для вводу, сітку для виводу
        container.add(inPanel);
        container.add(outPanel);
        //таким же чином кладемо в загальний контейнер кнопки
        container.add(calcButton);
        container.add(resetButton);
    }

    //клас-обробник подій для кнопки "Обчислити" (calcButton)
    class CalcButtonEventListener implements ActionListener {
        //при натискання на кнопку виконується наступний метод
        public void actionPerformed(ActionEvent e) {
            //обробка виключення - на той випадок якщо користувач
            //заповнить не усі необхідні поля або заповнить їх не цілими числами
            try {
                //отримуємо зміст полів вводу
                int width = getIntValue(inputWidth),
                        height = getIntValue(inputHeight),
                        length = getIntValue(inputLength);
                //обчислюємо площу
                int area = 2 * (width * height + height * length + width * length);
                //і об'єм
                int volume = width * height * length;
                //у поля виводу кладемо значення обчислених параметрів
                //N.B. - метод setText приймає лише дані типу String,
                //а значення параметрів що ми обчислюємо зберігаються в
                //int, тому ми конвертуємо ці значення у строкову форму
                //нехитрою конструкцією конкатенації пустої строки і числа
                outputArea.setText("" + area);
                outputVolume.setText("" + volume);
            } catch (NumberFormatException ex) {    //у разі виникнення виключення
                //просимо користувача ввести адекватні дані за допомогою діалогового вікна
                JOptionPane.showMessageDialog(
                        //цей параметр приймає посилання на вікно, у якому має бути
                        //показане повідомлення, а null означає, що буде вікрите дефолтне вікно
                        null,
                        //сам текст повідомлення
                        "Fill all fields with integers",
                        //тайтл вікна у якому буде показане повідомлення
                        "Input error",
                        //тип повідомлення, в нашому випадку це "попередження",
                        //просто додає поряд із повідомленням такий жовтий значок
                        //із знаком оклику
                        //є ще, наприклад, тип "помилка", тоді поряд буде червоний хрестик,
                        //але він мені не подобається
                        JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    //клас-обробник подій для кнопки "Очистити" (resetButton)
    class ResetButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //[1] тут ми скидаємо зміст усіх текстових полів,
            //встановлюючи їм зміст - пусту текстову строку
            //а завдяки тому, що ми помістили всі поля в один список, нам
            //не потрібно очищати кожне поле вручну
            for(JTextField field : textFields) {
                field.setText("");
            }
        }
    }

    //просто метод який вибирає з текстового поля int'ове значення і повертає його
    private int getIntValue(JTextField field) {
        return new Integer(
                field.getText().trim()
        );
    }

    public static void main(String[] args) {
        GuiApp app = new GuiApp();
        //показує вікно, яке ми створили
        //без цього пахати не буде, інфа 100
        app.setVisible(true);
    }

}
