package views;

import data_access.PrezzarioGateway;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
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
        createUIComponents();

        setLayout(new BorderLayout());
        add(mainPanelPrezzario, BorderLayout.CENTER);

        setVisible(true);
    }

    private void initTable() throws SQLException {
        pnTable = new JPanel();

        PrezzarioGateway prezzarioGateway = new PrezzarioGateway();
        String initialQuery = "SELECT * FROM Prezzario";
        ResultSet resultSet = prezzarioGateway.execSelectQuery(initialQuery);
        tblPrezzi = new JTable(prezzarioGateway.buildTableModel(resultSet));

        pnTable.add(new JScrollPane(tblPrezzi));
        tblPrezzi.setRowHeight(30);

        // Aggiungi il pannello della tabella al mainPanelPrezzario utilizzando il GridBagLayout
        mainPanelPrezzario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanelPrezzario.add(pnTable, gbc);
    }

    private void initButtons() {
        pnButtons = new JPanel(new GridBagLayout());
        buttonModifica = new JButton("Modifica");
        buttonAggiungi = new JButton("Aggiungi");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        pnButtons.add(buttonAggiungi, gbc);

        gbc.gridx = 1;

        // Margine per staccare i bottoni
        gbc.insets = new Insets(0, 10, 0, 0);
        pnButtons.add(buttonModifica, gbc);

        // Proprietà dei buttons

        // Aggiungi il pannello dei bottoni al mainPanelPrezzario utilizzando il GridBagLayout
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanelPrezzario.add(pnButtons, gbc);
    }

    private void createUIComponents() throws SQLException {
        mainPanelPrezzario = new JPanel();
        initTable();
        initButtons();
    }
}
