package ua.vntu.lab11;

import ua.vntu.lab11.jdbc.CarDataDao;
import ua.vntu.lab11.jdbc.MockCarDataDao;
import ua.vntu.lab11.jdbc.PostgresCarDataDao;
import ua.vntu.lab11.ui.DataListFrameHolder;
import ua.vntu.lab11.ui.MainFrame;
//стартова точка програми
public class Application {

    // об'єкт для зберігання даних в БД
    private CarDataDao carDataDao;
    // зберігання елементів графічного інтерфейсу
    private DataListFrameHolder dataListFrameHolder;
    // графічне вікно програми
    private MainFrame mainFrame;

    public Application() {
        // ініціалізуємо поля
        carDataDao = new PostgresCarDataDao();
        dataListFrameHolder = new DataListFrameHolder(carDataDao);
        mainFrame = new MainFrame(dataListFrameHolder.getContainer());
    }

    public static void main(String[] args) {
        // запускаємо програму
        Application app = new Application();
        app.run();
    }

    private void run() {
        mainFrame.setVisible(true);
    }

}
