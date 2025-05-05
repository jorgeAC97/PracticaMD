package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private PanelVerificador panelvVerificador;
    private PanelBusqueda panelBusqueda;
    private PanelRadioBotones panelRadioBotones;
    private PanelEscritura panelEscritura;



    private JPanel panelCentral;
    private CardLayout layoutCentral;

    public VentanaPrincipal() {
        setTitle("Gestión de tarifas de billetes sensillos de la EMT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // un poco más ancho para el doble panel
        setLocationRelativeTo(null);

        // Panel superior con botones y opciones
        PanelBotonesPrincipales panelBotones = new PanelBotonesPrincipales(this);
        this.panelEscritura = new PanelEscritura();

        MisRadioBotones gestorRadios = new MisRadioBotones(this);
        this.panelRadioBotones = gestorRadios.crearPanel();



        JPanel panelVacio = new JPanel();


        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        panelSuperior.setBackground(Color.LIGHT_GRAY);
        panelSuperior.add(panelBotones);



        layoutCentral = new CardLayout();
        panelCentral = new JPanel(layoutCentral);

        panelvVerificador = new PanelVerificador();
        panelBusqueda = new PanelBusqueda(this);

        panelCentral.add(panelvVerificador, "lectura");
        panelCentral.add(panelBusqueda, "busqueda");
        panelCentral.add(panelEscritura, "escritura");



        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        layoutCentral.show(panelCentral, "lectura");

        setVisible(true);

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
        panelvVerificador.limpiar_campo();
    }
    public void mostrarPanelBusqueda()
    {
        layoutCentral.show(panelCentral, "busqueda");
        panelBusqueda.mostrarVacio();
        panelBusqueda.getPanelRadioBotones().visualizar_panel();
    }
    public void mostrarPanelEscritura()
    {
        layoutCentral.show(panelCentral, "escritura");
        panelvVerificador.limpiar_campo();
        panelEscritura.mostrarFiltros();
    }
    public void visualizar_colectivos_sin_radios()
    {
        layoutCentral.show(panelCentral,"busqueda");
    }
    public void limpiar_panel_lectura_sin_radios()
    {
        panelvVerificador.limpiar_campo();
        panelBusqueda.mostrarVacio();
        panelBusqueda.getPanelRadioBotones().ocultar_panel();
    }
}
