package ua.vntu.lab11.jdbc;

import ua.vntu.lab11.CarData;

import java.util.ArrayList;
import java.util.List;

public class MockCarDataDao implements CarDataDao {

    private List<CarData> list;

    public MockCarDataDao() {
        list = new ArrayList<>();

        list.add(
                new CarData()
                        .withMark("BMW")
                        .withModel("X5")
                        .withVolume(4.6f)
                        .withBodyType("combi")
                        .withDriveType("FWD")
        );
        list.add(
                new CarData()
                        .withMark("Ford")
                        .withModel("Sunliner")
                        .withVolume(3.5f)
                        .withBodyType("cabriolet")
                        .withDriveType("RWD")
        );
    }

    @Override
    public CarData persist(CarData carData) {
        if (list.contains(carData)) throw new RowAlreadyExistsException();
        list.add(carData);
        System.out.println(carData.toString());
        System.out.println("SAVED");
        return carData;
    }

    @Override
    public CarData remove(CarData carData) {
        if (list.contains(carData)) {
            list.remove(carData);
            System.out.println(carData.toString());
            System.out.println("REMOVED");
        }
        return carData;
    }

    @Override
    public List<CarData> findAllCarData() {
        return list;
    }
}
