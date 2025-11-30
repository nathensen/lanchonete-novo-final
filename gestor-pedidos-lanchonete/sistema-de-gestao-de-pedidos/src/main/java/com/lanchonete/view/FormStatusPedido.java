package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

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
    private JButton btnMenu, btnMarcarPronto, btnConfirmarRetirada, btnExcluirPedido;

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

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões
        btnMenu.addActionListener(e -> mainFrame.showPanel("menu"));

        btnMarcarPronto.addActionListener(e -> {
            Pedido selecionado = listaPedidos.getSelectedValue();
            if (selecionado != null) {
                controller.marcarComoPronto(selecionado);
                atualizarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido para marcar como pronto.");
            }
        });

        btnConfirmarRetirada.addActionListener(e -> {
            Pedido selecionado = listaPedidos.getSelectedValue();
            if (selecionado != null) {
                controller.confirmarEntrega(selecionado);
                atualizarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido para confirmar retirada.");
            }
        });

        btnExcluirPedido.addActionListener(e -> {
            Pedido selecionado = listaPedidos.getSelectedValue();
            if (selecionado != null) {
                controller.excluirPedido(selecionado);
                atualizarLista();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pedido para excluir.");
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

    // Renderização com cores por status usando HTML
    private static class PedidoCellRenderer extends JLabel implements ListCellRenderer<Pedido> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Pedido> list,
                                                    Pedido value,
                                                    int index,
                                                    boolean isSelected,
                                                    boolean cellHasFocus) {

            // Define cor de acordo com o status usando contains()
            String status = value.getStatusEntrega().toUpperCase();
            String cor;
            if (status.contains("EM PRODUÇÃO")) cor = "red";
            else if (status.contains("PRONTO PARA ENTREGA")) cor = "blue";
            else if (status.contains("RETIRADO PELO CLIENTE")) cor = "green";
            else cor = "black";

            // Texto em HTML com cor
            String texto = "<html>"
                    + "<b>Cliente:</b> " + value.getNomeCliente()
                    + " | <b>Status:</b> <span style='color:" + cor + "'>" + value.getStatusEntrega() + "</span>"
                    + " | <b>Total:</b> R$ " + String.format("%.2f", value.calcularTotal())
                    + "</html>";

            setText(texto);
            setFont(new Font("Arial", Font.BOLD, 22));
            setOpaque(true);
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());

            return this;
        }
    }

}
