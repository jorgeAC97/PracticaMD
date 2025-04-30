package gui;

import javax.swing.*;
import java.awt.*;

public class PanelBotonesPrincipales extends JPanel
{
    public PanelBotonesPrincipales(VentanaPrincipal ventana)
    {
        setLayout(new BorderLayout());
        Misbotones misbotones= new Misbotones(ventana);
        add(misbotones,BorderLayout.CENTER);
    }
}
