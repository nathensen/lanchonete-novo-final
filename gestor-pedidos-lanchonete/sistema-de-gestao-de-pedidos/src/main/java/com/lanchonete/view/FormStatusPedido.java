package com.lanchonete.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.lanchonete.controller.PedidoController;
import com.lanchonete.model.Pedido;

public class FormStatusPedido extends JPanel {

    private MainFrame mainFrame;
    private PedidoController pedidoController;
    private DefaultTableModel tableModel;
    private JTable tblPedidos;

    public FormStatusPedido(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.pedidoController = new PedidoController(mainFrame);

        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Status dos Pedidos");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        String[] colunas = {"Cliente", "Itens", "Total", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tblPedidos = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tblPedidos);
        scroll.setPreferredSize(new Dimension(600, 300));
        add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton btnAtualizar = new JButton("Atualizar Status");
        btnAtualizar.addActionListener(e -> atualizarStatusSelecionado());
        JButton btnVoltar = new JButton("Menu Principal");
        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));
        bottomPanel.add(btnAtualizar);
        bottomPanel.add(btnVoltar);

        add(bottomPanel, BorderLayout.SOUTH);
        atualizarTabela();
    }

    private void atualizarTabela() {
        List<Pedido> pedidos = pedidoController.listarPedidosFinalizados();
        tableModel.setRowCount(0);
        for (Pedido p : pedidos) {
            String itens = String.join(", ", p.getItensConsumidos().stream().map(i -> i.descricao()).toList());
            tableModel.addRow(new Object[]{ p.getNomeCliente(), itens, String.format("%.2f", p.calcularTotal()), p.getStatusEntrega() });
        }
    }

    private void atualizarStatusSelecionado() {
        int row = tblPedidos.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Selecione um pedido para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        String cliente = (String) tableModel.getValueAt(row, 0);
        Pedido pedido = pedidoController.buscarPedidoPorCliente(cliente);
        if (pedido != null) {
            if (pedido.getStatusEntrega().equals("entregue")) {
                JOptionPane.showMessageDialog(this, "Este pedido já foi entregue.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                pedido.setStatusEntrega("entregue");
                pedidoController.atualizarPedido(pedido);
                JOptionPane.showMessageDialog(this, "Pedido marcado como entregue.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            }
        }
    }
}
