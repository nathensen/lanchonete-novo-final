package com.lanchonete;

import java.sql.Connection;

import com.lanchonete.database.Database;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

