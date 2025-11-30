package com.lanchonete.model;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.util.FormatadorMoeda;
import com.lanchonete.util.Validador;

public class Pedido {

    private String nomeCliente;
    private Vendedor vendedor; 
    private List<ItemPedido> itensConsumidos;
    private String statusEntrega = "em_producao";

    public Pedido(String nomeCliente, Vendedor vendedor) {
        Validador.validarString(nomeCliente, "Nome do cliente não pode ser vazio");
        if (vendedor == null) throw new IllegalArgumentException("Vendedor não pode ser nulo");
        this.nomeCliente = nomeCliente;
        this.vendedor = vendedor;
        this.itensConsumidos = new ArrayList<>();
    }

    public String getStatusEntrega() { 
        return statusEntrega; 
    }

    public void adicionarItem(ItemPedido item) {
        if (item != null) itensConsumidos.add(item);
    }

    public void removerItem(int index) {
        if (index >= 0 && index < itensConsumidos.size()) itensConsumidos.remove(index);
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itensConsumidos) total += item.getPrecoVenda();
        return total;
    }

    public double calcularTroco(double valorPago) {
        return valorPago - calcularTotal();
    }

    public String gerarResumoHistorico() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido | Cliente: ").append(nomeCliente)
          .append(" | Vendedor: ").append(vendedor.getNome())
          .append(" | Itens: ");
        for (ItemPedido item : itensConsumidos) sb.append(item.descricao()).append(", ");
        if (!itensConsumidos.isEmpty()) sb.setLength(sb.length() - 2);
        sb.append(" | Total: ").append(FormatadorMoeda.formatar(calcularTotal()))
          .append(" | Status: ").append(statusEntrega);
        return sb.toString();
    }

    public String getNomeCliente() { return nomeCliente; }
    public Vendedor getVendedor() { return vendedor; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }
    public boolean isEmpty() { return itensConsumidos.isEmpty(); }
    public List<ItemPedido> getItensConsumidos() { return itensConsumidos; }
    public void setStatusEntrega(String statusEntrega) { this.statusEntrega = statusEntrega; }
}
