package com.lanchonete.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.lanchonete.controller.PedidoController;
import com.lanchonete.model.ItemPedido;
import com.lanchonete.model.Pedido;

public class FormPedido extends JPanel {

    private MainFrame mainFrame;
    private PedidoController pedidoController;

    private DefaultTableModel tableModel;
    private JTable tblItens;
    private JLabel lblTotal;
    private JLabel lblCliente;

    public FormPedido(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.pedidoController = new PedidoController(mainFrame);
        setLayout(new BorderLayout());

        // Título
        JLabel lblTitle = new JLabel("Pedido Atual");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel titlePanel = new JPanel();
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Cliente
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblCliente = new JLabel("Cliente: [Nenhum pedido iniciado]");
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        clientePanel.add(lblCliente);
        add(clientePanel, BorderLayout.WEST);

        // Tabela de itens
        String[] colunas = {"Item", "Descrição", "Preço (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblItens = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tblItens);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        add(scrollPane, BorderLayout.CENTER);

        // Total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(lblTotal);
        add(totalPanel, BorderLayout.SOUTH);

        // Botões
        JPanel controlePanel = new JPanel(new FlowLayout());
        JButton btnMenu = new JButton("Menu Principal");
        btnMenu.addActionListener(e -> mainFrame.showPanel("menu"));
        JButton btnPagamento = new JButton("Forma de Pagamento");
        btnPagamento.addActionListener(e -> abrirPagamento());
        controlePanel.add(btnMenu);
        controlePanel.add(btnPagamento);
        add(controlePanel, BorderLayout.EAST);
    }

    public void atualizarPedido(Pedido pedido) {
        if (pedido != null) lblCliente.setText("Cliente: " + pedido.getNomeCliente());
        else lblCliente.setText("Cliente: [Nenhum pedido iniciado]");
        atualizarTabela(pedido);
        atualizarTotal(pedido);
    }

    public void atualizarTabela(Pedido pedido) {
        tableModel.setRowCount(0);
        if (pedido == null) return;
        int index = 1;
        for (ItemPedido item : pedido.getItensConsumidos()) {
            tableModel.addRow(new Object[]{ index++, item.descricao(), String.format("%.2f", item.getPrecoVenda()) });
        }
    }

    public void atualizarTotal(Pedido pedido) {
        if (pedido == null) {
            lblTotal.setText("Total: R$ 0,00");
            return;
        }
        lblTotal.setText("Total: R$ " + String.format("%.2f", pedido.calcularTotal()));
    }

    private void abrirPagamento() {
        Pedido pedido = mainFrame.getPedidoAtual();
        if (pedido == null) {
            JOptionPane.showMessageDialog(this, "Nenhum pedido iniciado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (pedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O pedido está vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FormPagamento form = new FormPagamento(mainFrame, pedidoController, pedido);
        mainFrame.addPanel("pagamento", form);
        mainFrame.showPanel("pagamento");
    }
}
