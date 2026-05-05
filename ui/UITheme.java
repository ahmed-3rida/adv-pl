package ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Central design system for the Courses Management System.
 * All colours, fonts and helper factories live here.
 */
public class UITheme {

    // ── Palette ──────────────────────────────────────────────────────────────
    public static final Color BG_DARK        = new Color(15,  23,  42);   // #0f172a
    public static final Color BG_CARD        = new Color(30,  41,  59);   // #1e293b
    public static final Color BG_CARD2       = new Color(38,  52,  74);   // slightly lighter
    public static final Color ACCENT         = new Color(99, 179, 237);   // sky-blue #63b3ed
    public static final Color ACCENT_DARK    = new Color(49, 130, 206);   // #3182ce
    public static final Color ACCENT_LIGHT   = new Color(144,205,244);    // hover tint
    public static final Color SUCCESS        = new Color(72, 187,120);    // #48bb78
    public static final Color DANGER         = new Color(252, 95, 95);    // #fc5f5f
    public static final Color TEXT_PRIMARY   = new Color(226,232,240);    // #e2e8f0
    public static final Color TEXT_SECONDARY = new Color(148,163,184);    // #94a3b8
    public static final Color TEXT_MUTED     = new Color(100,116,139);    // #64748b
    public static final Color BORDER_COLOR   = new Color(51, 65, 85);     // #334155
    public static final Color TABLE_HEADER   = new Color(30, 64, 120);    // deep blue
    public static final Color TABLE_ALT      = new Color(22, 32, 52);     // alternate row

    // ── Typography ───────────────────────────────────────────────────────────
    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONT_H2     = new Font("Segoe UI", Font.BOLD,  15);
    public static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_LABEL  = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD,  12);

    // ── Global L&F ───────────────────────────────────────────────────────────
    public static void applyGlobalDefaults() {
        UIManager.put("Panel.background",            BG_DARK);
        UIManager.put("OptionPane.background",       BG_CARD);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("Button.background",           ACCENT_DARK);
        UIManager.put("Button.foreground",           Color.WHITE);
        UIManager.put("Button.font",                 FONT_BTN);
        UIManager.put("Label.foreground",            TEXT_PRIMARY);
        UIManager.put("Label.font",                  FONT_BODY);
        UIManager.put("TextField.background",        BG_CARD2);
        UIManager.put("TextField.foreground",        TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground",   ACCENT);
        UIManager.put("TextField.font",              FONT_BODY);
        UIManager.put("PasswordField.background",    BG_CARD2);
        UIManager.put("PasswordField.foreground",    TEXT_PRIMARY);
        UIManager.put("PasswordField.caretForeground", ACCENT);
        UIManager.put("ComboBox.background",         BG_CARD2);
        UIManager.put("ComboBox.foreground",         TEXT_PRIMARY);
        UIManager.put("ComboBox.font",               FONT_BODY);
        UIManager.put("TextArea.background",         BG_CARD2);
        UIManager.put("TextArea.foreground",         TEXT_PRIMARY);
        UIManager.put("TextArea.font",               FONT_BODY);
        UIManager.put("ScrollPane.background",       BG_DARK);
        UIManager.put("ScrollBar.background",        BG_CARD);
        UIManager.put("ScrollBar.thumb",             new Color(71,85,105));
        UIManager.put("TabbedPane.background",       BG_DARK);
        UIManager.put("TabbedPane.foreground",       TEXT_SECONDARY);
        UIManager.put("TabbedPane.selected",         BG_CARD);
        UIManager.put("TabbedPane.selectedForeground", ACCENT);
        UIManager.put("TabbedPane.font",             FONT_BTN);
        UIManager.put("Table.background",            BG_DARK);
        UIManager.put("Table.foreground",            TEXT_PRIMARY);
        UIManager.put("Table.gridColor",             BORDER_COLOR);
        UIManager.put("Table.font",                  FONT_BODY);
        UIManager.put("TableHeader.background",      TABLE_HEADER);
        UIManager.put("TableHeader.foreground",      Color.WHITE);
        UIManager.put("TableHeader.font",            FONT_LABEL);
        UIManager.put("Table.selectionBackground",   ACCENT_DARK);
        UIManager.put("Table.selectionForeground",   Color.WHITE);
    }

    // ── Factories ─────────────────────────────────────────────────────────────

    /** Dark-themed JFrame base: sets icon-color, min-size, etc. */
    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(BG_DARK);
    }

    /** Styled card panel with rounded border feel */
    public static JPanel cardPanel(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        return p;
    }

    /** Title label */
    public static JLabel titleLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    /** Section heading */
    public static JLabel h2Label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_H2);
        lbl.setForeground(ACCENT);
        return lbl;
    }

    /** Standard body label */
    public static JLabel bodyLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }

    /** Styled text field */
    public static JTextField textField() {
        JTextField tf = new JTextField();
        styleTextField(tf);
        return tf;
    }

    public static JPasswordField passwordField() {
        JPasswordField pf = new JPasswordField();
        styleTextField(pf);
        return pf;
    }

    private static void styleTextField(JTextField tf) {
        tf.setBackground(BG_CARD2);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(ACCENT);
        tf.setFont(FONT_BODY);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        tf.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(ACCENT, 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
        });
    }

    /** Primary accent button */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed()  ? ACCENT_DARK.darker()
                         : getModel().isRollover() ? ACCENT_DARK.brighter()
                         : ACCENT_DARK;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }

    /** Danger/destructive button (red) */
    public static JButton dangerButton(String text) {
        JButton btn = primaryButton(text);
        btn.setForeground(Color.WHITE);
        // override paint
        btn.putClientProperty("dangerBtn", true);
        return btn;
    }

    /** Secondary (outline) button */
    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setForeground(TEXT_SECONDARY);
        btn.setBackground(BG_CARD2);
        btn.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setForeground(ACCENT);
                btn.setBorder(new LineBorder(ACCENT, 1, true));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setForeground(TEXT_SECONDARY);
                btn.setBorder(new LineBorder(BORDER_COLOR, 1, true));
            }
        });
        return btn;
    }

    /** Success (green) button */
    public static JButton successButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed()  ? SUCCESS.darker()
                         : getModel().isRollover() ? SUCCESS.brighter()
                         : SUCCESS;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }

    /** Style a JTable with dark theme */
    public static void styleTable(JTable table) {
        table.setBackground(BG_DARK);
        table.setForeground(TEXT_PRIMARY);
        table.setGridColor(BORDER_COLOR);
        table.setFont(FONT_BODY);
        table.setRowHeight(28);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(ACCENT_DARK);
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER);
        header.setForeground(Color.WHITE);
        header.setFont(FONT_LABEL);
        header.setBorder(BorderFactory.createEmptyBorder());

        // Alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                setForeground(sel ? Color.WHITE : TEXT_PRIMARY);
                setBackground(sel ? ACCENT_DARK : (row % 2 == 0 ? BG_DARK : TABLE_ALT));
                setFont(FONT_BODY);
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                return this;
            }
        });
    }

    /** Styled scroll pane */
    public static JScrollPane scrollPane(Component view) {
        JScrollPane sp = new JScrollPane(view);
        sp.getViewport().setBackground(BG_DARK);
        sp.setBackground(BG_DARK);
        sp.setBorder(new LineBorder(BORDER_COLOR, 1));
        sp.getVerticalScrollBar().setBackground(BG_CARD);
        sp.getHorizontalScrollBar().setBackground(BG_CARD);
        return sp;
    }

    /** Styled JComboBox */
    public static <T> JComboBox<T> comboBox() {
        JComboBox<T> cb = new JComboBox<>();
        cb.setBackground(BG_CARD2);
        cb.setForeground(TEXT_PRIMARY);
        cb.setFont(FONT_BODY);
        cb.setBorder(new LineBorder(BORDER_COLOR, 1));
        return cb;
    }

    /** Gradient background panel (for login) */
    public static JPanel gradientPanel() {
        return new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(
                    0, 0,             new Color(10, 19, 48),
                    getWidth(), getHeight(), new Color(20, 50, 100)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    /** Sidebar panel (for admin dashboard) */
    public static JPanel sidebarPanel() {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(
                    0, 0,             new Color(15, 28, 64),
                    0, getHeight(),   new Color(10, 19, 48)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        p.setOpaque(false);
        return p;
    }

    /** Sidebar navigation button */
    public static JButton navButton(String text, String emoji) {
        JButton btn = new JButton("  >  " + text) {
            boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hovered || getModel().isPressed()) {
                    g2.setColor(new Color(99, 179, 237, 40));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.setColor(ACCENT);
                    g2.fillRoundRect(0, 0, 4, getHeight(), 2, 2);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(TEXT_SECONDARY);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 10));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setForeground(ACCENT); }
            @Override public void mouseExited (MouseEvent e) { btn.setForeground(TEXT_SECONDARY); }
        });
        return btn;
    }
}
