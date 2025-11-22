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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lanchonete.model.Pedido;
import com.lanchonete.service.PedidoService;
import com.lanchonete.service.VendedorService;
import com.lanchonete.util.FormatadorMoeda;

public class FormMenu extends JPanel {
    private MainFrame mainFrame;
    private PedidoService pedidoService;
    private VendedorService vendedorService;
    
    public FormMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.pedidoService = new PedidoService();
        this.vendedorService = new VendedorService();
        
        setLayout(new BorderLayout());
        
        // Painel de boas-vindas
        JPanel welcomePanel = new JPanel();
        JLabel lblWelcome = new JLabel("Menu Principal");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(lblWelcome);
        
        // Painel de botões de categoria
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Botão Novo Pedido
        JButton btnNovoPedido = criarBotao("Novo Pedido", e -> novoPedido());
        buttonPanel.add(btnNovoPedido);
        
        // Botão Lanches
        JButton btnLanches = criarBotao("Lanches", e -> mainFrame.showPanel("lanche"));
        buttonPanel.add(btnLanches);
        
        // Botão Salgadinhos
        JButton btnSalgadinhos = criarBotao("Salgadinhos", e -> mainFrame.showPanel("salgadinho"));
        buttonPanel.add(btnSalgadinhos);
        
        // Botão Bebidas
        JButton btnBebidas = criarBotao("Bebidas", e -> mainFrame.showPanel("bebida"));
        buttonPanel.add(btnBebidas);
        
        // Botão Ver Pedido
        JButton btnVerPedido = criarBotao("Ver Pedido Atual", e -> mainFrame.showPanel("pedido"));
        buttonPanel.add(btnVerPedido);
        
        // Botão Ver Bônus
        JButton btnBonus = criarBotao("Ver Bônus do Vendedor", e -> mostrarBonus());
        buttonPanel.add(btnBonus);
        
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
    
//+++++++++++++++++++++++++++

private JButton criarBotao(String texto, java.awt.event.ActionListener listener) {
    JButton btn = new JButton(texto) {

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            // ---- DEGRADÊ ----
            Color corTopo = new Color(0, 100, 0);     // Verde mais escuro
            Color corBase = new Color(0, 150, 0);     // Verde mais claro

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

    // ---- LETRAS MAIORES E BRANCAS ----
    btn.setFont(new Font("SansSerif", Font.BOLD, 20));  // Ajuste o tamanho se quiser maior
    btn.setForeground(Color.WHITE);

    // Remove estilo padrão do Swing
    btn.setContentAreaFilled(false);
    btn.setOpaque(false);
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);

    btn.addActionListener(listener);
    return btn;
}

  //+++++++++++++++++++++++++++

    private void novoPedido() {
        String nomeCliente = JOptionPane.showInputDialog(this, 
                "Nome do cliente:", 
                "Novo Pedido", 
                JOptionPane.QUESTION_MESSAGE);
        
        if (nomeCliente != null && !nomeCliente.trim().isEmpty()) {
            Pedido pedido = pedidoService.criarPedido(nomeCliente);
            mainFrame.setPedidoAtual(pedido);
            
            JOptionPane.showMessageDialog(this, 
                    "Pedido criado para: " + nomeCliente, 
                    "Pedido Iniciado", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mostrarBonus() {
        if (mainFrame.getVendedor() == null) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao acessar informações do vendedor.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double codigo = mainFrame.getVendedor().getCodigo();
        double bonus = mainFrame.getVendedor().getBonus();
        
        JOptionPane.showMessageDialog(this, 
                "Vendedor: " + mainFrame.getVendedor().getNome() + "\n" +
                "Código do Vendedor: " + (codigo) + "\n" +
                "Bônus Acumulado: " + FormatadorMoeda.formatar(bonus) + "\n" +
                "Total a Receber: " + FormatadorMoeda.formatar(bonus),
                "Informações do Vendedor", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void encerrarTurno() {
        if (mainFrame.getVendedor() == null) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao acessar informações do vendedor.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente encerrar o turno?", 
                "Confirmar Saída", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            vendedorService.mostrarResumoFinalTurno(mainFrame.getVendedor());
            
            double codigo = mainFrame.getVendedor().getCodigo();
            double bonus = mainFrame.getVendedor().getBonus();
            
            JOptionPane.showMessageDialog(this, 
                    "==== RESUMO FINAL DO TURNO ====\n\n" +
                    "Vendedor: " + mainFrame.getVendedor().getNome() + "\n" +
                    "Código do Vendedor: " + (codigo) + "\n" +
                    "Bônus Acumulado: " + FormatadorMoeda.formatar(bonus) + "\n" +
                    "Total a Receber: " + FormatadorMoeda.formatar(bonus) + "\n\n" +
                    "Volte sempre!",
                    "Turno Encerrado", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(0);
        }
    }
}
