package CLASES;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una sala de cine, con su capacidad y la lista de asientos que contiene.
 */
public class Sala {

    // === Atributos de la sala ===
    private String salaID;
    private int numero;
    private int capacidad;

    // === Relación 1 a muchos: una sala tiene muchos asientos ===
    private List<Asiento> asientos;

    // === Constructor ===
    public Sala(String salaID, int numero, int capacidad) {
        this.salaID = salaID;
        this.numero = numero;
        this.capacidad = capacidad;
        this.asientos = new ArrayList<>(); // Inicializamos la lista vacía
    }

    // === Getters y Setters ===
    public String getSalaID() { return salaID; }
    public int getNumero() { return numero; }
    public int getCapacidad() { return capacidad; }
    public List<Asiento> getAsientos() { return asientos; }
    public void setAsientos(List<Asiento> asientos) { this.asientos = asientos; }
}