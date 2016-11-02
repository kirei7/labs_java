package ua.vntu.lab10.ui;

import ua.vntu.lab10.util.Message;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private Container container;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JTextArea inputField;
    private JButton sendButton;

    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public MainFrame() {
        super("Chat");
        int width = 350,
            height = 500;
        this.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        //завершуємо програму після закриття вікна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        container = getContentPane();
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane  = new JScrollPane(textArea);
        scrollPane.setSize(width, height - 50);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        inputField = new JTextArea(2, 20);
        inputField.setCaretPosition(0);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);

        JScrollPane inputScrollPane = new JScrollPane(inputField);
        inputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        inputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        sendButton = new JButton("Send");

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(inputScrollPane);
        southPanel.add(sendButton);

        container.add(scrollPane, BorderLayout.CENTER);
        container.add(southPanel, BorderLayout.SOUTH);

    }

    public void putMessage(Message msg) {
        StringBuilder builder = new StringBuilder();
        textArea.append(
                builder
                        .append(msg.getName())
                        .append(": ")
                        .append(msg.getText())
                        .append(LINE_SEPARATOR)
                .toString()
        );
    }
}
