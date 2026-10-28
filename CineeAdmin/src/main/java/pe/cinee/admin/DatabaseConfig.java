package pe.cinee.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/** Shared connection settings used by the admin app and the client-project adapter. */
public final class DatabaseConfig {
    public static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), ".cinee.properties");
    private String url;
    private String username;
    private String password;

    public DatabaseConfig(String url, String username, String password) {
        this.url = url == null ? "" : url.trim();
        this.username = username == null ? "" : username.trim();
        this.password = password == null ? "" : password;
    }

    public static DatabaseConfig load() throws IOException {
        Properties p = new Properties();
        if (Files.exists(CONFIG_PATH)) {
            try (InputStream in = Files.newInputStream(CONFIG_PATH)) { p.load(in); }
        }
        return new DatabaseConfig(
                p.getProperty("db.url", "jdbc:mysql://localhost:3306/cinee_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima&characterEncoding=UTF-8"),
                p.getProperty("db.user", "cinee_app"),
                p.getProperty("db.password", ""));
    }

    public void save() throws IOException {
        Properties p = new Properties();
        p.setProperty("db.url", url);
        p.setProperty("db.user", username);
        p.setProperty("db.password", password);
        p.setProperty("db.note", "Archivo local de desarrollo Cinee. No compartas este archivo.");
        try (OutputStream out = Files.newOutputStream(CONFIG_PATH)) {
            p.store(out, "Configuracion de conexion de Cinee - no compartir credenciales");
        }
    }

    public String getUrl() { return url; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setUrl(String url) { this.url = url == null ? "" : url.trim(); }
    public void setUsername(String username) { this.username = username == null ? "" : username.trim(); }
    public void setPassword(String password) { this.password = password == null ? "" : password; }
}
