package views;

import data_access.PrezzarioGateway;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.io.IOException;
import java.net.URISyntaxException;

public class MenuPrezzario extends JPanel {
    private JPanel mainPanelPrezzario;
    private JTable tblPrezzi;
    private JButton buttonModifica;
    private JButton buttonAggiungi;
    private JPanel pnButtons;
    private JPanel pnTable;

    public MenuPrezzario() throws URISyntaxException, IOException {

        //initTable();
        createUIComponents();

        //add(mainPanelPrezzario);
        setVisible(true);
    }

    // Inizializza la tabella
    private void initTable() {
        pnTable = new JPanel();
        tblPrezzi = new JTable();

        PrezzarioGateway prezzarioGateway = new PrezzarioGateway();

        pnTable.add(new JScrollPane(tblPrezzi));
        tblPrezzi.setRowHeight(50);

        add(pnTable);
    }

    private void createUIComponents(){
        initTable();
    }
}
