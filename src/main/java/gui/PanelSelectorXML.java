package gui;

import javax.swing.*;
import java.awt.*;

public class PanelSelectorXML extends JPanel {
    private JRadioButton radioPrueba;
    private JRadioButton radioCopia;
    private ButtonGroup grupo;

    public PanelSelectorXML() {
        setLayout(new GridLayout(2, 1, 5, 5));
        setBackground(Color.LIGHT_GRAY); // para que combine visualmente

        radioPrueba = new JRadioButton("Trabajar en Prueba.xml");
        radioCopia = new JRadioButton("Trabajar en XML_copia.xml");

        grupo = new ButtonGroup();
        grupo.add(radioPrueba);
        grupo.add(radioCopia);

        radioPrueba.setSelected(true); // por defecto

        add(radioPrueba);
        add(radioCopia);
    }

    public boolean esXMLCopiaSeleccionado() {
        return radioCopia.isSelected();
    }

    public boolean esXMLOriginalSeleccionado() {
        return radioPrueba.isSelected();
    }

    public String obtenerNombreArchivoSeleccionado() {
        return esXMLCopiaSeleccionado() ? "XML_copia.xml" : "Prueba.xml";
    }
}

