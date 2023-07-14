package views;

import data_access.PrezzarioGateway;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.*;

public class HomePage extends JFrame{

    final static String MENU_CAMPEGGIO = "Campeggio";
    final static String MENU_MARGHERITA = "Margherita";
    final static String MENU_PREZZARIO = "Prezzario";

    public HomePage() throws URISyntaxException, IOException {
        JTabbedPane tabbedPane = new JTabbedPane();

        //Crea i menu
        MenuCampeggio menuCampeggio = new MenuCampeggio();
        MenuMargherita menuMargherita = new MenuMargherita();
        MenuPrezzario menuPrezzario = new MenuPrezzario();

        //Aggiunge i menu al tabbed layout
        tabbedPane.addTab(MENU_CAMPEGGIO, menuCampeggio);
        tabbedPane.addTab(MENU_MARGHERITA, menuMargherita);
        tabbedPane.addTab(MENU_PREZZARIO, menuPrezzario);

        this.add(tabbedPane, BorderLayout.CENTER);

        //Imposta parametri visualizzazione HomePage
        this.setTitle("Calcolatore prezzi");
        this.setLocationRelativeTo(null);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
