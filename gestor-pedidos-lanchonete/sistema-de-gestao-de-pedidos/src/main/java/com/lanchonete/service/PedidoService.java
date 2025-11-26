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

    // Finaliza o pedido: apenas atualiza bônus
    public void finalizarPedido(Pedido pedido, Vendedor vendedor) {
        if (pedido == null) {
            throw new IllegalArgumentException("Nenhum pedido ativo.");
        }

        double total = pedido.calcularTotal();

        if (total <= 0) {
            throw new IllegalArgumentException("O pedido está vazio.");
        }

        // Atualiza bônus do vendedor
        vendedor.calcularBonus(total);
    }
}
