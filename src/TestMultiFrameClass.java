import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestMultiFrameClass extends JFrame {
    private Container container;
    Container first = new Panel(new GridLayout(1,1)),
            second = new Panel(new GridLayout(1,1));

    public TestMultiFrameClass() {
        super("TestFrame");
        this.setBounds(
                100, 100,   //координати x,y лівого верхнього кута вікна
                400, 150    //ширина і висота вікна
        );
        //завершуємо програму після закриття вікна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = getContentPane();
        container.setLayout(new GridLayout(1,1));



        Button firstButton = new Button("To second");
        firstButton.addActionListener(new FirstButtonListener());
        first.add(firstButton);

        Button secondButton = new Button("To first");
        secondButton.addActionListener(new SecondButtonListener());
        second.add(secondButton);

        container.add(first);
    }

    private class FirstButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.remove(first);
            container.add(second);
            validate();
        }
    }

    private class SecondButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.remove(second);
            container.add(first);
            validate();
        }
    }

    public static void main(String[] args) {
        TestMultiFrameClass testMultiFrameClass = new TestMultiFrameClass();
        testMultiFrameClass.setVisible(true);
    }
}
