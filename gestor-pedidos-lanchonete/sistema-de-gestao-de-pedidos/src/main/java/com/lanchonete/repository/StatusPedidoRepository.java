package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Pedido;

public class StatusPedidoRepository {

    private List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void atualizar(Pedido pedido) {
        // Como é a mesma instância, nada precisa ser feito aqui
    }

    public void remover(Pedido pedido) {
        pedidos.remove(pedido);
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    public boolean existe(Pedido pedido) {
        return pedidos.contains(pedido);
    }

    public boolean vazio() {
        return pedidos.isEmpty();
    }
}
