package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Lanche;

public class LancheRepository {

    private List<Lanche> lanches;

    public LancheRepository() {
        lanches = new ArrayList<>();
        inicializarLanches();
    }

    private void inicializarLanches() {

        lanches.add(new Lanche(10.0, "X-Burguer", "Carne", "Pão de forma"));
        lanches.add(new Lanche(12.0, "X-Salada", "Carne", "Pão de forma"));
        lanches.add(new Lanche(11.0, "X-Frango", "Frango", "Pão de forma"));
        lanches.add(new Lanche(13.0, "X-Egg", "Carne", "Pão de forma"));
        lanches.add(new Lanche(15.0, "X-Bacon", "Carne", "Pão de forma"));
        lanches.add(new Lanche(14.0, "X-Tudo", "Carne", "Pão de forma"));
        lanches.add(new Lanche(12.0, "Cheeseburger", "Carne", "Pão de hambúrguer"));
        lanches.add(new Lanche(13.0, "Hambúrguer de Frango", "Frango", "Pão de hambúrguer"));
        lanches.add(new Lanche(16.0, "Duplo X-Burguer", "Carne", "Pão de forma"));
        lanches.add(new Lanche(18.0, "Triplo X-Burguer", "Carne", "Pão de forma"));
        lanches.add(new Lanche(17.0, "X-Calabresa", "Calabresa", "Pão de forma"));
        lanches.add(new Lanche(19.0, "X-Atum", "Atum", "Pão de forma"));
        lanches.add(new Lanche(20.0, "X-Frango Bacon", "Frango e Bacon", "Pão de forma"));
    }

    public List<Lanche> listarLanches() {
        return lanches;
    }

    public Lanche getLanche(int index) {
        if (index < 0 || index >= lanches.size()) return null;
        return lanches.get(index);
    }
}
