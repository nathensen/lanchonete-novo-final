package com.lanchonete.controller;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.model.Lanche;
import com.lanchonete.model.Pedido;

public class LancheController {

    private List<Lanche> lanches;

    public LancheController() {
        lanches = criarLanches();
    }

    private List<Lanche> criarLanches() {
        List<Lanche> listaLanches = new ArrayList<>();

        listaLanches.add(new Lanche(8.0, "Francês", "Queijo e Presunto", "Maionese"));                // Misto
        listaLanches.add(new Lanche(10.0, "Brioche", "Carne", "Barbecue"));                           // Hambúrguer
        listaLanches.add(new Lanche(12.0, "Australiano", "Carne, Queijo", "Maionese"));               // X-Burger
        listaLanches.add(new Lanche(13.0, "Francês", "Carne, Ovo", "Maionese"));                      // X-Eggs
        listaLanches.add(new Lanche(15.0, "Brioche", "Duas Carnes", "Barbecue"));                     // Duplo
        listaLanches.add(new Lanche(14.0, "Australiano", "Carne, Calabresa", "Maionese"));            // X-Calabresa
        listaLanches.add(new Lanche(16.0, "Francês", "Carne, Ovo, Calabresa", "Maionese"));           // X-Eggs Calabresa
        listaLanches.add(new Lanche(15.0, "Brioche", "Carne, Bacon", "Barbecue"));                    // X-Bacon
        listaLanches.add(new Lanche(17.0, "Francês", "Carne, Ovo, Bacon", "Maionese"));               // X-Eggs Bacon
        listaLanches.add(new Lanche(18.0, "Brioche", "Duas Carnes, Bacon", "Barbecue"));              // X-Duplo com bacon
        listaLanches.add(new Lanche(17.0, "Australiano", "Carne, Bacon, Calabresa", "Maionese"));     // X-Bacon com Calabresa
        listaLanches.add(new Lanche(20.0, "Francês", "Carne, Ovo, Bacon, Calabresa, Queijo", "Maionese, Barbecue")); // X-Tudo

        return listaLanches;
    }

    public List<Lanche> getLanches() {
        return lanches;
    }

    public void adicionarAoPedido(Pedido pedido, int index) {
        if (pedido != null && index >= 0 && index < lanches.size()) {
            pedido.adicionarItem(lanches.get(index));
        }
    }
}
