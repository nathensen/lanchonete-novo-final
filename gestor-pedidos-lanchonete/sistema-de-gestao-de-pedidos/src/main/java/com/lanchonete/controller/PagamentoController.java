package com.lanchonete.controller;

import java.util.List;

import com.lanchonete.model.Pagamento;
import com.lanchonete.repository.PagamentoRepository;
import com.lanchonete.service.PagamentoService;

public class PagamentoController {

    private PagamentoService pagamentoService;

    public PagamentoController() {
        PagamentoRepository repository = new PagamentoRepository();
        this.pagamentoService = new PagamentoService(repository);
    }

    public String realizarPagamento(Pagamento metodo, double valor) {
        String resultado = pagamentoService.processarPagamento(metodo, valor);
        System.out.println("Pagamento registrado: " + resultado);
        return resultado;
    }

    public List<String> listarPagamentos() {
        List<String> lista = pagamentoService.listarPagamentos();
        System.out.println("Lista de pagamentos: " + lista);
        return lista;
    }
}
