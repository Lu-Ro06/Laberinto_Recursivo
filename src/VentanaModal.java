import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaModal {

    public int pedirEntero(String titulo, String mensaje) {
        String entrada = JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) System.exit(0);
        try {
            return Integer.parseInt(entrada.trim());
        } catch (NumberFormatException e) {
            mostrarError("Debes ingresar un número entero válido.");
            return pedirEntero(titulo, mensaje);
        }
    }

    public long pedirLong(String titulo, String mensaje) {
        String entrada = JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) System.exit(0);
        try {
            return Long.parseLong(entrada.trim());
        } catch (NumberFormatException e) {
            mostrarError("Debes ingresar una semilla numérica válida.");
            return pedirLong(titulo, mensaje);
        }
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error de Entrada", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarLaberintoModal(String titulo, String[][] mapaResuelto, String bloqueResultados) {
        JDialog dialogo = new JDialog((java.awt.Frame) null, titulo, true);
        dialogo.setLayout(new BorderLayout(8, 8));
        dialogo.getContentPane().setBackground(new Color(24, 24, 27));

        PanelLaberinto panelLab = new PanelLaberinto(mapaResuelto);
        JScrollPane scrollLab = new JScrollPane(panelLab);
        scrollLab.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelSur = new JPanel(new BorderLayout(6, 6));
        panelSur.setBackground(new Color(24, 24, 27));
        panelSur.setBorder(BorderFactory.createEmptyBorder(4, 12, 12, 12));

        JTextArea areaResultados = new JTextArea(bloqueResultados, 7, 60);
        areaResultados.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaResultados.setBackground(new Color(24, 24, 27));
        areaResultados.setForeground(new Color(228, 228, 231));
        areaResultados.setEditable(false);
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(79, 70, 229));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        panelSur.add(new JScrollPane(areaResultados), BorderLayout.CENTER);
        panelSur.add(btnCerrar, BorderLayout.SOUTH);

        dialogo.add(scrollLab, BorderLayout.CENTER);
        dialogo.add(panelSur, BorderLayout.SOUTH);

        dialogo.pack();
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);
    }
}