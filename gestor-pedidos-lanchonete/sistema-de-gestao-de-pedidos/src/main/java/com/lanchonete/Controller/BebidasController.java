package com.lanchonete.controller;

import java.util.List;

import javax.swing.JOptionPane;

import com.lanchonete.model.Bebidas;
import com.lanchonete.model.Pedido;
import com.lanchonete.service.BebidasService;
import com.lanchonete.view.MainFrame;

public class BebidasController {

    private MainFrame mainFrame;
    private BebidasService service;

    public BebidasController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.service = new BebidasService();
    }

    public List<Bebidas> getBebidas() {
        return service.listarTodas();
    }

    public void adicionarBebida(int index) {

        Bebidas bebidaSelecionada = service.buscarPorIndex(index);

        if (bebidaSelecionada == null) {
            JOptionPane.showMessageDialog(
                null,
                "Selecione uma bebida primeiro.",
                "Nenhuma Seleção",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Pedido pedido = mainFrame.getPedidoAtual();

        if (pedido == null) {
            JOptionPane.showMessageDialog(
                null,
                "Você deve iniciar um novo pedido primeiro.",
                "Pedido não iniciado",
                JOptionPane.WARNING_MESSAGE
            );
            mainFrame.showPanel("menu");
            return;
        }

        if (pedido.getNomeCliente() == null || pedido.getNomeCliente().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Informe o nome do cliente antes de adicionar produtos.",
                "Cliente não informado",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean sucesso = service.adicionarBebidaAoPedido(pedido, bebidaSelecionada);

        if (sucesso) {
            JOptionPane.showMessageDialog(
                null,
                bebidaSelecionada.descricao() + " adicionada ao pedido com sucesso!",
                "Item Adicionado",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
