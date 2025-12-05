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
        return new Vendedor(codigo, nome);
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

    public double calcularBonusSobreTotal(double totalVendido) {
        return totalVendido * 0.005; // 0,5%
    }

    public String gerarInformacoes(Vendedor vendedor, double totalVendido) {
        double bonusDoTurno = calcularBonusSobreTotal(totalVendido);

        return "=== Informações do Vendedor ===\n" +
                "Nome: " + vendedor.getNome() + "\n" +
                "Código: " + vendedor.getCodigo() + "\n" +
                "Total vendido no turno: R$ " + String.format("%.2f", totalVendido) + "\n" +
                "Bônus do turno (0,5%): R$ " + String.format("%.2f", bonusDoTurno) + "\n" +
                "Bônus acumulado: R$ " + String.format("%.2f", vendedor.getBonus()) + "\n";
    }

    public String gerarResumoFinalTurno(Vendedor vendedor, double totalVendido) {
        return "\n======== RESUMO FINAL DO TURNO ========\n" +
                gerarInformacoes(vendedor, totalVendido) +
                "=======================================\n";
    }
}
