package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelBusqueda extends JPanel
{
    private PanelRadioBotones panelRadioBotones;

    private JTable tablaTitulos;
    private JTable tablaColectivos;
    private JTable tablaTodosTitulos;

    private DefaultTableModel modeloTitulos;
    private DefaultTableModel modeloColectivos;
    private DefaultTableModel modeloTodosTitulos;

    private JPanel panelCentral;
    private CardLayout layoutCentral;

    public PanelBusqueda(VentanaPrincipal ventana)
    {
        setLayout(new BorderLayout());


        panelRadioBotones = new MisRadioBotones(ventana).crearPanel();
        add(panelRadioBotones, BorderLayout.NORTH);


        modeloTitulos = new DefaultTableModel(new String[]{"Código"}, 0);
        modeloColectivos = new DefaultTableModel(
                new String[]{"Código", "Descripción", "Tarifa 1", "Tarifa 2", "Tarifa 3"}, 0
        );
        modeloTodosTitulos =new DefaultTableModel(new String[]{"Códigp","Descripción","Zona"},0);

        tablaTitulos = new JTable(modeloTitulos);
        tablaColectivos = new JTable(modeloColectivos);
        tablaTodosTitulos =new JTable(modeloTodosTitulos);

        tablaColectivos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tablaColectivos.getColumnModel().getColumn(0).setPreferredWidth(70);
        tablaColectivos.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaColectivos.getColumnModel().getColumn(2).setPreferredWidth(70);
        tablaColectivos.getColumnModel().getColumn(3).setPreferredWidth(70);
        tablaColectivos.getColumnModel().getColumn(4).setPreferredWidth(70);

        JScrollPane scrollTitulos = new JScrollPane(tablaTitulos);
        JScrollPane scrollColectivos = new JScrollPane(tablaColectivos);
        JScrollPane scrollPaneTodosTitulos = new JScrollPane(tablaTodosTitulos);


        JPanel panelDoble = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelDoble.add(scrollTitulos, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.85;
        panelDoble.add(scrollColectivos, gbc);

        layoutCentral =new CardLayout();
        panelCentral =new JPanel(layoutCentral);

        panelCentral.add(new JPanel(), "vacio");
        panelCentral.add(scrollPaneTodosTitulos,"todosTitulos");
        panelCentral.add(panelDoble,"colectivos");



        add(panelCentral, BorderLayout.CENTER);
        layoutCentral.show(panelCentral,"vacio");
    }

    public void cargarTitulos(List<String[]> datos)
    {
        modeloTitulos.setRowCount(0);
        for (String[] fila : datos) {
            modeloTitulos.addRow(new Object[]{fila[0]});
        }
    }
    public void cargarTodosTitulos(List<String[]>datos)
    {
        modeloTodosTitulos.setRowCount(0);
        for (String[]fila: datos)
        {
            modeloTodosTitulos.addRow(fila);
        }
        layoutCentral.show(panelCentral,"todosTitulos");
    }

    public void cargarColectivos(List<String[]> datos)
    {
        modeloColectivos.setRowCount(0);
        for (String[] fila : datos) {
            modeloColectivos.addRow(fila);
        }
        layoutCentral.show(panelCentral,"colecctivos");
    }
    public void mostrarColectivos()
    {
        layoutCentral.show(panelCentral,"colecctivos");
    }

    public JTable getTablaTitulos() {
        return tablaTitulos;
    }

    public PanelRadioBotones getPanelRadioBotones() {
        return panelRadioBotones;
    }
    public void mostrarVacio()
    {
        layoutCentral.show(panelCentral,"vacio");
    }
}
