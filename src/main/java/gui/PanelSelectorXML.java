package gui;

import data.LectorXML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelSelectorXML extends JPanel {
    private JRadioButton radioPrueba;
    private JRadioButton radioCopia;
    private ButtonGroup grupo;

    private VentanaPrincipal ventana;

    public PanelSelectorXML(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new GridLayout(2, 1, 5, 5));
        setBackground(Color.LIGHT_GRAY);

        radioPrueba = new JRadioButton("Trabajar en Prueba.xml");
        radioCopia = new JRadioButton("Trabajar en XML_copia.xml");

        grupo = new ButtonGroup();
        grupo.add(radioPrueba);
        grupo.add(radioCopia);

        radioPrueba.setSelected(true); // por defecto

        add(radioPrueba);
        add(radioCopia);

        // 💥 Listeners para actualizar automáticamente la tabla al cambiar XML
        ActionListener listener = e -> cargarTitulosSeleccionados();
        radioPrueba.addActionListener(listener);
        radioCopia.addActionListener(listener);
    }

    private void cargarTitulosSeleccionados() {
        String archivo = obtenerNombreArchivoSeleccionado();
        List<String[]> titulos = LectorXML.obtenerTitulos(archivo);
        ventana.cargar_todos_los_titulos(titulos);
        ventana.mostrarPanelBusqueda();
    }

    public boolean esXMLCopiaSeleccionado() {
        return radioCopia.isSelected();
    }

    public boolean esXMLOriginalSeleccionado() {
        return radioPrueba.isSelected();
    }

    public String obtenerNombreArchivoSeleccionado() {
        return esXMLCopiaSeleccionado() ? LectorXML.ARCHIVO_COPIA : LectorXML.ARCHIVO_ORIGINAL;
    }
}
