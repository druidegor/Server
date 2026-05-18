package org.example.tasktraker.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream("server.properties")) {
            properties.load(input);
            System.out.println("Конфигурация успешно загружена.");
        } catch (IOException ex) {
            System.err.println("Не удалось найти server.properties. Используются настройки по умолчанию.");
            properties.setProperty("server.port", "8080");
            properties.setProperty("db.url", "jdbc:mysql://localhost:3306/tracker");
            properties.setProperty("db.user", "root");
            properties.setProperty("db.password", "1234");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}