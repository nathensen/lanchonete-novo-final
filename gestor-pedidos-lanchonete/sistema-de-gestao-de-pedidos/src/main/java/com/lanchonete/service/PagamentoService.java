package com.lanchonete.service;

import com.lanchonete.model.Pagamento;
import com.lanchonete.repository.PagamentoRepository;

public class PagamentoService {

    private PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public String processarPagamento(Pagamento metodo, double valor) {
        String resultado = metodo.processar(valor);
        repository.salvar(resultado + "\n");
        return resultado;
    }

    public java.util.List<String> listarPagamentos() {
        return repository.listar();
    }
}
