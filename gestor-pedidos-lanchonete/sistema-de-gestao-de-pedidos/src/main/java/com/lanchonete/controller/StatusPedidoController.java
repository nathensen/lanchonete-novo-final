package com.lanchonete.controller;

import java.util.List;

import com.lanchonete.model.Pedido;
import com.lanchonete.service.StatusPedidoService;

public class StatusPedidoController {

    private StatusPedidoService service;

    public StatusPedidoController() {
        service = new StatusPedidoService();
    }

    public void adicionarPedido(Pedido pedido) {
        service.adicionarPedido(pedido);
    }

    public void marcarComoPronto(Pedido pedido) {
        service.marcarComoPronto(pedido);
    }

    public void confirmarEntrega(Pedido pedido) {
        service.confirmarEntrega(pedido);
    }

    public void excluirPedido(Pedido pedido) {
        service.excluirPedido(pedido);
    }

    public List<Pedido> listarPedidos() {
        return service.listarPedidos();
    }

    public boolean isEmpty() {
        return service.isEmpty();
    }
}
