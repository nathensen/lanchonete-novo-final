package com.lanchonete.view;

import javax.swing.*;
import java.awt.*;

import com.lanchonete.controller.PagamentoController;
import com.lanchonete.controller.PedidoController;
import com.lanchonete.model.*;

public class FormPagamento extends JPanel {

    private MainFrame mainFrame;
    private PedidoController pedidoController;
    private PagamentoController pagamentoController;

    private JComboBox<String> cmbMetodo;
    private JTextArea txtResultado;

    JLabel lblTipoCartao;
    JComboBox<String> cmbTipoCartao;
    JLabel lblParcelas;
    JTextField txtParcelas;

    private Pedido pedido; // 🔥 Necessário para exibir total corretamente

    public FormPagamento(MainFrame mainFrame, PedidoController pedidoController, Pedido pedido) {
        this.mainFrame = mainFrame;
        this.pedidoController = pedidoController;
        this.pedido = pedido;
        this.pagamentoController = new PagamentoController();

        setLayout(null);
        setBounds(0, 0, 460, 540);

        initComponents();
    }

    private void initComponents() {

        double total = pedido.calcularTotal();

        JLabel lblTotal = new JLabel("Total a pagar: R$ " + String.format("%.2f", total));
        lblTotal.setBounds(40, 20, 300, 25);
        add(lblTotal);

        JLabel lblMetodo = new JLabel("Método de Pagamento:");
        lblMetodo.setBounds(40, 60, 150, 25);
        add(lblMetodo);

        cmbMetodo = new JComboBox<>(new String[]{"Boleto", "Cartão", "PIX"});
        cmbMetodo.setBounds(190, 60, 160, 25);
        add(cmbMetodo);

        lblTipoCartao = new JLabel("Tipo de cartão:");
        lblTipoCartao.setBounds(40, 100, 120, 25);
        lblTipoCartao.setVisible(false);
        add(lblTipoCartao);

        cmbTipoCartao = new JComboBox<>(new String[]{"Débito", "Crédito"});
        cmbTipoCartao.setBounds(160, 100, 190, 25);
        cmbTipoCartao.setVisible(false);
        add(cmbTipoCartao);

        lblParcelas = new JLabel("Parcelas:");
        lblParcelas.setBounds(40, 140, 120, 25);
        lblParcelas.setVisible(false);
        add(lblParcelas);

        txtParcelas = new JTextField();
        txtParcelas.setBounds(160, 140, 60, 25);
        txtParcelas.setVisible(false);
        add(txtParcelas);

        JButton btnConfirmar = new JButton("Confirmar Pagamento");
        btnConfirmar.setBounds(120, 190, 200, 35);
        add(btnConfirmar);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBounds(40, 240, 360, 140);
        add(scroll);

        cmbMetodo.addActionListener(e -> atualizarCampos());
        cmbTipoCartao.addActionListener(e -> atualizarParcelas());

        btnConfirmar.addActionListener(e -> finalizarPagamentoReal());
    }

    private void atualizarCampos() {
        boolean isCartao = cmbMetodo.getSelectedItem().equals("Cartão");
        lblTipoCartao.setVisible(isCartao);
        cmbTipoCartao.setVisible(isCartao);
        lblParcelas.setVisible(false);
        txtParcelas.setVisible(false);
    }

    private void atualizarParcelas() {
        boolean isCredito = cmbTipoCartao.getSelectedItem().equals("Crédito");
        lblParcelas.setVisible(isCredito);
        txtParcelas.setVisible(isCredito);
    }

    private void finalizarPagamentoReal() {

        double total = pedido.calcularTotal();

        Pagamento forma = obterMetodo();

        // pagamento interno
        String tempResult = pagamentoController.realizarPagamento(forma, total);
        txtResultado.append(tempResult + "\n\n");

        // finaliza pedido principal
        PedidoController.ResultadoPedido resultado =
                pedidoController.finalizarPedido(total);

        JOptionPane.showMessageDialog(null,
                "=== PAGAMENTO CONFIRMADO ===\n\n" +
                        "Cliente: " + pedido.getNomeCliente() + "\n" +
                        "Vendedor: " + mainFrame.getVendedor().getNome() + "\n" +
                        "Valor Total: R$ " + String.format("%.2f", total) + "\n" +
                        "Bônus gerado: R$ " + String.format("%.2f", resultado.getBonusPedido()) + "\n" +
                        "Troco: R$ " + String.format("%.2f", resultado.getTroco())
        );

        mainFrame.showPanel("menu");
    }

    private Pagamento obterMetodo() {

        String metodo = (String) cmbMetodo.getSelectedItem();

        switch (metodo) {
            case "Boleto":
                return new BoletoPagamento();

            case "Cartão":
                CartaoPagamento c = new CartaoPagamento();
                c.setTipo(cmbTipoCartao.getSelectedItem().toString()); //erro aqui

                if (cmbTipoCartao.getSelectedItem().equals("Crédito")) {
                    c.setParcelas(Integer.parseInt(txtParcelas.getText())); //erro aqui
                }
                return c;

            default:
                return new PixPagamento();
        }
    }
}
