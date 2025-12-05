package com.lanchonete.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PagamentoDinheiroTest {

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
    void deveCalcularTrocoCorretamente() {
        Vendedor vendedor = new Vendedor(1, "Vendedor Teste");
        Pedido pedido = new Pedido("Cliente Teste", vendedor);

        pedido.adicionarItem(new ItemFake(20.0));

        double troco = pedido.calcularTroco(50.0);

        assertEquals(30.0, troco, 0.0001);
    }
}
