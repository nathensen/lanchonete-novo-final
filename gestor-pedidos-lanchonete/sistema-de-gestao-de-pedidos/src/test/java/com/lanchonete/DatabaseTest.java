package com.lanchonete;

import java.sql.Connection;

import com.lanchonete.config.ConnectionFactory;

public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conectado com sucesso ao MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}