package data_access;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class PrezzarioGateway {

    private Connection connection;
    private final String dbPath = Objects.requireNonNull(getClass().getResource("/Campeggio.db")).toString();

    public PrezzarioGateway() {

        // Esegue connessione al database
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
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
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
        ResultSet resultSet = statement.executeQuery();

        return resultSet;
    }

    // Esegue le query di update ritornando il resultset
    public ResultSet execUpdateQuery(String query) {
        return null;
    }

    // Costruisce il table model passando il result set della query
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
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

        return new DefaultTableModel(data, columnNames);
    }

}
