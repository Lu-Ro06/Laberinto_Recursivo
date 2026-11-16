public class BuscadorRecursivo {
    private int mejorDistancia = Integer.MAX_VALUE;
    private String mejorRuta = "";

    // Método de arranque: reinicia contadores y dispara la recursividad desde (0,0)
    public String resolver(Laberinto lab) {
        mejorDistancia = Integer.MAX_VALUE;
        mejorRuta = "Sin solución";
        buscarRuta(lab.getMapa(), lab.getPortales(), 0, 0, 0, "(0,0)");
        return mejorRuta;
    }

    // ÚNICA FUNCIÓN RECURSIVA: Usa el Call Stack como Pila 
    private void buscarRuta(char[][] mapa, int[][] portales, int f, int c, int pasos, String rutaActual) {
        
        // 1. RESTRICCIONES: Si nos salimos de la matriz o topamos con pared ('#') o ya pasamos por aquí ('*'), abortamos esta rama
        if (f < 0 || f >= mapa.length || c < 0 || c >= mapa[0].length) return;
        if (mapa[f][c] == '#' || mapa[f][c] == '*') return; 

        // PODA: Si los pasos actuales ya superan la mejor ruta encontrada, no gastamos ciclos de CPU en seguir por aquí
        if (pasos >= mejorDistancia) return;

        // 2. CASO BASE: ¡Llegamos a la meta ('F')Guardamos el récord si es el camino más corto
        if (mapa[f][c] == 'F') {
            mejorDistancia = pasos;
            mejorRuta = "Pasos: " + pasos + " | Ruta: " + rutaActual;
            return;
        }

        // Guardamos el estado original y marcamos la casilla con '*' para evitar ciclos en esta misma rama
        char original = mapa[f][c];
        mapa[f][c] = '*'; 

        // 3. GESTIÓN DE PORTALES: Si caemos en 'P', saltamos directo a sus coordenadas destino (sin explorar los lados)
        if (original == 'P') {
            int destF = (portales[0][0] == f && portales[0][1] == c) ? portales[0][2] :
                        (portales[1][0] == f && portales[1][1] == c) ? portales[1][2] : portales[2][2];
            int destC = (portales[0][0] == f && portales[0][1] == c) ? portales[0][3] :
                        (portales[1][0] == f && portales[1][1] == c) ? portales[1][3] : portales[2][3];

            buscarRuta(mapa, portales, destF, destC, pasos + 1, rutaActual + " -> [Portal](" + destF + "," + destC + ")");
        } else {
            // 4. EXPLORACIÓN RECURSIVA: Probamos las 4 direcciones de forma secuencial (la pila del sistema maneja el orden LIFO)
            buscarRuta(mapa, portales, f + 1, c, pasos + 1, rutaActual + " -> (" + (f + 1) + "," + c + ")"); // Abajo
            buscarRuta(mapa, portales, f, c + 1, pasos + 1, rutaActual + " -> (" + f + "," + (c + 1) + ")"); // Derecha
            buscarRuta(mapa, portales, f - 1, c, pasos + 1, rutaActual + " -> (" + (f - 1) + "," + c + ")"); // Arriba
            buscarRuta(mapa, portales, f, c - 1, pasos + 1, rutaActual + " -> (" + f + "," + (c - 1) + ")"); // Izquierda
        }

        // 5. BACKTRACKING: Al salir de la pila de llamadas, restauramos el carácter original para liberar el camino a otras rutas
        mapa[f][c] = original;
    }
}