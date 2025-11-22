package com.lanchonete.service;

import com.lanchonete.model.ItemPedido;
import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;
import com.lanchonete.view.FormatadorMoeda;

public class PedidoService {
    public Pedido criarPedido(String nomeCliente) {
        return new Pedido(nomeCliente);
    }
    
    public void adicionarItem(Pedido pedido, ItemPedido item) {
        if (item != null) {
            pedido.adicionarItem(item);
        }
    }
    
    public boolean pedidoVazio(Pedido pedido) {
        return pedido.calcularTotal() == 0;
    }
    
    public void mostrarFatura(Pedido pedido) {
        pedido.mostrarFatura();
    }
    
    public double calcularTroco(Pedido pedido, double valorPago) {
        double total = pedido.calcularTotal();
        return valorPago - total;
    }
    
    public double finalizarPedido(Pedido pedido, double valorPago, Vendedor vendedor) {
        double total = pedido.calcularTotal();
        double troco = valorPago - total;
        // Processa o bônus mas não imprime nada.
        vendedor.calcularBonus(total);
        
        return troco;
    }
}
