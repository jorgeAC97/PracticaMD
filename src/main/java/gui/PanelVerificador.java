package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelVerificador extends JPanel
{
    private JTextArea areaTexto;
    private JScrollPane scrollTexto;
    private CardLayout layout;

    public PanelVerificador() {
        setBackground(Color.LIGHT_GRAY);
        layout = new CardLayout();
        setLayout(layout);

        areaTexto = new JTextArea(5, 30);
        areaTexto.setEditable(false);
        scrollTexto = new JScrollPane(areaTexto);

        add(scrollTexto, "texto");
        mostraTexto();
    }

    private void mostraTexto() {

        layout.show(this, "texto");
    }

    public void visualizar_mensaje(String mensaje) {
        areaTexto.append(mensaje + "\n");
    }

    public void limpiar_campo() {
        areaTexto.setText("");
    }

}
