package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private PanelVerificador panelvVerificador;
    private PanelBusqueda panelBusqueda;
    private PanelRadioBotones panelRadioBotones;
    private FiltrosPanelEscritura panelEscritura;

    private JPanel panelOpciones;
    private CardLayout layoutOpciones;

    private JPanel panelCentral;
    private CardLayout layoutCentral;

    public VentanaPrincipal() {
        setTitle("Gestión de tarifas de billetes sensillos de la EMT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // un poco más ancho para el doble panel
        setLocationRelativeTo(null);

        // Panel superior con botones y opciones
        PanelBotonesPrincipales panelBotones = new PanelBotonesPrincipales(this);
        this.panelEscritura = new FiltrosPanelEscritura();

        MisRadioBotones gestorRadios = new MisRadioBotones(this);
        this.panelRadioBotones = gestorRadios.crearPanel();

        layoutOpciones = new CardLayout();
        panelOpciones = new JPanel(layoutOpciones);

        JPanel panelVacio = new JPanel();
        panelOpciones.add(panelVacio, "vacio");
        panelOpciones.add(panelRadioBotones, "busqueda");
        panelOpciones.add(panelEscritura, "escritura");

        layoutOpciones.show(panelOpciones, "vacio");

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.add(panelBotones);
        panelSuperior.add(panelOpciones);


        layoutCentral = new CardLayout();
        panelCentral = new JPanel(layoutCentral);

        panelvVerificador = new PanelVerificador();
        panelBusqueda = new PanelBusqueda(this);

        panelCentral.add(panelvVerificador, "lectura");
        panelCentral.add(panelBusqueda, "busqueda");

        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        layoutCentral.show(panelCentral, "lectura");

        setVisible(true);

        // Acciones de los botones en panel de escritura
        panelEscritura.getBotonOK().addActionListener(e -> {
            String codigo = panelEscritura.getTextoBusqueda().trim();
            if (!codigo.isEmpty()) {
                String[] resultado = data.LectorXML.buscarTituloPorCodigo(codigo);
                if (resultado[0].equals("No encontrado") || resultado[0].equals("Error")) {
                    JOptionPane.showMessageDialog(this, "Código no encontrado: " + codigo, "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    List<String[]> colectivos = data.LectorXML.obtenerColectivosPorTitulo(codigo);
                    limpiar_panel_lectura();
                    cargar_colectivos(colectivos);
                    visualizar_solo_colectivos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor introduce un código.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelEscritura.getBotonTodos().addActionListener(e -> {
            limpiar_panel_lectura();
            visualizar_colectivos();
            List<String[]> titulos = data.LectorXML.obtenerTitulos();
            cargar_titulos(titulos);
        });
    }



    public void visualizar_mensaje(String mensaje) {
        panelvVerificador.visualizar_mensaje(mensaje);
    }


    public void limpiar_panel_lectura()
    {
        panelvVerificador.limpiar_campo();
        panelBusqueda.mostrarVacio();
    }

    public void cargar_titulos(List<String[]> titulos) {
        panelBusqueda.cargarTitulos(titulos);
    }

    public void cargar_colectivos(List<String[]> colectivos) {
        panelBusqueda.cargarColectivos(colectivos);
    }

    public JTable obtener_tabla_titulos() {
        return panelBusqueda.getTablaTitulos();
    }

    public void visualizar_opciones_busqueda() {
        layoutOpciones.show(panelOpciones, "busqueda");
    }

    public void visualizar_panel_escritura() {
        layoutOpciones.show(panelOpciones, "escritura");
    }

    public void visualizar_colectivos() {
        layoutCentral.show(panelCentral, "busqueda");
        panelBusqueda.getPanelRadioBotones().visualizar_panel();
    }

    public void visualizar_solo_colectivos() {
        layoutCentral.show(panelCentral, "busqueda");
        panelBusqueda.getPanelRadioBotones().visualizar_panel();
    }


    public void limpiar_paneles()
    {
        panelvVerificador.limpiar_campo();
        panelBusqueda.cargarColectivos(List.of());
        panelBusqueda.cargarTitulos(List.of());
    }

    public void cargar_todos_los_titulos(List<String[]> titulos)
    {
        panelBusqueda.cargarTodosTitulos(titulos);
    }

    public void mostrar_panel_colectivos()
    {
        panelBusqueda.mostrarColectivos();
    }
    public void mostrarPanelVerificador()
    {
        layoutCentral.show(panelCentral, "lectura");
        layoutOpciones.show(panelOpciones,"vacio");
        panelvVerificador.limpiar_campo();
    }
    public void mostrarPanelBusqueda()
    {
        layoutCentral.show(panelCentral, "busqueda");
        layoutOpciones.show(panelOpciones, "busqueda");
        panelBusqueda.mostrarVacio();
    }
    public void mostrarPanelEscritura()
    {
        layoutCentral.show(panelCentral, "escritura");
        layoutOpciones.show(panelOpciones, "escritura");
        panelvVerificador.limpiar_campo();
    }
}
