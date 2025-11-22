package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.lanchonete.controller.BebidaController;
import com.lanchonete.model.Bebidas;

public class FormBebidas extends JPanel {

    private MainFrame mainFrame;
    private BebidaController controller;

    private JList<String> listBebidas;
    private DefaultListModel<String> listModel;

    public FormBebidas(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new BebidaController();

        setLayout(new BorderLayout());

        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha uma Bebida");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        // Lista de bebidas
        List<Bebidas> bebidas = controller.getBebidas();
        String[] nomesBebidas = controller.getNomesBebidas();

        listModel = new DefaultListModel<>();
        for (int i = 0; i < bebidas.size(); i++) {
            Bebidas bebida = bebidas.get(i);
            listModel.addElement(nomesBebidas[i] + " - R$ " + String.format("%.2f", bebida.getPrecoVenda()));
        }

        listBebidas = new JList<>(listModel);
        listBebidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

        // Ações dos botões
        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));
        btnAdicionar.addActionListener(e -> adicionarBebida());
    }

    private void adicionarBebida() {
        int selectedIndex = listBebidas.getSelectedIndex();

        if (selectedIndex != -1) {
            if (mainFrame.getPedidoAtual() == null) {
                JOptionPane.showMessageDialog(this,
                        "Você deve iniciar um novo pedido primeiro.",
                        "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
                mainFrame.showPanel("menu");
                return;
            }

            controller.adicionarAoPedido(mainFrame.getPedidoAtual(), selectedIndex);

            String[] nomesBebidas = controller.getNomesBebidas();
            JOptionPane.showMessageDialog(this,
                    nomesBebidas[selectedIndex] + " adicionado(a) ao pedido com sucesso!",
                    "Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma bebida primeiro.",
                    "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }
}