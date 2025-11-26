package com.lanchonete.controller;

import java.util.List;

import com.lanchonete.model.Pagamento;
import com.lanchonete.repository.RepositoryPagamento;

public class PagamentoController {

    private RepositoryPagamento repository;

    public PagamentoController(){
        repository = new RepositoryPagamento();

    }

    public String realizarPagamento(Pagamento metodo, double valor){
        String resultado = metodo.processar(valor);
        repository.salvar(resultado + "\n");
        System.out.println("Pagamento registrado: " + resultado);
        return resultado;
    }

    public List<String> listarPagamentos(){
        List<String> lista = repository.listar();
        System.out.println("Lista de pagamentos: "+ lista);
        return lista;        
    }
}
