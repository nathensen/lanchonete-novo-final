package com.lanchonete.service;

import com.lanchonete.model.ItemPedido;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class PedidoService {

    // Cria um novo pedido com vendedor
    public Pedido criarPedido(String nomeCliente, Vendedor vendedor) {
        return new Pedido(nomeCliente, vendedor);
    }

    // Adiciona item ao pedido
    public void adicionarItem(Pedido pedido, ItemPedido item) {
        pedido.adicionarItem(item);
    }

    // Verifica se pedido está vazio
    public boolean pedidoVazio(Pedido pedido) {
        return pedido.getItensConsumidos().isEmpty();
    }

    // Finaliza o pedido: valida valor, atualiza bônus e calcula troco
    public double finalizarPedido(Pedido pedido, double valorPago, Vendedor vendedor) {
        if (pedido == null) {
            throw new IllegalArgumentException("Nenhum pedido ativo.");
        }

        double total = pedido.calcularTotal();

        if (total <= 0) {
            throw new IllegalArgumentException("O pedido está vazio.");
        }

        if (valorPago < total) {
            throw new IllegalArgumentException(
                "O valor pago é insuficiente. Total: R$ " + String.format("%.2f", total)
            );
        }

        // Atualiza bônus do vendedor
        double bonusPedido = vendedor.calcularBonus(total);

        // Calcula troco
        return valorPago - total;
    }
}
