package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.lanchonete.controller.StatusPedidoController;
import com.lanchonete.model.Pedido;

public class FormStatusPedido extends JPanel {

    private MainFrame mainFrame;
    private StatusPedidoController controller;
    private DefaultListModel<Pedido> listModel;
    private JList<Pedido> listaPedidos;
    private JButton btnMenu, btnConfirmarEntrega;

    public FormStatusPedido(MainFrame mainFrame, StatusPedidoController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        listaPedidos = new JList<>(listModel);
        listaPedidos.setCellRenderer(new PedidoCellRenderer());

        JScrollPane scroll = new JScrollPane(listaPedidos);
        scroll.setPreferredSize(new Dimension(600, 400));

        btnMenu = new JButton("Menu Principal");
        btnConfirmarEntrega = new JButton("Confirmar Entrega ao Cliente");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnConfirmarEntrega);
        painelBotoes.add(btnMenu);

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnMenu.addActionListener(e -> mainFrame.showPanel("menu"));

        btnConfirmarEntrega.addActionListener(e -> {
            Pedido selecionado = listaPedidos.getSelectedValue();
            if (selecionado != null) {
                controller.confirmarEntrega(selecionado);
                atualizarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido para confirmar entrega.");
            }
        });
    }

    public void adicionarPedido(Pedido pedido) {
        controller.adicionarPedido(pedido);
        atualizarLista();
    }

    public void atualizarLista() {
        listModel.clear();
        for (Pedido p : controller.listarPedidos()) {
            listModel.addElement(p);
        }
    }

    private static class PedidoCellRenderer extends JLabel implements ListCellRenderer<Pedido> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Pedido> list, Pedido value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(value.getNomeCliente() + " - Status: " + value.getStatusEntrega() + 
                    " | Total: R$ " + String.format("%.2f", value.calcularTotal()));
            setOpaque(true);
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }
}
