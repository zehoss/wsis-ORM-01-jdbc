package pl.blackfernsoft.wsis.orm.jdbc.daoexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static final String JDBC_DRIVER = "org.h2.Driver";

    // ./test stores db file in current dir (classpath)
    // ~/test will store db file in home dir
    public static final String JDBC_DB_URL = "jdbc:h2:./test";

    private static ConnectionManager instance;

    private final Connection connection;

    private ConnectionManager() throws ClassNotFoundException, SQLException {

        // Load driver
        Class.forName(JDBC_DRIVER);

        // Get connection
        connection = DriverManager.getConnection(JDBC_DB_URL);

        // Disable autocommit
//        connection.setAutoCommit(false);
    }

    public static ConnectionManager getConnection() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public void close() throws SQLException {
        connection.close();
    }

    public Connection connection() {
        return connection;
    }

}
