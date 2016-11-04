package ua.vntu.lab11.jdbc;

import ua.vntu.lab11.CarData;

import java.util.List;

public interface CarDataDao {
    CarData persist(CarData carData) throws RowAlreadyExistsException;
    CarData remove(CarData carData);
    List<CarData> findAllCarData();
}
