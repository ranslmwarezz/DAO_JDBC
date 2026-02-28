package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection(){

        if(conn == null){
            try {
                Properties properties = DB.properties();
                String url = properties.getProperty("dburl");
               conn = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                throw new ranslm_ware.com.db.DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn != null){
            try{
                conn.close();
                conn = null;
            } catch (SQLException e) {
                throw new ranslm_ware.com.db.DbException(e.getMessage());
            }
        }
    }
    public static void closeStatement(Statement statement){
        if(statement != null){
            try{
                statement.close();
            } catch (SQLException e) {
                throw new ranslm_ware.com.db.DbException(e.getMessage());
            }
        }

    }
    public static void closeResultSet(ResultSet resultSet){
        if(resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e) {
                throw new ranslm_ware.com.db.DbException(e.getMessage());
            }
        }

    }


    private static Properties properties(){

        try(FileInputStream fs = new FileInputStream("src/main/resources/db.properties")){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        } catch (IOException e) {
            throw new ranslm_ware.com.db.DbException(e.getMessage());
        }
    }
}
