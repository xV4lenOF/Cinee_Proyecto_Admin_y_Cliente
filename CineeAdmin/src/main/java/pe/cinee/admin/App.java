package pe.cinee.admin;

import pe.cinee.admin.ui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class App {
    private App() { }
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) { }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
