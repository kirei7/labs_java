package ua.vntu.lab9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class GuiApp extends JFrame {

    Container container;

    private JButton calcButton = new JButton("Calc");
    private JButton resetButton = new JButton("Reset");

    //поля для
    //ширина, висота, довжина
    private JTextField inputWidth = new JTextField("", 5);
    private JTextField inputHeight = new JTextField("", 5);
    private JTextField inputLength = new JTextField("", 5);
    //об'єм, площа
    private JTextField outputVolume = new JTextField("", 5);
    private JTextField outputArea = new JTextField("", 5);

    //підписи до полів
    private JLabel labelWidth = new JLabel("Width:");
    private JLabel labelHeight = new JLabel("Height:");
    private JLabel labelLength = new JLabel("Length:");
    private JLabel labelVolume = new JLabel("Volume:");
    private JLabel labelArea = new JLabel("Area:");

    Set<JTextField> textFields = new HashSet<>();
    {
        textFields.add(inputHeight);
        textFields.add(inputLength);
        textFields.add(inputWidth);
        textFields.add(outputArea);
        textFields.add(outputVolume);
    }

    public GuiApp()

    {

        super("Parallelepiped app");

        this.setBounds(100,100,400,150);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = this.getContentPane();
        container.setLayout(new GridLayout(2,2));

        Container inPanel = new Panel(new GridLayout(3,2));

        Container outPanel = new Panel(new GridLayout(2,2));

        inPanel.add(labelWidth);
        inPanel.add(inputWidth);

        inPanel.add(labelHeight);
        inPanel.add(inputHeight);

        inPanel.add(labelLength);
        inPanel.add(inputLength);

        outPanel.add(labelVolume);
        outPanel.add(outputVolume);

        outPanel.add(labelArea);
        outPanel.add(outputArea);
        
        calcButton.addActionListener(new GuiApp.CalcButtonEventListener());
        resetButton.addActionListener(new GuiApp.ResetButtonEventListener());

        container.add(inPanel);
        container.add(outPanel);

        container.add(calcButton);
        container.add(resetButton);
    }

    class CalcButtonEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                int width = getFloatValue(inputWidth),
                        height = getFloatValue(inputHeight),
                        length = getFloatValue(inputLength);
                int area = 2 * (width * height + height * length + width * length);
                int volume = width * height * length;

                outputArea.setText(""+area);
                outputVolume.setText(""+volume);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Fill all fields with integers",
                        "Output",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }

    }
    class ResetButtonEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for(JTextField field : textFields) {
                field.setText("");
            }
        }
    }

    private int getFloatValue(JTextField field) {
        return new Integer(
                field.getText().trim()
        );
    }

    public static void main(String[] args)

    {

        GuiApp app = new GuiApp();

        app.setVisible(true);

    }

}
