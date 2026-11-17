import java.util.Random;

public class Laberinto {
    private int filas;
    private int columnas;
    private String[][] mapa; // Cada celda almacena 3 caracteres: "###", "   ", " A ", " B ", "P1 ", "D1 ", etc.
    private Portal[] portales;
    private Random rand;

    public Laberinto(int filas, int columnas, long seed) {
        // Aseguramos dimensiones impares mínimas para paredes y pasillos bien formados
        this.filas = (filas < 7) ? 7 : (filas % 2 == 0 ? filas + 1 : filas);
        this.columnas = (columnas < 9) ? 9 : (columnas % 2 == 0 ? columnas + 1 : columnas);
        this.mapa = new String[this.filas][this.columnas];
        this.portales = new Portal[3];
        this.rand = new Random(seed);

        llenarMurosRecursivo(0, 0);
        // Sesgo horizontal para generar pasillos largos como en la imagen
        tallarPasillosRecursivo(1, 1, 0, elegirdireccionHorizontal());
        abrirConexionesRecursivo(1, 1);

        // Entrada A en borde izquierdo (1,0) y Salida B en borde derecho (filas-2, columnas-1)
        mapa[1][0] = " A ";
        mapa[1][1] = "   ";
        mapa[this.filas - 2][this.columnas - 1] = " B ";
        mapa[this.filas - 2][this.columnas - 2] = "   ";

        generarPortalesRecursivo(0);
    }

    private int elegirdireccionHorizontal() {
        // 70% probabilidad de ir izquierda/derecha para pasillos largos horizontales
        int r = rand.nextInt(10);
        if (r < 4) return 2; // Izquierda
        if (r < 8) return 3; // Derecha
        return rand.nextInt(2); // Arriba o Abajo
    }

    private void llenarMurosRecursivo(int f, int c) {
        if (f == filas) return;
        if (c == columnas) {
            llenarMurosRecursivo(f + 1, 0);
            return;
        }
        mapa[f][c] = "###";
        llenarMurosRecursivo(f, c + 1);
    }

    private void tallarPasillosRecursivo(int f, int c, int intentos, int dirBase) {
        mapa[f][c] = "   ";
        if (intentos == 4) return;

        int dir = (dirBase + intentos) % 4;
        int df = (dir == 0) ? -2 : (dir == 1) ? 2 : 0;
        int dc = (dir == 2) ? -2 : (dir == 3) ? 2 : 0;

        int nf = f + df;
        int nc = c + dc;

        if (nf > 0 && nf < filas - 1 && nc > 0 && nc < columnas - 1 && mapa[nf][nc].equals("###")) {
            mapa[f + df / 2][c + dc / 2] = "   ";
            tallarPasillosRecursivo(nf, nc, 0, elegirdireccionHorizontal());
        }

        tallarPasillosRecursivo(f, c, intentos + 1, dirBase);
    }

    private void abrirConexionesRecursivo(int f, int c) {
        if (f >= filas - 1) return;
        if (c >= columnas - 1) {
            abrirConexionesRecursivo(f + 1, 1);
            return;
        }
        if (mapa[f][c].equals("###") && rand.nextInt(100) < 14) {
            boolean horiz = mapa[f][c - 1].equals("   ") && mapa[f][c + 1].equals("   ");
            boolean vert = mapa[f - 1][c].equals("   ") && mapa[f + 1][c].equals("   ");
            if (horiz || vert) {
                mapa[f][c] = "   ";
            }
        }
        abrirConexionesRecursivo(f, c + 1);
    }

    private void generarPortalesRecursivo(int indice) {
        if (indice == 3) return;

        int pf = 1 + rand.nextInt(filas - 2);
        int pc = 1 + rand.nextInt(columnas - 2);

        if (!mapa[pf][pc].equals("   ")) {
            generarPortalesRecursivo(indice);
            return;
        }

        // Desplazamiento (p+x, p+y) válido dentro de pasillos
        int dx = rand.nextInt(9) - 4;
        int dy = rand.nextInt(9) - 4;
        int destF = pf + dx;
        int destC = pc + dy;

        if (esCasillaValida(destF, destC) && mapa[destF][destC].equals("   ") && (destF != pf || destC != pc)) {
            int num = indice + 1;
            mapa[pf][pc] = " P" + num;
            mapa[destF][destC] = " D" + num;
            portales[indice] = new Portal(num, pf, pc, destF, destC);
            generarPortalesRecursivo(indice + 1);
        } else {
            generarPortalesRecursivo(indice);
        }
    }

    public boolean esCasillaValida(int f, int c) {
        return f > 0 && f < filas - 1 && c > 0 && c < columnas - 1 && !mapa[f][c].equals("###");
    }

    public Portal obtenerPortalEn(int f, int c) {
        if (portales[0] != null && portales[0].coincideOrigen(f, c)) return portales[0];
        if (portales[1] != null && portales[1].coincideOrigen(f, c)) return portales[1];
        if (portales[2] != null && portales[2].coincideOrigen(f, c)) return portales[2];
        return null;
    }

    public String[][] getMapa() { return mapa; }
    public Portal[] getPortales() { return portales; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
}