package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.lanchonete.controller.PedidoController;
import com.lanchonete.model.ItemPedido;
import com.lanchonete.model.Pedido;
import com.lanchonete.service.PedidoService;
import com.lanchonete.service.VendedorService;

public class FormPedido extends JPanel {
    private MainFrame mainFrame;
    private PedidoService pedidoService;
    private VendedorService vendedorService;
    private PedidoController controller;
    
    private DefaultTableModel tableModel;
    private JTable tblItens;
    private JLabel lblTotal;
    private JTextField txtValorPago;
    
    public FormPedido(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.pedidoService = new PedidoService();
        this.vendedorService = new VendedorService();
        this.controller = new PedidoController(mainFrame);
        
        setLayout(new BorderLayout());
        
        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Pedido Atual");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);
        
        // Painel para exibição do cliente
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clientePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel lblCliente = new JLabel("Cliente: [Nenhum pedido iniciado]");
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        clientePanel.add(lblCliente);
        
        // Tabela de itens
        String[] colunas = {"Item", "Descrição", "Preço (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblItens = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblItens);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        // Painel para o total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(lblTotal);
        
        // Painel para pagamento
        JPanel pagamentoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pagamentoPanel.add(new JLabel("Valor Pago: R$ "));
        txtValorPago = new JTextField(10);
        pagamentoPanel.add(txtValorPago);
        
        JButton btnFinalizar = new JButton("Finalizar Pedido");
        btnFinalizar.addActionListener(e -> {
            boolean sucesso = controller.finalizarPedido(txtValorPago.getText().trim());
            if (sucesso) {
                atualizarTela();
                txtValorPago.setText("");
            }
        });
        pagamentoPanel.add(btnFinalizar);
        
        // Botão Voltar
        JPanel buttonPanel = new JPanel();
        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.addActionListener(e -> {
            mainFrame.showPanel("menu");
            atualizarTela();
        });
        buttonPanel.add(btnVoltar);
        
        // Painel central
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(clientePanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(totalPanel, BorderLayout.SOUTH);
        
        // Painel inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(pagamentoPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Layout final
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void atualizarTela() {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        
        Pedido pedido = mainFrame.getPedidoAtual();
        double total = 0;
        
        if (pedido != null) {
            JPanel clientePanel = (JPanel) ((JPanel) getComponent(1)).getComponent(0);
            JLabel lblCliente = (JLabel) clientePanel.getComponent(0);
            lblCliente.setText("Cliente: " + pedido.getNomeCliente());
            
            int i = 1;
            for (ItemPedido item : pedido.getItensConsumidos()) {
                Object[] row = {
                    "Item " + i,
                    item.descricao(),
                    String.format("%.2f", item.getPrecoVenda())
                };
                tableModel.addRow(row);
                total += item.getPrecoVenda();
                i++;
            }
            lblTotal.setText(String.format("Total: R$ %.2f", total));
        } else {
            lblTotal.setText("Total: R$ 0,00");
        }
    }
}
