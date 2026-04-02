package com.quizrpg;

import com.quizrpg.ui.GameUI;
import javax.swing.SwingUtilities;

/**
 * Entry point for the Quiz RPG Battle Game.
 */
public class Main {
    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (Best Practice)
        SwingUtilities.invokeLater(() -> {
            GameUI game = new GameUI();
            game.setVisible(true);
        });
    }
}
