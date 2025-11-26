package com.lanchonete.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.lanchonete.controller.PagamentoController;
import com.lanchonete.controller.PedidoController;
import com.lanchonete.model.BoletoPagamento;
import com.lanchonete.model.CartaoPagamento;
import com.lanchonete.model.Pagamento;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.PixPagamento;

public class FormPagamento extends JPanel {

    private MainFrame mainFrame;
    private PedidoController pedidoController;
    private PagamentoController pagamentoController;

    private JComboBox<String> cmbMetodo;
    private JTextArea txtResultado;

    private JLabel lblTipoCartao;
    private JComboBox<String> cmbTipoCartao;
    private JLabel lblParcelas;
    private JTextField txtParcelas;

    private Pedido pedido;

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

        btnConfirmar.addActionListener(e -> finalizarPagamento());
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

    private void finalizarPagamento() {

        double total = pedido.calcularTotal();

        Pagamento forma = obterMetodo();

        // registra pagamento
        String resultado = pagamentoController.realizarPagamento(forma, total);
        txtResultado.append(resultado + "\n\n");

        // finaliza pedido (sem valor pago, sem troco)
        PedidoController.ResultadoPedido res = pedidoController.finalizarPedido();

        JOptionPane.showMessageDialog(null,
                "=== PAGAMENTO CONFIRMADO ===\n\n" +
                        "Cliente: " + pedido.getNomeCliente() + "\n" +
                        "Vendedor: " + mainFrame.getVendedor().getNome() + "\n" +
                        "Valor Total: R$ " + String.format("%.2f", total) + "\n" +
                        "Bônus gerado: R$ " + String.format("%.2f", res.getBonusPedido())
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
                c.setTipo(cmbTipoCartao.getSelectedItem().toString());

                if (cmbTipoCartao.getSelectedItem().equals("Crédito")) {
                    try {
                        c.setParcelas(Integer.parseInt(txtParcelas.getText()));
                    } catch (NumberFormatException e) {
                        c.setParcelas(1); // padrão 1 parcela
                    }
                }
                return c;

            default:
                return new PixPagamento();
        }
    }
}
