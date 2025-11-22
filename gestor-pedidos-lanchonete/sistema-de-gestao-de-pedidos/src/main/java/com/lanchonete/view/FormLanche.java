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

import com.lanchonete.model.Lanche;

public class FormLanche extends JPanel {
    private MainFrame mainFrame;
    private List<Lanche> lanches;
    private JList<String> listLanches;
    private DefaultListModel<String> listModel;
    
    private static final String[] NOMES_LANCHES = {
        "Misto",
        "Hambúrguer",
        "X-Burger",
        "X-Eggs",
        "Duplo",
        "X-Calabresa",
        "X-Eggs Calabresa",
        "X-Bacon",
        "X-Eggs Bacon",
        "X-Duplo com bacon",
        "X-Bacon com Calabresa",
        "X-Tudo"
    };
    
    public FormLanche(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        
        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha um Lanche");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);
        
        // Cria a lista de lanches
        lanches = criarLanches();
        
        // Cria o modelo de lista
        listModel = new DefaultListModel<>();
        for (int i = 0; i < lanches.size(); i++) {
            Lanche lanche = lanches.get(i);
            // Formato: Nome - Preço
            listModel.addElement(NOMES_LANCHES[i] + " - R$ " + 
                    String.format("%.2f", lanche.getPrecoVenda()));
        }
        
        listLanches = new JList<>(listModel);
        listLanches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(listLanches);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnVoltar);
        
        // Adiciona os painéis ao layout
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Configura ações dos botões
        btnAdicionar.addActionListener(e -> adicionarLanche());
        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));
    }
    
    private List<Lanche> criarLanches() {
        List<Lanche> listaLanches = new ArrayList<>();
        
        // Criando os lanches diretamente
        listaLanches.add(new Lanche(8.0, "Francês", "Queijo e Presunto", "Maionese"));                // Misto
        listaLanches.add(new Lanche(10.0, "Brioche", "Carne", "Barbecue"));                           // Hambúrguer
        listaLanches.add(new Lanche(12.0, "Australiano", "Carne, Queijo", "Maionese"));               // X-Burger
        listaLanches.add(new Lanche(13.0, "Francês", "Carne, Ovo", "Maionese"));                      // X-Eggs
        listaLanches.add(new Lanche(15.0, "Brioche", "Duas Carnes", "Barbecue"));                     // Duplo
        listaLanches.add(new Lanche(14.0, "Australiano", "Carne, Calabresa", "Maionese"));            // X-Calabresa
        listaLanches.add(new Lanche(16.0, "Francês", "Carne, Ovo, Calabresa", "Maionese"));           // X-Eggs Calabresa
        listaLanches.add(new Lanche(15.0, "Brioche", "Carne, Bacon", "Barbecue"));                    // X-Bacon
        listaLanches.add(new Lanche(17.0, "Francês", "Carne, Ovo, Bacon", "Maionese"));               // X-Eggs Bacon
        listaLanches.add(new Lanche(18.0, "Brioche", "Duas Carnes, Bacon", "Barbecue"));              // X-Duplo com bacon
        listaLanches.add(new Lanche(17.0, "Australiano", "Carne, Bacon, Calabresa", "Maionese"));     // X-Bacon com Calabresa
        listaLanches.add(new Lanche(20.0, "Francês", "Carne, Ovo, Bacon, Calabresa, Queijo", "Maionese, Barbecue")); // X-Tudo
        
        return listaLanches;
    }
    
    private void adicionarLanche() {
        int selectedIndex = listLanches.getSelectedIndex();
        
        if (selectedIndex != -1) {
            if (mainFrame.getPedidoAtual() == null) {
                JOptionPane.showMessageDialog(this, 
                        "Você deve iniciar um novo pedido primeiro.", 
                        "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
                mainFrame.showPanel("menu");
                return;
            }
            
            // Pega o lanche diretamente da lista
            Lanche lancheSelecionado = lanches.get(selectedIndex);
            
            // Adiciona ao pedido
            mainFrame.getPedidoAtual().adicionarItem(lancheSelecionado);
            
            JOptionPane.showMessageDialog(this, 
                    NOMES_LANCHES[selectedIndex] + " adicionado ao pedido com sucesso!", 
                    "Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Selecione um lanche primeiro.", 
                    "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }
}
