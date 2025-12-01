package com.lanchonete.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/lanchonete";
    private static final String USER = "admin";
    private static final String PASSWORD = "1234";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}





// ✅ ARQUIVO SQL

// phpMyAdmin → guia SQL → Executar

// -- Cria o banco se não existir
// CREATE DATABASE IF NOT EXISTS lanchonete;
// USE lanchonete;

// -- Cria o usuário ADMEQUIPE com senha 1234
// CREATE USER IF NOT EXISTS 'ADMEQUIPE'@'localhost' IDENTIFIED BY '1234';

// -- Dá todas as permissões no banco lanchonete
// GRANT ALL PRIVILEGES ON lanchonete.* TO 'ADMEQUIPE'@'localhost';

// -- Atualiza permissões
// FLUSH PRIVILEGES;

//----------------------------------------------------------//


// ✅ TABELAS
// -- Tabela de Vendedores
// CREATE TABLE vendedor (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     nome VARCHAR(100) NOT NULL,
//     bonus_acumulado DECIMAL(10,2) DEFAULT 0
// );

// -- Tabela de Pedidos
// CREATE TABLE pedido (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     nome_cliente VARCHAR(100) NOT NULL,
//     id_vendedor INT NOT NULL,
//     status_entrega VARCHAR(50) NOT NULL,
//     data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     FOREIGN KEY (id_vendedor) REFERENCES vendedor(id)
// );

// -- Tabela de Itens do Pedido (genérica para qualquer tipo de item)
// CREATE TABLE item_pedido (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     id_pedido INT NOT NULL,
//     tipo_item VARCHAR(50) NOT NULL, -- BEBIDA, LANCHE etc
//     item_id INT NOT NULL,           -- id do item específico
//     quantidade INT NOT NULL,
//     preco_unitario DECIMAL(10,2) NOT NULL,
//     FOREIGN KEY (id_pedido) REFERENCES pedido(id)
// );

// -- Tabela de Bebidas
// CREATE TABLE bebida (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     nome VARCHAR(100) NOT NULL,
//     preco DECIMAL(10,2) NOT NULL
// );

// -- Tabela de Lanches
// CREATE TABLE lanche (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     nome VARCHAR(100) NOT NULL,
//     preco DECIMAL(10,2) NOT NULL
// );

// -- Tabela de Porções
// CREATE TABLE porcao (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     nome VARCHAR(100) NOT NULL,
//     preco DECIMAL(10,2) NOT NULL
// );

// -- Tabela de Sobremesas
// CREATE TABLE sobremesa (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     nome VARCHAR(100) NOT NULL,
//     preco DECIMAL(10,2) NOT NULL
// );

// -- Tabela de Histórico de Pagamentos
// CREATE TABLE pagamento (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     id_pedido INT NOT NULL,
//     forma_pagamento VARCHAR(50) NOT NULL, -- DINHEIRO, PIX, CRÉDITO
//     valor_pago DECIMAL(10,2) NOT NULL,
//     troco DECIMAL(10,2) DEFAULT 0,
//     data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     FOREIGN KEY (id_pedido) REFERENCES pedido(id)
// );
