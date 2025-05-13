package gui;

import data.EscritorXML;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PanelEscritura extends JPanel {
    private FiltrosPanelEscritura filtrosPanel;

    private JTable tablaColectivosUnico;
    private JTable tablaColectivosDoble;
    private JTable tablaCodigos;
    private JTable tablaDetallesEscritura;


    private DefaultTableModel modeloCodigos;
    private DefaultTableModel modeloColectivos;
    private DefaultTableModel modeloDetallesEscritura;


    private JPanel panelCentral;

    private JScrollPane scrollColectivosUnico;
    private JScrollPane scrollColectivosDoble;

    private CardLayout layoutCentral;

    public PanelEscritura() {
        setLayout(new BorderLayout());

        filtrosPanel = new FiltrosPanelEscritura(this);
        add(filtrosPanel, BorderLayout.NORTH);

        modeloCodigos = new DefaultTableModel(new String[]{"Código"}, 0);
        modeloColectivos = new DefaultTableModel(
                new String[]{"Código Título", "Descripción Título","Zona", "Código Colectivo", "Descripción Colectivo", "Tarifa 1", "Tarifa 2", "Tarifa 3"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 5;
            }
        };
        modeloDetallesEscritura= new DefaultTableModel(
          new String[]{"Empresa","Inicio","Cambio 1","Cambio 2","Fin"},0
        );


        tablaCodigos = new JTable(modeloCodigos);
        tablaColectivosUnico = new JTable(modeloColectivos);
        tablaColectivosDoble =new JTable(modeloColectivos);
        tablaDetallesEscritura=new JTable(modeloDetallesEscritura);
        tablaDetallesEscritura.setRowHeight(25);

        tablaColectivosUnico.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaColectivosUnico.getColumnModel().getColumn(0).setPreferredWidth(100); // Código Título
        tablaColectivosUnico.getColumnModel().getColumn(1).setPreferredWidth(180); // Descripción Título
        tablaColectivosUnico.getColumnModel().getColumn(2).setPreferredWidth(60);  // Zona
        tablaColectivosUnico.getColumnModel().getColumn(3).setPreferredWidth(100); // Código Colectivo
        tablaColectivosUnico.getColumnModel().getColumn(4).setPreferredWidth(180); // Descripción Colectivo
        tablaColectivosUnico.getColumnModel().getColumn(5).setPreferredWidth(70);  // Tarifa 1
        tablaColectivosUnico.getColumnModel().getColumn(6).setPreferredWidth(70);  // Tarifa 2
        tablaColectivosUnico.getColumnModel().getColumn(7).setPreferredWidth(70);  // Tarifa 3



        layoutCentral = new CardLayout();
        panelCentral = new JPanel(layoutCentral);


        scrollColectivosUnico = new JScrollPane(tablaColectivosUnico);
        scrollColectivosDoble = new JScrollPane(tablaColectivosDoble);

        JScrollPane scrollCodigos = new JScrollPane(tablaCodigos);


        JPanel panelDoble = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelDoble.add(scrollCodigos, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.85;
        panelDoble.add(scrollColectivosDoble, gbc);


        JScrollPane scroDetalles= new JScrollPane(tablaDetallesEscritura);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.weightx=1.0;
        gbc.weighty=0.2;

        panelDoble.add(scroDetalles,gbc);

        panelCentral.add(new JPanel(), "vacio");
        panelCentral.add(panelDoble, "doble");
        panelCentral.add(scrollColectivosUnico, "unico"); // ✅ Mismo scroll, diferente uso visual

        add(panelCentral, BorderLayout.CENTER);
        layoutCentral.show(panelCentral, "vacio");

        configurarEventos();
    }

    private void configurarEventos() {
        tablaCodigos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaCodigos.getSelectedRow() != -1) {
                String codigo = modeloCodigos.getValueAt(tablaCodigos.getSelectedRow(), 0).toString();
                VentanaPrincipal ventana = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
                String archivo = ventana.getNombreArchivoXMLSeleccionado();
                List<String[]> colectivos = data.LectorXML.obtenerColectivosParaEscritura(archivo, codigo);


                cargarColectivos(colectivos);
            }
        });
    }

    public void cargarColectivos(List<String[]> colectivos) {
        modeloColectivos.setRowCount(0);
        for (String[] fila : colectivos) {
            modeloColectivos.addRow(fila);
        }
    }

    public void mostrar_resultados_Titulos(List<String[]> titulos) {
        modeloCodigos.setRowCount(0);
        modeloColectivos.setRowCount(0);
        for (String[] fila : titulos) {
            modeloCodigos.addRow(new Object[]{fila[0]});
        }
        layoutCentral.show(panelCentral, "doble");
    }

    public void mostrar_resultados_colectivos(List<String[]> colectivos) {
        modeloCodigos.setRowCount(0);
        modeloColectivos.setRowCount(0);
        for (String[] fila : colectivos) {
            modeloColectivos.addRow(fila);
        }
        layoutCentral.show(panelCentral, "unico");
    }

    public void limpiarResultados() {
        modeloCodigos.setRowCount(0);
        modeloColectivos.setRowCount(0);
        modeloDetallesEscritura.setRowCount(0);

        layoutCentral.show(panelCentral, "vacio");
    }

    public void mostrarFiltros() {
        filtrosPanel.setVisible(true);
    }



    public void guardarCambios() {
        if (tablaColectivosUnico.isEditing()) {
            tablaColectivosUnico.getCellEditor().stopCellEditing();
        }
        if (tablaColectivosDoble.isEditing()) {
            tablaColectivosDoble.getCellEditor().stopCellEditing();
        }

        List<String[]> datos = new ArrayList<>();
        for (int i = 0; i < modeloColectivos.getRowCount(); i++) {
            String[] fila = new String[8];
            for (int j = 0; j < 8; j++) {
                fila[j] = modeloColectivos.getValueAt(i, j).toString();
            }
            datos.add(fila);
        }

        String[] metadatos = null;
        if (modeloDetallesEscritura.getRowCount() > 0) {
            metadatos = new String[5];
            for (int i = 0; i < 5; i++) {
                metadatos[i] = modeloDetallesEscritura.getValueAt(0, i).toString();
            }
        }

        String resultado = EscritorXML.guardarComoXMLCopia(datos, metadatos);
        JOptionPane.showMessageDialog(this, resultado);
    }

    public void agregarDetalles(String[] fila) {
        modeloDetallesEscritura.setRowCount(0); // ✅ Limpia filas anteriores
        modeloDetallesEscritura.addRow(fila);
    }


    public JTable getTablaCodigos() {
        return tablaCodigos;
    }

    public void limpiarDetalles() {
    }
}
