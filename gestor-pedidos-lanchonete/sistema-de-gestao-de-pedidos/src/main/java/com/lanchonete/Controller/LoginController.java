package com.lanchonete.controller;

import javax.swing.JOptionPane;

import com.lanchonete.model.Vendedor;
import com.lanchonete.service.VendedorService;
import com.lanchonete.view.MainFrame;

/**
 * LoginController
 * ----------------
 * Responsável por:
 *  - Validar dados do login
 *  - Criar o vendedor
 *  - Salvar no repository
 *  - Atualizar o MainFrame (setar vendedor e trocar tela)
 *
 * A VIEW (FormLogin) chama apenas o método login().
 */
public class LoginController {

    private static final String SENHA_FIXA = "1234";

    private final VendedorService vendedorService;

    public LoginController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    /**
     * Método principal chamado pela View.
     * Ele realiza o login COMPLETO:
     *  1. Valida campos
     *  2. Verifica senha
     *  3. Cria vendedor
     *  4. Salva no repository
     *  5. Atualiza o MainFrame
     */
    public void login(String nome, String senhaDigitada, MainFrame mainFrame) throws Exception {

        autenticar(nome, senhaDigitada);

        // Cria e salva vendedor
        Vendedor vendedor = vendedorService.criarVendedor(nome, 0);
        vendedorService.salvarVendedor(vendedor);

        // Atualiza o MainFrame
        mainFrame.setVendedor(vendedor);
        mainFrame.showPanel("menu");

        JOptionPane.showMessageDialog(null,
                "Bem-vindo(a), " + nome + "!");
    }

    /**
     * Valida nome + senha.
     */
    public void autenticar(String nome, String senhaDigitada) throws Exception {

        if (nome == null || nome.isBlank()) {
            throw new Exception("Digite o nome do vendedor.");
        }

        if (senhaDigitada == null || senhaDigitada.isBlank()) {
            throw new Exception("Digite a senha.");
        }

        if (!senhaDigitada.equals(SENHA_FIXA)) {
            throw new Exception("Senha incorreta!");
        }
    }
}
