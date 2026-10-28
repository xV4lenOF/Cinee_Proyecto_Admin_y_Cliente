package CLASES;

/**
 * Contrato que debe cumplir cualquier forma de pago (Tarjeta, Yape, etc.).
 */
public interface MetodoPago {
    void procesarPago(double monto);
}