package com.lanchonete.controller;

import java.util.List;

import javax.swing.JOptionPane;

import com.lanchonete.model.Lanche;
import com.lanchonete.model.Pedido;
import com.lanchonete.repository.LancheRepository;
import com.lanchonete.view.MainFrame;

public class LancheController {

    private MainFrame mainFrame;
    private LancheRepository repository;

    public LancheController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.repository = new LancheRepository();
    }

    public List<Lanche> getLanches() {
        return repository.listarLanches();
    }

    public void adicionarLanche(int index) {
        Lanche lancheSelecionado = repository.getLanche(index);
        if (lancheSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Selecione um lanche primeiro.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pedido pedido = mainFrame.getPedidoAtual();
        if (pedido == null) {
            JOptionPane.showMessageDialog(null, "Você deve iniciar um novo pedido primeiro.", "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
            mainFrame.showPanel("menu");
            return;
        }

        if (pedido.getNomeCliente() == null || pedido.getNomeCliente().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o nome do cliente antes de adicionar produtos.", "Cliente não informado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        pedido.adicionarItem(lancheSelecionado);
        JOptionPane.showMessageDialog(null, lancheSelecionado.descricao() + " adicionado ao pedido com sucesso!", "Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
    }
}
