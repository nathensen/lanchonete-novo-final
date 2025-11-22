package com.lanchonete.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.lanchonete.model.Bebidas;
import com.lanchonete.model.Pedido;
import com.lanchonete.view.MainFrame;

public class BebidasController {

    private MainFrame mainFrame;
    private List<Bebidas> bebidas;
    private static final String[] SABORES_SUCO = {"Goiaba", "Acerola", "Maracujá", "Abacaxi"};
    private static final String[] REFRIGERANTES = {"Coca-Cola", "Guaraná"};
    private static final String[] TAMANHOS_REFRI = {"Lata (350ml)", "Garrafa (1L)", "Garrafa (2L)"};
    private static final String[] TAMANHOS_AGUA = {"Garrafa (250ml)", "Garrafa (500ml)", "Garrafa (2L)"};

    public BebidasController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.bebidas = criarTodasBebidas();
    }

    public List<Bebidas> getBebidas() {
        return bebidas;
    }

    public void adicionarBebida(int index) {
        if (index < 0 || index >= bebidas.size()) {
            JOptionPane.showMessageDialog(null, "Selecione uma bebida primeiro.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pedido pedido = mainFrame.getPedidoAtual();
        if (pedido == null) {
            JOptionPane.showMessageDialog(null, "Você deve iniciar um novo pedido primeiro.", "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
            mainFrame.showPanel("menu");
            return;
        }

        if (pedido.getNomeCliente() == null || pedido.getNomeCliente().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o nome do cliente antes de adicionar produtos.", "Cliente não informado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Bebidas bebidaSelecionada = bebidas.get(index);
        pedido.adicionarItem(bebidaSelecionada);
        JOptionPane.showMessageDialog(null, bebidaSelecionada.descricao() + " adicionado(a) ao pedido com sucesso!", "Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
    }

    private List<Bebidas> criarTodasBebidas() {
        List<Bebidas> lista = new ArrayList<>();

        // Sucos
        for (String sabor : SABORES_SUCO) {
            double preco = (sabor.equals("Acerola")) ? 5.0 : 4.0;
            lista.add(new Bebidas(preco, "Suco", "de " + sabor, "300ml"));
        }

        // Refrigerantes
        for (String refri : REFRIGERANTES) {
            for (String tamanho : TAMANHOS_REFRI) {
                double preco = switch (tamanho) {
                    case "Lata (350ml)" -> 4.0;
                    case "Garrafa (1L)" -> 8.0;
                    case "Garrafa (2L)" -> 12.0;
                    default -> 0;
                };
                lista.add(new Bebidas(preco, "Refrigerante", refri, tamanho));
            }
        }

        // Água Mineral
        for (int i = 0; i < TAMANHOS_AGUA.length; i++) {
            double preco = switch (i) {
                case 0 -> 2.0;
                case 1 -> 4.0;
                case 2 -> 6.0;
                default -> 0;
            };
            lista.add(new Bebidas(preco, "Água Mineral", "Natural", TAMANHOS_AGUA[i]));
        }

        return lista;
    }
}

