package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.lanchonete.controller.LancheController;
import com.lanchonete.model.Lanche;

public class FormLanche extends JPanel {

    private MainFrame mainFrame;
    private LancheController controller;
    private JList<String> listLanches;
    private DefaultListModel<String> listModel;

    public FormLanche(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new LancheController(mainFrame);

        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha um Lanche");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        List<Lanche> lanches = controller.getLanches();
        listModel = new DefaultListModel<>();
        for (Lanche lanche : lanches) {
            listModel.addElement(lanche.descricao() + " - R$ " + String.format("%.2f", lanche.getPrecoVenda()));
        }

        listLanches = new JList<>(listModel);
        listLanches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listLanches.setFont(new Font("Arial", Font.BOLD, 27));
        JScrollPane scrollPane = new JScrollPane(listLanches);

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
            int selectedIndex = listLanches.getSelectedIndex();
            controller.adicionarLanche(selectedIndex);
        });
    }
}
