package tecdes.pagamento.controller;

import java.util.List;

import tecdes.pagamento.model.Pagamento;
import tecdes.pagamento.repository.RepositoryPagamento;

public class ControllerPagamento {

    private RepositoryPagamento repository;

    public ControllerPagamento(){
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
