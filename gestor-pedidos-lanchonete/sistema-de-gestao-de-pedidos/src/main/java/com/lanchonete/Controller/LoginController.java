package com.lanchonete.controller;

import javax.swing.JOptionPane;

import com.lanchonete.model.Vendedor;
import com.lanchonete.service.LoginService;
import com.lanchonete.view.MainFrame;

public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public void login(String nome, String senhaDigitada, MainFrame mainFrame) {

        try {
            Vendedor vendedor = loginService.autenticarELogar(nome, senhaDigitada);

            // Atualiza o MainFrame (sessão)
            mainFrame.setVendedor(vendedor);
            mainFrame.showPanel("menu");

            JOptionPane.showMessageDialog(null, "Bem-vindo(a), " + nome + "!");
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
