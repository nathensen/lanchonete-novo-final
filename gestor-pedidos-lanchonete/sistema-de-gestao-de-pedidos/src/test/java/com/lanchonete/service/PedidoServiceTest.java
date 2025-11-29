package com.lanchonete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.lanchonete.model.ItemPedido;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

class PedidoServiceTest {

    // Classe fake para teste
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
    void deveAdicionarBonusIgualAoTotalDoPedido() {
        // Arrange
        PedidoService service = new PedidoService();
        Vendedor vendedor = new Vendedor("João", 123);
        Pedido pedido = new Pedido("Cliente Teste", vendedor);

        pedido.adicionarItem(new ItemFake(50.0));  // total = 50

        // Act
        service.finalizarPedido(pedido, vendedor);

        // Assert
        assertEquals(50.0, vendedor.getBonus()); // bônus é igual ao total
    }
}
