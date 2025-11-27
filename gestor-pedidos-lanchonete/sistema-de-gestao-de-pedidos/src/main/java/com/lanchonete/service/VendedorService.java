package com.lanchonete.service;

import java.util.List;

import com.lanchonete.model.Vendedor;
import com.lanchonete.repository.IVendedorRepository;

public class VendedorService {

    private final IVendedorRepository repository;

    public VendedorService(IVendedorRepository repository) {
        this.repository = repository;
    }

    public Vendedor criarVendedor(String nome, int codigo) {
        return new Vendedor(nome, codigo);
    }

    public void salvarVendedor(Vendedor vendedor) {
        repository.salvar(vendedor);
    }

    public Vendedor buscarPorCodigo(int codigo) {
        return repository.buscarPorCodigo(codigo);
    }

    public List<Vendedor> listarTodos() {
        return repository.listarTodos();
    }

    public double calcularTotalReceber(Vendedor vendedor) {
        return vendedor.getBonus();
    }

    public String gerarInformacoes(Vendedor vendedor) {
        return "=== Informações do Vendedor ===\n" +
                "Nome: " + vendedor.getNome() + "\n" +
                "Código: " + vendedor.getCodigo() + "\n" +
                "Bônus acumulado: R$ " + String.format("%.2f", vendedor.getBonus()) + "\n" +
                "Total a receber: R$ " + String.format("%.2f", calcularTotalReceber(vendedor)) + "\n";
    }

    public String gerarResumoFinalTurno(Vendedor vendedor) {
        return "\n======== RESUMO FINAL DO TURNO ========\n" +
                gerarInformacoes(vendedor) +
                "=======================================\n";
    }
    
}
