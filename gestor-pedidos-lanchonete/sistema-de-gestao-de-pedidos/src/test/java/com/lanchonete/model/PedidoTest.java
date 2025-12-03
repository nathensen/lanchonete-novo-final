package com.lanchonete.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PedidoTest {

    static class ItemFake implements ItemPedido {
        private final double preco;

        public ItemFake(double preco) {
            this.preco = preco;
        }

        @Override
        public double getPrecoVenda() {
            return preco;
        }

        @Override
        public String descricao() {
            return "item fake";
        }
    }

    @Test
    void deveCalcularTotalCorretamente() {
        Vendedor vendedor = new Vendedor("Vendedor Teste", 1);
        Pedido pedido = new Pedido("Cliente Teste", vendedor);

        pedido.adicionarItem(new ItemFake(10.0));
        pedido.adicionarItem(new ItemFake(15.5));
        pedido.adicionarItem(new ItemFake(4.5));

        double total = pedido.calcularTotal();

        assertEquals(30.0, total, 0.0001);
    }
}
