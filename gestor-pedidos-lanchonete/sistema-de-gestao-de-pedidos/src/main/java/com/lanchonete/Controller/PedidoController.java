package com.lanchonete.controller;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;
import com.lanchonete.service.PedidoService;
import com.lanchonete.util.HistoricoTXT;
import com.lanchonete.view.MainFrame;

public class PedidoController {

    private PedidoService pedidoService;
    private MainFrame mainFrame;

    public PedidoController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.pedidoService = new PedidoService();
    }

    public static class ResultadoPedido {
        private final double bonusPedido;

        public ResultadoPedido(double bonusPedido) {
            this.bonusPedido = bonusPedido;
        }

        public double getBonusPedido() { return bonusPedido; }
    }

    // FINALIZA O PEDIDO (sem troco)
    public ResultadoPedido finalizarPedido() {
        Pedido pedido = mainFrame.getPedidoAtual();
        Vendedor vendedor = mainFrame.getVendedor();

        if (pedido == null) {
            throw new IllegalStateException("Nenhum pedido ativo.");
        }

        // Chama o service para validar e atualizar bônus
        pedidoService.finalizarPedido(pedido, vendedor);

        // Salva pedido no histórico
        HistoricoTXT.salvar(pedido, vendedor);

        // Retorna o bônus gerado
        double bonusPedido = pedido.getVendedor().getBonus();

        return new ResultadoPedido(bonusPedido);
    }
}
