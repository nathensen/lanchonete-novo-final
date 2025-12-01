package com.lanchonete;

import java.sql.Connection;

import com.lanchonete.database.Database;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// gestor-pedidos-lanchonete
// │
// └── sistema-de-gestao-de-pedidos
//     │
//     └── src
//         ├── main
//         │   └── java
//         │       └── com
//         │           └── lanchonete
//         │               ├── config
//         │               │   └── ConnectionFactory.java
//         │               │
//         │               ├── controller
//         │               │   ├── BebidasController.java
//         │               │   ├── LancheController.java
//         │               │   ├── LoginController.java
//         │               │   ├── MenuController.java
//         │               │   ├── PagamentoController.java
//         │               │   ├── PedidoController.java
//         │               │   ├── SalgadinhoController.java
//         │               │   ├── StatusPedidoController.java
//         │               │   └── VendedorController.java
//         │               │
//         │               ├── database
//         │               │   └── Database.java
//         │               │
//         │               ├── model
//         │               │   ├── Bebidas.java
//         │               │   ├── BoletoPagamento.java
//         │               │   ├── CartaoPagamento.java
//         │               │   ├── DinheiroPagamento.java
//         │               │   ├── ItemPedido.java
//         │               │   ├── Lanche.java
//         │               │   ├── Pagamento.java
//         │               │   ├── Pedido.java
//         │               │   ├── PixPagamento.java
//         │               │   ├── Prato.java
//         │               │   ├── Salgadinho.java
//         │               │   └── Vendedor.java
//         │               │
//         │               ├── repository
//         │               │   ├── BebidasRepository.java
//         │               │   ├── IVendedorRepository.java
//         │               │   ├── VendedorRepository.java
//         │               │   ├── LancheRepository.java
//         │               │   ├── PagamentoRepository.java
//         │               │   ├── PedidoRepository.java
//         │               │   ├── SalgadinhoRepository.java
//         │               │   └── StatusPedidoRepository.java
//         │               │
//         │               ├── resources
//         │               │   ├── db.properties
//         │               │   ├── FundoMenu.jpeg
//         │               │   └── script.sql
//         │               │
//         │               ├── service
//         │               │   ├── BebidasService.java
//         │               │   ├── LancheService.java
//         │               │   ├── LoginService.java
//         │               │   ├── PagamentoService.java
//         │               │   ├── PedidoService.java
//         │               │   ├── SalgadinhoService.java
//         │               │   ├── StatusPedidoService.java
//         │               │   └── VendedorService.java
//         │               │
//         │               ├── util
//         │               │   ├── FormatadorMoeda.java
//         │               │   ├── HistoricoTXT.java
//         │               │   ├── MenuBuilder.java
//         │               │   └── Validador.java
//         │               │
//         │               └── view
//         │                   ├── BackgroundPanel.java
//         │                   ├── DiagramaPagamento.drawio
//         │                   ├── FormBebidas.java
//         │                   ├── FormLanche.java
//         │                   ├── FormLogin.java
//         │                   ├── FormMenu.java
//         │                   ├── FormPagamento.java
//         │                   ├── FormPedido.java
//         │                   ├── FormSalgadinho.java
//         │                   ├── FormStatusPedido.java
//         │                   ├── Main.java
//         │                   └── MainFrame.java
//         │
//         └── test
//             └── java
//                 └── com
//                     └── lanchonete
//                         ├── model
//                         │   ├── PagamentoDinheiroTest.java
//                         │   └── PedidoTest.java
//                         │
//                         ├── service
//                         │   └── PedidoServiceTest.java
//                         │
//                         └── DatabaseTest.java
