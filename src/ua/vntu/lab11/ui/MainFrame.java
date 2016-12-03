package ua.vntu.lab11.ui;

import javax.swing.*;
import java.awt.*;

// графічне вікно програми, на що вказує extends JFrame
public class MainFrame extends JFrame{
    // маємо всього один контейнер, в який помістимо контейнер зі всіма графічними елементами
    private Container container;

    // у конструктор передаємо контейнер, що містить графічні елементи
    public MainFrame(Container currentFrame) {
        super("Database view");
        this.setBounds(
                100, 100,   //координати x,y лівого верхнього кута вікна
                800, 600    //ширина і висота вікна
        );
        // після закриття вікна - завершувати програму
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // поміщаємо контейнер з графічними елементами у вікно
        container = getContentPane();
        container.setLayout(new GridLayout(1,1));
        container.add(currentFrame);
    }
}
