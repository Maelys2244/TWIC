package com.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DaoFactory {

	private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }

        DaoFactory instance = new DaoFactory(
        		"jdbc:mysql://mysql-molkky-courteldauce.alwaysdata.net:3306/molkky-courteldauce_twic", "259624_twic", "C2db5xb9!22");        
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


  
}
