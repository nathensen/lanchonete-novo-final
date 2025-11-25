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
        private final double troco;
        private final double bonusPedido;

        public ResultadoPedido(double troco, double bonusPedido) {
            this.troco = troco;
            this.bonusPedido = bonusPedido;
        }

        public double getTroco() { return troco; }
        public double getBonusPedido() { return bonusPedido; }
    }

    public ResultadoPedido finalizarPedido(double valorPago) {
        Pedido pedido = mainFrame.getPedidoAtual();
        Vendedor vendedor = mainFrame.getVendedor();

        if (pedido == null) {
            throw new IllegalStateException("Nenhum pedido ativo.");
        }

        // Chama o serviço que valida pagamento e atualiza bônus
        double troco = pedidoService.finalizarPedido(pedido, valorPago, vendedor);

        // Salva pedido no histórico
        HistoricoTXT.salvar(pedido, vendedor);

        // Bônus do pedido (já atualizado)
        double bonusPedido = vendedor.calcularBonus(pedido.calcularTotal());

        return new ResultadoPedido(troco, bonusPedido);
    }
}
