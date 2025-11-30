package com.lanchonete.service;

import com.lanchonete.model.ItemPedido;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class PedidoService {

    public Pedido criarPedido(String nomeCliente, Vendedor vendedor) {
        return new Pedido(nomeCliente, vendedor);
    }

    public void adicionarItem(Pedido pedido, ItemPedido item) {
        pedido.adicionarItem(item);
    }

    public boolean pedidoVazio(Pedido pedido) {
        return pedido.getItensConsumidos().isEmpty();
    }

    public void finalizarPedido(Pedido pedido, Vendedor vendedor) {

    if (pedido == null) {
        throw new IllegalArgumentException("Nenhum pedido ativo.");
    }

    double total = pedido.calcularTotal();

    if (total <= 0) {
        throw new IllegalArgumentException("O pedido está vazio.");
    }

    // ➤ Soma no total vendido
    vendedor.adicionarVenda(total);

    // ➤ Calcula bônus (0,5%)
    double bonus = total * 0.005;

    // ➤ Acumula bônus no vendedor
    vendedor.adicionarBonus(bonus);
    
    }
}