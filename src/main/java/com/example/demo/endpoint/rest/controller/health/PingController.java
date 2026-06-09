package com.example.demo.endpoint.rest.controller.health;

import com.example.demo.db.DatabaseConfig;
import java.sql.Connection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

  public static final ResponseEntity<String> OK = ResponseEntity.ok("OK");

  @GetMapping("/ping")
  public ResponseEntity<String> ping() {
    return OK;
  }

  @GetMapping("/ping-db")
  public ResponseEntity<String> pingDb() {
    try (Connection conn = DatabaseConfig.getConnection()) {
      return ResponseEntity.ok("Connexion Neon OK !");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
    }
  }
}
