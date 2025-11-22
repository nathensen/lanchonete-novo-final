package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lanchonete.model.Vendedor;
import com.lanchonete.service.VendedorService;

public class FormLogin extends JPanel {

    private MainFrame mainFrame;
    private VendedorService vendedorService;

    private JTextField txtNome;
    private JTextField txtCodigo;
    private JButton btnEntrar;

    public FormLogin(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.vendedorService = new VendedorService();

        // Fundo branco
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Sistema de Lanchonete");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitle.setForeground(Color.BLACK);

        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Formulário central
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        JLabel lblNome = new JLabel("Nome do Vendedor:");
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblNome.setForeground(Color.BLACK);

        JLabel lblCodigo = new JLabel("Código do Vendedor (4 dígitos):");
        lblCodigo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblCodigo.setForeground(Color.BLACK);

        // Campos
        txtNome = new JTextField(18);
        txtNome.setFont(new Font("SansSerif", Font.PLAIN, 16));

        txtCodigo = new JTextField(18);
        txtCodigo.setFont(new Font("SansSerif", Font.PLAIN, 16));

        // Adiciona os campos
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblNome, gbc);

        gbc.gridx = 1;
        formPanel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblCodigo, gbc);

        gbc.gridx = 1;
        formPanel.add(txtCodigo, gbc);

        // Botão estilizado igual ao menu
        btnEntrar = criarBotao("Entrar no Sistema", e -> login());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(btnEntrar, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    // ---- BOTÃO IGUAL AO MENU ----
    private JButton criarBotao(String texto, java.awt.event.ActionListener listener) {

        JButton btn = new JButton(texto) {

            Color topo = new Color(0, 100, 0);
            Color base = new Color(0, 150, 0);

            Color topoHover = new Color(0, 70, 0);
            Color baseHover = new Color(0, 110, 0);

            boolean hover = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint grad = new GradientPaint(
                        0, 0, hover ? topoHover : topo,
                        0, getHeight(), hover ? baseHover : base
                );

                g2.setPaint(grad);
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
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.repaint(); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { btn.repaint(); }
        });

        btn.addActionListener(listener);
        return btn;
    }

    // ---- LÓGICA DO LOGIN ----
    private void login() {
        try {
            String nome = txtNome.getText().trim();
            String codigoText = txtCodigo.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, digite o nome do vendedor.", 
                        "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo = Integer.parseInt(codigoText);

            Vendedor vendedor = vendedorService.criarVendedor(nome, codigo);
            mainFrame.setVendedor(vendedor);

            JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + nome + "!");

            mainFrame.showPanel("menu");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
