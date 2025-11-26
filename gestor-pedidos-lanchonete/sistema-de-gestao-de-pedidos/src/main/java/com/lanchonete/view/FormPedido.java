// ================= FORM PEDIDO COMPLETO E CORRIGIDO ==================

package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
    private JTextField txtValorPago;
    private JLabel lblCliente;

    public FormPedido(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.pedidoController = new PedidoController(mainFrame);

        setLayout(new BorderLayout());

        // ------------------ TÍTULO ------------------
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Pedido Atual");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        // ------------------ CLIENTE ------------------
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clientePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblCliente = new JLabel("Cliente: [Nenhum pedido iniciado]");
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        clientePanel.add(lblCliente);

        // ------------------ TABELA ------------------
        String[] colunas = {"Item", "Descrição", "Preço (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblItens = new JTable(tableModel);

        JPopupMenu menu = new JPopupMenu();
        JMenuItem opcExcluir = new JMenuItem("Excluir item");
        menu.add(opcExcluir);

        tblItens.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            private void showMenu(java.awt.event.MouseEvent e) {
                int row = tblItens.rowAtPoint(e.getPoint());
                if (row != -1) {
                    tblItens.setRowSelectionInterval(row, row);
                    menu.show(tblItens, e.getX(), e.getY());
                }
            }
        });

        opcExcluir.addActionListener(e -> {
            int linha = tblItens.getSelectedRow();

            if (linha != -1) {

                int confirmar = JOptionPane.showConfirmDialog(null,
                        "Deseja excluir este item?",
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {

                    tableModel.removeRow(linha);

                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        tableModel.setValueAt(i + 1, i, 0);
                    }

                    Pedido pedido = mainFrame.getPedidoAtual();
                    if (pedido != null && linha < pedido.getItensConsumidos().size()) {
                        pedido.getItensConsumidos().remove(linha);
                    }

                    if (pedido != null) {
                        atualizarTotal(pedido);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblItens);
        scrollPane.setPreferredSize(new Dimension(500, 250));

        // ------------------ TOTAL ------------------
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(lblTotal);

        // ------------------ PAGAMENTO ------------------
        JPanel pagamentoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pagamentoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblValorPago = new JLabel("Valor Pago:");
        txtValorPago = new JTextField(10);
        JButton btnFinalizar = new JButton("Finalizar Pedido");
        pagamentoPanel.add(lblValorPago);
        pagamentoPanel.add(txtValorPago);
        pagamentoPanel.add(btnFinalizar);

        // ------------------ BOTÕES ------------------
        JPanel controlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnMenu = new JButton("Menu Principal");
        JButton btnSair = new JButton("Encerrar Sistema");
        controlePanel.add(btnMenu);
        controlePanel.add(btnSair);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(controlePanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        btnFinalizar.addActionListener(e -> abrirPagamento());
        btnMenu.addActionListener(e -> mainFrame.showPanel("menu"));
        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente encerrar o sistema?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // ------------------ ADD PANELS ------------------
        add(titlePanel, BorderLayout.NORTH);
        add(clientePanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(pagamentoPanel, BorderLayout.EAST);
    }

    // =====================================================
    //              MÉTODOS DO FORM
    // =====================================================
    public void atualizarPedido(Pedido pedido) {
        lblCliente.setText("Cliente: " + pedido.getNomeCliente());
        atualizarTabela(pedido);
        atualizarTotal(pedido);
    }

    public void atualizarTabela(Pedido pedido) {
        tableModel.setRowCount(0);
        int index = 1;
        for (ItemPedido item : pedido.getItensConsumidos()) {
            tableModel.addRow(new Object[]{
                    index++,
                    item.descricao(),
                    String.format("%.2f", item.getPrecoVenda())
            });
        }
    }

    public void atualizarTotal(Pedido pedido) {
        lblTotal.setText("Total: R$ " + String.format("%.2f", pedido.calcularTotal()));
    }

    public void adicionarItemTabela(ItemPedido item) {
        int index = tableModel.getRowCount() + 1;
        tableModel.addRow(new Object[]{
                index,
                item.descricao(),
                String.format("%.2f", item.getPrecoVenda())
        });
    }

    // =====================================================
    //             ABRIR TELA DE PAGAMENTO
    // =====================================================
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

        // ✔ Agora passando os 3 parâmetros corretamente
        FormPagamento form = new FormPagamento(mainFrame, pedidoController, pedido);

        // ✔ Registrar o painel
        mainFrame.addPanel("pagamento", form);

        // ✔ Mostrar painel corretamente
        mainFrame.showPanel("pagamento");
    }

}
