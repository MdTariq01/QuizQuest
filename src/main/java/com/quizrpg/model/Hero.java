package com.quizrpg.model;

import java.util.Random;

/**
 * Hero subclass extending Character.
 * Follows OOP Principles: Inheritance and Polymorphism.
 */
public class Hero extends Character {
    private int level;
    private int exp;
    private int powerUps;
    private boolean nextAttackDoubleDamage = false;
    private static final Random random = new Random();

    public Hero(String name) {
        super(name, 100);
        this.level = 1;
        this.exp = 0;
        this.powerUps = 2; // Default start with 2 powerups
    }

    @Override
    public int attack() {
        // Base damage increases with level
        int baseDamage = 15 + (level * 5);
        
        // Critical hit chance (15%)
        boolean isCritical = random.nextInt(100) < 15;
        if (isCritical) {
            System.out.println("Critical Hit!");
            baseDamage *= 2;
        }

        // Apply double damage power-up
        if (nextAttackDoubleDamage) {
            System.out.println("Power-up: Double Damage Active!");
            baseDamage *= 2;
            nextAttackDoubleDamage = false; // Consume power-up
        }

        return baseDamage;
    }

    public void addExp(int amount) {
        this.exp += amount;
        if (this.exp >= (level * 100)) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        exp = 0;
        maxHealth += 20;
        this.health = maxHealth; // Heal to full on level up
        System.out.println("Hero Leveled Up! Now level " + level);
    }

    public void useHealPowerUp() {
        if (powerUps > 0) {
            heal(40); // Restore 40 HP
            powerUps--;
            System.out.println("Hero used Heal Power-up!");
        }
    }

    public void useDoubleDamagePowerUp() {
        if (powerUps > 0) {
            nextAttackDoubleDamage = true;
            powerUps--;
            System.out.println("Hero used Double Damage Power-up!");
        }
    }

    public boolean isDoubleDamageActive() {
        return nextAttackDoubleDamage;
    }

    public int getLevel() { return level; }
    public int getExp() { return exp; }
    public int getPowerUps() { return powerUps; }
}
