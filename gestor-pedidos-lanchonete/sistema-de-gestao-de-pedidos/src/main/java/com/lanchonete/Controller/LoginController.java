package com.lanchonete.controller;

public class LoginController {

    private static final String SENHA_FIXA = "1234";

    public boolean autenticar(String nome, String codigoTexto) throws Exception {

        if (nome == null || nome.isBlank()) {
            throw new Exception("Digite o nome do vendedor.");
        }

        if (codigoTexto == null || codigoTexto.isBlank()) {
            throw new Exception("Digite a senha.");
        }

        if (!codigoTexto.equals(SENHA_FIXA)) {
            throw new Exception("Senha incorreta!.");
        }

        return true; // OK
    }
}
