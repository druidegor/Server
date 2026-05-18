package org.example.tasktraker.data;

import org.example.tasktraker.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Database() {}

    private static final String URL = Config.getProperty("db.url");
    private static final String USER = Config.getProperty("db.user");
    private static final String PASSWORD = Config.getProperty("db.password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
