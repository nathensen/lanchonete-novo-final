package com.lanchonete.service;

import java.util.List;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Salgadinho;
import com.lanchonete.repository.SalgadinhoRepository;

public class SalgadinhoService {

    private SalgadinhoRepository repository;

    public SalgadinhoService() {
        this.repository = new SalgadinhoRepository();
    }

    public List<Salgadinho> listarSalgadinhos() {
        return repository.listarTodos();
    }

    public boolean adicionarSalgadinhoAoPedido(Pedido pedido, int index) {
        if (pedido == null) return false;

        Salgadinho s = repository.buscarPorIndice(index);
        if (s == null) return false;

        pedido.adicionarItem(s);
        return true;
    }
}
