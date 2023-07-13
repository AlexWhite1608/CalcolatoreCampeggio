package data_access;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PrezzarioGateway {


    public PrezzarioGateway() throws URISyntaxException {

        // Salva il file in locale sul sistema
        saveFileToSystem();

    }

    private void saveFileToSystem() {
        String userHome = System.getProperty("user.home");
        String documentPath = userHome + File.separator + "Documents" + File.separator + "CalcolatoreCampeggio";

        // Crea la cartella se non esiste
        File documentsFolder = new File(documentPath);
        if (!documentsFolder.exists()) {
            documentsFolder.mkdirs();
        }

        // Salva il file nella cartella
        String inputFileResourcePath = "Prezzi.csv";
        String outputFileFullPath = documentPath + File.separator + "Prezzi.csv";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputFileResourcePath);
             OutputStream outputStream = new FileOutputStream(outputFileFullPath)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
