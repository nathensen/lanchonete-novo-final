package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Pedido;

public class PedidoRepository {

    private final List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) {
        if (pedido != null) pedidos.add(pedido);
    }

    public void remover(Pedido pedido) {
        pedidos.remove(pedido);
    }

    public List<Pedido> listar() {
        return new ArrayList<>(pedidos);
    }

    public void limpar() {
        pedidos.clear();
    }
}
