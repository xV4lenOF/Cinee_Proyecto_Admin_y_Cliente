package CLASES;

/**
 * Producto "simple": aquel que se vende directo (sin combinar con otros),
 * como una bebida o un snack sueltos. Maneja su propio stock/disponibilidad.
 */
public class Productos_Simple extends Producto {

    // === Atributos propios de un producto simple ===
    protected int stock;
    protected boolean disponible;

    // === Constructor ===
    public Productos_Simple(String productoID, String nombre, double precio, int stock, boolean disponible) {
        super(productoID, nombre, precio); // Llama al constructor de Producto
        this.stock = stock;
        this.disponible = disponible;
    }

    // === Implementación obligatoria del método abstracto de Producto ===
    @Override
    public double calcularPrecio() {
        return this.precio; // El precio de un producto simple es su precio base
    }
}