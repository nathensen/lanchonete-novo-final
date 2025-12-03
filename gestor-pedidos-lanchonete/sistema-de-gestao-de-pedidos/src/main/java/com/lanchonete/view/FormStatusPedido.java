package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.lanchonete.controller.StatusPedidoController;
import com.lanchonete.model.Pedido;

public class FormStatusPedido extends JPanel {

    private MainFrame mainFrame;
    private StatusPedidoController controller;

    private JTable tabela;
    private PedidoTableModel tableModel;

    private JButton btnMenu, btnMarcarPronto, btnConfirmarRetirada, btnExcluirPedido;

    public FormStatusPedido(MainFrame mainFrame, StatusPedidoController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        tableModel = new PedidoTableModel(controller.listarPedidos());
        tabela = new JTable(tableModel);

        tabela.setRowHeight(30);

        tabela.setFont(new Font("Arial", Font.BOLD, 25));

        // Fonte do cabeçalho
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));

        // Centraliza títulos
        ((DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        // Centraliza todas as células
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.setDefaultRenderer(Object.class, cellRenderer);

        tabela.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = value.toString().toUpperCase();

                switch(status) {
                    case "EM PRODUÇÃO": label.setForeground(java.awt.Color.RED); break;
                    case "PRONTO PARA ENTREGA": label.setForeground(java.awt.Color.BLUE); break;
                    case "RETIRADO PELO CLIENTE": label.setForeground(java.awt.Color.GREEN); break;
                    default: label.setForeground(java.awt.Color.BLACK);
                }

                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Botões
        btnMenu = new JButton("Menu Principal");
        btnMarcarPronto = new JButton("Marcar Pedido Pronto");
        btnConfirmarRetirada = new JButton("Confirmar Retirada");
        btnExcluirPedido = new JButton("Excluir Pedido");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnMarcarPronto);
        painelBotoes.add(btnConfirmarRetirada);
        painelBotoes.add(btnExcluirPedido);
        painelBotoes.add(btnMenu);

        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnMenu.addActionListener(e -> mainFrame.showPanel("menu"));

        btnMarcarPronto.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                Pedido selecionado = tableModel.getPedidoAt(linha);
                controller.marcarComoPronto(selecionado);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido para marcar como pronto.");
            }
        });

        btnConfirmarRetirada.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                Pedido selecionado = tableModel.getPedidoAt(linha);
                controller.confirmarEntrega(selecionado);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido para confirmar retirada.");
            }
        });

        btnExcluirPedido.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                Pedido selecionado = tableModel.getPedidoAt(linha);
                controller.excluirPedido(selecionado);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido para excluir.");
            }
        });
    }

    public void adicionarPedido(Pedido pedido) {
        controller.adicionarPedido(pedido);
        atualizarTabela();
    }

    public void atualizarTabela() {
        tableModel.atualizar(controller.listarPedidos());
    }

    private static class PedidoTableModel extends AbstractTableModel {
        private java.util.List<Pedido> pedidos;
        private final String[] colunas = {"Cliente", "Status", "Total"};

        public PedidoTableModel(java.util.List<Pedido> pedidos) {
            this.pedidos = pedidos;
        }

        public void atualizar(java.util.List<Pedido> pedidos) {
            this.pedidos = pedidos;
            fireTableDataChanged();
        }

        public Pedido getPedidoAt(int rowIndex) {
            return pedidos.get(rowIndex);
        }

        @Override
        public int getRowCount() { return pedidos.size(); }

        @Override
        public int getColumnCount() { return colunas.length; }

        @Override
        public String getColumnName(int column) { return colunas[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Pedido p = pedidos.get(rowIndex);
            switch(columnIndex) {
                case 0: return p.getNomeCliente();
                case 1: return p.getStatusEntrega();
                case 2: return "R$ " + String.format("%.2f", p.calcularTotal());
            }
            return null;
        }
    }
}
