package ua.vntu.lab11.jdbc;

import ua.vntu.lab11.CarData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// клас для роботи з БД postgresql
public class PostgresCarDataDao implements CarDataDao{

    private String user = "bduser"; //Логін користувача
    private String password = "qwerty";//Пароль
    private String tableName = "car";
    private Connection connection;

    public PostgresCarDataDao() {
        try {
            // підключаємо драйвер postgresql у програму
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
        }
        try {
            // встановлюємо з'єднання з БД
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/cardata", user,
                    password
            );
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }
    }


    // метод для зберігання даних
    @Override
    public CarData persist(CarData carData) throws RowAlreadyExistsException {
        try {
            // створюємо об'єкт команди, що має бути виконана базою даних
            Statement check = connection.createStatement();
            // за допомогою нього виконуємо запит на отримання рядку із
            // заданими маркою і моделлю
            ResultSet rs = check.executeQuery("SELECT * FROM car WHERE"
                    +
                    " mark = '" + carData.getMark() +
                    "' AND model = '" + carData.getModel() + "';"
            );
            // якщо такий рядок є(тобто машина такої марки і моделі вже існує в БД)
            if (rs.next()) {
                // кидаємо виключення, що рядок вже існує
                throw new RowAlreadyExistsException();
            }
            // тепер, коли ми впевнились, що цих даних ще не має у БД, ми
            // виконуємо запит на зберігання цих даних у БД
            Statement st = connection.createStatement();
            st.executeQuery(
                    "INSERT INTO " + tableName +
                            "(mark, model, volume, body_type, drive_type)" +
                            "VALUES ('" + carData.getMark() +
                            "', '" + carData.getModel() +
                            "', '" + carData.getVolume().toString() +
                            "', '" + carData.getBodyType() +
                            "', '" + carData.getDriveType() + "');"
            );
            // перехоплюємо виключення, які можуть виникати при виконанні програми і друкуємо їх текст
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return carData;
    }

    // метод видалення даних з БД
    @Override
    public CarData remove(CarData carData) {
        try {
            // створюємо і виконуємо запит на видалення
            Statement st = connection.createStatement();
            String query = "DELETE FROM " + tableName +
                    " WHERE" +
                    " mark = '" + carData.getMark() +
                    "' AND model = '" + carData.getModel() + "';";
            st.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // повертаємо видалені дані
        // насправді можна і не повертати, та раптом вони можуть знадобитись
        // наприклад, якщо ми захочемо при видаленні писати "авто такої-то моделі і марки було видалене"
        // но делать это я, конечно же, не буду
        return carData;
    }

    // отримання списку усіх даних машин
    @Override
    public List<CarData> findAllCarData() {
        // власне сам список для зберігання
        List<CarData> list = new ArrayList<>();
        try {
            // створюємо і виконуємо запит на отримання всіх даних з таблиці
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM car;"
            );
            // ці всі дані записані в об'єкті rs(який являє собою набір записів),
            // тому ми проходимо по усіх цих об'єктах за допомогою циклу і зберігаємо дані в список
            // в rs є метод getString(), який вертає дані, що зберігаються у полі з заданою назвою,
            // тобто rs.getString("mark") поверне дані, що зберігаються у стовпчику mark даного запису
            // метод next() переходить до наступного запису
            while (rs.next()) {
                CarData car = new CarData()
                        .withMark(
                                rs.getString("mark")
                        )
                        .withModel(
                                rs.getString("model")
                        )
                        .withVolume(
                                Float.parseFloat(rs.getString("volume"))
                        )
                        .withBodyType(
                                rs.getString("body_type")
                        )
                        .withDriveType(
                                rs.getString("drive_type")
                        );
                list.add(car);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // повертаємо список із збереженими даними
        return list;
    }
}
