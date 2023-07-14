package views;

import data_access.PrezzarioGateway;
import data_access.PrezzarioTableModel;

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

        // Crea il modello della tabella con i dati e i nomi delle colonne
        Object[][] data = {
                {"Camper", 13.0, 16.0},
                {"Tenda", 9.0, 12.0},
                {"Adulti", 8.0, 10.0},
                {"Bambini (3-10)", 4.5, 6.0},
                {"Animali", 3.0, 3.0},
                {"Tassa di soggiorno (>12)", 1.5, 1.5}
        };
        String[] columnNames = {"Tipologia", "Bassa Stagione", "Alta Stagione"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        tblPrezzi.setModel(tableModel);

        // Crea l'header della tabella
        JTableHeader tableHeader = tblPrezzi.getTableHeader();

        // Aggiungi la tabella e l'header al pannello
        pnTable.add(new JScrollPane(tblPrezzi));
        pnTable.add(tableHeader);

        tblPrezzi.setRowHeight(50);
        add(pnTable);
    }

    private void createUIComponents() throws URISyntaxException, IOException {
        initTable();
    }
}
