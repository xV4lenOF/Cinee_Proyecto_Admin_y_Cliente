package CLASES;

/**
 * Clase base abstracta para todo lo que se puede vender (boletos, bebidas,
 * snacks y combos). Cada tipo de producto decide cómo calcula su propio precio.
 */
public abstract class Producto {

    // === Atributos comunes a cualquier producto ===
    protected String productoID; // Usamos protected para que las hijas lo hereden fácil
    protected String nombre;
    protected double precio;

    // === Constructor ===
    public Producto(String productoID, String nombre, double precio) {
        this.productoID = productoID;
        this.nombre = nombre;
        this.precio = precio;
    }

    // === Método que cada subclase debe implementar ===
    public abstract double calcularPrecio();

    // === Getters ===
    public String getProductoID() { return productoID; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
}