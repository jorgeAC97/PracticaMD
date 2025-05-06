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

    public FiltrosPanelEscritura(PanelEscritura padre)
    {
        this.panelPadre= padre;

        setLayout(new FlowLayout());
        setVisible(false);

        JLabel etiqueta = new JLabel("Búsqueda manual:");
        campoBusqueda = new JTextField(15);
        botonOK = new JButton("OK");
        botonTodos = new JButton("Todos los códigos");
        butGuardar =new JButton("Guardar cambios");

        JPanel campoConBoton = new JPanel(new BorderLayout());
        campoConBoton.add(campoBusqueda, BorderLayout.CENTER);
        campoConBoton.add(botonOK, BorderLayout.EAST);

        add(etiqueta);
        add(campoConBoton);
        add(botonTodos);
        add(butGuardar);

        configurarEventos();
    }

    private void configurarEventos()
    {
        botonOK.addActionListener(e -> {
            String codigo = campoBusqueda.getText().trim();
            if (!codigo.isEmpty()) {
                String[] resultado = data.LectorXML.buscarTituloPorCodigo(codigo);
                if (resultado[0].equals("No encontrado") || resultado[0].equals("Error")) {
                    JOptionPane.showMessageDialog(this, "Codigo no encontrado: " + codigo, "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    List<String[]> colectivos = data.LectorXML.obtenerColectivosParaEscritura(codigo);

                    panelPadre.limpiarResultados();
                    panelPadre.mostrar_resultados_colectivos(colectivos); // ✅ Correcto
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor introduce un código.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

        botonTodos.addActionListener(e -> {
            List<String[]> titulos = data.LectorXML.obtenerTitulos();
            panelPadre.limpiarResultados();
            panelPadre.mostrar_resultados_Titulos(titulos);
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
