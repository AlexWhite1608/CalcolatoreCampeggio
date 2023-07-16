package views;

import data_access.PrezzarioGateway;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HomePage extends JFrame{

    private final static String MENU_CAMPEGGIO = "Campeggio";
    private final static String MENU_MARGHERITA = "Margherita";
    private final static String MENU_PREZZARIO = "Prezzario";

    public HomePage() throws URISyntaxException, IOException {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Imposta l'icona
        Image icon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/favicon-32x32.png")));
        setIconImage(icon);

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
