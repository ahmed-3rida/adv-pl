import ui.LoginFrame;
import javax.swing.*;

/**
 * Entry point for the Courses Management System (Swing GUI).
 */
public class App {

    public static void main(String[] args) {
        // Launch GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
