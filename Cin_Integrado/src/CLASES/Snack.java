package CLASES;

/**
 * Representa un snack salado de la dulcería: palomitas, cañonazo, piqueos
 * o nachos. Cuando se usa dentro de un Combo, se le resta stock individual.
 */
public class Snack extends Productos_Simple {

    // === Atributos propios de un snack ===
    private String tipoSnack;
    private String tamano;

    // === Constructor ===
    public Snack(String productoID, String nombre, double precio, int stock, boolean disponible, String tipoSnack, String tamano) {
        super(productoID, nombre, precio, stock, disponible);
        this.tipoSnack = tipoSnack;
        this.tamano = tamano;
    }
}