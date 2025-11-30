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
            pedido.setStatusEntrega("EM PRODUÇÃO"); // removido colchetes para ficar mais limpo
            pedidosEmProducao.add(pedido);
        }
    }

    public void marcarComoPronto(Pedido pedido) {
        if (pedido != null && pedidosEmProducao.contains(pedido)) {
            pedido.setStatusEntrega("PRONTO PARA ENTREGA");
        }
    }

    public void confirmarEntrega(Pedido pedido) {
        if (pedido != null && pedidosEmProducao.contains(pedido)) {
            pedido.setStatusEntrega("RETIRADO PELO CLIENTE");
        }
    }

    public void excluirPedido(Pedido pedido) {
        if (pedido != null) {
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
