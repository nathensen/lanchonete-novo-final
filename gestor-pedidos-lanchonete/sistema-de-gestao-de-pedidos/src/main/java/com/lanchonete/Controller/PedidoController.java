package com.lanchonete.controller;

import javax.swing.JOptionPane;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;
import com.lanchonete.view.MainFrame;

public class PedidoController {

    private MainFrame mainFrame;

    public PedidoController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public boolean finalizarPedido(String valorPagoTexto) {
        Pedido pedido = mainFrame.getPedidoAtual();

        if (pedido == null) {
            JOptionPane.showMessageDialog(null,
                    "Não há pedido em andamento.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (pedido.getItensConsumidos().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "O pedido não possui itens.",
                    "Pedido Vazio", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        double valorPago;

        try {
            valorPago = Double.parseDouble(valorPagoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Por favor, digite um valor numérico válido.",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return false; // Sai sem apagar o pedido
        }

        double total = pedido.calcularTotal();

        if (valorPago < total) {
            JOptionPane.showMessageDialog(null,
                    "O valor pago é insuficiente. Total: R$ " + String.format("%.2f", total),
                    "Valor Insuficiente", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Calcular troco e bônus
        double troco = valorPago - total;
        Vendedor vendedor = mainFrame.getVendedor();
        double bonus = vendedor.calcularBonus(total);

        JOptionPane.showMessageDialog(null,
                "Pedido finalizado com sucesso!\n\n" +
                "Total: R$ " + String.format("%.2f", total) + "\n" +
                "Valor pago: R$ " + String.format("%.2f", valorPago) + "\n" +
                "Troco: R$ " + String.format("%.2f", troco) + "\n" +
                "Bônus do vendedor: R$ " + String.format("%.2f", bonus),
                "Pedido Finalizado", JOptionPane.INFORMATION_MESSAGE);

        // Limpar o pedido atual
        mainFrame.setPedidoAtual(null);
        return true;
    }
}
