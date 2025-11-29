package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Salgadinho;

public class SalgadinhoRepository {
    private List<Salgadinho> salgadinhos;

    public SalgadinhoRepository() {
        salgadinhos = new ArrayList<>();
        inicializarSalgadinhos();
    }

    private void inicializarSalgadinhos() {
        salgadinhos.add(new Salgadinho(3.0, "Coxinha", "Frita", "Carne"));
        salgadinhos.add(new Salgadinho(3.0, "Coxinha", "Frita", "Frango"));
        salgadinhos.add(new Salgadinho(4.0, "Pastel", "Frito", "Carne"));
        salgadinhos.add(new Salgadinho(4.0, "Pastel", "Frito", "Frango"));
        salgadinhos.add(new Salgadinho(5.0, "Pastel", "Assado", "Carne"));
        salgadinhos.add(new Salgadinho(5.0, "Pastel", "Assado", "Frango"));
        salgadinhos.add(new Salgadinho(6.0, "Empanado", "Frito", "Frango"));
    }

    public List<Salgadinho> listarTodos() {
        return new ArrayList<>(salgadinhos);
    }

    public Salgadinho buscarPorIndice(int index) {
        if (index < 0 || index >= salgadinhos.size()) return null;
        return salgadinhos.get(index);
    }
}

