package com.lanchonete.view;

import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

    private Image img; // ← FALTAVA ISTO

    private static Image carregarImagem() {
        try {
            return ImageIO.read(
                BackgroundPanel.class.getResourceAsStream("/com/lanchonete/resources/fundoMenu.jpeg")
            );
        } catch (Exception ex) {
            System.err.println("Não foi possível carregar imagem de fundo: " + ex.getMessage());
            return null;
        }
    }

    public BackgroundPanel() {
        super();
        img = carregarImagem();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
