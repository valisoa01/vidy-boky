package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {

  @Value("${spring.datasource.url}")
  private String neonUrl;

  @Value("${spring.datasource.username}")
  private String neonUsername;

  @Value("${spring.datasource.password}")
  private String neonPassword;

  public Connection getConnection() throws SQLException {
    if (neonUrl == null || neonUsername == null || neonPassword == null) {
      throw new IllegalStateException("Database env vars not defined: url, username, password");
    }
    return DriverManager.getConnection(neonUrl, neonUsername, neonPassword);
  }
}
