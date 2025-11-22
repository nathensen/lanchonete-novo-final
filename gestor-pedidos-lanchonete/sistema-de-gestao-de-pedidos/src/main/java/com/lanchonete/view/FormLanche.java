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

import com.lanchonete.controller.LancheController;
import com.lanchonete.model.Lanche;

public class FormLanche extends JPanel {

    private MainFrame mainFrame;
    private LancheController controller;

    private JList<String> listLanches;
    private DefaultListModel<String> listModel;

    private static final String[] NOMES_LANCHES = {
        "Misto", "Hambúrguer", "X-Burger", "X-Eggs", "Duplo", "X-Calabresa",
        "X-Eggs Calabresa", "X-Bacon", "X-Eggs Bacon", "X-Duplo com bacon",
        "X-Bacon com Calabresa", "X-Tudo"
    };

    public FormLanche(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new LancheController();

        setLayout(new BorderLayout());

        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha um Lanche");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        // Cria o modelo da lista
        listModel = new DefaultListModel<>();
        List<Lanche> lanches = controller.getLanches();
        for (int i = 0; i < lanches.size(); i++) {
            Lanche lanche = lanches.get(i);
            listModel.addElement(NOMES_LANCHES[i] + " - R$ " + String.format("%.2f", lanche.getPrecoVenda()));
        }

        listLanches = new JList<>(listModel);
        listLanches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listLanches);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnVoltar);

        // Adiciona componentes ao painel
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionarLanche());
        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));
    }

    private void adicionarLanche() {
        int selectedIndex = listLanches.getSelectedIndex();

        if (selectedIndex != -1) {
            if (mainFrame.getPedidoAtual() == null) {
                JOptionPane.showMessageDialog(this,
                        "Você deve iniciar um novo pedido primeiro.",
                        "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
                mainFrame.showPanel("menu");
                return;
            }

            // Usa o controller para adicionar
            controller.adicionarAoPedido(mainFrame.getPedidoAtual(), selectedIndex);

            JOptionPane.showMessageDialog(this,NOMES_LANCHES[selectedIndex] + " adicionado ao pedido com sucesso!","Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,"Selecione um lanche primeiro.","Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }
}
