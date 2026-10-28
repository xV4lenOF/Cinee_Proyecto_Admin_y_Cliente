package CLASES;

/**
 * Pago con tarjeta de débito/crédito.
 */
public class Tarjeta implements MetodoPago {

    // === Atributos de la tarjeta ===
    private String numeroTarjeta;
    private String tipo; // Visa, Mastercard

    // === Constructor ===
    public Tarjeta(String numeroTarjeta, String tipo) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipo = tipo;
    }

    // === Procesa el cobro ===
    @Override
    public void procesarPago(double monto) {
        // Si el número tiene menos de 4 dígitos, evitamos el error y mostramos tal cual
        String ultimosDigitos = numeroTarjeta.length() >= 4
                ? numeroTarjeta.substring(numeroTarjeta.length() - 4)
                : numeroTarjeta;
        System.out.println("Cobrando S/" + monto + " a la tarjeta " + tipo + " terminada en " + ultimosDigitos);
    }
}