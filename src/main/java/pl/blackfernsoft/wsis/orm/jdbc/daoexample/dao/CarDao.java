package pl.blackfernsoft.wsis.orm.jdbc.daoexample.dao;

import pl.blackfernsoft.wsis.orm.jdbc.daoexample.entity.CarEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao extends AbstractDao<CarEntity> {

    private final Connection connection;

    public CarDao(Connection connection) throws SQLException {
        this.connection = connection;
        initializeTable();
    }

    public CarEntity save(CarEntity carEntity) throws SQLException {
        if (carEntity.getId() == null) {
            // insert new object
            PreparedStatement createCarStmt = connection.prepareStatement("INSERT INTO car (name, plates, available) VALUES(?, ?, ?)");
            createCarStmt.setString(1, carEntity.getName());
            createCarStmt.setString(2, carEntity.getPlates());
            createCarStmt.setBoolean(3, carEntity.getAvailable());
            createCarStmt.executeUpdate();

            // Ustawiamy ID w zwracanym obiekcie
            ResultSet generatedKeys = createCarStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                carEntity.setId(generatedKeys.getLong(1));
            }

        } else {
            // update existing object
            PreparedStatement updateCarStmt = connection.prepareStatement("UPDATE car SET name=?, plates=?, available=? WHERE id=?");
            updateCarStmt.setString(1, carEntity.getName());
            updateCarStmt.setString(2, carEntity.getPlates());
            updateCarStmt.setBoolean(3, carEntity.getAvailable());
            updateCarStmt.setLong(4, carEntity.getId());
            updateCarStmt.executeUpdate();
        }

        // TODO: close statements after use!
        return carEntity;
    }

    public List<CarEntity> findAll() throws SQLException {
        PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM car");
        ResultSet resultSet = prepStatement.executeQuery();

        List<CarEntity> cars = new ArrayList<>();
        while (resultSet.next()) {
            CarEntity car = mapToEntity(resultSet);
            cars.add(car);
        }

        return cars;
    }

    public CarEntity findById(Long id) throws SQLException {
        PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM car WHERE id=?");
        ResultSet resultSet = prepStatement.executeQuery();
        return mapToEntity(resultSet);
    }

    public void delete(CarEntity entity) throws SQLException {
        PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM car WHERE id=?");
        prepStatement.setLong(1, entity.getId());
        prepStatement.executeUpdate();
    }

    private CarEntity mapToEntity(ResultSet resultSet) throws SQLException {
        CarEntity car = new CarEntity();
        car.setId(resultSet.getLong("id"));
        car.setName(resultSet.getString("name"));
        car.setPlates(resultSet.getString("plates"));
        car.setAvailable(resultSet.getBoolean("available"));
        return car;
    }

    private void initializeTable() throws SQLException {
        Statement createCarSequenceStmt = connection.createStatement();
        createCarSequenceStmt.executeUpdate("CREATE SEQUENCE IF NOT EXISTS car_seq");

        Statement createTableStmt = connection.createStatement();
        createTableStmt.executeUpdate("CREATE TABLE IF NOT EXISTS car (" +
                "id NUMBER default car_seq.nextval PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "plates VARCHAR(8)," +
                "available BOOLEAN default TRUE " +
                ")");
    }

    public void dropTable() throws SQLException {
        System.err.println("DROPPING TABLE!");
        Statement statement = connection.createStatement();

        // Execute SQL query - drop table
        statement.executeUpdate("DROP TABLE car");
    }
}
