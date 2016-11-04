package ua.vntu.lab11;

import ua.vntu.lab11.jdbc.CarDataDao;
import ua.vntu.lab11.jdbc.MockCarDataDao;
import ua.vntu.lab11.ui.DataListFrameHolder;
import ua.vntu.lab11.ui.MainFrame;

public class Application {

    private CarDataDao carDataDao;
    private DataListFrameHolder dataListFrameHolder;
    private MainFrame mainFrame;

    public Application() {
        carDataDao = new MockCarDataDao();
        dataListFrameHolder = new DataListFrameHolder(carDataDao);
        mainFrame = new MainFrame(dataListFrameHolder.getContainer());
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    private void run() {
        mainFrame.setVisible(true);
    }

}
