package data_access;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PrezzarioGateway {


    public PrezzarioGateway() throws URISyntaxException {

        // Salva il file in locale sul sistema
        saveFileToSystem();

    }

    private void saveFileToSystem() throws URISyntaxException {
        String userHome = System.getProperty("user.home");
        String documentPath = userHome + File.separator + "Documents" + File.separator + "CalcolatoreCampeggio";

        // Crea la cartella se non esiste
        File documentsFolder = new File(documentPath);
        if (!documentsFolder.exists()) {
            documentsFolder.mkdirs();
        }

        // Salva il file nella cartella
        URL resource = getClass().getClassLoader().getResource("Prezzi.csv");
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            try {
                File inputFile = new File(resource.toURI());
                File outputFile = new File(documentPath, "Prezzi.csv");

                // Verifica se il file non esiste
                if (!Files.exists(outputFile.toPath())) {
                    Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
