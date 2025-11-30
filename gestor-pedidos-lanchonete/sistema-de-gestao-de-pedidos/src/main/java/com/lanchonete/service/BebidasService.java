package com.lanchonete.service;

import java.util.List;

import com.lanchonete.model.Bebidas;
import com.lanchonete.model.Pedido;
import com.lanchonete.repository.BebidasRepository;

public class BebidasService {

    private BebidasRepository repository;

    public BebidasService() {
        this.repository = new BebidasRepository();
    }

    public List<Bebidas> listarTodas() {
        return repository.getTodasBebidas();
    }

    public Bebidas buscarPorIndex(int index) {
        List<Bebidas> lista = repository.getTodasBebidas();

        if (index < 0 || index >= lista.size()) return null;

        return lista.get(index);
    }

    public boolean adicionarBebidaAoPedido(Pedido pedido, Bebidas bebidaSelecionada) {
        if (pedido == null) return false;
        if (bebidaSelecionada == null) return false;

        pedido.adicionarItem(bebidaSelecionada);
        return true;
    }
}
