package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class SpritePanel extends JPanel {

    private Image playerSprite;
    private Image enemySprite;

    private int playerX = 100;
    private int playerY = 150;
    
    private int enemyX = 450;
    private int enemyY = 50;

    public SpritePanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(800, 300));
    }

    public void setPlayerSprite(Image img) {
        this.playerSprite = img;
        repaint();
    }

    public void setEnemySprite(Image img) {
        this.enemySprite = img;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Anti-aliasing for text and smooth drawing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw Player Sprite (bottom-left)
        if (playerSprite != null) {
            g2d.drawImage(playerSprite, playerX, playerY, playerSprite.getWidth(null) * 2, playerSprite.getHeight(null) * 2, null);
        }

        // Draw Enemy Sprite (top-right)
        if (enemySprite != null) {
            g2d.drawImage(enemySprite, enemyX, enemyY, enemySprite.getWidth(null) * 2, enemySprite.getHeight(null) * 2, null);
        }

        g2d.dispose();
    }
}