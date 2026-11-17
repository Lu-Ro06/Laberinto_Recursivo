public class Main {
    public static void main(String[] args) {
        VentanaModal modal = new VentanaModal();

        int filas = modal.pedirEntero("Dimensiones", "Ingresa el número de filas (ej. 17):");
        int columnas = modal.pedirEntero("Dimensiones", "Ingresa el número de columnas (ej. 25):");
        long seed = modal.pedirLong("Semilla", "Ingresa la semilla (seed):");

        Laberinto lab = new Laberinto(filas, columnas, seed);
        Portal[] p = lab.getPortales();

        BuscadorRecursivo buscador = new BuscadorRecursivo();
        ResultadoBusqueda res = buscador.resolver(lab);

        StringBuilder salida = new StringBuilder();
        salida.append("========= RESULTADOS =========\n");
        salida.append("Punto A: (1, 0)\n");
        salida.append("Punto B: (").append(lab.getFilas() - 2).append(", ").append(lab.getColumnas() - 1).append(")\n");
        salida.append("Portales: ").append(p[0]).append(" | ").append(p[1]).append(" | ").append(p[2]).append("\n");
        if (res.isEncontrado()) {
            salida.append("Distancia mínima: ").append(res.getPasosMinimos()).append(" pasos\n");
            salida.append("Camino (*): ").append(res.getRutaTexto());
        } else {
            salida.append(res.getRutaTexto());
        }

        modal.mostrarLaberintoModal("Laberinto Recursivo (Seed: " + seed + ")", res.getMapaResuelto(), salida.toString());
    }
}