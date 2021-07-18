package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {


    private static String CONNECTION_USERNAME;
    private static String CONNECTION_PASSWORD;
    private static  String CONNECTION_URL;

    private static Connection connection;

    static{
        //get the environment variables from the config.properties file
        try{
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(input);

            String CONNECTION_USERNAME_VAR = properties.getProperty("CONNECTION_USERNAME");
            String CONNECTION_PASSWORD_VAR = properties.getProperty("CONNECTION_PASSWORD");
            String CONNECTION_URL_VAR = properties.getProperty("CONNECTION_URL");

            CONNECTION_USERNAME = System.getenv(CONNECTION_USERNAME_VAR);
            CONNECTION_PASSWORD = System.getenv(CONNECTION_PASSWORD_VAR);
            CONNECTION_URL = System.getenv(CONNECTION_URL_VAR);

            input.close();

        } catch(IOException ex) {
            System.out.println("Failed to load properties from file.");
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex){
            System.out.println("Could not register driver.");
        }
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD );
        }
        return  connection;
    }
}
