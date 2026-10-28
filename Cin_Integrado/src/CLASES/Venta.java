package CLASES;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa la venta ya confirmada: junta la preventa (entradas/butacas)
 * con todos los productos de dulcería comprados, y calcula el total final.
 */
public class Venta {

    // === Atributos de la venta ===
    private String ventaID;
    private Date fecha;
    private double total;

    // === Relaciones del diagrama ===
    private Preventa preventa;            // 0..1 (puede haber compra solo de dulcería, sin entradas)
    private List<Detalle_Venta> detalles; // Lista de todo lo comprado (entradas + dulcería)

    // === Constructor ===
    public Venta(String ventaID, Preventa preventa) {
        this.ventaID = ventaID;
        this.fecha = new Date();
        this.preventa = preventa;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    // === Agrega un producto a la venta y suma su subtotal al total ===
    public void agregarDetalle(Detalle_Venta detalle) {
        this.detalles.add(detalle);
        this.total += detalle.getSubTotal();
    }

    // === Getters ===
    public double getTotal() { return total; }
    public String getVentaID() { return ventaID; }
    public Date getFecha() { return fecha; }
    public Preventa getPreventa() { return preventa; }
    public List<Detalle_Venta> getDetalles() { return detalles; }
}