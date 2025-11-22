package com.lanchonete.controller;

import java.util.List;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Salgadinho;
import com.lanchonete.repository.SalgadinhoRepository;
import com.lanchonete.view.MainFrame;

public class SalgadinhoController {
    private MainFrame mainFrame;
    private SalgadinhoRepository repository;

    public SalgadinhoController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.repository = new SalgadinhoRepository();
    }

    public List<Salgadinho> listarSalgadinhos() {
        return repository.listarTodos();
    }

    public boolean adicionarSalgadinhoAoPedido(int index) {
        Pedido pedido = mainFrame.getPedidoAtual();
        if (pedido == null) return false; // Nenhum pedido iniciado

        Salgadinho salgadinho = repository.buscarPorIndice(index);
        if (salgadinho != null) {
            pedido.adicionarItem(salgadinho);
            return true;
        }
        return false;
    }
}
