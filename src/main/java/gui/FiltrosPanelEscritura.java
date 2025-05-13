package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FiltrosPanelEscritura extends JPanel {
    private JTextField campoBusqueda;
    private JButton botonOK;
    private JButton botonTodos;
    private JButton butGuardar;
    private final PanelEscritura panelPadre;

    public FiltrosPanelEscritura(PanelEscritura padre) {
        this.panelPadre = padre;

        setLayout(new FlowLayout());
        setVisible(false);

        botonTodos = new JButton("Todos los códigos");
        butGuardar = new JButton("Guardar cambios");

        add(botonTodos);
        add(butGuardar);

        configurarEventos();
    }


    private void configurarEventos()
    {


        botonTodos.addActionListener(e -> {
            VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
            String archivoSeleccionado = ventana.getNombreArchivoXMLSeleccionado();

            List<String[]> titulos = data.LectorXML.obtenerTitulos(archivoSeleccionado);
            panelPadre.limpiarResultados();
            panelPadre.mostrar_resultados_Titulos(titulos);

            // Agregar el listener aquí para detectar selección
            JTable tablaCodigos = panelPadre.getTablaCodigos(); // 👈 este método lo añades abajo
            tablaCodigos.getSelectionModel().addListSelectionListener(ev -> {
                if (!ev.getValueIsAdjusting() && tablaCodigos.getSelectedRow() != -1) {
                    String codigo = tablaCodigos.getValueAt(tablaCodigos.getSelectedRow(), 0).toString();

                    List<String[]> colectivos = data.LectorXML.obtenerColectivosParaEscritura(archivoSeleccionado,codigo);
                    panelPadre.cargarColectivos(colectivos);

                    String[] metadatos = data.LectorXML.obtenerMetadatosTitulo(archivoSeleccionado, codigo);
                    panelPadre.limpiarDetalles(); // 👈 LIMPIA FILAS anteriores
                    panelPadre.agregarDetalles(metadatos);
                }
            });
        });


        butGuardar.addActionListener(e->{
            panelPadre.guardarCambios();
        });
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
