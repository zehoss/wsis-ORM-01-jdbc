package pl.blackfernsoft.wsis.orm.jdbc.daoexample.dao;

import pl.blackfernsoft.wsis.orm.jdbc.daoexample.entity.CustomerEntity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends AbstractDao<CustomerEntity> {

    private final Connection connection;

    public CustomerDao(Connection connection) throws SQLException {
        this.connection = connection;
        this.initTable();
    }

    public CustomerEntity save(CustomerEntity entity) throws SQLException {
        PreparedStatement createStmt = connection.prepareStatement("INSERT INTO customer (first_name, last_name, birth_date) VALUES(?, ?, ?)");
        createStmt.setString(1, entity.getFirstName());
        createStmt.setString(2, entity.getLastName());
        createStmt.setDate(3, new java.sql.Date(entity.getBirthDate().getTime()));
        createStmt.executeUpdate();
        return entity;
    }

    public List<CustomerEntity> findAll() throws SQLException {
        PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM customer");
        ResultSet resultSet = prepStatement.executeQuery();

        List<CustomerEntity> customerEntities = new ArrayList<>();
        while (resultSet.next()) {
            CustomerEntity customer = mapToEntity(resultSet);
            customerEntities.add(customer);
        }

        return customerEntities;
    }

    private CustomerEntity mapToEntity(ResultSet resultSet) throws SQLException {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(resultSet.getLong("id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setBirthDate(resultSet.getDate("birth_date"));
        return customer;
    }

    public CustomerEntity findById(Long id) {
        throw new NotImplementedException();
    }

    public void delete(CustomerEntity entity) {
        throw new NotImplementedException();
    }

    private void initTable() throws SQLException {
        Statement customerSeqStmt = connection.createStatement();
        customerSeqStmt.executeUpdate("CREATE SEQUENCE IF NOT EXISTS customer_seq");

        Statement customerStmt = connection.createStatement();
        customerStmt.executeUpdate("CREATE TABLE IF NOT EXISTS customer (" +
                "id NUMBER default customer_seq.nextval PRIMARY KEY," +
                "first_name VARCHAR(50) NOT NULL," +
                "last_name VARCHAR(60) NOT NULL," +
                "birth_date Date" +
                ")");
    }
}
