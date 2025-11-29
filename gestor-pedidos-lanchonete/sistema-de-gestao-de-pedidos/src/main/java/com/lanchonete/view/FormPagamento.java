package com.lanchonete.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
import com.lanchonete.util.HistoricoTXT;

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

        setLayout(new GridBagLayout()); // CENTRALIZA TUDO
        initComponents();
    }

    private void initComponents() {
        double total = pedido.calcularTotal();

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setPreferredSize(new Dimension(450, 500));

        // ---------- COMPONENTES ----------
        JLabel lblTotal = new JLabel("Total a pagar: R$ " + String.format("%.2f", total));
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMetodo = new JLabel("Método de Pagamento:");
        lblMetodo.setAlignmentX(Component.CENTER_ALIGNMENT);

        cmbMetodo = new JComboBox<>(new String[]{"Boleto","Cartão","PIX","Dinheiro"});
        cmbMetodo.setMaximumSize(new Dimension(250, 30));
        cmbMetodo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTipoCartao = new JLabel("Tipo de cartão:");
        lblTipoCartao.setVisible(false);
        lblTipoCartao.setAlignmentX(Component.CENTER_ALIGNMENT);

        cmbTipoCartao = new JComboBox<>(new String[]{"Débito","Crédito"});
        cmbTipoCartao.setVisible(false);
        cmbTipoCartao.setMaximumSize(new Dimension(250, 30));
        cmbTipoCartao.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblParcelas = new JLabel("Parcelas:");
        lblParcelas.setVisible(false);
        lblParcelas.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtParcelas = new JTextField();
        txtParcelas.setMaximumSize(new Dimension(60, 30));
        txtParcelas.setVisible(false);
        txtParcelas.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnConfirmar = new JButton("Confirmar Pagamento");
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnMenu = new JButton("Menu Principal");
        btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnVoltar = new JButton("Voltar para Pedido");
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtResultado = new JTextArea(6, 30);
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---------- ADICIONA NO CONTENT ----------
        content.add(Box.createVerticalStrut(10));
        content.add(lblTotal);
        content.add(Box.createVerticalStrut(20));
        content.add(lblMetodo);
        content.add(cmbMetodo);
        content.add(Box.createVerticalStrut(20));
        content.add(lblTipoCartao);
        content.add(cmbTipoCartao);
        content.add(Box.createVerticalStrut(10));
        content.add(lblParcelas);
        content.add(txtParcelas);
        content.add(Box.createVerticalStrut(25));
        content.add(btnConfirmar);
        content.add(Box.createVerticalStrut(10));
        content.add(btnMenu);
        content.add(Box.createVerticalStrut(10));
        content.add(btnVoltar);
        content.add(Box.createVerticalStrut(20));
        content.add(scroll);

        // ---------- CENTRALIZA NO PAINEL ----------
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(content, gbc);

        // ---------- EVENTOS ----------
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
            JOptionPane.showMessageDialog(this, "Selecione um método válido", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = pagamentoController.realizarPagamento(forma, total);
        txtResultado.append(resultado + "\n\n");

        // Finaliza o pedido
        PedidoController.ResultadoPedido res = pedidoController.finalizarPedido();

        if (res != null) {
            // SALVAR NO HISTÓRICO
            HistoricoTXT.salvar(pedido, mainFrame.getVendedor());

            // 🔹 Enviar para StatusPedido sem alterar layout
            if (mainFrame.getStatusPedidoPanel() != null) {
                mainFrame.getStatusPedidoPanel().adicionarPedido(pedido);
            }
        }

        double troco = 0;
        if (forma instanceof DinheiroPagamento dp) {
            troco = dp.getValorRecebido() - total;
        }

        JOptionPane.showMessageDialog(null,
            "=== PAGAMENTO CONFIRMADO ===\n\n" +
            "Cliente: " + pedido.getNomeCliente() + "\n" +
            "Vendedor: " + mainFrame.getVendedor().getNome() + "\n" +
            "Valor Total: R$ " + String.format("%.2f", total) + "\n" +
            (troco>0 ? "Troco: R$ "+String.format("%.2f", troco)+"\n" : "") +
            "Bônus gerado: R$ " + String.format("%.2f", res.getBonusPedido())
        );

        // Limpa pedido atual e retorna ao menu
        mainFrame.setPedidoAtual(null);
        mainFrame.showPanel("menu");
    }

    private Pagamento obterMetodo() {
        String metodo = (String)cmbMetodo.getSelectedItem();
        if (metodo == null) return null;

        switch(metodo) {
            case "Boleto": return new BoletoPagamento();

            case "Cartão":
                CartaoPagamento c = new CartaoPagamento();
                c.setTipo(cmbTipoCartao.getSelectedItem().toString());
                if (cmbTipoCartao.getSelectedItem().equals("Crédito")) {
                    try { c.setParcelas(Integer.parseInt(txtParcelas.getText())); }
                    catch(NumberFormatException e) { c.setParcelas(1); }
                }
                return c;

            case "PIX": case "Pix": return new PixPagamento();

            case "Dinheiro":
                while (true) {
                    String valorStr = JOptionPane.showInputDialog(this, "Digite o valor entregue pelo cliente:");
                    if (valorStr == null) return null;
                    try {
                        double recebido = Double.parseDouble(valorStr);
                        if (recebido < pedido.calcularTotal()) {
                            JOptionPane.showMessageDialog(this, "Valor insuficiente!",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        return new DinheiroPagamento(recebido);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Valor inválido!",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
        }
        return null;
    }
}
