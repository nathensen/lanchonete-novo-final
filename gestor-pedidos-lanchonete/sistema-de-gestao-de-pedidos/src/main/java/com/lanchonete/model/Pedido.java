package com.lanchonete.model;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.util.FormatadorMoeda;
import com.lanchonete.util.Validador;

public class Pedido {
    private String nomeCliente;
    private Vendedor vendedor; // Vendedor associado ao pedido
    private List<ItemPedido> itensConsumidos;

    public Pedido(String nomeCliente, Vendedor vendedor) {
        Validador.validarString(nomeCliente, "Nome do cliente não pode ser vazio");
        if (vendedor == null) {
            throw new IllegalArgumentException("Vendedor não pode ser nulo");
        }
        this.nomeCliente = nomeCliente;
        this.vendedor = vendedor;
        this.itensConsumidos = new ArrayList<>();
    }

    public void adicionarItem(ItemPedido item) {
        if (item != null) {
            itensConsumidos.add(item);
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itensConsumidos) {
            total += item.getPrecoVenda();
        }
        return total;
    }

    public void mostrarFatura() {
        System.out.println("------- ProgLanches -------");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Vendedor: " + vendedor.getNome());
        System.out.println("Itens consumidos:");
        for (ItemPedido item : itensConsumidos) {
            System.out.println("- " + item.descricao() + " (" + FormatadorMoeda.formatar(item.getPrecoVenda()) + ")");
        }
        System.out.println("Total: " + FormatadorMoeda.formatar(calcularTotal()));
    }

    public double calcularTroco(double valorPago) {
        return valorPago - calcularTotal();
    }

    // Getters
    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<ItemPedido> getItensConsumidos() {
        return new ArrayList<>(itensConsumidos);
    }

    public boolean isEmpty() {
        return itensConsumidos.isEmpty();
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    // Resumo para histórico
    public String gerarResumoHistorico() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido | Cliente: ").append(nomeCliente)
          .append(" | Vendedor: ").append(vendedor.getNome())
          .append(" | Itens: ");

        for (ItemPedido item : itensConsumidos) {
            sb.append(item.descricao()).append(", ");
        }

        if (!itensConsumidos.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove a última vírgula e espaço
        }

        sb.append(" | Total: ").append(FormatadorMoeda.formatar(calcularTotal()));
        return sb.toString();
    }
}
