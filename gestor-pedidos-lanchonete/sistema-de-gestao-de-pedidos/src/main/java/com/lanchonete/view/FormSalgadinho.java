package com.lanchonete.view;

import javax.swing.*;
import java.awt.*;
import com.lanchonete.controller.SalgadinhoController;
import com.lanchonete.model.Pedido;

public class FormSalgadinho extends JPanel {
    private MainFrame mainFrame;
    private JList<String> listSalgadinhos;
    private SalgadinhoController controller;

    public FormSalgadinho(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new SalgadinhoController();

        setLayout(new BorderLayout());

        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha um Salgadinho");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        // Lista de opções
        listSalgadinhos = new JList<>(controller.getNomesSalgadinhos());
        listSalgadinhos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listSalgadinhos);

        // Botões
        JPanel buttonPanel = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnVoltar);

        // Layout
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações
        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));
        btnAdicionar.addActionListener(e -> adicionarSalgadinho());
    }

    private void adicionarSalgadinho() {
        int index = listSalgadinhos.getSelectedIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um salgadinho primeiro.",
                    "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pedido pedido = mainFrame.getPedidoAtual();
        if (pedido == null) {
            JOptionPane.showMessageDialog(this, "Você deve iniciar um novo pedido primeiro.",
                    "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
            mainFrame.showPanel("menu");
            return;
        }

        controller.adicionarAoPedido(pedido, index);

        JOptionPane.showMessageDialog(this,
                controller.getNomesSalgadinhos()[index] + " adicionado ao pedido com sucesso!",
                "Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
    }
}
