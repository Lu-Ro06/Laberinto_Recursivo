import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelLaberinto extends JPanel {
    private String[][] mapa;
    private int anchoCelda = 26;
    private int altoCelda = 20;

    public PanelLaberinto(String[][] mapa) {
        this.mapa = mapa;
        int ancho = mapa[0].length * anchoCelda + 30;
        int alto = mapa.length * altoCelda + 30;
        setPreferredSize(new Dimension(ancho, alto));
        setBackground(new Color(24, 24, 27)); // Fondo oscuro estilo VS Code terminal
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Consolas", Font.BOLD, 13));

        dibujarTextoMatrizRecursivo(g2, 0, 0);
    }

    private void dibujarTextoMatrizRecursivo(Graphics2D g2, int f, int c) {
        if (f == mapa.length) return;
        if (c == mapa[0].length) {
            dibujarTextoMatrizRecursivo(g2, f + 1, 0);
            return;
        }

        int x = 15 + c * anchoCelda;
        int y = 22 + f * altoCelda;
        String val = mapa[f][c];

        if (val.equals("###")) {
            g2.setColor(new Color(148, 163, 184)); // Muros ### en gris claro
        } else if (val.equals(" A ") || val.equals(" B ")) {
            g2.setColor(new Color(74, 222, 128)); // Punto A y Punto B en verde brillante
        } else if (val.startsWith(" P") || val.startsWith("P")) {
            g2.setColor(new Color(192, 132, 252)); // Portales P1, P2, P3 en morado
        } else if (val.startsWith(" D") || val.startsWith("D")) {
            g2.setColor(new Color(56, 189, 248)); // Destinos D1, D2, D3 en azul
        } else if (val.contains("*")) {
            g2.setColor(new Color(250, 204, 21)); // Camino óptimo * en amarillo dorado
        } else {
            g2.setColor(new Color(39, 39, 42));
        }

        g2.drawString(val, x, y);
        dibujarTextoMatrizRecursivo(g2, f, c + 1);
    }
}