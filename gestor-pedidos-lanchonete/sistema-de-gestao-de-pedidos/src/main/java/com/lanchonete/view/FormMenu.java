package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lanchonete.controller.MenuController;
import com.lanchonete.model.Pedido;

public class FormMenu extends JPanel {

    private MainFrame mainFrame;
    private MenuController controller;

    private JButton btnNovoPedido, btnLanches, btnSalgadinhos, btnBebidas;
    private JButton btnVerPedido, btnBonus, btnStatus, btnEncerrarTurno;

    public FormMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new MenuController();

        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // =========================
        // PAINEL SUPERIOR (TÍTULO)
        // =========================
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTopo.setOpaque(false);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // perto da borda superior

        JLabel lblTitulo = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.BLACK);
        painelTopo.add(lblTitulo);

        add(painelTopo, BorderLayout.NORTH);

        // =========================
        // PAINEL CENTRAL (MENU)
        // =========================
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonte = new Font("SansSerif", Font.BOLD, 24);
        Color cor = Color.BLACK;

        // ---------- ROW 1 ----------
        btnNovoPedido = criarBotao("Novo Pedido", fonte, cor);
        btnNovoPedido.addActionListener(e -> novoPedido());
        adicionar(painelCentro, gbc, btnNovoPedido, 0, 0);

        btnVerPedido = criarBotao("Ver Pedido Atual", fonte, cor);
        btnVerPedido.addActionListener(e -> mainFrame.showPanel("pedido"));
        adicionar(painelCentro, gbc, btnVerPedido, 1, 0);

        btnBonus = criarBotao("Bônus do Vendedor", fonte, cor);
        btnBonus.addActionListener(e -> controller.mostrarBonus(mainFrame.getVendedor()));
        adicionar(painelCentro, gbc, btnBonus, 2, 0);

        // ---------- ROW 2 ----------
        btnLanches = criarBotao("Lanches", fonte, cor);
        btnLanches.addActionListener(e -> mainFrame.showPanel("lanche"));
        btnLanches.setVisible(false);
        adicionar(painelCentro, gbc, btnLanches, 0, 1);

        btnSalgadinhos = criarBotao("Salgadinhos", fonte, cor);
        btnSalgadinhos.addActionListener(e -> mainFrame.showPanel("salgadinho"));
        btnSalgadinhos.setVisible(false);
        adicionar(painelCentro, gbc, btnSalgadinhos, 1, 1);

        btnBebidas = criarBotao("Bebidas", fonte, cor);
        btnBebidas.addActionListener(e -> mainFrame.showPanel("bebidas"));
        btnBebidas.setVisible(false);
        adicionar(painelCentro, gbc, btnBebidas, 2, 1);

        // ---------- ROW 3 ----------
        btnStatus = criarBotao("Status do Pedido", fonte, cor);
        btnStatus.addActionListener(e -> mainFrame.showPanel("status"));
        adicionar(painelCentro, gbc, btnStatus, 1, 2);

        add(painelCentro, BorderLayout.CENTER);

        // =========================
        // PAINEL INFERIOR (RODAPÉ)
        // =========================
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRodape.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        btnEncerrarTurno = new JButton("Encerrar Turno e Sair");
        btnEncerrarTurno.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnEncerrarTurno.setForeground(Color.WHITE);
        btnEncerrarTurno.setBackground(Color.RED);
        btnEncerrarTurno.setOpaque(true);
        btnEncerrarTurno.setBorderPainted(false);
        btnEncerrarTurno.setPreferredSize(new Dimension(250, 45));
        btnEncerrarTurno.addActionListener(e -> controller.encerrarTurno(mainFrame.getVendedor()));

        // Hover do botão
        btnEncerrarTurno.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEncerrarTurno.setBackground(new Color(200, 0, 0));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEncerrarTurno.setBackground(Color.RED);
            }
        });

        painelRodape.add(btnEncerrarTurno);
        add(painelRodape, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String texto, Font font, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(font);
        btn.setForeground(color);
        btn.setPreferredSize(new Dimension(310, 70));
        return btn;
    }

    private void adicionar(JPanel painel, GridBagConstraints gbc, JButton btn, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weighty = 0;
        painel.add(btn, gbc);
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
