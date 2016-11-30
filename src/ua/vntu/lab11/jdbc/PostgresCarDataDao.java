package ua.vntu.lab11.jdbc;

import ua.vntu.lab11.CarData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresCarDataDao implements CarDataDao{

    private String user = "bduser"; //Логін користувача
    private String password = "qwerty";//Пароль
    private String tableName = "car";
    private Connection connection;

    public PostgresCarDataDao() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/cardata", user,
                    password
            );
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }



    @Override
    public CarData persist(CarData carData) throws RowAlreadyExistsException {
        try {
            Statement check = connection.createStatement();
            ResultSet rs = check.executeQuery("SELECT * FROM car WHERE"
                    +
                    " mark = '" + carData.getMark() +
                    "' AND model = '" + carData.getModel() + "';"
            );
            if (rs.next()) {
                throw new RowAlreadyExistsException();
            }
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return carData;
    }

    @Override
    public CarData remove(CarData carData) {
        try {
            Statement st = connection.createStatement();
            String query = "DELETE FROM " + tableName +
                    " WHERE" +
                    " mark = '" + carData.getMark() +
                    "' AND model = '" + carData.getModel() + "';";
            System.out.println(query);
            st.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return carData;
    }

    @Override
    public List<CarData> findAllCarData() {
        List<CarData> list = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM car;"
            );
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
        return list;
    }
}
