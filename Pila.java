// Estructura Pila propia para llevar el recorrido sin librerías externas
public class Pila {
    private static class Nodo {
        int fila, col;
        Nodo siguiente;
        Nodo(int fila, int col, Nodo siguiente) {
            this.fila = fila;
            this.col = col;
            this.siguiente = siguiente;
        }
    }

    private Nodo cima;
    private int tamano;

    public void push(int fila, int col) {
        cima = new Nodo(fila, col, cima);
        tamano++;
    }

    public void pop() {
        if (cima != null) {
            cima = cima.siguiente;
            tamano--;
        }
    }

    public int getTamano() {
        return tamano;
    }

    public String obtenerRutaRecursiva() {
        return construirCadena(cima);
    }

    private String construirCadena(Nodo actual) {
        if (actual == null) return "";
        String previo = construirCadena(actual.siguiente);
        String coord = "(" + actual.fila + "," + actual.col + ")";
        return previo.isEmpty() ? coord : previo + " -> " + coord;
    }
}