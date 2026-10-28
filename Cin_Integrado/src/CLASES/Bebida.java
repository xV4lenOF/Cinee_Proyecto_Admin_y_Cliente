package CLASES;

/**
 * Representa una bebida de la dulcería (gaseosa, agua, etc.), con su tamaño
 * (Pequeño/Mediano/Grande) y el tipo de bebida.
 */
public class Bebida extends Productos_Simple {

    // === Atributos propios de una bebida ===
    private String tamano;
    private String tipoBebida;

    // === Constructor ===
    public Bebida(String productoID, String nombre, double precio, int stock, boolean disponible, String tamano, String tipoBebida) {
        super(productoID, nombre, precio, stock, disponible);
        this.tamano = tamano;
        this.tipoBebida = tipoBebida;
    }
}