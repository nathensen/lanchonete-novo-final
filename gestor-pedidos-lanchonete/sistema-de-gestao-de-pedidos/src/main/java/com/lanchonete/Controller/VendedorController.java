package com.lanchonete.controller;

import javax.swing.JOptionPane;

import com.lanchonete.model.Vendedor;
import com.lanchonete.service.VendedorService;

public class VendedorController {

    private final VendedorService service;

    public VendedorController(VendedorService service) {
        this.service = service;
    }
    
    // Resumo final do turno
    public void mostrarResumoFinalTurno(int codigo) {
        Vendedor vendedor = service.buscarPorCodigo(codigo);

        JOptionPane.showMessageDialog(null,
                service.gerarResumoFinalTurno(vendedor),
                "Resumo do Turno", JOptionPane.INFORMATION_MESSAGE);
        }
 }
