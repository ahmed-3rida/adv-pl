import service.AuthService;
import ui.LoginFrame;

import javax.swing.*;
import java.io.IOException;

/**
 * Entry point for the Courses Management System (Swing GUI).
 */
public class App {

    public static void main(String[] args) {
        // Seed default admin on first run (console output only for bootstrap info)
        try {
            AuthService.seedDefaultAdmin();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Could not initialise data directory:\n" + e.getMessage(),
                    "Fatal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Launch GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
