package testTask.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

//    private static String url = "jdbc:h2:mem:dbname";
    private static String url = "jdbc:postgresql://localhost:5432/PTMK";
    private static String user = "postgres";
    private static String password = "1234";
    private static Connection connection = null;

    public static Connection getConnection(){
        if (connection == null)
            createConnection();
        return connection;
    }

    public static Connection getConnection(String url, String user, String password) {
        if (connection == null) {
            ConnectionFactory.url = url;
            ConnectionFactory.user = user;
            ConnectionFactory.password = password;
            createConnection();
        }

        return connection;
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
