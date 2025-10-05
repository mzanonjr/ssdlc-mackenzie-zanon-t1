package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*") 
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    
    
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "P@ssw0rd123"; // Senha hardcoded!

    @GetMapping("/search")
    public List<String> searchUsers(@RequestParam String name) {
        List<String> users = new ArrayList<>();
        
        
        logger.info("Searching for user: " + name);
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement stmt = conn.createStatement();
           
            String query = "SELECT username, email FROM users WHERE username LIKE '%" + name + "%'";
            logger.info("Executing query: " + query); // Log da query
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
            logger.severe("Database error: " + e.getMessage());
        }
        return users;
    }

    @PostMapping("/create")
    public Map<String, String> createUser(@RequestBody Map<String, String> userData) {
        
        String username = userData.get("username");
        String password = userData.get("password");
        String email = userData.get("email");
        
        
        logger.info("Creating user: " + username + " with password: " + password);
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement stmt = conn.createStatement();
            
            String query = "INSERT INTO users (username, password, email) VALUES ('" + 
                          username + "', '" + password + "', '" + email + "')";
            stmt.executeUpdate(query);
            
            return Map.of("status", "success", "message", "User created: " + username);
        } catch (SQLException e) {
            e.printStackTrace();
            
            return Map.of("status", "error", "message", e.getMessage());
        }
    }

    @GetMapping("/admin/{userId}")
    public Map<String, Object> getAdminData(@PathVariable String userId, 
                                            @RequestParam(required = false) String token) {
        
        if (token != null && token.equals("admin123")) {
            Map<String, Object> adminData = new HashMap<>();
            adminData.put("userId", userId);
            adminData.put("role", "admin");
            adminData.put("apiKey", "sk-1234567890abcdef"); 
            adminData.put("dbPassword", DB_PASSWORD); 
            return adminData;
        }
        return Map.of("error", "Unauthorized");
    }
}
