package CLASES;

/**
 * Representa una línea de la venta: un producto específico (boleto, bebida,
 * snack o combo) con su cantidad y el subtotal ya calculado.
 */
public class Detalle_Venta {

    // === Atributos del detalle ===
    private String detalleID;
    private int cantidad;
    private double subTotal;

    // === Relación del diagrama (1 a Producto) ===
    private Producto producto; // Gracias al polimorfismo, aquí entra un Combo, Bebida, Snack o Entrada

    // === Constructor ===
    public Detalle_Venta(String detalleID, Producto producto, int cantidad) {
        this.detalleID = detalleID;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subTotal = producto.calcularPrecio() * cantidad; // Se calcula automáticamente
    }

    // === Getters ===
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubTotal() { return subTotal; }
}