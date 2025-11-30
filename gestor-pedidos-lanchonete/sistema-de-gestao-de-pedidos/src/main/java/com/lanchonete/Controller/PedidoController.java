package com.lanchonete.controller;

import java.util.List;
import java.util.Optional;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Vendedor;
import com.lanchonete.repository.PedidoRepository;
import com.lanchonete.service.PedidoService;
import com.lanchonete.view.MainFrame;

public class PedidoController {

    private PedidoRepository repository;
    private PedidoService service;
    private MainFrame mainFrame;

    public PedidoController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.repository = new PedidoRepository();
        this.service = new PedidoService();
    }

    public Pedido novoPedido(String nomeCliente, Pedido pedidoAtual, Vendedor vendedor) {
        return service.criarPedido(nomeCliente, vendedor);
    }

    public ResultadoPedido finalizarPedido() {

        Pedido pedido = mainFrame.getPedidoAtual();
        if (pedido == null) return new ResultadoPedido(0);

        // >>> AGORA O SERVICE DEVOLVE O BONUS REAL
        double bonusAplicado = service.finalizarPedido(pedido, mainFrame.getVendedor());

        // salva no histórico
        repository.salvar(pedido);

        return new ResultadoPedido(bonusAplicado);
    }

    public List<Pedido> listarPedidosFinalizados() {
        return repository.listar();
    }

    public Pedido buscarPedidoPorCliente(String cliente) {
        Optional<Pedido> pedido = repository.listar().stream()
                .filter(p -> p.getNomeCliente().equals(cliente))
                .findFirst();
        return pedido.orElse(null);
    }

    public void atualizarPedido(Pedido pedido) {
        repository.remover(pedido);
        repository.salvar(pedido);
    }

    public static class ResultadoPedido {
        private double bonusPedido;
        public ResultadoPedido(double bonusPedido) {
            this.bonusPedido = bonusPedido;
        }
        public double getBonusPedido() {
            return bonusPedido;
        }
    }
}
