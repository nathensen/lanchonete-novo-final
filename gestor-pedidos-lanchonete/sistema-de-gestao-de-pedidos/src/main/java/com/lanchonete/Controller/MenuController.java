package com.lanchonete.controller;

import javax.swing.JOptionPane;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;
import com.lanchonete.repository.IVendedorRepository;
import com.lanchonete.repository.VendedorRepository;
import com.lanchonete.service.PedidoService;
import com.lanchonete.service.VendedorService;
import com.lanchonete.util.FormatadorMoeda;

public class MenuController {

    private PedidoService pedidoService;
    private VendedorService vendedorService;

    public MenuController() {
        this.pedidoService = new PedidoService();
        IVendedorRepository vendedorRepository = new VendedorRepository();
        this.vendedorService = new VendedorService(vendedorRepository);
    }

    public Pedido novoPedido(String nomeCliente, Pedido pedidoAtual, Vendedor vendedorAtual) {
        if (pedidoAtual != null) {
            JOptionPane.showMessageDialog(null,
                "Já existe um pedido em andamento. Finalize o pedido atual antes de criar um novo.",
                "Pedido em Andamento",
                JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Nome do cliente inválido.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return pedidoService.criarPedido(nomeCliente, vendedorAtual);
    }

    public void mostrarBonus(Vendedor vendedor) {
        if (vendedor == null) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao acessar informações do vendedor.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double bonus = vendedor.getBonus();
        JOptionPane.showMessageDialog(null,
                "Vendedor: " + vendedor.getNome() + "\n" +
                "Bônus Acumulado: " + FormatadorMoeda.formatar(bonus) + "\n" +
                "Total a Receber: " + FormatadorMoeda.formatar(bonus),
                "Informações do Vendedor",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void encerrarTurno(Vendedor vendedor) {
        if (vendedor == null) return;

        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Deseja realmente encerrar o turno?",
                "Confirmar Saída",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            double bonus = vendedor.getBonus();
            JOptionPane.showMessageDialog(null,
                    "==== RESUMO FINAL DO TURNO ====\n\n" +
                    "Vendedor: " + vendedor.getNome() + "\n" +
                    "Bônus Acumulado: " + FormatadorMoeda.formatar(bonus) + "\n" +
                    "Total a Receber: " + FormatadorMoeda.formatar(bonus) + "\n\n" +
                    "Volte sempre!",
                    "Turno Encerrado",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
