package com.lanchonete.model;

public class CartaoPagamento implements Pagamento {

    private String tipo;     
    private int parcelas;    

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    @Override
    public String processar(double valor) {

        if (tipo == null) {
            tipo = "Desconhecido";
        }

        if (tipo.equalsIgnoreCase("Crédito")) {
            return "Pagamento de R$ " + valor +
                    " via Cartão de Crédito em " + parcelas + "x.";
        }

        return "Pagamento de R$ " + valor +
                " via Cartão de Débito.";
    }
}
