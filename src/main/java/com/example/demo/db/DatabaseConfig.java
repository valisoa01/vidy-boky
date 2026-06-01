package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URl = System.getenv(("NEON_DB_URL"));
    private  static Connection getConnection() throws SQLException {
        if(URl == null) {
            throw new  IllegalAccessError(("NEON_DB_URL is not defined"));
        }
        return DriverManager.getConnection(URl);
    }
}
