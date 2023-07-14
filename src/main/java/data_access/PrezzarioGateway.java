package data_access;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PrezzarioGateway {

    private ArrayList<ArrayList<String>> values;
    private final URL urlPrezzi = getClass().getClassLoader().getResource("Prezzi.csv");
    private final String pathToPrezzi = Paths.get(urlPrezzi.toURI()).toString();

    public PrezzarioGateway() throws URISyntaxException {
        values = readFile();
    }

    // Legge il file excel per la tabella
    private ArrayList<ArrayList<String>> readFile() throws URISyntaxException {
        ArrayList<ArrayList<String>> values = new ArrayList<>();
        ArrayList<String> line = new ArrayList<>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(pathToPrezzi));
            String[] nextLine;
            //reads one line at a time
            while ((nextLine = reader.readNext()) != null) {
                for (String token: nextLine) {
                    line.add(token);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        values.add(line);
        return values;
    }

    public ArrayList<ArrayList<String>> getValues() {
        return values;
    }
}
