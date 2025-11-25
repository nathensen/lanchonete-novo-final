package com.lanchonete.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class HistoricoTXT {

    // Caminho do histórico
    private static final String CAMINHO = "/historico/historico_pedidos.txt";

    public static void salvar(Pedido pedido, Vendedor vendedor) {

        // Garantir que a pasta exista
        File pasta = new File("historico");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        // Print para ver onde o arquivo está sendo criado
        System.out.println("Caminho real do arquivo: " + new File(CAMINHO).getAbsolutePath());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO, true))) {

            writer.write("===== PEDIDO FINALIZADO =====\n");
            writer.write("Cliente: " + pedido.getNomeCliente() + "\n");
            writer.write("Vendedor: " + vendedor.getNome() + "\n");
            writer.write("Valor Total: R$ " + String.format("%.2f", pedido.calcularTotal()) + "\n");

            writer.write("Itens:\n");
            for (var item : pedido.getItensConsumidos()) {
                writer.write(" - " + item.descricao() +
                             " | R$ " + String.format("%.2f", item.getPrecoVenda()) + "\n");
            }

            writer.write("-----------------------------\n\n");

            // Confirmação no console
            System.out.println("Pedido salvo no histórico: " + pedido.getNomeCliente());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
