import views.HomePage;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new HomePage();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}