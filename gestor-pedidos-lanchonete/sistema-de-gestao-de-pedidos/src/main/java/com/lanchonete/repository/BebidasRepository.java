package com.lanchonete.repository;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Bebidas;

public class BebidasRepository {

    private List<Bebidas> bebidas;

    public BebidasRepository() {
        this.bebidas = new ArrayList<>();
        inicializarBebidas();
    }

    private void inicializarBebidas() {
        // Sucos
        String[] saboresSuco = {"Goiaba", "Acerola", "Maracujá", "Abacaxi"};
        for (int i = 0; i < saboresSuco.length; i++) {
            double preco = (i == 1) ? 5.0 : 4.0;
            bebidas.add(new Bebidas(preco, "Suco", "de " + saboresSuco[i], "300ml"));
        }

        // Refrigerantes
        String[] refrigerantes = {"Coca-Cola", "Guaraná"};
        String[] tamanhosRefri = {"Lata (350ml)", "Garrafa (1L)", "Garrafa (2L)"};
        for (String refri : refrigerantes) {
            for (int j = 0; j < tamanhosRefri.length; j++) {
                double preco = switch (j) {
                    case 0 -> 4.0;
                    case 1 -> 8.0;
                    case 2 -> 12.0;
                    default -> 0;
                };
                bebidas.add(new Bebidas(preco, "Refrigerante", refri, tamanhosRefri[j]));
            }
        }

        // Água mineral
        String[] tamanhosAgua = {"Garrafa (250ml)", "Garrafa (500ml)", "Garrafa (2L)"};
        for (int i = 0; i < tamanhosAgua.length; i++) {
            double preco = switch (i) {
                case 0 -> 2.0;
                case 1 -> 4.0;
                case 2 -> 6.0;
                default -> 0;
            };
            bebidas.add(new Bebidas(preco, "Água Mineral", "Natural", tamanhosAgua[i]));
        }
    }

    public List<Bebidas> getTodasBebidas() {
        return bebidas;
    }
}

