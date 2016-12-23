package com.userok.learning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class Kolobok extends JFrame {
    volatile Graphics g;

    Thread first;
    Thread second;
    Thread third;
    public Kolobok() {
        JPanel contentPane=new JPanel(new BorderLayout());
        getContentPane().add(contentPane);
        int size = 450;
        setSize(size,size);

        JPanel buttons = new JPanel(new GridLayout(1,2));
        JButton buttonStart =new JButton("start");
        buttonStart.addActionListener(new StartActionListener());
        JButton buttonPause =new JButton("pause");
        buttonPause.addActionListener(new PauseActionListener());
        buttons.add(buttonStart);
        buttons.add(buttonPause);

        final JPanel  drawingPanel  = new JPanel();
        contentPane.add(drawingPanel,  BorderLayout.CENTER);

        contentPane.add(buttons, BorderLayout.SOUTH);

        setVisible(true);
        g = drawingPanel.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, size, size);
        first = new Thread(new CircleDrawer(g, 1000, Color.BLACK));
        second = new Thread(new CircleDrawer(g, 2000, Color.RED));
        third = new Thread(new CircleDrawer(g, 500, Color.BLUE));
    }

    public static void main(String[] args) {
        Kolobok kolobok = new Kolobok();

        kolobok.first.start();
        kolobok.second.start();
        kolobok.third.start();
    }

    private class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized (first) {
                first.notify();
            }
            synchronized (second) {
                second.notify();
            }
            synchronized (third) {
                third.notify();
            }
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                synchronized (first) {
                    first.wait();
                }
                synchronized (second) {
                    second.wait();
                }
                synchronized (third) {
                    third.wait();
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class CircleDrawer implements Runnable {
        Graphics graphics;
        long interval;
        static final int DIAMETER = 50;
        int x = 250, y = 250;
        Color color;

        CircleDrawer(Graphics graphics, long interval, Color color) {
            this.graphics = graphics;
            this.interval = interval;
            this.color = color;
        }

        @Override
        public void run() {
            do {
                redrawCircle();
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
        private void redrawCircle() {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.drawOval(x, y, DIAMETER, DIAMETER);
            move();
            graphics.setColor(color);
            graphics.drawOval(x, y, DIAMETER, DIAMETER);
        }
        private void move() {
            x += ThreadLocalRandom.current().nextInt(-30, 30);
            y += ThreadLocalRandom.current().nextInt(-30, 30);
        }
    }
}
