package com.lanchonete.controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;
import com.lanchonete.service.PedidoService;

public class MenuController {

    private PedidoService pedidoService = new PedidoService();

    public MenuController() {}

    public Pedido novoPedido(String nomeCliente, Pedido atual, Vendedor vendedor) {
        if (atual != null && !atual.getItensConsumidos().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Finalize o pedido atual antes de abrir outro!");
            return null;
        }

        return pedidoService.criarPedido(nomeCliente, vendedor);
    }

    public void mostrarBonus(Vendedor vendedor) {
        if (vendedor == null) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum vendedor encontrado!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String msg = "Bônus acumulado: R$ " + String.format("%.2f", vendedor.getBonus());
        JOptionPane.showMessageDialog(null, msg, "Bônus", JOptionPane.INFORMATION_MESSAGE);
    }

    public void encerrarTurno(Vendedor vendedor, JFrame frame) {

        String resumo =
                "=== ENCERRAMENTO DE TURNO ===\n\n" +
                "Vendedor: " + vendedor.getNome() + "\n" +
                "Total Vendido: R$ " + String.format("%.2f", vendedor.getTotalVendido()) + "\n" +
                "Bônus (5%): R$ " + String.format("%.2f", vendedor.getBonus()) + "\n";

        JOptionPane.showMessageDialog(
                null,
                resumo,
                "Resumo Final",
                JOptionPane.INFORMATION_MESSAGE
        );

        vendedor.resetarTurno();

        frame.dispose();
        System.exit(0);
    }


}
