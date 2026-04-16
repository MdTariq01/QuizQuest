package ui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class AnimationManager {
    private Map<String, Image[]> playerAnimations = new HashMap<>();
    private Map<String, Image[]> enemyAnimations = new HashMap<>();
    
    private String playerState = "idle";
    private String enemyState = "idle";
    
    private String lastEnemyFolder = "";
    
    private int playerFrame = 0;
    private int enemyFrame = 0;
    
    private SpritePanel panel;
    private Timer tickTimer;
    private Runnable onPlayerAnimComplete;
    private Runnable onEnemyAnimComplete;
    
    public AnimationManager(SpritePanel panel) {
        this.panel = panel;
        loadPlayerAnimations();
        
        tickTimer = new Timer(100, e -> tick());
        tickTimer.start();
    }
    
    private void loadPlayerAnimations() {
        playerAnimations.put("idle", loadFrames("Images/MainCharacter/idle"));
        playerAnimations.put("attack", loadFrames("Images/MainCharacter/attack_1"));
        playerAnimations.put("hurt", loadFrames("Images/MainCharacter/hurt"));
        playerAnimations.put("die", loadFrames("Images/MainCharacter/die"));
    }
    
    public void loadEnemyAnimations(String enemyName) {
        enemyAnimations.clear();
        String folder = getEnemyFolder(enemyName);
        this.lastEnemyFolder = folder;
        
        applyEnemyPosition("idle");
        
        enemyAnimations.put("idle", loadFrames("Images/" + folder + "/idle"));
        enemyAnimations.put("attack", loadFrames("Images/" + folder + "/attack"));
        enemyAnimations.put("hurt", loadFrames("Images/" + folder + "/hurt"));
        enemyAnimations.put("die", loadFrames("Images/" + folder + "/die"));
        setEnemyState("idle", null);
    }
    
    private void applyEnemyPosition(String state) {
        // Define specific positions for each enemy sprite to make them tunable
        switch(lastEnemyFolder) {
            case "GoblinBoss":
                if ("attack".equals(state) || "die".equals(state)) {
                    // Shift the 250x250 Boss sprite so it matches the 150x150 idle position
                    panel.setEnemyPosition(400, -90); 
                } else {
                    // 150x150 idle position
                    panel.setEnemyPosition(480, 20);
                }
                break;
            case "GoblinGiant":
                panel.setEnemyPosition(420, -40);
                break;
            case "GoblinBlade":
                panel.setEnemyPosition(410, -70);
                break;
            case "GoblinWarrior":
            default:
                panel.setEnemyPosition(450, -40);
                break;
        }
    }
    
    private String getEnemyFolder(String enemyName) {
        if (enemyName.contains("Boss")) return "GoblinBoss";
        if (enemyName.contains("Giant")) return "GoblinGiant";
        if (enemyName.contains("Blade")) return "GoblinBlade";
        return "GoblinWarrior"; // Default
    }
    
    private Image[] loadFrames(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return new Image[0];
        }
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));
        if (files == null || files.length == 0) return new Image[0];
        
        Image[] frames = new Image[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                frames[i] = ImageIO.read(files[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frames;
    }
    
    public void setPlayerState(String state, Runnable onComplete) {
        this.playerState = state;
        this.playerFrame = 0;
        this.onPlayerAnimComplete = onComplete;
        
        if ("none".equals(state)) return;

        Image[] frames = playerAnimations.get(state);
        if (frames == null || frames.length == 0) {
            if (this.onPlayerAnimComplete != null) {
                Runnable cb = this.onPlayerAnimComplete;
                this.onPlayerAnimComplete = null;
                cb.run();
            }
            this.playerState = "idle";
        }
    }
    
    public void setEnemyState(String state, Runnable onComplete) {
        this.enemyState = state;
        this.enemyFrame = 0;
        this.onEnemyAnimComplete = onComplete;
        
        applyEnemyPosition(state);
        
        if ("none".equals(state)) return;

        Image[] frames = enemyAnimations.get(state);
        if (frames == null || frames.length == 0) {
            if (this.onEnemyAnimComplete != null) {
                Runnable cb = this.onEnemyAnimComplete;
                this.onEnemyAnimComplete = null;
                cb.run();
            }
            this.enemyState = "idle";
            applyEnemyPosition("idle");
        }
    }
    
    private void tick() {
        Image[] pFrames = playerAnimations.get(playerState);
        Image[] eFrames = enemyAnimations.get(enemyState);
        
        if (pFrames != null && pFrames.length > 0) {
            panel.setPlayerSprite(pFrames[playerFrame % pFrames.length]);
            playerFrame++;
            if (playerFrame >= pFrames.length && !playerState.equals("idle") && !playerState.equals("none")) {
                if (onPlayerAnimComplete != null) {
                    Runnable cb = onPlayerAnimComplete;
                    onPlayerAnimComplete = null;
                    cb.run();
                } else {
                    setPlayerState("idle", null); // return to idle
                }
            }
        }
        
        if (eFrames != null && eFrames.length > 0) {
            panel.setEnemySprite(eFrames[enemyFrame % eFrames.length]);
            enemyFrame++;
            if (enemyFrame >= eFrames.length && !enemyState.equals("idle") && !enemyState.equals("none")) {
                if (onEnemyAnimComplete != null) {
                    Runnable cb = onEnemyAnimComplete;
                    onEnemyAnimComplete = null;
                    cb.run();
                } else {
                    setEnemyState("idle", null); // return to idle
                }
            }
        }
        
        if (playerState.equals("none")) panel.setPlayerSprite(null);
        if (enemyState.equals("none")) panel.setEnemySprite(null);
        
        panel.repaint();
    }
}