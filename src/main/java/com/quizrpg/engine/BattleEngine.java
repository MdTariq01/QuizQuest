package com.quizrpg.engine;

import com.quizrpg.data.QuestionBank;
import com.quizrpg.model.Boss;
import com.quizrpg.model.Enemy;
import com.quizrpg.model.Hero;
import com.quizrpg.model.Question;

/**
 * BattleEngine manages the state and logic of the game battle.
 * Follows separation of concerns: Model-Logic-UI.
 */
public class BattleEngine {
    private Hero hero;
    private Enemy currentEnemy;
    private QuestionBank questionBank;
    private Question currentQuestion;
    private int battlesWon;
    
    // Advanced stats for adaptive difficulty
    private int currentLevel; // The "Adventure Level"
    private int enemiesDefeatedInLevel;
    private int totalQuestionsAsked;
    private int correctAnswersCount;
    private static final int ENEMIES_PER_LEVEL = 3;

    public BattleEngine(String heroName) {
        this.hero = new Hero(heroName);
        this.questionBank = new QuestionBank();
        resetStats();
        startBattle();
    }

    private void resetStats() {
        this.battlesWon = 0;
        this.currentLevel = 1;
        this.enemiesDefeatedInLevel = 0;
        this.totalQuestionsAsked = 0;
        this.correctAnswersCount = 0;
        if (questionBank != null) questionBank.resetQuestions();
    }

    public void resetGame(String newName) {
        this.hero = new Hero(newName);
        resetStats();
        startBattle();
    }

    public void startBattle() {
        spawnNextEnemy();
    }

    public void spawnNextEnemy() {
        // If we defeated level enemies, it's Boss time
        if (enemiesDefeatedInLevel >= ENEMIES_PER_LEVEL) {
            String bossName = "Level " + currentLevel + " Overlord";
            int baseHealth = 150 + (currentLevel * 100);
            currentEnemy = new Boss(bossName, baseHealth);
        } else {
            // Normal scale enemy
            String diff = getEnemyDifficulty();
            int health = 40 + (currentLevel * 20) + (enemiesDefeatedInLevel * 10);
            int expReward = 50 + (currentLevel * 30);
            currentEnemy = new Enemy("Java Shadow " + (enemiesDefeatedInLevel + 1), health, diff, expReward);
        }
    }

    private String getEnemyDifficulty() {
        double accuracy = getAccuracy();
        
        // Adaptive logic: If accuracy is high, increase difficulty faster
        if (currentLevel == 1) {
            return (accuracy > 0.8) ? "MEDIUM" : "EASY";
        } else if (currentLevel == 2) {
            return (accuracy > 0.7) ? "HARD" : "MEDIUM";
        } else {
            return "HARD"; // Level 3 and above are HARD
        }
    }

    public Question getNextQuestion() {
        currentQuestion = questionBank.getRandomQuestionByDifficulty(currentEnemy.getDifficulty());
        return currentQuestion;
    }

    public String processAnswer(int choiceIndex) {
        StringBuilder log = new StringBuilder();
        totalQuestionsAsked++;
        
        // Correct answer check (Hero's Turn)
        if (choiceIndex != -1 && currentQuestion.checkAnswer(choiceIndex)) {
            correctAnswersCount++;
            int damage = hero.attack();
            currentEnemy.takeDamage(damage);
            log.append("CORRECT! ").append(hero.getName()).append(" deals ").append(damage).append(" damage.\n");
            
            if (!currentEnemy.isAlive()) {
                battlesWon++;
                log.append(currentEnemy.getName()).append(" defeated! Gained ").append(currentEnemy.getRewardExp()).append(" EXP.\n");
                hero.addExp(currentEnemy.getRewardExp());
                
                // Check if it was a boss
                if (currentEnemy instanceof Boss) {
                    currentLevel++;
                    enemiesDefeatedInLevel = 0;
                    log.append("--- LEVEL CLEARED! Moving to Level ").append(currentLevel).append(" ---\n");
                } else {
                    enemiesDefeatedInLevel++;
                }
                
                spawnNextEnemy();
                log.append("Up Next: ").append(currentEnemy.getName()).append("!\n");
            }
        } 
        // Wrong answer or Timeout (Enemy's Turn)
        else {
            int damage = currentEnemy.attack();
            // Scaling damage based on accuracy? Maybe just flat scale
            hero.takeDamage(damage);
            String reason = (choiceIndex == -1) ? "TIMEOUT!" : "WRONG!";
            log.append(reason).append(" ").append(currentEnemy.getName()).append(" deals ").append(damage).append(" damage.\n");
        }
        
        return log.toString();
    }

    public double getAccuracy() {
        if (totalQuestionsAsked == 0) return 1.0;
        return (double) correctAnswersCount / totalQuestionsAsked;
    }

    public int getCurrentLevel() { return currentLevel; }
    public int getEnemiesDefeatedInLevel() { return enemiesDefeatedInLevel; }

    public Hero getHero() { return hero; }
    public Enemy getCurrentEnemy() { return currentEnemy; }
    public boolean isGameOver() { return !hero.isAlive(); }
    public int getBattlesWon() { return battlesWon; }
}
