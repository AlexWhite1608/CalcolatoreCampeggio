package views;

import data_access.PrezzarioGateway;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuPrezzario extends JPanel {
    private JPanel mainPanelPrezzario;
    private JTable tblPrezzi;
    private JButton buttonModifica;
    private JButton buttonAggiungi;
    private JPanel pnButtons;
    private JPanel pnTable;

    public MenuPrezzario() throws SQLException {

        //initTable();
        createUIComponents();

        //add(mainPanelPrezzario);
        setVisible(true);
    }

    // Inizializza la tabella
    private void initTable() throws SQLException {
        pnTable = new JPanel();

        PrezzarioGateway prezzarioGateway = new PrezzarioGateway();

        // Query di select iniziale per vedere tutti i prezzi
        String initialQuery = "SELECT * FROM Prezzario";
        ResultSet resultSet = prezzarioGateway.execSelectQuery(initialQuery);

        tblPrezzi = new JTable(prezzarioGateway.buildTableModel(resultSet));

        pnTable.add(new JScrollPane(tblPrezzi));
        tblPrezzi.setRowHeight(30);

        add(pnTable);
    }

    private void createUIComponents() throws SQLException {
        initTable();
    }
}
