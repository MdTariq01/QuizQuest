package model;

/**
 * Abstract class representing a Character in the Quiz RPG game.
 * Follows OOP Principles: Abstraction and Encapsulation.
 */
public abstract class Character {
    protected String name;
    protected int health;
    protected int maxHealth;

    public Character(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    /**
     * Abstract method to calculate attack damage.
     * Must be implemented by subclasses (Polymorphism).
     */
    public abstract int attack();

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    // Getters and Setters (Encapsulation)
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    
    public void heal(int amount) {
        this.health += amount;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }
}
