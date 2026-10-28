package CLASES;

public class Efectivo implements MetodoPago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago de S/" + monto + " en efectivo en caja.");
    }
}