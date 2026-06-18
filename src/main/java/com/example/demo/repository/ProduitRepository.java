package com.example.demo.repository;

import com.example.demo.db.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ProduitRepository {
  @Autowired private DatabaseConfig databaseConfig;

  public List<String> findAll() throws SQLException {
    List<String> produits = new ArrayList<>();
    String sql = "SELECT nom FROM produit";

    try (Connection conn = databaseConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        produits.add(rs.getString("nom"));
      }
    }
    return produits;
  }
}
