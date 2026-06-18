package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  public Connection getConnection() throws SQLException {
    if (url == null || username == null || password == null) {
      throw new IllegalStateException("Database env vars not defined: url, username, password");
    }
    return DriverManager.getConnection(url, username, password);
  }
}
