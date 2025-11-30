package com.lanchonete.service;

import java.util.List;

import com.lanchonete.model.Pedido;
import com.lanchonete.repository.StatusPedidoRepository;

public class StatusPedidoService {

    private StatusPedidoRepository repository;

    public StatusPedidoService() {
        this.repository = new StatusPedidoRepository();
    }

    public void adicionarPedido(Pedido pedido) {
        if (pedido != null && !repository.existe(pedido)) {
            pedido.setStatusEntrega("EM PRODUÇÃO");
            repository.salvar(pedido);
        }
    }

    public void marcarComoPronto(Pedido pedido) {
        if (pedido != null && repository.existe(pedido)) {
            pedido.setStatusEntrega("PRONTO PARA ENTREGA");
            repository.atualizar(pedido);
        }
    }

    public void confirmarEntrega(Pedido pedido) {
        if (pedido != null && repository.existe(pedido)) {
            pedido.setStatusEntrega("RETIRADO PELO CLIENTE");
            repository.atualizar(pedido);
        }
    }

    public void excluirPedido(Pedido pedido) {
        if (pedido != null) {
            repository.remover(pedido);
        }
    }

    public List<Pedido> listarPedidos() {
        return repository.listarTodos();
    }

    public boolean isEmpty() {
        return repository.vazio();
    }
}
