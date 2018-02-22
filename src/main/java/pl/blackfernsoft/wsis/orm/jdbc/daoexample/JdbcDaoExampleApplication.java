package pl.blackfernsoft.wsis.orm.jdbc.daoexample;

import pl.blackfernsoft.wsis.orm.jdbc.daoexample.dao.CarDao;
import pl.blackfernsoft.wsis.orm.jdbc.daoexample.dao.CustomerDao;
import pl.blackfernsoft.wsis.orm.jdbc.daoexample.entity.CarEntity;

import java.sql.SQLException;
import java.util.List;

public class JdbcDaoExampleApplication {

    public static void main(String[] args) {

        ConnectionManager connectionManager = null;

        try {
            // Create connection manager to handle database connection
            connectionManager = ConnectionManager.getConnection();

            // Create DTO object to access DB tables
            CarDao carDao = new CarDao(connectionManager.connection());
            CustomerDao customerDao = new CustomerDao(connectionManager.connection());

            // Create a new Car Entity
            CarEntity carEntity = new CarEntity();
            carEntity.setName("Volvo V40");
            carEntity.setPlates("DW123123");

            // Store the new Car Entity in DB
            carEntity = carDao.save(carEntity);

            // Fetch and display all Car Entities from DB
            List<CarEntity> carList = carDao.findAll();
            for (CarEntity car : carList) {
                System.out.println(car);
            }

            // Update Car Entity
            carEntity.setPlates("DW45XXX");
            carDao.save(carEntity);

            // Fetch and display all Car Entities from DB
            carList = carDao.findAll();
            for (CarEntity car : carList) {
                System.out.println(car);
            }

            // Remove table from DB
            carDao.dropTable();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connectionManager != null)
                try {
                    connectionManager.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }
}
