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
import com.lanchonete.model.DinheiroPagamento;
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

        cmbMetodo = new JComboBox<>(new String[]{"Boleto", "Cartão", "PIX", "Dinheiro"});
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

        JButton btnMenu = new JButton("Menu Principal");
        btnMenu.setBounds(120, 240, 200, 35);
        add(btnMenu);

        JButton btnVoltar = new JButton("Voltar para Pedido");
        btnVoltar.setBounds(120, 290, 200, 35);
        add(btnVoltar);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBounds(40, 340, 360, 140);
        add(scroll);

        cmbMetodo.addActionListener(e -> atualizarCampos());
        cmbTipoCartao.addActionListener(e -> atualizarParcelas());

        btnConfirmar.addActionListener(e -> finalizarPagamento());
        btnMenu.addActionListener(e -> mainFrame.showPanel("menu"));
        btnVoltar.addActionListener(e -> mainFrame.showPanel("pedido"));
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
        if (forma == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um método de pagamento válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = pagamentoController.realizarPagamento(forma, total);
        txtResultado.append(resultado + "\n\n");

        PedidoController.ResultadoPedido res = pedidoController.finalizarPedido();

        // ================= CALCULAR TROCO =================
        double troco = 0;
        if (forma instanceof DinheiroPagamento) {
            DinheiroPagamento dp = (DinheiroPagamento) forma;
            troco = dp.getValorRecebido() - total;
        }

        // ================= MENSAGEM FINAL =================
        JOptionPane.showMessageDialog(null,
                "=== PAGAMENTO CONFIRMADO ===\n\n" +
                        "Cliente: " + pedido.getNomeCliente() + "\n" +
                        "Vendedor: " + mainFrame.getVendedor().getNome() + "\n" +
                        "Valor Total: R$ " + String.format("%.2f", total) + "\n" +
                        (troco > 0 ? "Troco: R$ " + String.format("%.2f", troco) + "\n" : "") +
                        "Bônus gerado: R$ " + String.format("%.2f", res.getBonusPedido())
        );

        // =================== RESETAR PEDIDO ===================
        mainFrame.setPedidoAtual(null);   // <---- zera o pedido atual
        mainFrame.showPanel("menu");      // volta ao menu
    }

    private Pagamento obterMetodo() {
        String metodo = (String) cmbMetodo.getSelectedItem();
        if (metodo == null) return null;

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
                        c.setParcelas(1);
                    }
                }
                return c;

            case "Pix":
            case "PIX":
                return new PixPagamento();

            case "Dinheiro":
                while (true) { // loop até o usuário digitar valor válido e suficiente
                    String valorStr = JOptionPane.showInputDialog(this, "Digite o valor entregue pelo cliente:");
                    if (valorStr == null) return null; // cancelou a entrada
                    try {
                        double recebido = Double.parseDouble(valorStr);
                        if (recebido < pedido.calcularTotal()) {
                            JOptionPane.showMessageDialog(this,
                                "Valor insuficiente! O total é R$ " + String.format("%.2f", pedido.calcularTotal()),
                                "Erro", JOptionPane.ERROR_MESSAGE);
                            continue; // repete o input
                        }
                        return new DinheiroPagamento(recebido);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
        }

        return null;
    }

}
