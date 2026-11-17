public class ResultadoBusqueda {
    private int pasosMinimos;
    private String rutaTexto;
    private boolean encontrado;
    private String[][] mapaResuelto;

    public ResultadoBusqueda(String[][] mapaBase) {
        this.pasosMinimos = Integer.MAX_VALUE;
        this.rutaTexto = "No se encontró ruta entre Punto A y Punto B.";
        this.encontrado = false;
        this.mapaResuelto = new String[mapaBase.length][mapaBase[0].length];
        copiarMatrizRecursivo(mapaBase, this.mapaResuelto, 0, 0);
    }

    public void actualizarMejorSolucion(int pasos, String ruta, String[][] mapaActual) {
        this.pasosMinimos = pasos;
        this.rutaTexto = ruta;
        this.encontrado = true;
        copiarMatrizRecursivo(mapaActual, this.mapaResuelto, 0, 0);
    }

    private void copiarMatrizRecursivo(String[][] origen, String[][] destino, int f, int c) {
        if (f == origen.length) return;
        if (c == origen[0].length) {
            copiarMatrizRecursivo(origen, destino, f + 1, 0);
            return;
        }
        destino[f][c] = origen[f][c];
        copiarMatrizRecursivo(origen, destino, f, c + 1);
    }

    public int getPasosMinimos() { return pasosMinimos; }
    public String getRutaTexto() { return rutaTexto; }
    public boolean isEncontrado() { return encontrado; }
    public String[][] getMapaResuelto() { return mapaResuelto; }
}