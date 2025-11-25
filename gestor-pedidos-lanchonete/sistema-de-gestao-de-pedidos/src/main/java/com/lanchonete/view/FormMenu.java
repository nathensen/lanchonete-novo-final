package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

        // Painel de boas-vindas
        JPanel welcomePanel = new JPanel();
        JLabel lblWelcome = new JLabel("Menu Principal");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(lblWelcome);

        // Painel de botões de categoria
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Botões
        buttonPanel.add(criarBotao("Novo Pedido", e -> novoPedido()));
        buttonPanel.add(criarBotao("Lanches", e -> mainFrame.showPanel("lanche")));
        buttonPanel.add(criarBotao("Salgadinhos", e -> mainFrame.showPanel("salgadinho")));
        buttonPanel.add(criarBotao("Bebidas", e -> mainFrame.showPanel("bebida")));
        buttonPanel.add(criarBotao("Ver Pedido Atual", e -> mainFrame.showPanel("pedido")));
        buttonPanel.add(criarBotao("Ver Bônus do Vendedor", e -> mostrarBonus()));

        // Painel inferior
        JPanel bottomPanel = new JPanel();
        JButton btnLogout = new JButton("Encerrar Turno e Sair");
        btnLogout.addActionListener(e -> encerrarTurno());
        bottomPanel.add(btnLogout);

        // Montagem final
        add(welcomePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color corTopo = new Color(0, 100, 0);
                Color corBase = new Color(0, 150, 0);

                GradientPaint gradiente = new GradientPaint(
                        0, 0, corTopo,
                        0, getHeight(), corBase
                );

                g2.setPaint(gradiente);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            public void updateUI() {
                setUI(new javax.swing.plaf.basic.BasicButtonUI());
            }
        };

        btn.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.addActionListener(listener);

        return btn;
    }

    private void novoPedido() {
        String nomeCliente = javax.swing.JOptionPane.showInputDialog(this,
                "Nome do cliente:",
                "Novo Pedido",
                javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            return;
        }

        Pedido pedido = controller.novoPedido(nomeCliente,
                mainFrame.getPedidoAtual(),
                mainFrame.getVendedor()); // passa também o vendedor logado

        if (pedido != null) {
            mainFrame.setPedidoAtual(pedido);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Pedido criado para: " + nomeCliente,
                    "Pedido Iniciado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
