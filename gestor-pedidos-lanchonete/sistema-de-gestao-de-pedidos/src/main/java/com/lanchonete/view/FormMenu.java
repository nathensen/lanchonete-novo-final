package com.lanchonete.view;

import java.awt.*;
import javax.swing.*;

import com.lanchonete.controller.MenuController;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class FormMenu extends JPanel {

    private MainFrame mainFrame;
    private MenuController controller;

    private JButton btnNovoPedido, btnLanches, btnSalgadinhos, btnBebidas;
    private JButton btnVerPedido, btnBonus, btnStatus, btnEncerrarTurno;

    public FormMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new MenuController();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonte = new Font("SansSerif", Font.BOLD, 24);
        Color cor = Color.BLACK;

        // ---------------- ROW 0: TÍTULO ----------------
        JLabel lblTitulo = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        // ---------------- ROW 1 ----------------
        btnNovoPedido = criarBotao("Novo Pedido", fonte, cor);
        btnNovoPedido.addActionListener(e -> novoPedido());
        adicionar(gbc, btnNovoPedido, 0, 1);

        btnVerPedido = criarBotao("Ver Pedido Atual", fonte, cor);
        btnVerPedido.addActionListener(e -> mainFrame.showPanel("pedido"));
        adicionar(gbc, btnVerPedido, 1, 1);

        btnBonus = criarBotao("Bônus do Vendedor", fonte, cor);
        btnBonus.addActionListener(e -> controller.mostrarBonus(mainFrame.getVendedor()));
        adicionar(gbc, btnBonus, 2, 1);

        // ---------------- ROW 2 ----------------
        btnLanches = criarBotao("Lanches", fonte, cor);
        btnLanches.addActionListener(e -> mainFrame.showPanel("lanche"));
        btnLanches.setVisible(false);
        adicionar(gbc, btnLanches, 0, 2);

        btnSalgadinhos = criarBotao("Salgadinhos", fonte, cor);
        btnSalgadinhos.addActionListener(e -> mainFrame.showPanel("salgadinho"));
        btnSalgadinhos.setVisible(false);
        adicionar(gbc, btnSalgadinhos, 1, 2);

        btnBebidas = criarBotao("Bebidas", fonte, cor);
        btnBebidas.addActionListener(e -> mainFrame.showPanel("bebidas"));
        btnBebidas.setVisible(false);
        adicionar(gbc, btnBebidas, 2, 2);

        // ---------------- ROW 3 ----------------
        btnStatus = criarBotao("Status do Pedido", fonte, cor);
        btnStatus.addActionListener(e -> mainFrame.showPanel("status"));
        adicionar(gbc, btnStatus, 1, 3);

        // ---------------- ROW 4 ----------------
        btnEncerrarTurno = criarBotao("Encerrar Turno e Sair", fonte, cor);
        btnEncerrarTurno.addActionListener(e -> controller.encerrarTurno(mainFrame.getVendedor()));
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 3;
        add(btnEncerrarTurno, gbc);
    }

    private JButton criarBotao(String texto, Font font, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(font);
        btn.setForeground(color);
        btn.setPreferredSize(new Dimension(250, 60));
        return btn;
    }

    private void adicionar(GridBagConstraints gbc, JButton btn, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(btn, gbc);
    }

    private void novoPedido() {
        String nomeCliente = JOptionPane.showInputDialog(this, "Nome do cliente:");
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) return;

        Pedido pedido = controller.novoPedido(
                nomeCliente,
                mainFrame.getPedidoAtual(),
                mainFrame.getVendedor()
        );

        if (pedido != null) {
            btnLanches.setVisible(true);
            btnSalgadinhos.setVisible(true);
            btnBebidas.setVisible(true);

            mainFrame.setPedidoAtual(pedido);

            JOptionPane.showMessageDialog(this, "Pedido criado para: " + nomeCliente);
        }
    }
}
