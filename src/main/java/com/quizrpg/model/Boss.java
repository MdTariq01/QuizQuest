package com.quizrpg.model;

import java.util.Random;

/**
 * Boss extension of Enemy.
 * Follows OOP Principles: Multi-level Inheritance and Specialization.
 */
public class Boss extends Enemy {
    private static final Random random = new Random();

    public Boss(String name, int health) {
        super(name, health, "HARD", 500);
    }

    @Override
    public int attack() {
        int baseDamage = super.attack();
        // 20% chance to trigger special ability
        if (random.nextInt(100) < 20) {
            System.out.println("Boss special ability triggered!");
            return specialAbility(baseDamage);
        }
        return baseDamage;
    }

    public int specialAbility(int baseDamage) {
        int choice = random.nextInt(2);
        if (choice == 0) {
            // Heal for 20 points
            heal(20);
            System.out.println("Boss Healed!");
            return baseDamage;
        } else {
            // Double attack
            System.out.println("Boss Double Damage!");
            return baseDamage * 2;
        }
    }
}
