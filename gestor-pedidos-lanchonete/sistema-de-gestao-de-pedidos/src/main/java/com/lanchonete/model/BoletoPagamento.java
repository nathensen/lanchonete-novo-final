package com.lanchonete.model;

public class BoletoPagamento implements  Pagamento{

    @Override
    public String processar(double valor) {
        return "Pagamento de R$ " + valor + " Gerou um boleto bancario.";
    }

}
