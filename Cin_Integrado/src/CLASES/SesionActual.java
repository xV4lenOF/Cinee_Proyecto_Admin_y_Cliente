package CLASES;
import java.util.LinkedList;
import java.util.List;

/**
 * Gestiona el estado global de la sesión de compra en el cine.
 * Utiliza variables y métodos estáticos para actuar como un contenedor centralizado
 * de datos, permitiendo que pantallas independientes (Asientos, Dulcería, Pago)
 * compartan y modifiquen la información de la transacción actual sin perderla
 * durante la navegación.
 */
public class SesionActual {

    // === Variables globales de la compra en curso ===
    // Al ser 'static' pertenecen a la clase, no a un objeto: cualquier pantalla
    // (Asientos, Dulcería, Pago) las ve sin que su contenido se borre al navegar.
    public static Preventa preventaEnCurso;
    public static Pelicula peliculaSeleccionada;
    public static Cliente clienteLogueado; // Se llenará en la pantalla de Pago

    // === Carrito de la dulcería ===
    public static List<Detalle_Venta> carritoDulceria = new LinkedList<>();

    // === Agrega, actualiza o quita un producto del carrito de dulcería ===
    public static void actualizarProductoEnCarrito(Producto p, int cantidad) {
        // 1. Buscamos si el producto ya estaba en el carrito y lo borramos temporalmente
        for (int i = 0; i < carritoDulceria.size(); i++) {
            if (carritoDulceria.get(i).getProducto().getNombre().equals(p.getNombre())) {
                carritoDulceria.remove(i);
                break;
            }
        }

        // 2. Si la cantidad es mayor a 0, lo volvemos a meter con la nueva cantidad y subtotal
        if (cantidad > 0) {
            carritoDulceria.add(new Detalle_Venta("D01", p, cantidad));
        }
    }

    // === Suma el total de todo lo que hay en el carrito de dulcería ===
    public static double calcularTotalDulceria() {
        double total = 0;
        for (Detalle_Venta detalle : carritoDulceria) {
            total += detalle.getSubTotal();
        }
        return total;
    }

    // === Limpia todos los datos cuando termina o se cancela la compra ===
    public static void reiniciarSesion() {
        preventaEnCurso = new Preventa();
        peliculaSeleccionada = null;
        clienteLogueado = null;
        carritoDulceria.clear(); // Vaciamos el carrito
    }
}