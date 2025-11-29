package com.lanchonete.view;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.lanchonete.controller.StatusPedidoController;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class MainFrame extends javax.swing.JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Painéis
    private FormLogin loginPanel;
    private FormMenu menuPanel;
    private FormLanche lanchePanel;
    private FormSalgadinho salgadinhoPanel;
    private FormBebidas bebidasPanel;
    private FormPedido pedidoPanel;
    private FormPagamento pagamentoPanel;
    private FormStatusPedido statusPedidoPanel;

    // Controller
    private StatusPedidoController statusPedidoController;

    // Dados
    private Vendedor vendedor;
    private Pedido pedidoAtual;

    public MainFrame() {
        setTitle("Sistema de Lanchonete");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Controller de status
        statusPedidoController = new StatusPedidoController();

        // Inicialização dos painéis
        loginPanel = new FormLogin(this);
        menuPanel = new FormMenu(this);
        lanchePanel = new FormLanche(this);
        salgadinhoPanel = new FormSalgadinho(this);
        bebidasPanel = new FormBebidas(this);
        pedidoPanel = new FormPedido(this);

        // Status
        statusPedidoPanel = new FormStatusPedido(this, statusPedidoController);

        // Registro dos painéis
        cardPanel.add(loginPanel, "login");
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(lanchePanel, "lanche");
        cardPanel.add(salgadinhoPanel, "salgadinho");
        cardPanel.add(bebidasPanel, "bebidas");
        cardPanel.add(pedidoPanel, "pedido");
        cardPanel.add(statusPedidoPanel, "statusPedido");

        add(cardPanel);

        showPanel("login");
    }

    public void addPanel(String name, JPanel panel) {
        cardPanel.add(panel, name);
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);

        if (panelName.equals("pedido") && pedidoPanel != null && pedidoAtual != null) {
            pedidoPanel.atualizarPedido(pedidoAtual);
        }
    }

    // Getters e Setters
    public Vendedor getVendedor() { return vendedor; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }

    public Pedido getPedidoAtual() { return pedidoAtual; }
    public void setPedidoAtual(Pedido pedidoAtual) {
        this.pedidoAtual = pedidoAtual;
        if (pedidoPanel != null) pedidoPanel.atualizarPedido(pedidoAtual);
    }

    public FormPedido getFormPedido() { return pedidoPanel; }
    public FormStatusPedido getStatusPedidoPanel() { return statusPedidoPanel; }
}
