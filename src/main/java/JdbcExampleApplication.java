import java.sql.*;

/**
 * Created by zehoss on 30.01.18.
 */
public class JdbcExampleApplication {

    public static final String JDBC_DRIVER = "org.h2.Driver";
    // ./test stores db file in current dir (classpath)
    // ~/test will store db file in home dir
    public static final String JDBC_DB_URL = "jdbc:h2:./test";

    public static void main(String[] args) {

        try {
            // Load driver
            Class.forName(JDBC_DRIVER);

            // Get connection
            Connection connection = DriverManager.getConnection(JDBC_DB_URL);

            // Disable autocommit
            connection.setAutoCommit(false);

            // Create sequence for Car table
            Statement createCarSequenceStmt = connection.createStatement();
            createCarSequenceStmt.executeUpdate("CREATE SEQUENCE IF NOT EXISTS car_seq");

            // Create table
            Statement createTableStmt = connection.createStatement();
            createTableStmt.executeUpdate("CREATE TABLE IF NOT EXISTS car (" +
                    "id NUMBER default car_seq.nextval PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "plates VARCHAR(8)," +
                    "available BOOLEAN default TRUE " +
                    ")");


            createCar(connection, "Volvo XC60", "DW 3523E");
            createCar(connection, "BMW Z3", "DW 2343");

            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM car WHERE available=?");
            prepStatement.setBoolean(1, true);
            ResultSet resObj = prepStatement.executeQuery();

            while (resObj.next()) {
                System.out.println(String.format("CAR{%s, %s, %s, %b}", resObj.getString("id"), resObj.getString("name"), resObj.getString("plates"), resObj.getString("available")));
            }


            connection.commit();
            //connection.rollback();

            // TODO: close connection and prepared statements in finally block
            // Close connection
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void createCar(Connection connection, String name, String plates) throws SQLException {
        PreparedStatement createCarStmt = connection.prepareStatement("INSERT INTO car (name, plates) VALUES(?, ?)");
        createCarStmt.setString(1, name);
        createCarStmt.setString(2, plates);
        createCarStmt.executeUpdate();
    }
}
