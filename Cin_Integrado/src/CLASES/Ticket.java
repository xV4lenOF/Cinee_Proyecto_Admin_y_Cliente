package CLASES;

/**
 * Representa el comprobante final que se le entrega al cliente,
 * con su código QR y el precio total de la venta asociada.
 */
public class Ticket {

    // === Atributos del ticket ===
    private String ticketID;
    private String codigoQR;
    private double precio;

    // === Relación: cada ticket pertenece a una venta ===
    private Venta venta;

    // === Constructor ===
    public Ticket(String ticketID, String codigoQR, Venta venta) {
        this.ticketID = ticketID;
        this.codigoQR = codigoQR;
        this.venta = venta;
        this.precio = venta.getTotal();
    }

    // === Getters ===
    public String getTicketID() { return ticketID; }
    public String getCodigoQR() { return codigoQR; }
    public double getPrecio() { return precio; }
    public Venta getVenta() { return venta; }
}