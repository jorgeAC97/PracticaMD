package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelEscritura extends JPanel {
    private FiltrosPanelEscritura filtrosPanel;
    private JTable tablaResultados;
    private DefaultTableModel modelo;

    public PanelEscritura() {
        setLayout(new BorderLayout());

        filtrosPanel = new FiltrosPanelEscritura(this);
        add(filtrosPanel, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{"Código", "Descripción", "Tarifa 1", "Tarifa 2", "Tarifa 3"}, 0
        );
        tablaResultados = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void mostrarResultados(List<String[]> datos) {
        modelo.setRowCount(0);
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
    }

    public void limpiarResultados() {
        modelo.setRowCount(0);
    }
    public void mostrarFiltros() {
        filtrosPanel.setVisible(true);
    }

}
