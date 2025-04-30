package org.example;

import gui.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e)
        {
            System.out.println("No se pudo aplicar Nimbus: " + e.getMessage());
        }
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
