package com.lanchonete.controller;

import javax.swing.JOptionPane;

import com.lanchonete.model.Vendedor;
import com.lanchonete.service.VendedorService;

public class VendedorController {

    private final VendedorService service;

    public VendedorController(VendedorService service) {
        this.service = service;
    }

    // Agora recebe diretamente o vendedor logado
    public void mostrarResumoFinalTurno(Vendedor vendedorLogado, double totalVendido) {

        String resumo = service.gerarResumoFinalTurno(vendedorLogado, totalVendido); // erro aqui

        JOptionPane.showMessageDialog(
                null,
                resumo,
                "Resumo do Turno",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}

