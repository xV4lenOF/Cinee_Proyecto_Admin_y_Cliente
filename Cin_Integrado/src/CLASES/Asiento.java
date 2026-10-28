package CLASES;

/**
 * Representa una butaca individual dentro de una Sala.
 */
public class Asiento {

    // === Atributos del asiento ===
    private String asientoID;
    private String numero; // Número del asiento, ej: "M12"
    private String estado; // "Libre", "Ocupado", "Seleccionado"

    // === Constructor ===
    public Asiento(String asientoID, String numero, String estado) {
        this.asientoID = asientoID;
        this.numero = numero;
        this.estado = estado;
    }

    // === Getters y Setters ===
    public String getAsientoID() { return asientoID; }
    public void setAsientoID(String asientoID) { this.asientoID = asientoID; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}