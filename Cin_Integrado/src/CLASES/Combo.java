package CLASES;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un combo de dulcería (ej: Palomitas + Gaseosa) que agrupa
 * varios productos individuales bajo un precio promocional fijo.
 */
public class Combo extends Producto {

    // === Relación del diagrama (agregación): un combo contiene varios productos ===
    private List<Producto> productos; // Lista que guarda las bebidas y snacks

    // === Constructor ===
    public Combo(String productoID, String nombre, double precio) {
        super(productoID, nombre, precio);
        this.productos = new ArrayList<>();
    }

    // === Métodos ===

    // Agrega un snack o bebida dentro del combo
    public void agregarAlCombo(Producto p) {
        this.productos.add(p);
    }

    // Calcula el precio final del combo
    @Override
    public double calcularPrecio() {
        // Se podría sumar el precio de los productos individuales, pero por ahora
        // se retorna el precio promocional fijo que se definió en el constructor.
        return this.precio;
    }
}