package com.lanchonete.service;

import com.lanchonete.model.Vendedor;
import com.lanchonete.view.FormatadorMoeda;

public class VendedorService {
    /**
     * Cria uma nova instância de Vendedor
     */
    public Vendedor criarVendedor(String nome, double codigo) {
        return new Vendedor(nome, codigo);
    }
    
    /**
     * Calcula o valor total a receber (código + bônus)
     */
    public double calcularTotalReceber(Vendedor vendedor) {
        return vendedor.getCodigo() + vendedor.getBonus();
    }
    
    public void mostrarInformacoes(Vendedor vendedor) {
        System.out.println("\n=== Informações do Vendedor ===");
        System.out.println("Nome: " + vendedor.getNome());
        System.out.println("Código do Vendedor: " + FormatadorMoeda.formatar(vendedor.getCodigo()));
        System.out.println("Bônus acumulado: " + FormatadorMoeda.formatar(vendedor.getBonus()));
        System.out.println("Total a receber: " + 
                FormatadorMoeda.formatar(vendedor.getCodigo() + vendedor.getBonus()));
    }
    
    public void mostrarResumoFinalTurno(Vendedor vendedor) {
        System.out.println("\n======== RESUMO FINAL DO TURNO ========");
        System.out.println("Vendedor: " + vendedor.getNome());
        System.out.println("Código do Vendedor: " + FormatadorMoeda.formatar(vendedor.getCodigo()));
        System.out.println("Bônus total acumulado: " + FormatadorMoeda.formatar(vendedor.getBonus()));
        System.out.println("Valor total a receber: " + 
                FormatadorMoeda.formatar(vendedor.getCodigo() + vendedor.getBonus()));
        System.out.println("=======================================");
    }
}