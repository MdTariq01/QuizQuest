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
    private int playerY = 100;
    
    private int enemyX = 450;
    private int enemyY = -40;

    public SpritePanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(800, 300));
    }

    public void setEnemyPosition(int x, int y) {
        this.enemyX = x;
        this.enemyY = y;
        repaint();
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
            // Scale up player sprite while keeping the same bottom baseline as previous scale (2x).
            int oldScale = 2;
            int newScale = 3; // increase player size
            int pW = playerSprite.getWidth(null) * newScale;
            int pH = playerSprite.getHeight(null) * newScale;
            int oldH = playerSprite.getHeight(null) * oldScale;
            int drawY = playerY + oldH - pH; // adjust so feet remain anchored
            g2d.drawImage(playerSprite, playerX, drawY, pW, pH, null);
        }

        // Draw Enemy Sprite (top-right)
        if (enemySprite != null) {
            int eW = enemySprite.getWidth(null) * 2;
            int eH = enemySprite.getHeight(null) * 2;
            g2d.drawImage(enemySprite, enemyX + eW, enemyY, -eW, eH, null);
        }

        g2d.dispose();
    }
}