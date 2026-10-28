package CLASES;

import java.util.LinkedList;

/**
 *
 * La usamos para guardar el historial de pantallas por las que ha pasado el usuario
 * (ver CLASES.Navegador). Cada vez que se abre una pantalla nueva, se apila la anterior;
 * cuando el usuario presiona "Volver", se desapila y se muestra tal cual estaba,
 * sin necesidad de reconstruirla desde cero.
 */
public class Pila<T> {

    // Usamos una LinkedList internamente, pero SOLO como almacén:
    private final LinkedList<T> elementos = new LinkedList<>();

    // Agrega (apila) un elemento en el TOPE de la pila
    public void apilar(T elemento) {
        elementos.addFirst(elemento);
    }

    // Retira y devuelve el elemento que está en el TOPE de la pila
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        return elementos.removeFirst();
    }

    // Devuelve el elemento del TOPE sin retirarlo de la pila
    public T verTope() {
        if (estaVacia()) {
            return null;
        }
        return elementos.getFirst();
    }

    // Indica si la pila no tiene elementos
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    // Cantidad de elementos apilados actualmente
    public int tamano() {
        return elementos.size();
    }
}
