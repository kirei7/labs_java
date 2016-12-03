package ua.vntu.lab11.jdbc;

import ua.vntu.lab11.CarData;

import java.util.List;

// інтерфейс для об'єктів, що будуть працювати з БД
public interface CarDataDao {
    // збереження даних
    CarData persist(CarData carData) throws RowAlreadyExistsException;
    // видалення даних
    CarData remove(CarData carData);
    // отримання списку з усіма записами
    List<CarData> findAllCarData();
}
