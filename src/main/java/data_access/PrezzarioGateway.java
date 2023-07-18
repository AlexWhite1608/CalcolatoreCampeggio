package data_access;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;

public class PrezzarioGateway {

    private Connection connection;
    private final String dbName = "Campeggio.db";

    public PrezzarioGateway() {

        connect();
    }

    // Esegue connessione al database
    private void connect(){
        try {
            // Verifica se la connessione esiste già
            if (connection != null && !connection.isClosed()) {
                return;
            }

            // Carica il driver JDBC per SQLite
            Class.forName("org.sqlite.JDBC");

            // Apre la connessione al database SQLite
            connection = DriverManager.getConnection("jdbc:sqlite::resource:" + dbName);
            System.out.println("Connesso al database");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver non trovato");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Impossibile connettersi al database");
            e.printStackTrace();
        }

    }

    // Esegue disconnessione dal database
    private void disconnect() {
        try {
            // Verifica se la connessione esiste e chiude la connessione
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnesso dal database");
            }

        } catch (SQLException e) {
            System.out.println("Impossibile disconnettersi dal database");
            e.printStackTrace();
        }
    }

    // Esegue la query di select fornita ritornando il resultset
    public ResultSet execSelectQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        return statement.executeQuery();
    }

    // Esegue update del database quando richiesto
    public void updateCellData(String tipologia, Object data, int column) throws SQLException {
        String columnName = (column == 1) ? "Bassa_stagione" : "Alta_stagione";

        // Esegui la query di aggiornamento nel database utilizzando i dati passati
        String updateQuery = "UPDATE Prezzario SET " + columnName + " = ? WHERE Tipologia = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setObject(1, data.toString());
            statement.setString(2, tipologia);
            statement.executeUpdate();
        }
    }

    // Costruisce il table model passando il result set della query
    public DefaultTableModel buildCustomTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Nome delle colonne
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // Data
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        DefaultTableModel tableModel = setTableModelParams(data, columnNames);
        return tableModel;
    }

    // Modifica i metodi del DefaultTableModel per la modifica della tabella
    private DefaultTableModel setTableModelParams(Vector<Vector<Object>> data, Vector<String> columnNames){
        DefaultTableModel model = new DefaultTableModel(data, columnNames){
            @Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return column != 0;
            }

        };

        return model;
    }

    // Estrae i prezzi dal database
    public Map<String, Double[]> getPrices() throws SQLException {
        Map<String, Double[]> prezziStagioni = new HashMap<>();

        String query = "SELECT * FROM Prezzario";
        ResultSet resultSet = this.execSelectQuery(query);

        while (resultSet.next()) {
            String tipologia = resultSet.getString("Tipologia");
            double bassaStagione = resultSet.getDouble("Bassa stagione");
            double altaStagione = resultSet.getDouble("Alta stagione");

            prezziStagioni.put(tipologia, new Double[]{bassaStagione, altaStagione});
        }

        return prezziStagioni;
    }

}
