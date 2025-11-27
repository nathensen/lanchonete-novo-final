package com.lanchonete.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Painéis da aplicação
    private FormLogin loginPanel;
    private FormMenu menuPanel;
    private FormLanche lanchePanel;
    private FormSalgadinho salgadinhoPanel;
    private FormBebidas bebidasPanel;
    private FormPedido pedidoPanel;

    // Dados da aplicação
    private Vendedor vendedor;
    private Pedido pedidoAtual;

    public MainFrame() {
        setTitle("Sistema de Lanchonete");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Inicialização dos painéis fixos
        loginPanel = new FormLogin(this);
        menuPanel = new FormMenu(this);
        lanchePanel = new FormLanche(this);
        salgadinhoPanel = new FormSalgadinho(this);
        bebidasPanel = new FormBebidas(this);
        pedidoPanel = new FormPedido(this);

        // Registro dos painéis fixos
        cardPanel.add(loginPanel, "login");
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(lanchePanel, "lanche");
        cardPanel.add(salgadinhoPanel, "salgadinho");

        // 🔧 Ajustado: manter nome consistente
        cardPanel.add(bebidasPanel, "bebidas");

        cardPanel.add(pedidoPanel, "pedido");

        add(cardPanel);

        showPanel("login");
    }

    // Registrar novos painéis (ex.: pagamento)
    public void addPanel(String name, JPanel panel) {
        cardPanel.add(panel, name);
    }

    // Navegação
    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);

        if (panelName.equals("pedido") && pedidoPanel != null && pedidoAtual != null) {
            pedidoPanel.atualizarPedido(pedidoAtual);
        }
    }

    // Getters e Setters
    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Pedido getPedidoAtual() {
        return pedidoAtual;
    }

    public void setPedidoAtual(Pedido pedidoAtual) {
        this.pedidoAtual = pedidoAtual;

        // Atualiza automaticamente
        if (pedidoPanel != null) {
            pedidoPanel.atualizarPedido(pedidoAtual);
        }
    }

    // 🔧 Getter útil para acessar painel de pedido
    public FormPedido getFormPedido() {
        return pedidoPanel;
    }
}
