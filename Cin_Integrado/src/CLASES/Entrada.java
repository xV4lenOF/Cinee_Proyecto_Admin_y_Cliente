package CLASES;

/**
 * Representa un boleto de cine (Adulto, Niño o Adulto Mayor) como un Producto más,
 * para poder reutilizar Detalle_Venta y sumarlo dentro de la misma Venta que la dulcería.
 */
public class Entrada extends Productos_Simple {

    // === Atributo propio de una entrada ===
    private String tipoEntrada; // "Adulto", "Niño", "Adulto Mayor"

    // === Constructor ===
    public Entrada(String productoID, String nombre, double precio, String tipoEntrada) {
        // stock=999 y disponible=true porque una entrada no maneja inventario físico
        super(productoID, nombre, precio, 999, true);
        this.tipoEntrada = tipoEntrada;
    }

    // === Getter ===
    public String getTipoEntrada() { return tipoEntrada; }
}
