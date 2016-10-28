package ua.vntu.lab11;

import ua.vntu.lab11.ui.DataListFrameHolder;
import ua.vntu.lab11.ui.MainFrame;

public class Application {

    private DataListFrameHolder dataListFrameHolder = new DataListFrameHolder();
    private MainFrame mainFrame = new MainFrame();

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    private void run() {
        mainFrame.setFrame(dataListFrameHolder.getContainer());
        mainFrame.setVisible(true);
    }

}
