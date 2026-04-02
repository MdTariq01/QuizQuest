package com.quizrpg.model;

import java.util.Random;

/**
 * Enemy subclass extending Character.
 * Follows OOP Principles: Inheritance and Polymorphism.
 */
public class Enemy extends Character {
    protected String difficulty;
    protected int rewardExp;
    private static final Random random = new Random();

    public Enemy(String name, int health, String difficulty, int rewardExp) {
        super(name, health);
        this.difficulty = difficulty;
        this.rewardExp = rewardExp;
    }

    @Override
    public int attack() {
        int baseDamage = 10;
        if (difficulty.equals("MEDIUM")) {
            baseDamage += 10;
        } else if (difficulty.equals("HARD")) {
            baseDamage += 20;
        }
        return baseDamage;
    }

    public int getRewardExp() { return rewardExp; }
    public String getDifficulty() { return difficulty; }
}
