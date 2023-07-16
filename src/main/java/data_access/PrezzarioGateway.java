package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

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

}
