package views;

import data_access.PrezzarioGateway;

import javax.swing.*;
import java.net.URISyntaxException;

public class MenuPrezzario extends JPanel {
    private JPanel mainPanelPrezzario;
    private JTable tblPrezzi;
    private JButton buttonModifica;
    private JButton buttonAggiungi;
    private JPanel pnButtons;

    public MenuPrezzario() throws URISyntaxException {

        PrezzarioGateway prezzarioGateway = new PrezzarioGateway();

        add(mainPanelPrezzario);
        setVisible(true);
    }
}
