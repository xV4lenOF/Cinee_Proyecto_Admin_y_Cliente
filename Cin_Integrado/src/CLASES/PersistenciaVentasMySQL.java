package CLASES;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Integración JDBC para el proyecto cliente Cin. Copia esta clase a src/CLASES.
 * Usa el mismo archivo %USERPROFILE%\\.cinee.properties que el panel administrador.
 * No almacena datos de la tarjeta; solamente registra el método de pago elegido.
 */
public final class PersistenciaVentasMySQL {
    private static final Path CONFIG = Paths.get(System.getProperty("user.home"), ".cinee.properties");
    private PersistenciaVentasMySQL() { }

    private static Connection conectar() throws SQLException {
        Properties p = new Properties();
        if (Files.exists(CONFIG)) {
            try (InputStream in = Files.newInputStream(CONFIG)) { p.load(in); }
            catch (IOException ex) { throw new SQLException("No se pudo leer la configuración " + CONFIG, ex); }
        }
        String url = p.getProperty("db.url", "jdbc:mysql://localhost:3306/cinee_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima&characterEncoding=UTF-8");
        String user = p.getProperty("db.user", "cinee_app");
        String password = p.getProperty("db.password", "");
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException ex) { throw new SQLException("No se encuentra mysql-connector-j en el classpath del proyecto Cin.", ex); }
        return DriverManager.getConnection(url, user, password);
    }

    /** Persiste el encabezado, productos y butacas en una sola transacción. */
    public static void guardarVenta(Venta venta, Cliente cliente, String tipoDocumento,
                                    String metodoPago, Ticket ticket) throws SQLException {
        if (venta == null) throw new SQLException("No se puede guardar una venta vacía.");
        try (Connection c = conectar()) {
            c.setAutoCommit(false);
            try {
                Long clienteId = insertarCliente(c, cliente, tipoDocumento);
                Preventa preventa = venta.getPreventa();
                Funcion funcion = preventa == null ? null : preventa.getFuncion();
                String codigoFuncion = prepararFuncion(c, funcion);
                String salaCodigo = funcion != null && funcion.getSala() != null ? funcion.getSala().getSalaID() : null;
                Integer salaNumero = funcion != null && funcion.getSala() != null ? funcion.getSala().getNumero() : null;
                String peliTitulo = funcion != null && funcion.getPelicula() != null ? funcion.getPelicula().getTitulo() : null;
                String fechaFuncion = funcion == null ? null : funcion.getFecha();
                String horaFuncion = funcion == null ? null : funcion.getHora();

                String insertVenta = "INSERT INTO ventas (venta_id, fecha_venta, cliente_id, codigo_funcion, cliente_tipo_documento_snapshot, cliente_documento_snapshot, cliente_nombre_snapshot, cliente_email_snapshot, cliente_telefono_snapshot, pelicula_titulo_snapshot, fecha_funcion_snapshot, hora_funcion_snapshot, codigo_sala_snapshot, numero_sala_snapshot, ticket_codigo, qr_codigo, metodo_pago, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'CONFIRMADA')";
                try (PreparedStatement ps = c.prepareStatement(insertVenta)) {
                    ps.setString(1, venta.getVentaID());
                    ps.setTimestamp(2, new Timestamp(venta.getFecha().getTime()));
                    if (clienteId == null) ps.setNull(3, java.sql.Types.BIGINT); else ps.setLong(3, clienteId);
                    if (codigoFuncion == null) ps.setNull(4, java.sql.Types.VARCHAR); else ps.setString(4, codigoFuncion);
                    ps.setString(5, tipoDocumento); ps.setString(6, cliente == null ? null : cliente.getUsuarioID());
                    ps.setString(7, cliente == null ? null : cliente.getNombre()); ps.setString(8, cliente == null ? null : cliente.getEmail());
                    ps.setString(9, cliente == null ? null : cliente.getTelefono()); ps.setString(10, peliTitulo);
                    ps.setString(11, fechaFuncion); ps.setString(12, horaFuncion); ps.setString(13, salaCodigo);
                    if (salaNumero == null) ps.setNull(14, java.sql.Types.INTEGER); else ps.setInt(14, salaNumero);
                    ps.setString(15, ticket == null ? null : ticket.getTicketID()); ps.setString(16, ticket == null ? null : ticket.getCodigoQR());
                    ps.setString(17, metodoPago == null || metodoPago.isBlank() ? "No registrado" : metodoPago);
                    ps.setBigDecimal(18, BigDecimal.valueOf(venta.getTotal()).setScale(2, RoundingMode.HALF_UP));
                    ps.executeUpdate();
                }

                String insertDetail = "INSERT INTO venta_detalles (venta_id, producto_codigo, producto_nombre, categoria, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = c.prepareStatement(insertDetail)) {
                    for (Detalle_Venta d : venta.getDetalles()) {
                        Producto product = d.getProducto();
                        String productId = product == null ? "PRODUCTO" : nonEmpty(product.getProductoID(), "PRODUCTO");
                        String name = product == null ? "Producto" : nonEmpty(product.getNombre(), "Producto");
                        String category = categoryOf(product);
                        BigDecimal subtotal = BigDecimal.valueOf(d.getSubTotal()).setScale(2, RoundingMode.HALF_UP);
                        BigDecimal unit = d.getCantidad() > 0 ? subtotal.divide(BigDecimal.valueOf(d.getCantidad()), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
                        upsertProduct(c, productId, name, category, unit);
                        ps.setString(1, venta.getVentaID()); ps.setString(2, productId); ps.setString(3, name);
                        ps.setString(4, category); ps.setInt(5, d.getCantidad()); ps.setBigDecimal(6, unit); ps.setBigDecimal(7, subtotal); ps.addBatch();
                    }
                    ps.executeBatch();
                }

                if (preventa != null && codigoFuncion != null) {
                    try (PreparedStatement ps = c.prepareStatement("INSERT INTO venta_asientos (venta_id, codigo_funcion, codigo_asiento, codigo_sala_snapshot) VALUES (?, ?, ?, ?)")) {
                        for (Asiento seat : preventa.getAsientos()) {
                            ps.setString(1, venta.getVentaID()); ps.setString(2, codigoFuncion);
                            ps.setString(3, seat.getNumero()); ps.setString(4, salaCodigo); ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                }
                c.commit();
            } catch (SQLException | RuntimeException ex) {
                try { c.rollback(); } catch (SQLException ignored) { }
                throw ex;
            }
        }
    }

    /** Devuelve asientos ya vendidos en una función para bloquearlos al abrir el mapa. */
    public static Set<String> obtenerAsientosVendidos(int peliculaId, String fecha, String hora, String salaCodigo) throws SQLException {
        Set<String> seats = new HashSet<>();
        String sql = "SELECT va.codigo_asiento FROM venta_asientos va JOIN funciones f ON f.codigo_funcion = va.codigo_funcion JOIN ventas v ON v.venta_id=va.venta_id WHERE f.pelicula_id = ? AND f.fecha_funcion = ? AND f.hora_funcion = ? AND f.codigo_sala = ? AND v.estado <> 'ANULADA'";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, peliculaId); ps.setString(2, fecha); ps.setString(3, hora); ps.setString(4, salaCodigo);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) seats.add(rs.getString(1)); }
        }
        return seats;
    }

    private static Long insertarCliente(Connection c, Cliente cliente, String tipoDocumento) throws SQLException {
        if (cliente == null) return null;
        String sql = "INSERT INTO clientes (tipo_documento, documento, nombre, email, telefono) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE cliente_id=LAST_INSERT_ID(cliente_id), nombre=VALUES(nombre), email=VALUES(email), telefono=VALUES(telefono)";
        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tipoDocumento); ps.setString(2, cliente.getUsuarioID()); ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getEmail()); ps.setString(5, cliente.getTelefono()); ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getLong(1) : null; }
        }
    }

    private static String prepararFuncion(Connection c, Funcion funcion) throws SQLException {
        if (funcion == null || funcion.getPelicula() == null || funcion.getSala() == null) return null;
        Pelicula movie = funcion.getPelicula(); Sala room = funcion.getSala();
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO peliculas (pelicula_id, titulo) VALUES (?, ?) ON DUPLICATE KEY UPDATE titulo=VALUES(titulo)")) {
            ps.setInt(1, movie.getId()); ps.setString(2, movie.getTitulo()); ps.executeUpdate();
        }
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO salas (codigo_sala, numero_sala, capacidad) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE numero_sala=VALUES(numero_sala), capacidad=VALUES(capacidad)")) {
            ps.setString(1, room.getSalaID()); ps.setInt(2, room.getNumero()); ps.setInt(3, room.getCapacidad()); ps.executeUpdate();
        }
        String code = movie.getId() + "|" + nonEmpty(funcion.getFecha(), "SIN_FECHA") + "|" + nonEmpty(funcion.getHora(), "SIN_HORA") + "|" + room.getSalaID();
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO funciones (codigo_funcion, funcion_id_origen, pelicula_id, codigo_sala, fecha_funcion, hora_funcion) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE funcion_id_origen=VALUES(funcion_id_origen), pelicula_id=VALUES(pelicula_id), codigo_sala=VALUES(codigo_sala), fecha_funcion=VALUES(fecha_funcion), hora_funcion=VALUES(hora_funcion)")) {
            ps.setString(1, code); ps.setString(2, funcion.getFuncionID()); ps.setInt(3, movie.getId()); ps.setString(4, room.getSalaID());
            ps.setString(5, funcion.getFecha()); ps.setString(6, funcion.getHora()); ps.executeUpdate();
        }
        return code;
    }

    private static void upsertProduct(Connection c, String code, String name, String category, BigDecimal price) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO productos (producto_codigo, nombre, categoria, precio_referencia) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), categoria=VALUES(categoria), precio_referencia=VALUES(precio_referencia)")) {
            ps.setString(1, code); ps.setString(2, name); ps.setString(3, category); ps.setBigDecimal(4, price); ps.executeUpdate();
        }
    }
    private static String categoryOf(Producto p) {
        if (p instanceof Entrada) return "ENTRADA";
        if (p instanceof Combo) return "COMBO";
        if (p instanceof Bebida) return "BEBIDA";
        if (p instanceof Snack) return "SNACK";
        return "OTRO";
    }
    private static String nonEmpty(String value, String fallback) { return value == null || value.isBlank() ? fallback : value; }
}
