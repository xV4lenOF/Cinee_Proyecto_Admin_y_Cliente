package CLASES;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Punto único de navegación entre pantallas para toda la aplicación
 * (Cartelera, Horarios, Asientos, Boletos, Dulcería y Pago).
 *
 */
public class Navegador {

    // Historial compartido por toda la app: cada pantalla por la que se pasa
    // queda guardada aquí hasta que se use "Volver" para recuperarla.
    private static final Pila<JComponent> historial = new Pila<>();

    // Se llama al avanzar a una pantalla nueva: guarda la pantalla actual en
    // el historial antes de reemplazarla, para poder recuperarla más tarde.
    public static void irA(JFrame frame, JComponent nuevaPantalla) {
        historial.apilar((JComponent) frame.getContentPane());
        frame.setContentPane(nuevaPantalla);
        frame.revalidate();
        frame.repaint();
    }

    // Se llama al presionar "Volver": recupera del historial la última
    // pantalla guardada y la vuelve a mostrar tal como estaba.
    public static void regresar(JFrame frame) {
        if (historial.estaVacia()) {
            return; // No hay pantalla anterior guardada
        }
        JComponent anterior = historial.desapilar();
        frame.setContentPane(anterior);
        frame.revalidate();
        frame.repaint();
    }

    // Permite saber si hay historial disponible (útil para habilitar/deshabilitar un botón "Volver")
    public static boolean hayHistorial() {
        return !historial.estaVacia();
    }
}
