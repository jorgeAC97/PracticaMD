package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FiltrosPanelEscritura extends JPanel {
    private JTextField campoBusqueda;
    private JButton botonOK;
    private JButton botonTodos;
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

        JPanel campoConBoton = new JPanel(new BorderLayout());
        campoConBoton.add(campoBusqueda, BorderLayout.CENTER);
        campoConBoton.add(botonOK, BorderLayout.EAST);

        add(etiqueta);
        add(campoConBoton);
        add(botonTodos);

        configurarEventos();
    }

    private void configurarEventos()
    {
        botonOK.addActionListener(e->{
            String codigo= campoBusqueda.getText().trim();
            if (!codigo.isEmpty())
            {
                String[] resultado = data.LectorXML.buscarTituloPorCodigo(codigo);
                if (resultado[0].equals("No encontrado")|| resultado[0].equals("Error"))
                {
                    JOptionPane.showMessageDialog(this,"Codigo no encontrado;"+codigo,"Aviso",JOptionPane.WARNING_MESSAGE);
                }else
                {
                    List<String[]>colectivos= data.LectorXML.obtenerColectivosPorTitulo(codigo);
                    panelPadre.limpiarResultados();
                    panelPadre.mostrarResultados(colectivos);
                }
            }else
            {
                JOptionPane.showMessageDialog(this,"Por favor introduce un código.","Campo vacio",JOptionPane.WARNING_MESSAGE);
            }
        });
        botonTodos.addActionListener(e -> {
            List<String[]> titulos = data.LectorXML.obtenerTitulos();
            panelPadre.limpiarResultados();
            panelPadre.mostrarResultados(titulos);
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
