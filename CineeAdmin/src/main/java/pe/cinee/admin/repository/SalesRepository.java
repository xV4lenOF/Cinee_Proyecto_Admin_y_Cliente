package pe.cinee.admin.repository;

import pe.cinee.admin.Database;
import pe.cinee.admin.model.DashboardData;
import pe.cinee.admin.model.DetailLine;
import pe.cinee.admin.model.SaleDetail;
import pe.cinee.admin.model.SaleRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SalesRepository {
    private static final String HEADER_SELECT = """
        SELECT v.venta_id, v.fecha_venta, COALESCE(v.cliente_nombre_snapshot,'Cliente no registrado') AS cliente,
               COALESCE(v.cliente_documento_snapshot,'—') AS documento,
               COALESCE(v.cliente_email_snapshot,'') AS email, COALESCE(v.cliente_telefono_snapshot,'') AS telefono,
               COALESCE(NULLIF(v.pelicula_titulo_snapshot,''),'Dulcería / sin función') AS pelicula,
               COALESCE(v.fecha_funcion_snapshot,'—') AS fecha_funcion,
               COALESCE(v.hora_funcion_snapshot,'—') AS hora_funcion,
               COALESCE(v.codigo_sala_snapshot,'—') AS sala_codigo,
               COALESCE(CAST(v.numero_sala_snapshot AS CHAR),'—') AS sala_numero,
               COALESCE((SELECT GROUP_CONCAT(va.codigo_asiento ORDER BY va.codigo_asiento SEPARATOR ', ')
                         FROM venta_asientos va WHERE va.venta_id = v.venta_id),'—') AS asientos,
               COALESCE(v.metodo_pago,'No registrado') AS metodo_pago, v.total, COALESCE(v.estado,'CONFIRMADA') AS estado,
               COALESCE(v.ticket_codigo,'—') AS ticket_codigo, COALESCE(v.qr_codigo,'—') AS qr_codigo,
               COALESCE(v.codigo_funcion,'') AS codigo_funcion
        FROM ventas v
        """;

    public List<SaleRecord> getSales(LocalDate from, LocalDate to, String search) throws SQLException, IOException {
        StringBuilder sql = new StringBuilder(HEADER_SELECT).append(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();
        appendDateFilter(sql, args, from, to);
        if (search != null && !search.isBlank()) {
            sql.append(" AND (v.venta_id LIKE ? OR v.cliente_nombre_snapshot LIKE ? OR v.cliente_documento_snapshot LIKE ?")
               .append(" OR v.pelicula_titulo_snapshot LIKE ? OR v.ticket_codigo LIKE ? OR EXISTS (SELECT 1 FROM venta_asientos va2 WHERE va2.venta_id=v.venta_id AND va2.codigo_asiento LIKE ?))");
            String pattern = "%" + search.trim() + "%";
            for (int i = 0; i < 6; i++) args.add(pattern);
        }
        sql.append(" ORDER BY v.fecha_venta DESC, v.venta_id DESC LIMIT 2500");
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            bind(ps, args);
            try (ResultSet rs = ps.executeQuery()) {
                List<SaleRecord> list = new ArrayList<>();
                while (rs.next()) list.add(readSale(rs));
                return list;
            }
        }
    }

    public SaleDetail getSaleDetail(String saleId) throws SQLException, IOException {
        SaleRecord sale;
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement(HEADER_SELECT + " WHERE v.venta_id = ?")) {
            ps.setString(1, saleId);
            try (ResultSet rs = ps.executeQuery()) { if (!rs.next()) return null; sale = readSale(rs); }
            List<DetailLine> lines = new ArrayList<>();
            try (PreparedStatement dp = c.prepareStatement("SELECT producto_codigo, producto_nombre, categoria, cantidad, precio_unitario, subtotal FROM venta_detalles WHERE venta_id = ? ORDER BY detalle_id")) {
                dp.setString(1, saleId);
                try (ResultSet dr = dp.executeQuery()) {
                    while (dr.next()) lines.add(new DetailLine(dr.getString(1), dr.getString(2), dr.getString(3), dr.getInt(4), dr.getBigDecimal(5), dr.getBigDecimal(6)));
                }
            }
            return new SaleDetail(sale, List.copyOf(lines));
        }
    }

    public DashboardData getDashboard(LocalDate from, LocalDate to) throws SQLException, IOException {
        long count; BigDecimal revenue; BigDecimal avg; long customers;
        StringBuilder summary = new StringBuilder("SELECT COUNT(*), COALESCE(SUM(total),0), COALESCE(AVG(total),0), COUNT(DISTINCT cliente_id) FROM ventas WHERE estado <> 'ANULADA'");
        List<Object> args = new ArrayList<>(); appendDateFilter(summary, args, from, to);
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement(summary.toString())) {
            bind(ps, args); try (ResultSet rs = ps.executeQuery()) { rs.next(); count=rs.getLong(1); revenue=rs.getBigDecimal(2); avg=rs.getBigDecimal(3); customers=rs.getLong(4); }
        }
        List<DashboardData.DailyRevenue> days = new ArrayList<>();
        StringBuilder daily = new StringBuilder("SELECT DATE(fecha_venta), COALESCE(SUM(total),0), COUNT(*) FROM ventas WHERE estado <> 'ANULADA'");
        args = new ArrayList<>(); appendDateFilter(daily, args, from, to); daily.append(" GROUP BY DATE(fecha_venta) ORDER BY DATE(fecha_venta)");
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement(daily.toString())) {
            bind(ps,args); try (ResultSet rs=ps.executeQuery()) { while (rs.next()) days.add(new DashboardData.DailyRevenue(rs.getDate(1).toLocalDate(), rs.getBigDecimal(2), rs.getLong(3))); }
        }
        List<DashboardData.NamedValue> methods = new ArrayList<>();
        StringBuilder pay = new StringBuilder("SELECT COALESCE(metodo_pago,'No registrado'), COALESCE(SUM(total),0), COUNT(*) FROM ventas WHERE estado <> 'ANULADA'");
        args = new ArrayList<>(); appendDateFilter(pay,args,from,to); pay.append(" GROUP BY metodo_pago ORDER BY SUM(total) DESC");
        try (Connection c=Database.connect(); PreparedStatement ps=c.prepareStatement(pay.toString())) {
            bind(ps,args); try(ResultSet rs=ps.executeQuery()) { while(rs.next()) methods.add(new DashboardData.NamedValue(rs.getString(1),rs.getBigDecimal(2),rs.getLong(3))); }
        }
        List<DashboardData.NamedValue> movies = new ArrayList<>();
        StringBuilder top = new StringBuilder("SELECT COALESCE(NULLIF(pelicula_titulo_snapshot,''),'Dulcería / sin función'), COALESCE(SUM(total),0), COUNT(*) FROM ventas WHERE estado <> 'ANULADA'");
        args = new ArrayList<>(); appendDateFilter(top,args,from,to); top.append(" GROUP BY COALESCE(NULLIF(pelicula_titulo_snapshot,''),'Dulcería / sin función') ORDER BY SUM(total) DESC LIMIT 6");
        try (Connection c=Database.connect(); PreparedStatement ps=c.prepareStatement(top.toString())) {
            bind(ps,args); try(ResultSet rs=ps.executeQuery()) { while(rs.next()) movies.add(new DashboardData.NamedValue(rs.getString(1),rs.getBigDecimal(2),rs.getLong(3))); }
        }
        return new DashboardData(count, revenue == null ? BigDecimal.ZERO : revenue, avg == null ? BigDecimal.ZERO : avg, customers, List.copyOf(days), List.copyOf(methods), List.copyOf(movies));
    }

    private static void appendDateFilter(StringBuilder sql, List<Object> args, LocalDate from, LocalDate to) {
        if (from != null) { sql.append(" AND DATE(fecha_venta) >= ?"); args.add(java.sql.Date.valueOf(from)); }
        if (to != null) { sql.append(" AND DATE(fecha_venta) <= ?"); args.add(java.sql.Date.valueOf(to)); }
    }
    private static void bind(PreparedStatement ps, List<Object> args) throws SQLException {
        for (int i=0;i<args.size();i++) ps.setObject(i+1,args.get(i));
    }
    private static SaleRecord readSale(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("fecha_venta");
        LocalDateTime date = timestamp == null ? null : timestamp.toLocalDateTime();
        BigDecimal total = rs.getBigDecimal("total");
        return new SaleRecord(rs.getString("venta_id"), date, rs.getString("cliente"), rs.getString("documento"),
                rs.getString("email"), rs.getString("telefono"), rs.getString("pelicula"), rs.getString("fecha_funcion"),
                rs.getString("hora_funcion"), rs.getString("sala_codigo"), rs.getString("sala_numero"), rs.getString("asientos"),
                rs.getString("metodo_pago"), total == null ? BigDecimal.ZERO : total, rs.getString("estado"),
                rs.getString("ticket_codigo"), rs.getString("qr_codigo"), rs.getString("codigo_funcion"));
    }
}
