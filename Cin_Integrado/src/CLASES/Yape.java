package CLASES;

/**
 * Pago con Yape (billetera digital en línea).
 */
public class Yape implements MetodoPago {

    // === Procesa el cobro ===
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago de S/" + monto + " a través de Yape. ¡Plin, Plin!");
    }
}