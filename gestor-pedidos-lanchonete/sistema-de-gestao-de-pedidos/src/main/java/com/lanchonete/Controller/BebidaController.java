package com.lanchonete.controller;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Bebidas;
import com.lanchonete.model.Pedido;

public class BebidaController {

    private List<Bebidas> bebidas;

    private static final String[] SABORES_SUCO = { "Goiaba", "Acerola", "Maracujá", "Abacaxi" };
    private static final String[] REFRIGERANTES = { "Coca-Cola", "Guaraná" };
    private static final String[] TAMANHOS_REFRI = { "Lata (350ml)", "Garrafa (1L)", "Garrafa (2L)" };
    private static final String[] TAMANHOS_AGUA = { "Garrafa (250ml)", "Garrafa (500ml)", "Garrafa (2L)" };

    public BebidaController() {
        bebidas = criarTodasBebidas();
    }

    private List<Bebidas> criarTodasBebidas() {
        List<Bebidas> bebidasList = new ArrayList<>();

        // Sucos
        for (int i = 0; i < SABORES_SUCO.length; i++) {
            double preco = (i == 1) ? 5.0 : 4.0; // Acerola é R$5, outros são R$4
            bebidasList.add(new Bebidas(preco, "Suco", "de " + SABORES_SUCO[i], "300ml"));
        }

        // Refrigerantes
        for (int i = 0; i < REFRIGERANTES.length; i++) {
            for (int j = 0; j < TAMANHOS_REFRI.length; j++) {
                double preco = 0;
                switch(j) {
                    case 0: preco = 4.0; break;  // Lata (350ml)
                    case 1: preco = 8.0; break;  // Garrafa (1L)
                    case 2: preco = 12.0; break; // Garrafa (2L)
                }
                bebidasList.add(new Bebidas(preco, "Refrigerante", REFRIGERANTES[i], TAMANHOS_REFRI[j]));
            }
        }

        // Água Mineral
        for (int i = 0; i < TAMANHOS_AGUA.length; i++) {
            double preco = 0;
            switch(i) {
                case 0: preco = 2.0; break;
                case 1: preco = 4.0; break;
                case 2: preco = 6.0; break;
            }
            bebidasList.add(new Bebidas(preco, "Água Mineral", "Natural", TAMANHOS_AGUA[i]));
        }

        return bebidasList;
    }

    public List<Bebidas> getBebidas() {
        return bebidas;
    }

    public String[] getNomesBebidas() {
        String[] nomes = new String[bebidas.size()];
        int index = 0;

        // Sucos
        for (int i = 0; i < SABORES_SUCO.length; i++) {
            nomes[index++] = "Suco de " + SABORES_SUCO[i] + " (300ml)";
        }

        // Refrigerantes
        for (int i = 0; i < REFRIGERANTES.length; i++) {
            for (int j = 0; j < TAMANHOS_REFRI.length; j++) {
                nomes[index++] = REFRIGERANTES[i] + " - " + TAMANHOS_REFRI[j];
            }
        }

        // Águas
        for (int i = 0; i < TAMANHOS_AGUA.length; i++) {
            nomes[index++] = "Água Mineral - " + TAMANHOS_AGUA[i];
        }

        return nomes;
    }

    public void adicionarAoPedido(Pedido pedido, int index) {
        if (pedido != null && index >= 0 && index < bebidas.size()) {
            pedido.adicionarItem(bebidas.get(index));
        }
    }
}