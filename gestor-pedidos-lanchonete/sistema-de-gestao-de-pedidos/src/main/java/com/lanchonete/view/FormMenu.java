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

        // Painel de boas-vindas
        JPanel welcomePanel = new JPanel();
        JLabel lblWelcome = new JLabel("Menu Principal");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(lblWelcome);

        // Painel de botões com layout absoluto
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null); // permite posicionamento manual

        // Fonte e cores compartilhadas
        Font botaoFonte = new Font("SansSerif", Font.BOLD, 26);
        Color botaoCor = Color.BLACK;

       // Cada botão é separado, com tamanho e posição definidos
        JButton btnNovoPedido = new JButton("Novo Pedido");
        btnNovoPedido.setFont(botaoFonte);
        btnNovoPedido.setForeground(botaoCor);
        btnNovoPedido.addActionListener(e -> novoPedido());
        btnNovoPedido.setBounds(20, 20, 250, 70); // x, y, largura, altura
        buttonPanel.add(btnNovoPedido);

        JButton btnLanches = new JButton("Lanches");
        btnLanches.setFont(botaoFonte);
        btnLanches.setForeground(botaoCor);
        btnLanches.addActionListener(e -> mainFrame.showPanel("lanche"));
        btnLanches.setBounds(580, 110, 250, 70);
        buttonPanel.add(btnLanches);

        JButton btnSalgadinhos = new JButton("Salgadinhos");
        btnSalgadinhos.setFont(botaoFonte);
        btnSalgadinhos.setForeground(botaoCor);
        btnSalgadinhos.addActionListener(e -> mainFrame.showPanel("salgadinho"));
        btnSalgadinhos.setBounds(300, 110, 250, 70);
        buttonPanel.add(btnSalgadinhos);

        JButton btnBebidas = new JButton("Bebidas");
        btnBebidas.setFont(botaoFonte);
        btnBebidas.setForeground(botaoCor);
        btnBebidas.addActionListener(e -> mainFrame.showPanel("bebida"));
        btnBebidas.setBounds(20, 110, 250, 70);
        buttonPanel.add(btnBebidas);

        JButton btnVerPedido = new JButton("Ver Pedido Atual");
        btnVerPedido.setFont(botaoFonte);
        btnVerPedido.setForeground(botaoCor);
        btnVerPedido.addActionListener(e -> mainFrame.showPanel("pedido"));
        btnVerPedido.setBounds(300, 20, 250, 70);
        buttonPanel.add(btnVerPedido);

        JButton btnBonus = new JButton("Bônus do Vendedor");
        btnBonus.setFont(botaoFonte);
        btnBonus.setForeground(botaoCor);
        btnBonus.addActionListener(e -> mostrarBonus());
        btnBonus.setBounds(580, 20, 250, 70); 
        buttonPanel.add(btnBonus);

        JButton btnStatus = new JButton("Status do Pedido");
        btnStatus.setFont(botaoFonte);
        btnStatus.setForeground(botaoCor);
        btnStatus.addActionListener(e -> mainFrame.showPanel("status"));
        btnStatus.setBounds(300, 200, 250, 70);
        buttonPanel.add(btnStatus);

        // Painel inferior com botão de logout
        JPanel bottomPanel = new JPanel();
        JButton btnLogout = new JButton("Encerrar Turno e Sair");
        btnLogout.setFont(botaoFonte);
        btnLogout.setForeground(botaoCor);
        btnLogout.addActionListener(e -> encerrarTurno());
        bottomPanel.add(btnLogout);

        add(welcomePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
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
