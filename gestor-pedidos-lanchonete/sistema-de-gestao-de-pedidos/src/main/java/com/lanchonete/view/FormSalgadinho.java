package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.lanchonete.controller.SalgadinhoController;
import com.lanchonete.model.Salgadinho;

public class FormSalgadinho extends JPanel {
    private MainFrame mainFrame;
    private SalgadinhoController controller;
    private JList<String> listSalgadinhos;

    public FormSalgadinho(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new SalgadinhoController(mainFrame);

        setLayout(new BorderLayout());

        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha um Salgado");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        // Lista de salgados
        List<Salgadinho> salgadinhos = controller.listarSalgadinhos();
        String[] opcoes = new String[salgadinhos.size()];
        for (int i = 0; i < salgadinhos.size(); i++) {
            Salgadinho s = salgadinhos.get(i);
            opcoes[i] = s.descricao() + " - R$ " + String.format("%.2f", s.getPrecoVenda());
        }

        listSalgadinhos = new JList<>(opcoes);
        listSalgadinhos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSalgadinhos.setFont(new Font("Arial", Font.BOLD, 27));
        JScrollPane scrollPane = new JScrollPane(listSalgadinhos);

        // Botões
        JPanel buttonPanel = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");
        JButton btnVoltar = new JButton("Voltar ao Menu");

        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnVoltar);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));

        btnAdicionar.addActionListener(e -> {
            int index = listSalgadinhos.getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um salgado primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean sucesso = controller.adicionarSalgadinhoAoPedido(index);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Item adicionado ao pedido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Você deve iniciar um pedido antes.", "Erro", JOptionPane.WARNING_MESSAGE);
                mainFrame.showPanel("menu");
            }
        });
    }
}