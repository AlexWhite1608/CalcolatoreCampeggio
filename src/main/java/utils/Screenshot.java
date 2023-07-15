package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Screenshot {
    public static void screenShot(Component c , String nomePrenotazione) throws IOException {
        try {
            BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();
            c.printAll(g);
            g.dispose();

            // Salva immagine
            File directory = new File(System.getProperty("user.home") + "/Desktop/Screenshot_Prenotazioni");
            if (!directory.exists()){
                directory.mkdir();
            }

            String pathName = System.getProperty("user.home") + "/Desktop/Screenshot_Prenotazioni/" + "prenotazione_" + nomePrenotazione + ".png";
            ImageIO.write(image, "png", new File(pathName));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
