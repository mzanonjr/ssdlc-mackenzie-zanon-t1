-- Schema para inicialização do banco H2
-- Este arquivo é executado automaticamente pelo Spring Boot

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Dados de exemplo para testes
INSERT INTO users (username, password, email) VALUES 
    ('admin', 'admin123', 'admin@example.com'),
    ('user1', 'pass123', 'user1@example.com'),
    ('testuser', 'test123', 'test@example.com'),
    ('john_doe', 'john123', 'john@example.com');

