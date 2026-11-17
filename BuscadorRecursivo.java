public class BuscadorRecursivo {

    public ResultadoBusqueda resolver(Laberinto laberinto) {
        String[][] mapa = laberinto.getMapa();
        ResultadoBusqueda resultado = new ResultadoBusqueda(mapa);
        explorar(laberinto, mapa, 1, 0, 0, "(1, 0)", resultado);
        return resultado;
    }

    // ÚNICA FUNCIÓN RECURSIVA DE BÚSQUEDA (Sin ciclos, sin estructuras auxiliares)
    private void explorar(Laberinto lab, String[][] mapa, int f, int c, int pasos, String ruta, ResultadoBusqueda mejor) {
        if (f < 0 || f >= mapa.length || c < 0 || c >= mapa[0].length) return;

        String celda = mapa[f][c];
        if (celda.equals("###") || celda.equals(" * ") || celda.endsWith("*")) return;
        if (pasos >= mejor.getPasosMinimos()) return;

        if (celda.equals(" B ")) {
            mejor.actualizarMejorSolucion(pasos, ruta, mapa);
            return;
        }

        // Marca la celda como visitada en el Call Stack conservando la etiqueta si es P1/D1
        if (celda.equals("   ")) {
            mapa[f][c] = " * ";
        } else if (!celda.equals(" A ")) {
            mapa[f][c] = celda.substring(1, 3) + "*";
        }

        // Si cae en un portal P1, P2 o P3, salta a su destino D1, D2 o D3
        Portal p = lab.obtenerPortalEn(f, c);
        if (p != null) {
            explorar(lab, mapa, p.getFilaDestino(), p.getColDestino(), pasos + 1,
                    ruta + " -> [P" + p.getNumero() + "->D" + p.getNumero() + "](" + p.getFilaDestino() + ", " + p.getColDestino() + ")", mejor);
        }

        // Exploración en las 4 direcciones (Call Stack funciona como Pila)
        explorar(lab, mapa, f, c + 1, pasos + 1, ruta + " -> (" + f + ", " + (c + 1) + ")", mejor); // Derecha
        explorar(lab, mapa, f + 1, c, pasos + 1, ruta + " -> (" + (f + 1) + ", " + c + ")", mejor); // Abajo
        explorar(lab, mapa, f - 1, c, pasos + 1, ruta + " -> (" + (f - 1) + ", " + c + ")", mejor); // Arriba
        explorar(lab, mapa, f, c - 1, pasos + 1, ruta + " -> (" + f + ", " + (c - 1) + ")", mejor); // Izquierda

        // Retroceso (Backtracking): restaura la celda al desapilar
        mapa[f][c] = celda;
    }
}