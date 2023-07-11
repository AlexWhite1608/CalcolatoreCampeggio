package views;

import java.awt.*;
import javax.swing.*;

public class HomePage extends JFrame{

    final static String MENU_CAMPEGGIO = "Campeggio";
    final static String MENU_MARGHERITA = "Margherita";
    final static String MENU_PREZZARIO = "Prezzario";

    public HomePage() {
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
        this.setResizable(false);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
