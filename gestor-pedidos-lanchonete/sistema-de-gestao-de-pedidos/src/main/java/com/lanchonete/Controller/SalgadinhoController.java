package com.lanchonete.controller;

import java.util.List;

import com.lanchonete.model.Pedido;
import com.lanchonete.model.Salgadinho;
import com.lanchonete.service.SalgadinhoService;
import com.lanchonete.view.MainFrame;

public class SalgadinhoController {

    private MainFrame mainFrame;
    private SalgadinhoService service;

    public SalgadinhoController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.service = new SalgadinhoService();
    }

    public List<Salgadinho> listarSalgadinhos() {
        return service.listarSalgadinhos();
    }

    public boolean adicionarSalgadinhoAoPedido(int index) {
        Pedido pedido = mainFrame.getPedidoAtual();
        return service.adicionarSalgadinhoAoPedido(pedido, index);
    }
}
