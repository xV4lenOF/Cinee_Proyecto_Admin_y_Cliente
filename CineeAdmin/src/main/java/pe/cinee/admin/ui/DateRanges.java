package pe.cinee.admin.ui;

import java.time.LocalDate;

public final class DateRanges {
    private DateRanges() { }
    public record Range(LocalDate from, LocalDate to) { }
    public static Range resolve(String value) {
        LocalDate today = LocalDate.now();
        return switch (value == null ? "Últimos 7 días" : value) {
            case "Hoy" -> new Range(today, today);
            case "Este mes" -> new Range(today.withDayOfMonth(1), today);
            case "Todo" -> new Range(null, null);
            case "Últimos 30 días" -> new Range(today.minusDays(29), today);
            default -> new Range(today.minusDays(6), today);
        };
    }
    public static javax.swing.JComboBox<String> createCombo(String initial) {
        javax.swing.JComboBox<String> combo = new javax.swing.JComboBox<>(new String[]{"Hoy","Últimos 7 días","Últimos 30 días","Este mes","Todo"});
        combo.setSelectedItem(initial); combo.setBackground(Ui.CARD_ALT); combo.setForeground(Ui.TEXT); combo.setFont(new java.awt.Font("Segoe UI",java.awt.Font.PLAIN,12)); return combo;
    }
}
