package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
public class DatabaseInitializer {

  
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "P@ssw0rd123";

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                Statement stmt = conn.createStatement();
                
                // Criar tabela users
                String createTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(100),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
                stmt.execute(createTable);
                System.out.println("✅ Tabela 'users' criada com sucesso!");
                
                // Inserir dados de exemplo
                String insertData = """
                    INSERT INTO users (username, password, email) VALUES 
                        ('admin', 'admin123', 'admin@example.com'),
                        ('user1', 'pass123', 'user1@example.com'),
                        ('testuser', 'test123', 'test@example.com'),
                        ('john_doe', 'john123', 'john@example.com')
                """;
                stmt.execute(insertData);
                System.out.println("✅ Dados de exemplo inseridos com sucesso!");
                System.out.println("✅ Banco de dados H2 inicializado!");
                
            } catch (Exception e) {
                System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}

