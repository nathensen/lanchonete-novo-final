package com.lanchonete.model;

public class DinheiroPagamento implements Pagamento{

    @Override
    public String processar(double valor) {
        return "Pagamento de R$ " + valor + " realizado via dinheiro";
    }

}
