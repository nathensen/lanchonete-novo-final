package com.lanchonete.service;

import com.lanchonete.model.Vendedor;
import com.lanchonete.repository.IVendedorRepository;
import com.lanchonete.util.FormatadorMoeda;
import java.util.List;

public class VendedorService {
    private final IVendedorRepository repository;

    public VendedorService(IVendedorRepository repository) {
        this.repository = repository;
    }

    // Cria um vendedor (não salva automaticamente)
    public Vendedor criarVendedor(String nome, double codigo) {
        return new Vendedor(nome, codigo);
    }

    // Salva no repositório
    public void salvarVendedor(Vendedor vendedor) {
        repository.salvar(vendedor);
    }

    // Busca um vendedor pelo código
    public Vendedor buscarPorCodigo(double codigo) {
        return repository.buscarPorCodigo(codigo);
    }

    // Lista todos os vendedores
    public List<Vendedor> listarTodos() {
        return repository.listarTodos();
    }

    // Calcula total a receber (código + bônus)
    public double calcularTotalReceber(Vendedor vendedor) {
        return vendedor.getCodigo() + vendedor.getBonus();
    }

    // Mostra informações do vendedor no console
    public void mostrarInformacoes(Vendedor vendedor) {
        System.out.println("\n=== Informações do Vendedor ===");
        System.out.println("Nome: " + vendedor.getNome());
        System.out.println("Código do Vendedor: " + FormatadorMoeda.formatar(vendedor.getCodigo()));
        System.out.println("Bônus acumulado: " + FormatadorMoeda.formatar(vendedor.getBonus()));
        System.out.println("Total a receber: " + FormatadorMoeda.formatar(calcularTotalReceber(vendedor)));
    }

    // Mostra resumo final do turno
    public void mostrarResumoFinalTurno(Vendedor vendedor) {
        System.out.println("\n======== RESUMO FINAL DO TURNO ========");
        mostrarInformacoes(vendedor);
        System.out.println("=======================================");
    }
}
