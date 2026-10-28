package pe.cinee.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private Database() {}

    public static Connection connect() throws SQLException, IOException {
        return connect(DatabaseConfig.load());
    }

    public static Connection connect(DatabaseConfig config) throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver MySQL Connector/J. Abre el proyecto con Maven y recarga sus dependencias.", e);
        }
        return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
    }
}
