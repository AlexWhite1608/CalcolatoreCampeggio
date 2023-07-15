package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Screenshot {
    public static boolean screenShot(Component c) throws IOException {
        try {
            BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();
            c.printAll(g);
            g.dispose();

            // Salva immagine
            String pathName = System.getProperty("user.home") + "/Desktop/Screen_Prenotazioni/" + "prenotazione_" + LocalDate.now() + ".png";
            ImageIO.write(image, "png", new File(pathName));
        } catch (Exception e){
            return false;
        }

        return true;
    }
}
