package com.lanchonete.service;

import java.util.Set;

import com.lanchonete.model.Vendedor;

public class LoginService {

    private static final Set<String> USUARIOS_PERMITIDOS = Set.of(
        "NATANE_HENSEN",
        "PATRICK_CAMPESTRINI",
        "THIAGO_BIAVATTI"
    );

    private static final String SENHA_FIXA = "1234";

    private final VendedorService vendedorService;

    public LoginService(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    /**
     * Valida credenciais e retorna o Vendedor autenticado.
     * Lança Exception com mensagem amigável em caso de falha.
     */
    public Vendedor autenticarELogar(String nome, String senhaDigitada) throws Exception {

        if (nome == null || nome.isBlank()) {
            throw new Exception("Digite o login.");
        }

        if (senhaDigitada == null || senhaDigitada.isBlank()) {
            throw new Exception("Digite a senha.");
        }

        if (!USUARIOS_PERMITIDOS.contains(nome)) {
            throw new Exception("Usuário não cadastrado!");
        }

        if (!SENHA_FIXA.equals(senhaDigitada)) {
            throw new Exception("Senha incorreta!");
        }

        // Cria e salva vendedor (mantendo compatibilidade com seu fluxo atual)
        Vendedor vendedor = vendedorService.criarVendedor(nome, 0);
        vendedorService.salvarVendedor(vendedor);

        return vendedor;
    }
}
