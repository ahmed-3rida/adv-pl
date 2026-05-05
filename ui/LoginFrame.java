package ui;

import models.*;
import service.AuthService;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginFrame extends JFrame {

    private final JTextField     emailField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        super("Courses Management System — Login");
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 560);
        setLocationRelativeTo(null);
        setResizable(false);
        UITheme.styleFrame(this);

        // ── Full-window gradient background ──────────────────────────────────
        JPanel bg = UITheme.gradientPanel();
        bg.setLayout(new GridBagLayout());
        setContentPane(bg);

        // ── Card ─────────────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UITheme.BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        card.setMaximumSize(new Dimension(380, 460));
        card.setPreferredSize(new Dimension(380, 460));

        // Logo / Icon area
        JLabel icon = new JLabel("[ CMS ]", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 28));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Sign in to your account", SwingConstants.CENTER);
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Form ─────────────────────────────────────────────────────────────
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(UITheme.BG_CARD);
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setMaximumSize(new Dimension(300, 200));

        JLabel emailLbl = UITheme.bodyLabel("Email Address");
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = UITheme.textField();
        emailField.setMaximumSize(new Dimension(300, 36));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passLbl = UITheme.bodyLabel("Password");
        passLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField = UITheme.passwordField();
        passwordField.setMaximumSize(new Dimension(300, 36));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.addActionListener(e -> doLogin());

        form.add(emailLbl);
        form.add(Box.createVerticalStrut(4));
        form.add(emailField);
        form.add(Box.createVerticalStrut(14));
        form.add(passLbl);
        form.add(Box.createVerticalStrut(4));
        form.add(passwordField);

        // ── Login button ─────────────────────────────────────────────────────
        JButton loginBtn = UITheme.primaryButton("Sign In");
        loginBtn.setMaximumSize(new Dimension(300, 42));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());

        // ── System label ─────────────────────────────────────────────────────
        JLabel footer = new JLabel("Courses Management System v1.0", SwingConstants.CENTER);
        footer.setFont(UITheme.FONT_SMALL);
        footer.setForeground(UITheme.TEXT_MUTED);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Assemble card ────────────────────────────────────────────────────
        card.add(icon);
        card.add(Box.createVerticalStrut(12));
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(28));
        card.add(form);
        card.add(Box.createVerticalStrut(20));
        card.add(loginBtn);
        card.add(Box.createVerticalStrut(24));
        card.add(footer);

        bg.add(card);
        setVisible(false);
    }

    private void doLogin() {
        String email    = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter your email and password.", "Input Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User user = AuthService.login(email, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this,
                    "Invalid email or password. Please try again.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                return;
            }

            setVisible(false);
            dispose();

            switch (user.getRole()) {
                case "ADMIN"      -> new AdminDashboard((Admin) user).setVisible(true);
                case "STUDENT"    -> new StudentDashboard((Student) user).setVisible(true);
                case "INSTRUCTOR" -> new InstructorDashboard((Instructor) user).setVisible(true);
                default           -> JOptionPane.showMessageDialog(null, "Unknown role: " + user.getRole());
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "IO Error: " + ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
