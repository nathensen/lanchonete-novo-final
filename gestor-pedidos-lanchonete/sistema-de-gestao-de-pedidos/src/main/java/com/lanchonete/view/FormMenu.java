package com.lanchonete.view;

import java.awt.*;
import javax.swing.*;

import com.lanchonete.controller.MenuController;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class FormMenu extends JPanel {

    private MainFrame mainFrame;
    private MenuController controller;

    public FormMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new MenuController();
        setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel();
        JLabel lblWelcome = new JLabel("Menu Principal");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(lblWelcome);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        buttonPanel.add(criarBotao("Novo Pedido", e -> novoPedido()));
        buttonPanel.add(criarBotao("Lanches", e -> mainFrame.showPanel("lanche")));
        buttonPanel.add(criarBotao("Salgadinhos", e -> mainFrame.showPanel("salgadinho")));
        buttonPanel.add(criarBotao("Bebidas", e -> mainFrame.showPanel("bebida")));
        buttonPanel.add(criarBotao("Ver Pedido Atual", e -> mainFrame.showPanel("pedido")));
        buttonPanel.add(criarBotao("Ver Bônus do Vendedor", e -> mostrarBonus()));
        buttonPanel.add(criarBotao("Status do Pedido", e -> mainFrame.showPanel("status"))); // NOVO

        JPanel bottomPanel = new JPanel();
        JButton btnLogout = new JButton("Encerrar Turno e Sair");
        btnLogout.addActionListener(e -> encerrarTurno());
        bottomPanel.add(btnLogout);

        add(welcomePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn.setForeground(Color.BLACK);
        btn.addActionListener(listener);
        return btn;
    }

    private void novoPedido() {
        String nomeCliente = JOptionPane.showInputDialog(this, "Nome do cliente:", "Novo Pedido", JOptionPane.QUESTION_MESSAGE);
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) return;
        Pedido pedido = controller.novoPedido(nomeCliente, mainFrame.getPedidoAtual(), mainFrame.getVendedor());
        if (pedido != null) {
            mainFrame.setPedidoAtual(pedido);
            JOptionPane.showMessageDialog(this, "Pedido criado para: " + nomeCliente, "Pedido Iniciado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarBonus() {
        Vendedor vendedor = mainFrame.getVendedor();
        controller.mostrarBonus(vendedor);
    }

    private void encerrarTurno() {
        Vendedor vendedor = mainFrame.getVendedor();
        controller.encerrarTurno(vendedor);
    }
}
