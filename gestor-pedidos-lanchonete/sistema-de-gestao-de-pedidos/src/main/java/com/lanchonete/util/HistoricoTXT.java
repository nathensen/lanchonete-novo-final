package com.lanchonete.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;

public class HistoricoTXT {

    // Caminho do histórico
    private static final String CAMINHO = "historico/historico_pedidos.txt";

    public static void salvar(Pedido pedido, Vendedor vendedor) {

        // Garantir que a pasta exista
        File pasta = new File("historico");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        // Print para ver onde o arquivo está sendo criado
        System.out.println("Caminho real do arquivo: " + new File(CAMINHO).getAbsolutePath());

        try (FileWriter writer = new FileWriter("historico_pedidos.txt")){

            writer.write("===== PEDIDO FINALIZADO =====\n");
            writer.write("Cliente: " + pedido.getNomeCliente() + "\n");
            writer.write("Vendedor: " + vendedor.getNome() + "\n");
            writer.write("Valor Total: R$ " + String.format("%.2f", pedido.calcularTotal()) + "\n");
            writer.write("-----------------------------\n");

            writer.write("Itens:\n");
            for (var item : pedido.getItensConsumidos()) {
                writer.write(" - " + item.descricao() + " | R$ " + String.format("%.2f", item.getPrecoVenda()) + "\n");
            }
            writer.write("-----------------------------\n\n");

            // Confirmação no console
            JOptionPane.showMessageDialog(null,"Pedido salvo no histórico: " + pedido.getNomeCliente());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro na gravacão do Arquivo"+e.getMessage());

        }
    }
}
