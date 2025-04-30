package gui;

import data.VerificadorXML;
import javax.swing.*;
import java.util.*;

public class Misbotones extends JPanel
{
    private final Map<String, Runnable> acciones = new LinkedHashMap<>();

    public Misbotones(VentanaPrincipal ventana)
    {
        acciones.put("Verificar",()->{
            ventana.limpiar_paneles();
            ventana.mostrarPanelVerificador();
           String resultado=VerificadorXML.verificar();
           ventana.visualizar_mensaje(resultado);

        });
        acciones.put("Buqueda", () -> {
            ventana.limpiar_paneles();
            ventana.mostrarPanelBusqueda();

    });
        acciones.put("Escritura", () -> {
            ventana.limpiar_panel_lectura();
            ventana.mostrarPanelEscritura();
        });



        JButton[] botones = BotonFactory.crearBotones(acciones.keySet().stream().toList());
        for (JButton boton : botones)
        {
            Runnable accion= acciones.get(boton.getText());

            if (accion!=null)
            {
                boton.addActionListener(e->accion.run());
            }
            add(boton);
        }
    }
}
