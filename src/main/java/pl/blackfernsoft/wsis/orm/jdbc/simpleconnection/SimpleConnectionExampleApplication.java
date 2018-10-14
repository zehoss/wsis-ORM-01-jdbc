package pl.blackfernsoft.wsis.orm.jdbc.simpleconnection;

import java.sql.*;

public class SimpleConnectionExampleApplication {


    public static void main(String[] args) {
        try {

            // Load driver
            Class.forName("org.h2.Driver");

            // Get connection
            // ./test stores db file in current dir (classpath)
            // ~/test will store db file in home dir
            final Connection connection = DriverManager.getConnection("jdbc:h2:./test");


            // Create CarEntity sequence

            // Create CarEntity table if not exists

            // Create statement object
            Statement statement = connection.createStatement();

            // Execute SQL query - create sequence
            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS car_seq");

            // Execute SQL query - drop table
//            statement.executeUpdate("DROP TABLE car");

            // Execute SQL query - create new table if not exists
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS car (" +
                    "id NUMBER default car_seq.nextval PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "plates VARCHAR(8) NOT NULL," +
                    "available BOOLEAN default TRUE NOT NULL" +
                    ")");


            // Insert data into CarEntity table
            PreparedStatement createCarStmt = connection.prepareStatement("INSERT INTO car (name, plates) VALUES(?, ?)");
            createCarStmt.setString(1, "Volvo V40");
            createCarStmt.setString(2, "DW 3542");
            createCarStmt.executeUpdate();


            // Fetch all data from CarEntity table
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM car WHERE available=?");
            prepStatement.setBoolean(1, true);
            ResultSet resultSet = prepStatement.executeQuery();


            // Display results
            while (resultSet.next()) {
                System.out.println(String.format("CAR{%s, %s, %s, %b}",
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("plates"),
                        resultSet.getString("available")
                ));
            }

            // Close connection
            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
