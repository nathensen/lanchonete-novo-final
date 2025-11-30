package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.lanchonete.controller.LoginController;
import com.lanchonete.repository.IVendedorRepository;
import com.lanchonete.repository.VendedorRepository;
import com.lanchonete.service.LoginService;
import com.lanchonete.service.VendedorService;

public class FormLogin extends BackgroundPanel {

    private MainFrame mainFrame;
    private VendedorService vendedorService;
    private LoginController loginController;

    private JTextField txtNome;
    private JPasswordField txtCodigo;
    private JButton btnEntrar;

    public FormLogin(MainFrame mainFrame) {

           Image img = null;
        try {
            img = ImageIO.read(BackgroundPanel.class.getResourceAsStream("/com/lanchonete/resources/fundoMenu.jpg"));
        } catch (Exception ex) {
            System.err.println("Não foi possível carregar imagem de fundo: " + ex.getMessage());
        }

        this.mainFrame = mainFrame;
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);



        // Repository + service (igual ao que você já tem)
        IVendedorRepository vendedorRepository = new VendedorRepository();
        VendedorService vendedorService = new VendedorService(vendedorRepository);

        // novo: LoginService que usa VendedorService
        LoginService loginService = new LoginService(vendedorService);

        // Controller agora recebe LoginService
        this.loginController = new LoginController(loginService);


        // Layout geral
        setLayout(new BorderLayout());
        setBackground(null);

        // Título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(null);
        titlePanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Sistema de Lanchonete");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitle.setForeground(Color.BLACK);

        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(null);
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Login:");
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel lblCodigo = new JLabel("Senha:");
        lblCodigo.setFont(new Font("SansSerif", Font.BOLD, 18));

        txtNome = new JTextField(18);
        txtNome.setFont(new Font("SansSerif", Font.PLAIN, 16));

        txtCodigo = new JPasswordField(18);
        txtCodigo.setFont(new Font("SansSerif", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblNome, gbc);

        gbc.gridx = 1;
        formPanel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblCodigo, gbc);

        gbc.gridx = 1;
        formPanel.add(txtCodigo, gbc);

        btnEntrar = criarBotao("Entrar no Sistema", e -> executarLogin());

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(btnEntrar, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    // ---- Botão estilizado ----
    private JButton criarBotao(String texto, java.awt.event.ActionListener listener) {

        JButton btn = new JButton(texto) {

            Color topo = new Color(0, 120, 215);
            Color base = new Color(0, 100, 180);
            Color topoHover = new Color(0, 100, 190);
            Color baseHover = new Color(0, 80, 160);

            boolean hover = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint grad = new GradientPaint(
                        0, 0, hover ? topoHover : topo,
                        0, getHeight(), hover ? baseHover : base);

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

    // ---- CHAMA O CONTROLLER ----
    private void executarLogin() {
        String nome = txtNome.getText().trim();
        String codigo = String.valueOf(txtCodigo.getPassword()).trim();

        try {
            loginController.login(nome, codigo, mainFrame);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro no Login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
