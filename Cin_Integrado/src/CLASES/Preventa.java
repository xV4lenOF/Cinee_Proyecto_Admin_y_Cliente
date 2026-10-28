package CLASES;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa el proceso de compra en curso: quién compra, qué función eligió
 * y qué butacas seleccionó, antes de confirmarse como una Venta.
 */
public class Preventa {

    // === Atributos de la preventa ===
    private String preventaID;
    private Date fecha;
    private String estado; // "En proceso", "Confirmada", "Cancelada"

    // === Relaciones del diagrama ===
    private Cliente cliente;        // Quién compra
    private Funcion funcion;        // Qué película y a qué hora
    private List<Asiento> asientos; // Qué butacas eligió (1 a muchos)

    // === Constructor vacío, para cuando recién empieza la compra ===
    public Preventa() {
        this.asientos = new ArrayList<>();
        this.estado = "En proceso";
    }

    // === Métodos para ir llenando la preventa ===
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setFuncion(Funcion funcion) { this.funcion = funcion; }
    public void agregarAsiento(Asiento asiento) { this.asientos.add(asiento); }

    // === Getters y Setters ===
    public Funcion getFuncion() { return funcion; }
    public List<Asiento> getAsientos() { return asientos; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}