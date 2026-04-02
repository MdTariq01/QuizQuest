package com.quizrpg.ui;

import com.quizrpg.engine.BattleEngine;
import com.quizrpg.model.Hero;
import com.quizrpg.model.Enemy;
import com.quizrpg.model.Question;

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
    private JPanel mainPanel;
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
        setTitle("Quiz RPG: Java Master");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initStartScreen();
    }

    private void initStartScreen() {
        startPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        startPanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        
        JLabel title = new JLabel("QUIZ RPG BATTLE", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        
        JTextField nameField = new JTextField("Hero");
        JButton startBtn = new JButton("START GAME");
        
        startBtn.addActionListener(e -> {
            engine = new BattleEngine(nameField.getText());
            initBattleScreen();
            updateUI();
        });

        startPanel.add(title);
        startPanel.add(nameField);
        startPanel.add(startBtn);

        setContentPane(startPanel);
        revalidate();
    }

    private void initBattleScreen() {
        battlePanel = new JPanel(new BorderLayout(10, 10));
        battlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top: stats
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        heroInfoLabel = new JLabel("Hero: Level 1");
        heroHealthBar = new JProgressBar(0, 100);
        heroHealthBar.setForeground(Color.GREEN);
        heroHealthBar.setStringPainted(true);

        enemyInfoLabel = new JLabel("Enemy: Java Shadow");
        enemyHealthBar = new JProgressBar(0, 100);
        enemyHealthBar.setForeground(Color.RED);
        enemyHealthBar.setStringPainted(true);

        statsPanel.add(heroInfoLabel);
        statsPanel.add(enemyInfoLabel);
        statsPanel.add(heroHealthBar);
        statsPanel.add(enemyHealthBar);

        // Center: Question and Options
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        questionLabel = new JLabel("Question text here...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        questionLabel.setPreferredSize(new Dimension(800, 100));

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + (i + 1));
            int index = i;
            optionButtons[i].addActionListener(e -> handleAnswer(index));
            optionsPanel.add(optionButtons[i]);
        }
        
        // Power-ups and Timer
        JPanel actionsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        healBtn = new JButton("HEAL (+HP)");
        doubleDamageBtn = new JButton("x2 DAMAGE");
        timerLabel = new JLabel("Time: 15s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 20));

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
        JScrollPane scroll = new JScrollPane(battleLog);
        
        feedbackLabel = new JLabel("Ready?", SwingConstants.CENTER);
        feedbackLabel.setForeground(Color.BLUE);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(feedbackLabel, BorderLayout.NORTH);
        bottomPanel.add(scroll, BorderLayout.CENTER);

        battlePanel.add(statsPanel, BorderLayout.NORTH);
        battlePanel.add(centerPanel, BorderLayout.CENTER);
        battlePanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(battlePanel);
        revalidate();
    }

    private void updateUI() {
        Hero hero = engine.getHero();
        Enemy enemy = engine.getCurrentEnemy();
        
        heroInfoLabel.setText(hero.getName() + " (Lvl " + hero.getLevel() + ") - Powers: " + hero.getPowerUps());
        heroHealthBar.setMaximum(hero.getMaxHealth());
        heroHealthBar.setValue(hero.getHealth());
        heroHealthBar.setString("HP: " + hero.getHealth() + " / " + hero.getMaxHealth());

        enemyInfoLabel.setText(enemy.getName() + " [" + enemy.getDifficulty() + "]");
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
        timerLabel.setForeground(Color.BLACK);
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
            feedbackLabel.setText("Timeout! You took damage.");
        } else if (result.startsWith("Correct")) {
            feedbackLabel.setText("CORRECT!");
        } else {
            feedbackLabel.setText("WRONG ANSWER!");
        }

        if (engine.isGameOver()) {
            gameOver();
        } else {
            // Wait 1.5 seconds before next question
            Timer nextTimer = new Timer(1500, e -> updateUI());
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
        heroHealthBar.setString("HP: " + hero.getHealth() + " / " + hero.getMaxHealth());
        heroInfoLabel.setText(hero.getName() + " (Lvl " + hero.getLevel() + ") - Powers: " + hero.getPowerUps());
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
