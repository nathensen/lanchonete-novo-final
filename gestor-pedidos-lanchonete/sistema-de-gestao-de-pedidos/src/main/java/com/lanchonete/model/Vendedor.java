package com.lanchonete.model;


import com.lanchonete.util.Validador;

public class Vendedor {
    private String nome;
    private double codigo;
    private double bonus;
    private static final double TAXA_BONUS = 0.02; // 2% de comissão

    public Vendedor(String nome, double codigo) {
        Validador.validarString(nome, "Nome do vendedor não pode ser vazio");
        Validador.validarNumero(codigo, 0, "Código não pode ser negativo");
        
        this.nome = nome;
        this.codigo = codigo;
        this.bonus = 0;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public double getCodigo() {
        return codigo;
    }

    public double getBonus() {
        return bonus;
    }

    // Setters
    public void setNome(String nome) {
        Validador.validarString(nome, "Nome do vendedor não pode ser vazio!");
        this.nome = nome;
    }

    public void setCodigo(double codigo) {
        Validador.validarNumero(codigo, 0, "Senha náo pode ser negativa!");
        this.codigo = codigo;
    }

    public double calcularBonus(double valorVendido) {
        double bonusAtual = valorVendido * TAXA_BONUS;
        bonus += bonusAtual;
        return bonusAtual;
    }

}

