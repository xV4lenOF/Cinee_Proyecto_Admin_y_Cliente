package pe.cinee.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class AdminRepository {
    public boolean hasAdministrators() throws SQLException, IOException {
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM administradores"); ResultSet rs = ps.executeQuery()) {
            rs.next(); return rs.getInt(1) > 0;
        }
    }

    public void createFirstAdministrator(String username, char[] password) throws SQLException, IOException {
        if (username == null || !username.matches("[A-Za-z0-9._-]{3,40}")) {
            throw new IllegalArgumentException("El usuario debe tener entre 3 y 40 caracteres: letras, números, punto, guion o guion bajo.");
        }
        if (password == null || password.length < 10) throw new IllegalArgumentException("La contraseña debe tener al menos 10 caracteres.");
        PasswordSecurity.Encoded encoded = PasswordSecurity.encode(password);
        try (Connection c = Database.connect()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement check = c.prepareStatement("SELECT COUNT(*) FROM administradores"); ResultSet rs = check.executeQuery()) {
                    rs.next(); if (rs.getInt(1) != 0) throw new SQLException("Ya existe un administrador. Inicia sesión normalmente.");
                }
                try (PreparedStatement ps = c.prepareStatement("INSERT INTO administradores(usuario, salt_hex, hash_hex, activo) VALUES (?, ?, ?, 1)")) {
                    ps.setString(1, username); ps.setString(2, encoded.saltHex()); ps.setString(3, encoded.hashHex()); ps.executeUpdate();
                }
                c.commit();
            } catch (SQLException | RuntimeException ex) { c.rollback(); throw ex; }
        }
    }

    public boolean authenticate(String username, char[] password) throws SQLException, IOException {
        String salt = null, hash = null;
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement(
                "SELECT salt_hex, hash_hex FROM administradores WHERE usuario = ? AND activo = 1")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { salt = rs.getString(1); hash = rs.getString(2); } }
        }
        return salt != null && hash != null && PasswordSecurity.verify(password, salt, hash);
    }
}
