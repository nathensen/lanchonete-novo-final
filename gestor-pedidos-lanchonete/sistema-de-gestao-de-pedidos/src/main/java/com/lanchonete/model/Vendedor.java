package com.lanchonete.model;

public class Vendedor {

    private String nome;
    private int codigo;

    private double bonus;
    private double totalVendido; // NOVO

    public Vendedor(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.bonus = 0;
        this.totalVendido = 0;
    }

    public String getNome() { return nome; }
    public int getCodigo() { return codigo; }

    public double getBonus() { return bonus; }
    public void adicionarBonus(double valor) { this.bonus += valor; }

    public double getTotalVendido() { return totalVendido; }
    public void adicionarVenda(double valor) { this.totalVendido += valor; }

    public void resetarTurno() {
        this.totalVendido = 0;
        this.bonus = 0;
    }
}
