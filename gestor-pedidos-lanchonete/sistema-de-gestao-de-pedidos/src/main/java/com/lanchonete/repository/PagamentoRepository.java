package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

public class PagamentoRepository {

    private final List<String> pagamentos = new ArrayList<>();

    public void salvar(String pagamento) {
        if (pagamento != null && !pagamento.isEmpty()) {
            pagamentos.add(pagamento);
        }
    }

    public List<String> listar() {
        return new ArrayList<>(pagamentos);
    }

    public void limpar() {
        pagamentos.clear();
    }
}
