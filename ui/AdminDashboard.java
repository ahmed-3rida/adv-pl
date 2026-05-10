package ui;

import models.Admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    private final Admin admin;
    private final JPanel contentArea;


    public AdminDashboard(Admin admin) {
        super("Admin Dashboard");
        this.admin = admin;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 640);
        setLocationRelativeTo(null);
        UITheme.styleFrame(this);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);

        // ── Sidebar ───────────────────────────────────────────────────────────
        JPanel sidebar = buildSidebar();
        root.add(sidebar, BorderLayout.WEST);

        // ── Content area (right side placeholder) ─────────────────────────────
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(UITheme.BG_DARK);
        contentArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcome = new JLabel("Select an option from the menu", SwingConstants.CENTER);
        welcome.setFont(UITheme.FONT_H2);
        welcome.setForeground(UITheme.TEXT_MUTED);
        contentArea.add(welcome, BorderLayout.CENTER);
        root.add(contentArea, BorderLayout.CENTER);
    }

    // ── Sidebar builder ────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel sidebar = UITheme.sidebarPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, UITheme.BORDER_COLOR));
        sidebar.setBackground(new Color(12, 20, 48));

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(12, 20, 48));
        header.setBorder(BorderFactory.createEmptyBorder(24, 16, 20, 16));

        JLabel emoji = new JLabel("CMS");
        emoji.setFont(new Font("Segoe UI", Font.BOLD, 24));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appName = new JLabel("CMS Admin");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        appName.setForeground(UITheme.TEXT_PRIMARY);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel adminName = new JLabel(admin.getName());
        adminName.setFont(UITheme.FONT_SMALL);
        adminName.setForeground(UITheme.ACCENT);
        adminName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(UITheme.BORDER_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        header.add(emoji);
        header.add(Box.createVerticalStrut(6));
        header.add(appName);
        header.add(Box.createVerticalStrut(2));
        header.add(adminName);
        header.add(Box.createVerticalStrut(16));
        header.add(sep);

        // Nav items
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(new Color(12, 20, 48));
        nav.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JButton[] navBtns = {
            addNavBtn(nav, "Manage Students",       ""),
            addNavBtn(nav, "Manage Instructors",     ""),
            addNavBtn(nav, "Manage Parent Courses",  ""),
            addNavBtn(nav, "Manage Courses",         ""),
            addNavBtn(nav, "Manage Rooms",           ""),
            addNavBtn(nav, "Manage Branches",        ""),
            addNavBtn(nav, "Reports",                ""),
            addNavBtn(nav, "View Surveys",           ""),
        };

        navBtns[0].addActionListener(e -> { setVisible(false); new ManageStudentsFrame(admin).setVisible(true); });
        navBtns[1].addActionListener(e -> { setVisible(false); new ManageInstructorsFrame(admin).setVisible(true); });
        navBtns[2].addActionListener(e -> { setVisible(false); new ManageParentCoursesFrame(admin).setVisible(true); });
        navBtns[3].addActionListener(e -> { setVisible(false); new ManageCoursesFrame(admin).setVisible(true); });
        navBtns[4].addActionListener(e -> { setVisible(false); new ManageRoomsFrame(admin).setVisible(true); });
        navBtns[5].addActionListener(e -> { setVisible(false); new ManageBranchesFrame(admin).setVisible(true); });
        navBtns[6].addActionListener(e -> { setVisible(false); new ReportsFrame(admin).setVisible(true); });
        navBtns[7].addActionListener(e -> new ViewSurveysFrame().setVisible(true));

        // Footer logout
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(12, 20, 48));
        footer.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 0, 12, 0)
        ));
        JButton logoutBtn = UITheme.navButton("Logout", "");
        logoutBtn.setForeground(UITheme.DANGER);
        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { logoutBtn.setForeground(UITheme.DANGER.brighter()); }
            @Override public void mouseExited (MouseEvent e) { logoutBtn.setForeground(UITheme.DANGER); }
        });
        logoutBtn.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });
        footer.add(logoutBtn, BorderLayout.CENTER);

        sidebar.add(header, BorderLayout.NORTH);
        sidebar.add(nav,    BorderLayout.CENTER);
        sidebar.add(footer, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton addNavBtn(JPanel nav, String label, String emoji) {
        JButton btn = UITheme.navButton(label, emoji);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        nav.add(btn);
        return btn;
    }
}
