public class Portal {
    private int numero;
    private int filaOrigen;
    private int colOrigen;
    private int filaDestino;
    private int colDestino;

    public Portal(int numero, int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        this.numero = numero;
        this.filaOrigen = filaOrigen;
        this.colOrigen = colOrigen;
        this.filaDestino = filaDestino;
        this.colDestino = colDestino;
    }

    public boolean coincideOrigen(int f, int c) {
        return this.filaOrigen == f && this.colOrigen == c;
    }

    public int getNumero() { return numero; }
    public int getFilaOrigen() { return filaOrigen; }
    public int getColOrigen() { return colOrigen; }
    public int getFilaDestino() { return filaDestino; }
    public int getColDestino() { return colDestino; }

    @Override
    public String toString() {
        return "P" + numero + " (" + filaOrigen + ", " + colOrigen + ") -> D" + numero + " (" + filaDestino + ", " + colDestino + ")";
    }
}