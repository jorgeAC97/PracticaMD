package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PanelRadioBotones extends JPanel {
    private ButtonGroup grupo;

    public PanelRadioBotones() {
        setLayout(new FlowLayout());
        grupo = new ButtonGroup();
        setVisible(false);
    }


    public void cargarOpciones(Map<String, Runnable> opciones) {
        removeAll();
        grupo = new ButtonGroup();

        for (Map.Entry<String, Runnable> entrada : opciones.entrySet()) {
            String texto = entrada.getKey();
            Runnable accion = entrada.getValue();

            JRadioButton boton = new JRadioButton(texto);
            boton.addActionListener(e -> accion.run());

            grupo.add(boton);
            add(boton);
        }

        revalidate();
        repaint();
    }

    public void visualizar_panel() {
        setVisible(true);
    }

    public void ocultar_panel() {
        setVisible(false);
    }
}
