package views;

import data_access.PrezzarioGateway;
import data_access.PrezzarioTableModel;

import javax.swing.*;
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
    private void initTable() throws URISyntaxException, IOException {
        PrezzarioGateway prezzarioGateway = new PrezzarioGateway();
        pnTable = new JPanel();
        tblPrezzi = new JTable();

        pnTable.add(new JScrollPane(tblPrezzi));

        tblPrezzi.setModel(new PrezzarioTableModel(prezzarioGateway.getValues()));
        tblPrezzi.setTableHeader(new JTableHeader());
        tblPrezzi.setRowHeight(50);
    }


    private void createUIComponents() throws URISyntaxException, IOException {
        initTable();
    }
}
