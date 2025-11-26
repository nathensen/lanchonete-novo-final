package com.lanchonete.model;

public class DinheiroPagamento implements Pagamento { 
    
    private double valorRecebido;

    public DinheiroPagamento(double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public double getValorRecebido() {
        return valorRecebido;
    }

    @Override 
    public String processar(double valor) {  
        double troco = valorRecebido - valor;

        if (valorRecebido < valor) {
            return "Valor insuficiente! Faltam R$ " +
                    String.format("%.2f", valor - valorRecebido);
        }

        return "Pagamento em dinheiro confirmado. Troco: R$ " +
                String.format("%.2f", troco);
    }
}
