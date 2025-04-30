package gui;

import javax.swing.*;
import java.awt.*;

public class FiltrosPanelEscritura extends JPanel {
    private JTextField campoBusqueda;
    private JButton botonOK;
    private JButton botonTodos;

    public FiltrosPanelEscritura() {
        setLayout(new FlowLayout());
        setVisible(false);

        JLabel etiqueta = new JLabel("Búsqueda manual:");
        campoBusqueda = new JTextField(15);
        botonOK = new JButton("OK");
        botonTodos = new JButton("Todos los códigos");

        JPanel campoConBoton = new JPanel(new BorderLayout());
        campoConBoton.add(campoBusqueda, BorderLayout.CENTER);
        campoConBoton.add(botonOK, BorderLayout.EAST);

        add(etiqueta);
        add(campoConBoton);
        add(botonTodos);
    }

    public void visualizar_panel() {
        setVisible(true);
    }

    public String getTextoBusqueda() {
        return campoBusqueda.getText();
    }

    public JButton getBotonOK() {
        return botonOK;
    }

    public JButton getBotonTodos() {
        return botonTodos;
    }
}
