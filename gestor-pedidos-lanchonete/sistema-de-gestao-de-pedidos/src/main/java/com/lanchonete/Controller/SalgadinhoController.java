package com.lanchonete.controller;

import com.lanchonete.model.Salgadinho;
import com.lanchonete.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class SalgadinhoController {
    private List<Salgadinho> salgadinhos;
    private static final String[] NOMES_SALGADINHOS = {
        "Coxinha de Carne",
        "Coxinha de Frango",
        "Pastel Frito de Carne",
        "Pastel Frito de Frango",
        "Pastel Assado de Carne",
        "Pastel Assado de Frango",
        "Empanado de Frango"
    };

    public SalgadinhoController() {
        salgadinhos = criarSalgadinhos();
    }

    private List<Salgadinho> criarSalgadinhos() {
        List<Salgadinho> lista = new ArrayList<>();
        lista.add(new Salgadinho(3.0, "Coxinha", "Frita", "Carne"));
        lista.add(new Salgadinho(3.0, "Coxinha", "Frita", "Frango"));
        lista.add(new Salgadinho(4.0, "Pastel", "Frito", "Carne"));
        lista.add(new Salgadinho(4.0, "Pastel", "Frito", "Frango"));
        lista.add(new Salgadinho(5.0, "Pastel", "Assado", "Carne"));
        lista.add(new Salgadinho(5.0, "Pastel", "Assado", "Frango"));
        lista.add(new Salgadinho(6.0, "Empanado", "Frito", "Frango"));
        return lista;
    }

    public List<Salgadinho> getSalgadinhos() {
        return salgadinhos;
    }

    public String[] getNomesSalgadinhos() {
        String[] nomes = new String[salgadinhos.size()];
        for (int i = 0; i < salgadinhos.size(); i++) {
            Salgadinho s = salgadinhos.get(i);
            nomes[i] = NOMES_SALGADINHOS[i] + " - R$ " + String.format("%.2f", s.getPrecoVenda());
        }
        return nomes;
    }

    public void adicionarAoPedido(Pedido pedido, int index) {
        if (pedido == null) {
            throw new IllegalStateException("Pedido não iniciado.");
        }
        if (index < 0 || index >= salgadinhos.size()) {
            throw new IllegalArgumentException("Índice inválido.");
        }

        Salgadinho item = salgadinhos.get(index);
        pedido.adicionarItem(item);
    }
}
