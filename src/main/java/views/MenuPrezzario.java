package views;

import data_access.PrezzarioGateway;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuPrezzario extends JPanel {
    private JPanel mainPanelPrezzario;
    private JTable tblPrezzi;
    private JButton buttonModifica;
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

        // Popola la tabella con le informazioni nel database
        PrezzarioGateway prezzarioGateway = new PrezzarioGateway();
        String initialQuery = "SELECT * FROM Prezzario";
        ResultSet resultSet = prezzarioGateway.execSelectQuery(initialQuery);
        tblPrezzi = new JTable(prezzarioGateway.buildCustomTableModel(resultSet));

        // Impostazioni della tabella
        int visibleRows = 10;
        int rowHeight = 30;
        int headerHeight = 30;
        int width = 500;
        int rowCount = tblPrezzi.getRowCount();
        int height = Math.min(rowCount * rowHeight + headerHeight, visibleRows * rowHeight + headerHeight);
        tblPrezzi.setPreferredScrollableViewportSize(new Dimension(width, height - rowHeight));
        tblPrezzi.setRowHeight(rowHeight);
        tblPrezzi.getTableHeader().setReorderingAllowed(false);
        tblPrezzi.setDefaultEditor(Object.class, null);

        // Aggiungi il pannello della tabella al mainPanelPrezzario utilizzando il GridBagLayout
        mainPanelPrezzario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanelPrezzario.add(pnTable, gbc);

        pnTable.add(new JScrollPane(tblPrezzi));
    }

    private void initButtons() {
        pnButtons = new JPanel(new GridBagLayout());
        buttonModifica = new JButton("Salva modifiche");

        // Impostazione del layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;

        // Margine per staccare i bottoni
        gbc.insets = new Insets(0, 10, 10, 0);
        pnButtons.add(buttonModifica, gbc);

        // Proprietà dei buttons
        buttonModifica.setFocusPainted(false);

        // Aggiungi il pannello dei bottoni al mainPanelPrezzario utilizzando il GridBagLayout
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanelPrezzario.add(pnButtons, gbc);

        //Azione: doppio click sulla cella ne seleziona il contenuto
        final boolean[] isDoubleClick = {false};

        tblPrezzi.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    isDoubleClick[0] = true;
                    int row = tblPrezzi.rowAtPoint(e.getPoint());
                    int column = tblPrezzi.columnAtPoint(e.getPoint());

                    // Verifica che si sia cliccato su una cella della seconda o terza colonna
                    if (column == 1 || column == 2) {
                        tblPrezzi.editCellAt(row, column);
                        Component editor = tblPrezzi.getEditorComponent();
                        if (editor != null) {
                            editor.requestFocusInWindow();
                            if (editor instanceof JTextComponent) {
                                ((JTextComponent) editor).selectAll();
                            }
                        }
                    }
                } else {
                    isDoubleClick[0] = false;
                }
            }
        });

        tblPrezzi.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!isDoubleClick[0]) {
                    // Ripristina il contenuto della cella quando si fa clic fuori dalla tabella
                    tblPrezzi.editingCanceled(null);
                }
            }
        });

        // Azione: modifica della table quando si clicca sul button
        buttonModifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrezzarioGateway prezzarioGateway = new PrezzarioGateway();

                // Esegui l'aggiornamento del database solo se è stato confermato l'azione
                int confirmResult = JOptionPane.showConfirmDialog(MenuPrezzario.this,
                        "Vuoi salvare le modifiche?",
                        "Conferma Modifica",
                        JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    TableModel tableModel = tblPrezzi.getModel();
                    for (int row = 0; row < tableModel.getRowCount(); row++) {
                        for (int column = 1; column < tableModel.getColumnCount(); column++) {
                            Object data = tableModel.getValueAt(row, column);
                            String tipologia = tableModel.getValueAt(row, 0).toString();
                            try {
                                prezzarioGateway.updateCellData(tipologia, data, column);
                            } catch (SQLException ex) {
                                JOptionPane.showConfirmDialog(MenuPrezzario.this,
                                        "Impossibile salvare le modifiche!",
                                        "Errore salvataggio modifiche",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    JOptionPane.showConfirmDialog(MenuPrezzario.this,
                            "Modifiche salvate correttamente!",
                            "Salvataggio modifiche",
                            JOptionPane.CLOSED_OPTION);
                }

                prezzarioGateway.disconnect();
            }
        });
    }

    private void createUIComponents() throws SQLException {
        mainPanelPrezzario = new JPanel();
        initTable();
        initButtons();
    }
}
