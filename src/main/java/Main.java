import views.HomePage;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new HomePage();
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}