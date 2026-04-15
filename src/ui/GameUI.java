package ui;

import engine.BattleEngine;
import model.Hero;
import model.Enemy;
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
    private JTextArea battleLog;
    private JLabel feedbackLabel;
    private JPanel battlePanel;
    private JPanel startPanel;
    private JButton healBtn;
    private JButton doubleDamageBtn;
    private Timer gameTimer;
    private JLabel timerLabel;
    private int timeLeft;

    public GameUI() {
        setTitle("🛡️ QuizQuest: Java Master");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initStartScreen();
    }

    private void initStartScreen() {
        startPanel = new JPanel(new BorderLayout(20, 20));
        startPanel.setBackground(new Color(25, 25, 35));
        startPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        // Title
        JLabel title = new JLabel("🛡️ QuizQuest: Java Master", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 42));
        title.setForeground(new Color(255, 215, 0)); // Gold title
        startPanel.add(title, BorderLayout.NORTH);

        // Rules Section
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

        // Input and Buttons
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
        battlePanel = new JPanel(new BorderLayout(10, 10));
        battlePanel.setBackground(new Color(25, 25, 35));
        battlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top: stats
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        statsPanel.setOpaque(false);
        heroInfoLabel = new JLabel("Hero: Level 1");
        heroInfoLabel.setForeground(Color.WHITE);
        heroHealthBar = new JProgressBar(0, 100);
        heroHealthBar.setBackground(new Color(50, 50, 65));
        heroHealthBar.setForeground(new Color(46, 204, 113));
        heroHealthBar.setStringPainted(true);

        enemyInfoLabel = new JLabel("Enemy: Java Shadow");
        enemyInfoLabel.setForeground(Color.WHITE);
        enemyHealthBar = new JProgressBar(0, 100);
        enemyHealthBar.setBackground(new Color(50, 50, 65));
        enemyHealthBar.setForeground(new Color(231, 76, 60));
        enemyHealthBar.setStringPainted(true);

        statsPanel.add(heroInfoLabel);
        statsPanel.add(enemyInfoLabel);
        statsPanel.add(heroHealthBar);
        statsPanel.add(enemyHealthBar);

        // Control Buttons
        JButton restartBtn = new JButton("RESTART");
        JButton exitBtn = new JButton("EXIT");
        restartBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        exitBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        restartBtn.setBackground(new Color(60, 60, 80));
        restartBtn.setForeground(Color.WHITE);
        exitBtn.setBackground(new Color(80, 40, 40));
        exitBtn.setForeground(Color.WHITE);

        restartBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Restart Game?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) initStartScreen();
        });
        exitBtn.addActionListener(e -> System.exit(0));

        JPanel topControlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topControlPanel.setOpaque(false);
        topControlPanel.add(restartBtn);
        topControlPanel.add(exitBtn);

        // Center: Question and Options
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        questionLabel = new JLabel("Question text here...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setPreferredSize(new Dimension(800, 100));

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsPanel.setOpaque(false);
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + (i + 1));
            optionButtons[i].setBackground(new Color(60, 60, 80));
            optionButtons[i].setForeground(Color.WHITE);
            int index = i;
            optionButtons[i].addActionListener(e -> handleAnswer(index));
            optionsPanel.add(optionButtons[i]);
        }
        
        // Power-ups and Timer
        JPanel actionsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        actionsPanel.setOpaque(false);
        healBtn = new JButton("HEAL (+HP)");
        healBtn.setBackground(new Color(40, 70, 40));
        healBtn.setForeground(Color.WHITE);
        doubleDamageBtn = new JButton("x2 DAMAGE");
        doubleDamageBtn.setBackground(new Color(70, 70, 40));
        doubleDamageBtn.setForeground(Color.WHITE);
        timerLabel = new JLabel("Time: 15s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 22));
        timerLabel.setForeground(new Color(255, 215, 0));

        healBtn.addActionListener(e -> useHealPowerUp());
        doubleDamageBtn.addActionListener(e -> useDoubleDamagePowerUp());

        actionsPanel.add(healBtn);
        actionsPanel.add(doubleDamageBtn);
        actionsPanel.add(timerLabel);
        
        centerPanel.add(questionLabel, BorderLayout.NORTH);
        centerPanel.add(optionsPanel, BorderLayout.CENTER);
        centerPanel.add(actionsPanel, BorderLayout.SOUTH);

        // Bottom: Log
        battleLog = new JTextArea(8, 50);
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        battleLog.setBackground(new Color(30, 30, 40));
        battleLog.setForeground(new Color(200, 200, 220));
        JScrollPane scroll = new JScrollPane(battleLog);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 90)));
        
        feedbackLabel = new JLabel("Ready?", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        feedbackLabel.setForeground(new Color(255, 215, 0));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(feedbackLabel, BorderLayout.NORTH);
        bottomPanel.add(scroll, BorderLayout.CENTER);

        battlePanel.add(statsPanel, BorderLayout.NORTH);
        battlePanel.add(topControlPanel, BorderLayout.WEST);
        battlePanel.add(centerPanel, BorderLayout.CENTER);
        battlePanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(battlePanel);
        revalidate();
    }

    private void updateUI() {
        Hero hero = engine.getHero();
        Enemy enemy = engine.getCurrentEnemy();
        
        heroInfoLabel.setText(hero.getName() + " (HP) - Level " + engine.getCurrentLevel());
        heroHealthBar.setMaximum(hero.getMaxHealth());
        heroHealthBar.setValue(hero.getHealth());
        heroHealthBar.setString(hero.getHealth() + " / " + hero.getMaxHealth());

        String enemySuffix = (enemy instanceof model.Boss) ? " [BOSS]" : " [" + (engine.getEnemiesDefeatedInLevel() + 1) + "/3]";
        enemyInfoLabel.setText(enemy.getName() + enemySuffix);
        enemyHealthBar.setMaximum(enemy.getMaxHealth());
        enemyHealthBar.setValue(enemy.getHealth());
        enemyHealthBar.setString(enemy.getHealth() + " / " + enemy.getMaxHealth());

        healBtn.setEnabled(hero.getPowerUps() > 0);
        doubleDamageBtn.setEnabled(hero.getPowerUps() > 0 && !hero.isDoubleDamageActive());

        loadNextQuestion();
    }

    private void loadNextQuestion() {
        Question q = engine.getNextQuestion();
        questionLabel.setText("<html><center>" + q.getText() + "</center></html>");
        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(opts[i]);
            optionButtons[i].setEnabled(true);
        }
        startTimer();
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
        
        String result = engine.processAnswer(index);
        battleLog.append(result + "-----------------\n");
        battleLog.setCaretPosition(battleLog.getDocument().getLength());

        if (index == -1) {
            feedbackLabel.setText("TIMEOUT! You took damage.");
            feedbackLabel.setForeground(Color.RED);
        } else if (result.contains("CORRECT")) {
            feedbackLabel.setText("CORRECT! Proceeding...");
            feedbackLabel.setForeground(new Color(0, 150, 0));
        } else {
            feedbackLabel.setText("WRONG! Enemy counter-attacks.");
            feedbackLabel.setForeground(Color.RED);
        }

        // Check for special events in logs
        if (result.contains("LEVEL CLEARED")) {
            JOptionPane.showMessageDialog(this, "CONGRATULATIONS!\nLevel Cleared! Heading to next level.", "Level Up", JOptionPane.INFORMATION_MESSAGE);
        } else if (result.contains("Level") && result.contains("Overlord")) {
             feedbackLabel.setText("BOSS ALERT! BE CAREFUL!");
             feedbackLabel.setForeground(Color.ORANGE);
        }

        if (engine.isGameOver()) {
            gameOver();
        } else {
            // Wait 2 seconds before next question to let user read feedback
            Timer nextTimer = new Timer(2000, e -> updateUI());
            nextTimer.setRepeats(false);
            nextTimer.start();
        }
    }

    private void useHealPowerUp() {
        engine.getHero().useHealPowerUp();
        updateUIWithoutLoading();
        battleLog.append(">> Hero used HEAL (+40 HP)!\n");
    }

    private void useDoubleDamagePowerUp() {
        engine.getHero().useDoubleDamagePowerUp();
        doubleDamageBtn.setEnabled(false);
        battleLog.append(">> Hero used DOUBLE DAMAGE (Next attack x2)!\n");
    }

    private void updateUIWithoutLoading() {
        Hero hero = engine.getHero();
        heroHealthBar.setValue(hero.getHealth());
        heroHealthBar.setString(hero.getHealth() + " / " + hero.getMaxHealth());
        heroInfoLabel.setText(hero.getName() + " (HP) - Level " + engine.getCurrentLevel());
        healBtn.setEnabled(hero.getPowerUps() > 0);
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
