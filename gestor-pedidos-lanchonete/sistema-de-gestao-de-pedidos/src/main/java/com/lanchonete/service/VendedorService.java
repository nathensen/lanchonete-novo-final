/**
 * VendedorService
 * ----------------
 * Camada responsável pela REGRA DE NEGÓCIO dos vendedores.
 *
 * Ela não cuida de interface visual nem de persistência direta.
 * Aqui fica:
 *  - criação de vendedores
 *  - cálculos (ex.: bônus, total a receber)
 *  - geração de relatórios (resumo de turno, informações)
 *
 * O Repository é usado SOMENTE para salvar, remover e buscar os dados.
 */

package com.lanchonete.service;

import java.util.List;

import javax.swing.JOptionPane;

import com.lanchonete.model.Vendedor;
import com.lanchonete.repository.IVendedorRepository;

public class VendedorService {

    private final IVendedorRepository repository;

    public VendedorService(IVendedorRepository repository) {
        this.repository = repository;
    }

    // Cria vendedor (não salva automaticamente)
    public Vendedor criarVendedor(String nome, int codigo) {
        return new Vendedor(nome, codigo);
    }

    // Salva no repositório
    public void salvarVendedor(Vendedor vendedor) {
        repository.salvar(vendedor);
    }

    // Busca vendedor pelo código
    public Vendedor buscarPorCodigo(int codigo) {
        return repository.buscarPorCodigo(codigo);
    }

    // Lista todos os vendedores
    public List<Vendedor> listarTodos() {
        return repository.listarTodos();
    }

    // Calcula total a receber (apenas bônus aqui)
    public double calcularTotalReceber(Vendedor vendedor) {
        return vendedor.getBonus();
    }

    // Gera string de informações do vendedor
    public String gerarInformacoes(Vendedor vendedor) {
        return "=== Informações do Vendedor ===\n" +
               "Nome: " + vendedor.getNome() + "\n" +
               "Código: " + vendedor.getCodigo() + "\n" +
               "Bônus acumulado: R$ " + String.format("%.2f", vendedor.getBonus()) + "\n" +
               "Total a receber: R$ " + String.format("%.2f", calcularTotalReceber(vendedor)) + "\n";
    }

    // Gera RESUMO final do turno
    public String gerarResumoFinalTurno(Vendedor vendedor) {
        return "\n======== RESUMO FINAL DO TURNO ========\n" +
               gerarInformacoes(vendedor) +
               "=======================================\n";
    }

    /**
     * Novo método: MOSTRA o resumo diretamente na tela
     * (para combinação com o seu MenuController).
     */
    public void mostrarResumoFinalTurno(Vendedor vendedor) {
        String resumo = gerarResumoFinalTurno(vendedor);
        JOptionPane.showMessageDialog(null, resumo, 
                "Resumo Final do Turno",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
