package com.lanchonete.service;

import java.util.List;

import com.lanchonete.model.Lanche;
import com.lanchonete.model.Pedido;
import com.lanchonete.repository.LancheRepository;

public class LancheService {

    private LancheRepository repository;

    public LancheService() {
        this.repository = new LancheRepository();
    }

    public List<Lanche> listarTodos() {
        return repository.listarLanches();
    }

    public Lanche buscarPorIndex(int index) {
        return repository.getLanche(index);
    }

    public boolean adicionarLancheAoPedido(Pedido pedido, Lanche lancheSelecionado) {

        if (pedido == null) return false;
        if (lancheSelecionado == null) return false;

        pedido.adicionarItem(lancheSelecionado);
        return true;
    }
}
