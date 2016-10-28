package ua.vntu.lab11.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private Container container;

    public MainFrame() {
        super("Database view");
        this.setBounds(
                100, 100,   //координати x,y лівого верхнього кута вікна
                800, 600    //ширина і висота вікна
        );
        //завершуємо програму після закриття вікна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = getContentPane();
        container.setLayout(new GridLayout(1,1));
    }

    public void create(Container frame) {
        container.add(frame);
        //validate();
    }
}
