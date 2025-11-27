package com.lanchonete.model;

public class Vendedor {

    private String nome;
    private int codigo;
    private double bonus;

    public Vendedor(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.bonus = 0.0;
    }

    public String getNome() { return nome; }

    public int getCodigo() { return codigo; }

    public double getBonus() { return bonus; }

    public void adicionarBonus(double valor) {
        this.bonus += valor;
    }
}
