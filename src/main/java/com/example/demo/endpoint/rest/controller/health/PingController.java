package com.example.demo.endpoint.rest.controller.health;

import com.example.demo.db.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class PingController {

    @Autowired
    private DatabaseConfig databaseConfig;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/ping-db")
    public String pingDb() {
        try (Connection conn = databaseConfig.getConnection()) {
            return "pong-db";
        } catch (SQLException e) {
            return "error: " + e.getMessage();
        }
    }
}