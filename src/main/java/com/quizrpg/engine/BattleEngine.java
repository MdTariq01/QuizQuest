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

    public BattleEngine(String heroName) {
        this.hero = new Hero(heroName);
        this.questionBank = new QuestionBank();
        this.battlesWon = 0;
        startBattle();
    }

    public void startBattle() {
        spawnNextEnemy();
    }

    public void spawnNextEnemy() {
        battlesWon++;
        if (battlesWon % 5 == 0) {
            // Boss Battle
            currentEnemy = new Boss("The OOP Overlord", 250);
        } else {
            // Random scaling enemy
            String diff = getEnemyDifficulty();
            int health = 50 + (battlesWon * 10);
            currentEnemy = new Enemy("Java Shadow lvl " + battlesWon, health, diff, 50 + (battlesWon * 20));
        }
    }

    private String getEnemyDifficulty() {
        if (battlesWon <= 3) return "EASY";
        if (battlesWon <= 8) return "MEDIUM";
        return "HARD";
    }

    public Question getNextQuestion() {
        currentQuestion = questionBank.getRandomQuestionByDifficulty(currentEnemy.getDifficulty());
        return currentQuestion;
    }

    public String processAnswer(int choiceIndex) {
        StringBuilder log = new StringBuilder();
        
        // Correct answer check (Hero's Turn)
        if (choiceIndex != -1 && currentQuestion.checkAnswer(choiceIndex)) {
            int damage = hero.attack();
            currentEnemy.takeDamage(damage);
            log.append("CORRECT! ").append(hero.getName()).append(" attacks ").append(currentEnemy.getName()).append(" for ").append(damage).append(" damage.\n");
            
            if (!currentEnemy.isAlive()) {
                log.append("ENEMY DEFEATED! Gained ").append(currentEnemy.getRewardExp()).append(" EXP.\n");
                hero.addExp(currentEnemy.getRewardExp());
                spawnNextEnemy();
                log.append("Next Opponent: ").append(currentEnemy.getName()).append("!\n");
            }
        } 
        // Wrong answer or Timeout (Enemy's Turn)
        else {
            int damage = currentEnemy.attack();
            hero.takeDamage(damage);
            String reason = (choiceIndex == -1) ? "Timeout!" : "Wrong Answer!";
            log.append(reason).append(" ").append(currentEnemy.getName()).append(" counter-attacks for ").append(damage).append(" damage.\n");
        }
        
        return log.toString();
    }

    public Hero getHero() { return hero; }
    public Enemy getCurrentEnemy() { return currentEnemy; }
    public boolean isGameOver() { return !hero.isAlive(); }
    public int getBattlesWon() { return battlesWon; }
}
