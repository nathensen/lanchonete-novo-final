package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
        lblCliente.setFont(new Font("Arial", Font.BOLD, 20));
        clientePanel.add(lblCliente);
        add(clientePanel, BorderLayout.WEST);

        // Tabela
        String[] colunas = {"Item", "Descrição", "Preço (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblItens = new JTable(tableModel);
        tblItens.setFont(new Font("Arial", Font.BOLD, 22));
        tblItens.setRowHeight(28);
        tblItens.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));


        JScrollPane scrollPane = new JScrollPane(tblItens);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        add(scrollPane, BorderLayout.CENTER);

        // Painel inferior com total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 25));
        totalPanel.add(lblTotal);
        add(totalPanel, BorderLayout.SOUTH);

        // Painel com os 2 botões na vertical
        JPanel painelVertical = new JPanel(new GridLayout(2, 1, 5, 5));

        JButton btnPagamento = new JButton("Forma de Pagamento");
        btnPagamento.addActionListener(e -> abrirPagamento());

        JButton btnExcluirItem = new JButton("Excluir Item");
        btnExcluirItem.addActionListener(e -> excluirItem());

        painelVertical.add(btnPagamento);
        painelVertical.add(btnExcluirItem);

        // Botão menu separado
        JPanel painelMenu = new JPanel(new BorderLayout());
        JButton btnMenu = new JButton("Voltar ao Menu");
        btnMenu.addActionListener(e -> mainFrame.showPanel("menu"));
        painelMenu.add(btnMenu, BorderLayout.CENTER);
        painelMenu.add(btnMenu);

        // Une tudo na direita
        JPanel painelDireita = new JPanel(new BorderLayout());
        painelDireita.add(painelVertical, BorderLayout.NORTH);
        painelDireita.add(painelMenu, BorderLayout.SOUTH);

        add(painelDireita, BorderLayout.EAST);
    }

    // Atualizar cliente + tabela + total
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

    private void excluirItem() {
        int linha = tblItens.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pedido pedido = mainFrame.getPedidoAtual();
        if (pedido == null) return;

        pedido.getItensConsumidos().remove(linha);

        atualizarTabela(pedido);
        atualizarTotal(pedido);
    }
}
