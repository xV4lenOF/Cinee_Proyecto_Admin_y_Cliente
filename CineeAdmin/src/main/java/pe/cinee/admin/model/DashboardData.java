package pe.cinee.admin.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DashboardData(
        long saleCount,
        BigDecimal revenue,
        BigDecimal averageSale,
        long customerCount,
        List<DailyRevenue> dailyRevenue,
        List<NamedValue> paymentTotals,
        List<NamedValue> topMovies) {
    public record DailyRevenue(LocalDate date, BigDecimal revenue, long sales) { }
    public record NamedValue(String name, BigDecimal value, long count) { }
}
