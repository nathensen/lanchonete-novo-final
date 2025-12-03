package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.lanchonete.controller.BebidasController;
import com.lanchonete.model.Bebidas;

public class FormBebidas extends JPanel {

    private MainFrame mainFrame;
    private JList<String> listBebidas;
    private DefaultListModel<String> listModel;
    private BebidasController controller;

    public FormBebidas(MainFrame mainFrame, BebidasController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new BorderLayout());

        // Painel de título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha uma Bebida");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        // Lista de bebidas
        List<Bebidas> bebidas = controller.getBebidas();
        listModel = new DefaultListModel<>();

        for (Bebidas bebida : bebidas) {
            listModel.addElement(bebida.descricao() + " - R$ " + String.format("%.2f", bebida.getPrecoVenda()));
        }

        listBebidas = new JList<>(listModel);
        listBebidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listBebidas.setFont(new Font("Arial", Font.BOLD, 27));

        JScrollPane scrollPane = new JScrollPane(listBebidas);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");
        JButton btnVoltar = new JButton("Voltar ao Menu");

        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnVoltar);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));

        btnAdicionar.addActionListener(e -> {
            int index = listBebidas.getSelectedIndex();
            controller.adicionarBebida(index);
        });
    }
}

