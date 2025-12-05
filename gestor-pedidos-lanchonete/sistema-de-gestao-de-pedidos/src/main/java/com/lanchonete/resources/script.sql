DROP DATABASE IF EXISTS db_sa_6;
CREATE DATABASE db_sa_6;
USE db_sa_6;

CREATE TABLE vendedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    total_vendido DOUBLE DEFAULT 0,
    bonus DOUBLE DEFAULT 0
);

CREATE TABLE lanche (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100),
    recheio VARCHAR(100),
    pao VARCHAR(100),
    molho VARCHAR(100),
    preco DOUBLE NOT NULL
);

CREATE TABLE salgadinho (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100),
    massa VARCHAR(100),
    recheio VARCHAR(100),
    preco DOUBLE NOT NULL
);

CREATE TABLE bebidas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100),
    sabor VARCHAR(100),
    tamanho VARCHAR(50),
    preco DOUBLE NOT NULL
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_cliente VARCHAR(100) NOT NULL,
    vendedor_id INT NOT NULL,
    status_entrega VARCHAR(30) DEFAULT 'em_producao',
    total DOUBLE DEFAULT 0,
    FOREIGN KEY (vendedor_id) REFERENCES vendedor(id)
);

CREATE TABLE pedido_itens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    item_tipo ENUM('lanche', 'salgadinho', 'bebidas') NOT NULL,
    item_id INT NOT NULL,
    quantidade INT DEFAULT 1,

    FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);
