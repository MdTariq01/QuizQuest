
import ui.GameUI;
import javax.swing.SwingUtilities;

/**
 * Entry point for the QuizQuest Battle Game.
 */
public class QuizQuest {
    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (Best Practice)
        SwingUtilities.invokeLater(() -> {
            GameUI game = new GameUI();
            game.setVisible(true);
        });
    }
}
