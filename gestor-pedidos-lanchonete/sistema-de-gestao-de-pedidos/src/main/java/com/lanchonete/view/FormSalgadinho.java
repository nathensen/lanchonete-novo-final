package com.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.lanchonete.model.Salgadinho;

public class FormSalgadinho extends JPanel {
    private MainFrame mainFrame;
    private JList<String> listSalgadinhos;
    private List<Salgadinho> salgadinhos;
    
    // Usando as mesmas opções que estavam definidas em SalgadinhoFactory
    private static final String[] NOMES_SALGADINHOS = {
        "Coxinha de Carne",
        "Coxinha de Frango",
        "Pastel Frito de Carne",
        "Pastel Frito de Frango",
        "Pastel Assado de Carne",
        "Pastel Assado de Frango",
        "Empanado de Frango"
    };
    
    public FormSalgadinho(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        
        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Escolha um Salgadinho");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle);
        
        // Criar lista de salgadinhos
        salgadinhos = criarSalgadinhos();
        
        // Converter para array de strings para exibição
        String[] opcoes = new String[salgadinhos.size()];
        for (int i = 0; i < salgadinhos.size(); i++) {
            Salgadinho s = salgadinhos.get(i);
            opcoes[i] = NOMES_SALGADINHOS[i] + " - R$ " + String.format("%.2f", s.getPrecoVenda());
        }
        
        listSalgadinhos = new JList<>(opcoes);
        listSalgadinhos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listSalgadinhos);
        
        // Botões
        JPanel buttonPanel = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnVoltar);
        
        // Adicionar ao layout
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Implementar ações
        btnVoltar.addActionListener(e -> mainFrame.showPanel("menu"));
        btnAdicionar.addActionListener(e -> adicionarSalgadinho());
    }
    
    private List<Salgadinho> criarSalgadinhos() {
        List<Salgadinho> lista = new ArrayList<>();
        
        // Usando exatamente os mesmos valores que estavam em SalgadinhoFactory
        lista.add(new Salgadinho(3.0, "Coxinha", "Frita", "Carne"));
        lista.add(new Salgadinho(3.0, "Coxinha", "Frita", "Frango"));
        lista.add(new Salgadinho(4.0, "Pastel", "Frito", "Carne"));
        lista.add(new Salgadinho(4.0, "Pastel", "Frito", "Frango"));
        lista.add(new Salgadinho(5.0, "Pastel", "Assado", "Carne"));
        lista.add(new Salgadinho(5.0, "Pastel", "Assado", "Frango"));
        lista.add(new Salgadinho(6.0, "Empanado", "Frito", "Frango"));
        
        return lista;
    }
    
    private void adicionarSalgadinho() {
        int selectedIndex = listSalgadinhos.getSelectedIndex();
        
        if (selectedIndex != -1) {
            // Verifica se existe um pedido ativo
            if (mainFrame.getPedidoAtual() == null) {
                JOptionPane.showMessageDialog(this, 
                        "Você deve iniciar um novo pedido primeiro.", 
                        "Pedido não iniciado", JOptionPane.WARNING_MESSAGE);
                mainFrame.showPanel("menu");
                return;
            }
            
            // Pega o salgadinho selecionado
            Salgadinho salgadinhoSelecionado = salgadinhos.get(selectedIndex);
            
            // Adiciona ao pedido
            mainFrame.getPedidoAtual().adicionarItem(salgadinhoSelecionado);
            
            JOptionPane.showMessageDialog(this, 
                    NOMES_SALGADINHOS[selectedIndex] + " adicionado ao pedido com sucesso!", 
                    "Item Adicionado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Selecione um salgadinho primeiro.", 
                    "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }
}
