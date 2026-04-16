package ui;

import engine.BattleEngine;
import model.Hero;
import model.Enemy;
import model.Boss;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main GUI class for the Quiz RPG Game.
 * Follows UI separation using BattleEngine as controller.
 */
public class GameUI extends JFrame {
    private BattleEngine engine;
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private JProgressBar heroHealthBar;
    private JProgressBar enemyHealthBar;
    private JLabel heroInfoLabel;
    private JLabel enemyInfoLabel;
    private JTextArea terminalLog;
    private JPanel battlePanel;
    private JPanel startPanel;
    private JButton healBtn;
    private JButton doubleDamageBtn;
    private Timer gameTimer;
    private JLabel timerLabel;
    private int timeLeft;
    
    // New Animation Integration fields
    private SpritePanel spritePanel;
    private AnimationManager animationManager;
    private JPanel bottomActionPanel;
    private CardLayout bottomCardLayout;

    public GameUI() {
        setTitle("QuizQuest: Java Master");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initStartScreen();
    }

    private void initStartScreen() {
        startPanel = new JPanel(new BorderLayout(20, 20));
        startPanel.setBackground(new Color(25, 25, 35));
        startPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        JLabel title = new JLabel("QuizQuest", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 42));
        title.setForeground(new Color(255, 215, 0));
        startPanel.add(title, BorderLayout.NORTH);

        StringBuilder rules = new StringBuilder();
        rules.append("--- GAME RULES ---\n\n");
        rules.append("⚔️ CORE COMBAT: Correct = Attack! Wrong/Timeout = Take Damage.\n");
        rules.append("📈 PROGRESSION: Defeat 3 Enemies to face the Level Boss.\n");
        rules.append("🎓 SYSTEM ADAPTATION: >80% accuracy? Difficulty UP!\n");
        rules.append("⚡ POWERUPS: Use HEAL (+40 HP) or x2 DAMAGE strategically.\n");
        rules.append("🧠 NO LOOPING: Questions will never repeat in a session.\n\n");
        rules.append("Do you have what it takes to defeat the Java Overlords?");

        JTextArea rulesArea = new JTextArea(rules.toString());
        rulesArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        rulesArea.setEditable(false);
        rulesArea.setLineWrap(true);
        rulesArea.setWrapStyleWord(true);
        rulesArea.setBackground(new Color(40, 40, 55));
        rulesArea.setForeground(Color.WHITE);
        rulesArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 90)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        startPanel.add(rulesArea, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomPanel.setOpaque(false);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Enter Hero Name: ");
        nameLabel.setForeground(Color.WHITE);
        JTextField nameField = new JTextField("Hero", 15);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        btnPanel.setOpaque(false);
        JButton startBtn = new JButton("START GAME");
        JButton exitBtn = new JButton("EXIT");

        startBtn.addActionListener(e -> {
            engine = new BattleEngine(nameField.getText());
            initBattleScreen();
            updateUI();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        btnPanel.add(startBtn);
        btnPanel.add(exitBtn);

        bottomPanel.add(inputPanel);
        bottomPanel.add(btnPanel);
        startPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(startPanel);
        revalidate();
    }

    private void initBattleScreen() {
        battlePanel = new JPanel(new BorderLayout(0, 0));
        battlePanel.setBackground(new Color(35, 45, 55)); 

        spritePanel = new SpritePanel();
        spritePanel.setLayout(null);
        animationManager = new AnimationManager(spritePanel);

        JButton restartBtn = new JButton("RESTART");
        JButton exitBtn = new JButton("EXIT");
        restartBtn.setBounds(10, 10, 100, 30);
        exitBtn.setBounds(120, 10, 80, 30);
        
        JPanel enemyCard = createEntityCard(true);
        enemyCard.setBounds(500, 20, 250, 60);

        JPanel playerCard = createEntityCard(false);
        playerCard.setBounds(20, 150, 250, 60);
        
        spritePanel.add(restartBtn);
        spritePanel.add(exitBtn);
        spritePanel.add(enemyCard);
        spritePanel.add(playerCard);
        
        restartBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Restart Game?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                engine = new BattleEngine(engine.getHero().getName());
                updateUI();
            }
        });
        exitBtn.addActionListener(e -> System.exit(0));

        battlePanel.add(spritePanel, BorderLayout.CENTER);

        bottomCardLayout = new CardLayout();
        bottomActionPanel = new JPanel(bottomCardLayout);
        bottomActionPanel.setPreferredSize(new Dimension(800, 220));
        bottomActionPanel.setBackground(new Color(20, 20, 30));
        bottomActionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 90), 3, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JPanel optionsStatePanel = new JPanel(new BorderLayout(10, 10));
        optionsStatePanel.setOpaque(false);
        
        questionLabel = new JLabel("Question text here...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        questionLabel.setForeground(Color.WHITE);
        
        JPanel optionsGrid = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsGrid.setOpaque(false);
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + (i + 1));
            optionButtons[i].setBackground(new Color(60, 60, 80));
            optionButtons[i].setForeground(Color.WHITE);
            optionButtons[i].setFont(new Font("SansSerif", Font.BOLD, 14));
            optionButtons[i].setFocusPainted(false);
            int index = i;
            optionButtons[i].addActionListener(e -> handleAnswer(index));
            optionsGrid.add(optionButtons[i]);
        }
        
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        actionsPanel.setOpaque(false);
        healBtn = new JButton("HEAL (+HP)");
        doubleDamageBtn = new JButton("x2 DAMAGE");
        timerLabel = new JLabel("Time: 15s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        timerLabel.setForeground(new Color(255, 215, 0));
        actionsPanel.add(healBtn);
        actionsPanel.add(doubleDamageBtn);
        actionsPanel.add(timerLabel);
        
        healBtn.addActionListener(e -> useHealPowerUp());
        doubleDamageBtn.addActionListener(e -> useDoubleDamagePowerUp());

        optionsStatePanel.add(questionLabel, BorderLayout.NORTH);
        optionsStatePanel.add(optionsGrid, BorderLayout.CENTER);
        optionsStatePanel.add(actionsPanel, BorderLayout.SOUTH);

        JPanel terminalStatePanel = new JPanel(new BorderLayout());
        terminalStatePanel.setOpaque(false);
        
        terminalLog = new JTextArea();
        terminalLog.setEditable(false);
        terminalLog.setFont(new Font("Monospaced", Font.BOLD, 16));
        terminalLog.setBackground(new Color(20, 20, 30));
        terminalLog.setForeground(new Color(0, 255, 0));
        terminalStatePanel.add(new JScrollPane(terminalLog), BorderLayout.CENTER);

        bottomActionPanel.add(optionsStatePanel, "OPTIONS");
        bottomActionPanel.add(terminalStatePanel, "TERMINAL");

        battlePanel.add(bottomActionPanel, BorderLayout.SOUTH);

        animationManager.setPlayerState("idle", null);

        setContentPane(battlePanel);
        revalidate();
    }

    private JPanel createEntityCard(boolean isEnemy) {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(false);
        
        JLabel nameLabel = new JLabel(isEnemy ? "Enemy Name" : "Hero Name");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JProgressBar hpBar = new JProgressBar(0, 100);
        hpBar.setStringPainted(true);
        if (isEnemy) {
            enemyInfoLabel = nameLabel;
            enemyHealthBar = hpBar;
            hpBar.setForeground(new Color(231, 76, 60));
        } else {
            heroInfoLabel = nameLabel;
            heroHealthBar = hpBar;
            hpBar.setForeground(new Color(46, 204, 113));
        }
        hpBar.setBackground(new Color(50, 50, 65));
        
        card.add(nameLabel, BorderLayout.NORTH);
        card.add(hpBar, BorderLayout.CENTER);
        return card;
    }

    private void updateUI() {
        Hero hero = engine.getHero();
        Enemy enemy = engine.getCurrentEnemy();
        
        heroInfoLabel.setText(hero.getName() + " - Lv " + engine.getCurrentLevel());
        heroHealthBar.setMaximum(hero.getMaxHealth());
        heroHealthBar.setValue(hero.getHealth());
        heroHealthBar.setString(hero.getHealth() + " / " + hero.getMaxHealth());

        enemyInfoLabel.setText(enemy.getName());
        enemyHealthBar.setMaximum(enemy.getMaxHealth());
        enemyHealthBar.setValue(enemy.getHealth());
        enemyHealthBar.setString(enemy.getHealth() + " / " + enemy.getMaxHealth());

        healBtn.setEnabled(hero.getPowerUps() > 0);
        doubleDamageBtn.setEnabled(hero.getPowerUps() > 0 && !hero.isDoubleDamageActive());
        
        animationManager.loadEnemyAnimations(enemy.getName());

        loadNextQuestion();
    }

    private void loadNextQuestion() {
        bottomCardLayout.show(bottomActionPanel, "OPTIONS");
        terminalLog.setText(""); 
        
        Question q = engine.getNextQuestion();
        if (q != null) {
            questionLabel.setText("<html><center>" + q.getText() + "</center></html>");
            String[] opts = q.getOptions();
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(opts[i]);
                optionButtons[i].setEnabled(true);
            }
            startTimer();
        } else {
            terminalLog.setText("No more questions. GAME OVER.");
        }
    }

    private void startTimer() {
        if (gameTimer != null) gameTimer.stop();
        timeLeft = 15;
        timerLabel.setText("Time: " + timeLeft + "s");
        timerLabel.setForeground(new Color(255, 215, 0));
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time: " + timeLeft + "s");
                if (timeLeft <= 5) timerLabel.setForeground(Color.RED);
                if (timeLeft <= 0) {
                    gameTimer.stop();
                    handleAnswer(-1); // Timeout
                }
            }
        });
        gameTimer.start();
    }

    private void handleAnswer(int index) {
        if (gameTimer != null) gameTimer.stop();
        for (JButton b : optionButtons) b.setEnabled(false);
        bottomCardLayout.show(bottomActionPanel, "TERMINAL");
        
        if (index == -1) {
            String result = engine.processAnswer(-1);
            animationManager.setEnemyState("attack", () -> {
                animationManager.setPlayerState("hurt", () -> {
                    terminalLog.append(result);
                    updateBars();
                    checkGameState();
                });
            });
            
        } else {
            Hero hero = engine.getHero();
            Enemy enemy = engine.getCurrentEnemy();
            int hp = enemy.getHealth();
            int hpHero = hero.getHealth();
            
            String result = engine.processAnswer(index);
            
            if (result.contains("CORRECT")) {
                animationManager.setPlayerState("attack", () -> {
                    animationManager.setEnemyState("hurt", () -> {
                        terminalLog.append(result);
                        updateBars();
                        checkGameState();
                    });
                });
            } else {
                animationManager.setEnemyState("attack", () -> {
                    animationManager.setPlayerState("hurt", () -> {
                        terminalLog.append(result);
                        updateBars();
                        checkGameState();
                    });
                });
            }
        }
    }

    private void updateBars() {
        Hero hero = engine.getHero();
        Enemy enemy = engine.getCurrentEnemy();
        
        if (heroHealthBar != null) {
            heroHealthBar.setValue(hero.getHealth());
            heroHealthBar.setString(hero.getHealth() + " / " + hero.getMaxHealth());
        }
        if (enemyHealthBar != null) {
            enemyHealthBar.setValue(enemy.getHealth());
            enemyHealthBar.setString(enemy.getHealth() + " / " + enemy.getMaxHealth());
        }
    }

    private void checkGameState() {
        if (engine.isGameOver()) {
             animationManager.setPlayerState("die", () -> {
                 terminalLog.append("You were defeated by " + engine.getCurrentEnemy().getName() + ".\n");
                 Timer waitTimer = new Timer(2500, e -> gameOver());
                 waitTimer.setRepeats(false);
                 waitTimer.start();
             });
        } else if (!engine.getCurrentEnemy().isAlive()) {
             animationManager.setEnemyState("die", () -> {
                 animationManager.setEnemyState("none", null);
                 Enemy e = engine.getCurrentEnemy();
                 int xp = 50; 
                 if (e instanceof Boss) xp = 500;
                 else xp = e.getRewardExp();
                 
                 // Show floating UI effect on SpritePanel
                 JLabel defeatBanner = new JLabel("ENEMY DEFEATED!", SwingConstants.CENTER);
                 defeatBanner.setFont(new Font("SansSerif", Font.BOLD, 36));
                 defeatBanner.setForeground(new Color(255, 215, 0));
                 defeatBanner.setBounds(200, 100, 400, 60);
                 
                 JLabel xpPopup = new JLabel("+" + xp + " XP", SwingConstants.CENTER);
                 xpPopup.setFont(new Font("Monospaced", Font.BOLD, 24));
                 xpPopup.setForeground(new Color(50, 255, 50));
                 xpPopup.setBounds(500, 70, 200, 40); // Near enemy
                 
                 spritePanel.add(defeatBanner);
                 spritePanel.add(xpPopup);
                 spritePanel.repaint();
                 
                 Timer waitTimer = new Timer(2500, ev -> {
                     spritePanel.remove(defeatBanner);
                     spritePanel.remove(xpPopup);
                     spritePanel.repaint();
                     engine.spawnNextEnemy();
                     updateUI(); 
                 });
                 waitTimer.setRepeats(false);
                 waitTimer.start();
             });
        } else {
            Timer waitTimer = new Timer(2000, ev -> loadNextQuestion());
            waitTimer.setRepeats(false);
            waitTimer.start();
        }
    }

    private void useHealPowerUp() {
        engine.getHero().useHealPowerUp();
        updateBars();
        healBtn.setEnabled(engine.getHero().getPowerUps() > 0);
        
        terminalLog.append(">> Hero consumed a HEAL Power-up (+40 HP)!\n");
        bottomCardLayout.show(bottomActionPanel, "TERMINAL");
        Timer waitTimer = new Timer(1500, e -> bottomCardLayout.show(bottomActionPanel, "OPTIONS"));
        waitTimer.setRepeats(false);
        waitTimer.start();
    }

    private void useDoubleDamagePowerUp() {
        engine.getHero().useDoubleDamagePowerUp();
        doubleDamageBtn.setEnabled(false);
        
        terminalLog.append(">> Hero activated DOUBLE DAMAGE (Next attack x2)!\n");
        bottomCardLayout.show(bottomActionPanel, "TERMINAL");
        Timer waitTimer = new Timer(1500, e -> bottomCardLayout.show(bottomActionPanel, "OPTIONS"));
        waitTimer.setRepeats(false);
        waitTimer.start();
    }

    private void gameOver() {
        int score = engine.getBattlesWon();
        int choice = JOptionPane.showConfirmDialog(this, 
            "GAME OVER!\nHero health: 0\nBattles won: " + score + "\nRestart?", 
            "Defeat", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            initStartScreen();
        } else {
            System.exit(0);
        }
    }
}
