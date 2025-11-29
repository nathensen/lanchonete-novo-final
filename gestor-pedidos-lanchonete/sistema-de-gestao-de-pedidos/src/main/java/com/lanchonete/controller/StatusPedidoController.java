package com.lanchonete.controller;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Pedido;

public class StatusPedidoController {

    private List<Pedido> pedidosEmProducao;

    public StatusPedidoController() {
        pedidosEmProducao = new ArrayList<>();
    }

    public void adicionarPedido(Pedido pedido) {
        if (pedido != null && !pedidosEmProducao.contains(pedido)) {
            pedido.setStatusEntrega("em_producao");
            pedidosEmProducao.add(pedido);
        }
    }

    public void confirmarEntrega(Pedido pedido) {
        if (pedido != null && pedidosEmProducao.contains(pedido)) {
            pedido.setStatusEntrega("entregue");
            pedidosEmProducao.remove(pedido);
        }
    }

    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidosEmProducao);
    }

    public boolean isEmpty() {
        return pedidosEmProducao.isEmpty();
    }
}
