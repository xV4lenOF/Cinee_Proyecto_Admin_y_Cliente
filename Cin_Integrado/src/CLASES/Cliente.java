package CLASES;

/**
 * Representa al cliente que realiza una compra (datos del formulario de Pago).
 */
public class Cliente {

    // === Atributos del cliente ===
    private String usuarioID;
    private String nombre;
    private String email;
    private String telefono;

    // === Constructor ===
    public Cliente(String usuarioID, String nombre, String email, String telefono) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // === Getters ===
    public String getUsuarioID() { return usuarioID; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
}