package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.lanchonete.model.Bebidas;
import com.lanchonete.model.ItemPedido;
public class FormBebidas extends JPanel {
    private MainFrame mainFrame;
    private JList<String> listBebidas;
    private DefaultListModel<String> listModel;
    private List<Bebidas> bebidas;
    
    // Arrays com dados centralizados
    private static final String[] CATEGORIAS = {
        "Suco",
        "Refrigerante",
        "Água Mineral"
    };
    
    private static final String[] SABORES_SUCO = {
        "Goiaba",
        "Acerola",
        "Maracujá",
        "Abacaxi"
    };
    
    private static final String[] REFRIGERANTES = {
        "Coca-Cola",
        "Guaraná"
    };
    
    private static final String[] TAMANHOS_REFRI = {
        "Lata (350ml)",
        "Garrafa (1L)",
        "Garrafa (2L)"
    };
    
    private static final String[] TAMANHOS_AGUA = {
        "Garrafa (250ml)",
        "Garrafa (500ml)",
        "Garrafa (2L)"
    };
    
    public FormBebidas(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        
        // Painel de título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha uma Bebida");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);
        
        // Cria a lista de bebidas
        bebidas = criarTodasBebidas();
        String[] nomesBebidas = getNomesBebidas();
        
        // Cria o modelo de lista
        listModel = new DefaultListModel<>();
        for (int i = 0; i < bebidas.size(); i++) {
            Bebidas bebida = bebidas.get(i);
            // Formato: Nome - Preço
            listModel.addElement(nomesBebidas[i] + " - R$ " + 
                    String.format("%.2f", bebida.getPrecoVenda()));
        }
        
        listBebidas = new JList<>(listModel);
        listBebidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(listBebidas);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnVoltar);
        
        // Adiciona os componentes ao painel principal
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Configura ações dos botões
        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));
        btnAdicionar.addActionListener(e -> adicionarBebida());
    }
    
    // Método que retorna todas as bebidas disponíveis
    private List<Bebidas> criarTodasBebidas() {
        List<Bebidas> bebidasList = new ArrayList<>();
        
        // Sucos
        for (int i = 0; i < SABORES_SUCO.length; i++) {
            double preco = (i == 1) ? 5.0 : 4.0; // Acerola é R$5, outros são R$4
            bebidasList.add(new Bebidas(preco, "Suco", "de " + SABORES_SUCO[i], "300ml"));
        }
        
        // Refrigerantes
        for (int i = 0; i < REFRIGERANTES.length; i++) {
            for (int j = 0; j < TAMANHOS_REFRI.length; j++) {
                double preco = 0;
                switch(j) {
                    case 0: preco = 4.0; break;  // Lata (350ml)
                    case 1: preco = 8.0; break;  // Garrafa (1L)
                    case 2: preco = 12.0; break; // Garrafa (2L)
                }
                bebidasList.add(new Bebidas(preco, "Refrigerante", REFRIGERANTES[i], TAMANHOS_REFRI[j]));
            }
        }
        
        // Água Mineral
        for (int i = 0; i < TAMANHOS_AGUA.length; i++) {
            double preco = 0;
            switch(i) {
                case 0: preco = 2.0; break;  // Garrafa (250ml)
                case 1: preco = 4.0; break;  // Garrafa (500ml)
                case 2: preco = 6.0; break;  // Garrafa (2L)
            }
            bebidasList.add(new Bebidas(preco, "Água Mineral", "Natural", TAMANHOS_AGUA[i]));
        }
        
        return bebidasList;
    }
    
    // Método que retorna os nomes formatados para exibição na interface gráfica
    private String[] getNomesBebidas() {
        String[] nomes = new String[13]; // 4 sucos + 6 refrigerantes + 3 águas
        int index = 0;
        
        // Nomes dos sucos
        for (int i = 0; i < SABORES_SUCO.length; i++) {
            nomes[index++] = "Suco de " + SABORES_SUCO[i] + " (300ml)";
        }
        
        // Nomes dos refrigerantes
        for (int i = 0; i < REFRIGERANTES.length; i++) {
            for (int j = 0; j < TAMANHOS_REFRI.length; j++) {
                nomes[index++] = REFRIGERANTES[i] + " - " + TAMANHOS_REFRI[j];
            }
        }
        
        // Nomes das águas
        for (int i = 0; i < TAMANHOS_AGUA.length; i++) {
            nomes[index++] = "Água Mineral - " + TAMANHOS_AGUA[i];
        }
        
        return nomes;
    }
    
    private void adicionarBebida() {
        int selectedIndex = listBebidas.getSelectedIndex();
        
        if (selectedIndex != -1) {
            if (mainFrame.getPedidoAtual() == null) {
                JOptionPane.showMessageDialog(this, 
                        "Você deve iniciar um novo pedido primeiro.", 
                        "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
                mainFrame.showPanel("menu");
                return;
            }
            
            // Pega a bebida diretamente da lista
            Bebidas bebidaSelecionada = bebidas.get(selectedIndex);
            
            // Adiciona ao pedido
            mainFrame.getPedidoAtual().adicionarItem(bebidaSelecionada);
            
            // Obter o nome formatado
            String[] nomesBebidas = getNomesBebidas();
            
            JOptionPane.showMessageDialog(this, 
                    nomesBebidas[selectedIndex] + " adicionado(a) ao pedido com sucesso!", 
                    "Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Selecione uma bebida primeiro.", 
                    "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }
}
