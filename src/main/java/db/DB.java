package db;

import model.entities.Seller;
import ranslm_ware.com.db.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static int rowsAffected;
    private static PreparedStatement st = null;
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

    public static int getRowsAffected() {
        return rowsAffected;
    }

    public static void setRowsAffected(int rowsAffected) {
        DB.rowsAffected = rowsAffected;
    }

    public static PreparedStatement getSt() {
        return st;
    }

    public static void setSt(PreparedStatement st) {
        DB.st = st;
    }

    public static void methodTest(Seller obj){

       try {
           if (rowsAffected > 0) {
             ResultSet rs = st.getGeneratedKeys();
               if (rs.next()) {
                   int id = rs.getInt(1);
                   obj.setId(id);
               }
               DB.closeResultSet(rs);
           }
       } catch (SQLException e){
           throw new DbException(e.getMessage());
       }
    }
}
