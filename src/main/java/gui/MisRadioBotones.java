package gui;

import data.LectorXML;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MisRadioBotones {


    private final Map<String, Runnable> acciones = new LinkedHashMap<>();

    public MisRadioBotones(VentanaPrincipal ventana) {
        acciones.put("Todos los títulos", () -> {
            ventana.limpiar_panel_lectura();
            List<String[]> titulos = LectorXML.obtenerTitulos();

            ventana.cargar_todos_los_titulos(titulos);
        });
        acciones.put("Colectivos según título", () -> {
            ventana.limpiar_panel_lectura();
            List<String[]>titulos = LectorXML.obtenerTitulos();
            ventana.cargar_titulos(titulos);
            ventana.mostrar_panel_colectivos();

            JTable tabla= ventana.obtener_tabla_titulos();
            tabla.getSelectionModel().addListSelectionListener(e->{
                if (!e.getValueIsAdjusting()&& tabla.getSelectedRow()!=-1)
                {
                    String codigoTitulo= tabla.getValueAt(tabla.getSelectedRow(),0).toString();
                    List<String[]>colectivos = LectorXML.obtenerColectivosPorTitulo(codigoTitulo);
                    ventana.cargar_colectivos(colectivos);
                }
            });

        });
    }

    public PanelRadioBotones crearPanel() {
        PanelRadioBotones panel = new PanelRadioBotones();

        panel.cargarOpciones(acciones);
        return panel;
    }
}
