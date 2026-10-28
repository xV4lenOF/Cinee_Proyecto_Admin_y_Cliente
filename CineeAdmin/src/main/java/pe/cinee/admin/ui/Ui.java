package pe.cinee.admin.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class Ui {
    public static final Color BG = new Color(10, 15, 28);
    public static final Color SIDEBAR = new Color(13, 20, 36);
    public static final Color CARD = new Color(21, 30, 49);
    public static final Color CARD_ALT = new Color(25, 37, 59);
    public static final Color BORDER = new Color(42, 55, 78);
    public static final Color TEXT = new Color(240, 245, 252);
    public static final Color MUTED = new Color(151, 165, 188);
    public static final Color RED = new Color(225, 29, 72);
    public static final Color BLUE = new Color(56, 189, 248);
    public static final Color GREEN = new Color(52, 211, 153);
    public static final Color GOLD = new Color(251, 191, 36);
    private Ui() { }

    public static JPanel panel(Color background) { JPanel p = new JPanel(); p.setBackground(background); return p; }
    public static JLabel label(String text, int size, boolean bold, Color color) {
        JLabel l = new JLabel(text); l.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, size)); l.setForeground(color); return l;
    }
    public static JButton button(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13)); b.setForeground(TEXT); b.setBackground(bg);
        b.setFocusPainted(false); b.setBorderPainted(false); b.setOpaque(true); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 15, 10, 15)); return b;
    }
    public static JLabel money(BigDecimal amount, int size, boolean bold, Color color) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-PE"));
        nf.setCurrency(java.util.Currency.getInstance("PEN"));
        JLabel l = label(nf.format(amount == null ? BigDecimal.ZERO : amount), size, bold, color); return l;
    }
    public static String moneyText(BigDecimal amount) { return "S/ " + String.format(Locale.US, "%,.2f", amount == null ? BigDecimal.ZERO : amount); }
    public static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13)); table.setRowHeight(34);
        table.setBackground(CARD); table.setForeground(TEXT); table.setGridColor(BORDER);
        table.setSelectionBackground(new Color(120, 32, 58)); table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(false); table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(CARD_ALT); table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setBorder(new EmptyBorder(8, 6, 8, 6));
        table.getTableHeader().setReorderingAllowed(false);
    }
    public static JPanel cardLayout(String title, String subtitle, Component content) {
        JPanel p = new JPanel(new BorderLayout(0, 12)); p.setBackground(CARD);
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), new EmptyBorder(16, 16, 16, 16)));
        JPanel top = new JPanel(); top.setOpaque(false); top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(label(title, 16, true, TEXT)); if (subtitle != null && !subtitle.isBlank()) { top.add(Box.createVerticalStrut(4)); top.add(label(subtitle, 12, false, MUTED)); }
        p.add(top, BorderLayout.NORTH); p.add(content, BorderLayout.CENTER); return p;
    }
    public static JScrollPane scroll(Component c) {
        JScrollPane sp = new JScrollPane(c); sp.setBorder(BorderFactory.createLineBorder(BORDER));
        sp.getViewport().setBackground(CARD); sp.getVerticalScrollBar().setUnitIncrement(16); return sp;
    }
}
