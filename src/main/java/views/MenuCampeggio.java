package views;

import com.github.lgooddatepicker.components.DatePicker;
import data_access.PrezzarioGateway;
import utils.Screenshot;
import utils.Stagione;
import utils.TextFieldsController;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class MenuCampeggio extends JPanel {
    private JPanel mainPanelCampeggio;
    private DatePicker datePickerArrivo;
    private DatePicker datePickerPartenza;
    private JLabel labelArrivo;
    private JLabel labelPartenza;
    private JLabel labelNumNotti;
    private JTextField tfNumNotti;
    private JLabel labelBs;
    private JLabel labelAs;
    private JLabel labelNumAdulti;
    private JTextField tfNumAdulti;
    private JTextField tfNoTax;
    private JLabel labelNoTax;
    private JLabel labelNumBambini;
    private JTextField tfNumBambini;
    private JLabel labelNumAnimali;
    private JTextField tfNumAnimali;
    private JRadioButton rbCamper;
    private JRadioButton rbTenda;
    private JTextField tfExtra;
    private JButton btnCancella;
    private JButton btnCalcola;
    private JLabel labelTotaleCampeggio;
    private JLabel labelTassaSoggiorno;
    private JLabel labelTotaleTassa;
    private JLabel labelExtra;
    private JPanel pnButtons;
    private JPanel pnInnerForm;
    private JLabel labelCalcoloTassaSoggiorno;
    private JLabel labelCalcoloTotaleCampeggio;
    private JLabel labelCalcoloTotaleTassa;
    private JTextField tfTotale;
    private JButton btnStampa;
    private JPanel pnToolBar;
    private JToolBar toolBar;
    private JButton btnSconto;
    private JLabel lblAAcconto;
    private JTextField tfAcconto;

    public MenuCampeggio() {
        // Setup textField (solo interi)
        setupTextFields();

        // Setup button per stampare schermata
        setupPrintButton();

        // Setup button per lo sconto
        setupScontoButton();

        // Calcolo numero di notti
        calculateNumNotti();

        // Cancella il form quando si clicca su "cancella"
        clearFormOnCancel();

        // Calcolo totale
        calculateTotal();

        add(mainPanelCampeggio);
        setVisible(true);
    }

    // Setup textField
    private void setupTextFields() {
        TextFieldsController.setupTextFieldsFloat(tfExtra);
        TextFieldsController.setupTextFieldsInteger(tfNumAdulti);
        TextFieldsController.setupTextFieldsInteger(tfNoTax);
        TextFieldsController.setupTextFieldsInteger(tfNumBambini);
        TextFieldsController.setupTextFieldsInteger(tfNumAnimali);
        TextFieldsController.setupTextFieldsFloat(tfAcconto);
    }

    // Codice per cancellare tutto il form
    private void clearFormOnCancel() {
        btnCancella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Rimuovi il DocumentFilter temporaneamente
                DocumentFilter numberFilter = ((AbstractDocument) tfNumAdulti.getDocument()).getDocumentFilter();
                ((AbstractDocument) tfNumAdulti.getDocument()).setDocumentFilter(null);
                ((AbstractDocument) tfNoTax.getDocument()).setDocumentFilter(null);
                ((AbstractDocument) tfNumBambini.getDocument()).setDocumentFilter(null);
                ((AbstractDocument) tfNumAnimali.getDocument()).setDocumentFilter(null);
                ((AbstractDocument) tfExtra.getDocument()).setDocumentFilter(null);

                tfNumAdulti.setText("");
                tfNumBambini.setText("");
                tfNumAnimali.setText("");
                tfExtra.setText("");
                tfNoTax.setText("");
                tfAcconto.setText("");
                rbCamper.setSelected(false);
                rbTenda.setSelected(false);

                // Ripristina il DocumentFilter
                ((AbstractDocument) tfNumAdulti.getDocument()).setDocumentFilter(numberFilter);
                ((AbstractDocument) tfNoTax.getDocument()).setDocumentFilter(numberFilter);
                ((AbstractDocument) tfNumBambini.getDocument()).setDocumentFilter(numberFilter);
                ((AbstractDocument) tfNumAnimali.getDocument()).setDocumentFilter(numberFilter);
                ((AbstractDocument) tfExtra.getDocument()).setDocumentFilter(numberFilter);

                // Cancella date
                datePickerArrivo.clear();
                datePickerPartenza.clear();

                // Cancella totali
                labelCalcoloTotaleCampeggio.setText("");
                labelCalcoloTotaleTassa.setText("");
                labelCalcoloTassaSoggiorno.setText("");
                tfTotale.setText("");

                // Reimposta vincoli sulle textfields
                setupTextFields();
            }
        });
    }

    // Calcolo numero notti
    private void calculateNumNotti(){

        PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                // SOLTANTO quando entrambe le date sono scelte calcola il numero di notti
                if (datePickerArrivo.getDate() != null && datePickerPartenza.getDate() != null) {
                    setNumNights();
                } else {
                    tfNumNotti.setText("");
                    labelAs.setText("AS:");
                    labelBs.setText("BS:");
                }
            }

            // Controlla la correttezza delle date inserite
            private boolean checkDates(LocalDate arrivo, LocalDate partenza){
                return partenza.isAfter(arrivo);
            }

            // Calcola e imposta il numero di notti
            private void setNumNights() {
                LocalDate arrivo = datePickerArrivo.getDate();
                LocalDate partenza = datePickerPartenza.getDate();

                if(checkDates(arrivo, partenza)){
                    int nights = (int) ChronoUnit.DAYS.between(arrivo, partenza);
                    tfNumNotti.setText(Integer.toString(nights));

                    // Imposta giorni bassa/alta stagione
                    calculateAsBs();

                // Errore selezione date
                } else {
                    resetForm();
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "La data di partenza inserita è precedente alla data di arrivo",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            // Calcola giorni bassa/alta stagione
            private void calculateAsBs(){

                String meseArrivo = datePickerArrivo.getDate().getMonth().toString();
                String mesePartenza = datePickerPartenza.getDate().getMonth().toString();

                // Mese di arrivo e mese di partenza coincidono
                if(Objects.equals(meseArrivo, mesePartenza)){
                    if(Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo)){
                        labelBs.setText("BS: " + tfNumNotti.getText());
                        labelAs.setText("AS: " + "0");
                    } else {
                        labelAs.setText("AS: " + tfNumNotti.getText());
                        labelBs.setText("BS: " + "0");
                    }

                // Mese di arrivo e di partenza entrambi in bassa stagione
                } else if(Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo) &&
                          Arrays.asList(Stagione.BassaStagione.getMesi()).contains(mesePartenza)) {

                    labelBs.setText("BS: " + tfNumNotti.getText());
                    labelAs.setText("AS: " + "0");

                // Mese di arrivo e di partenza entrambi in alta stagione
                } else if(Arrays.asList(Stagione.AltaStagione.getMesi()).contains(meseArrivo) &&
                          Arrays.asList(Stagione.AltaStagione.getMesi()).contains(mesePartenza)) {

                    labelAs.setText("AS: " + tfNumNotti.getText());
                    labelBs.setText("BS: " + "0");

                // Mese di arrivo e mese di partenza in stagioni diverse
                } else if(Arrays.asList(Stagione.AltaStagione.getMesi()).contains(meseArrivo) && Arrays.asList(Stagione.BassaStagione.getMesi()).contains(mesePartenza) ||
                          Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo) && Arrays.asList(Stagione.AltaStagione.getMesi()).contains(mesePartenza)){

                    int giornoArrivoInt = datePickerArrivo.getDate().getDayOfMonth();
                    int meseArrivoInt = datePickerArrivo.getDate().getMonthValue();
                    int annoArrivoInt = datePickerArrivo.getDate().getYear();

                    LocalDate initialArrivo = LocalDate.of(annoArrivoInt, meseArrivoInt, giornoArrivoInt).withDayOfMonth(1);
                    LocalDate endArrivo = initialArrivo.withDayOfMonth(initialArrivo.lengthOfMonth());

                    int numeroNotti = Integer.parseInt(tfNumNotti.getText());
                    int numeroNottiInBS = 0;
                    int numeroNottiInAS = 0;

                    if (Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo)) {
                        numeroNottiInBS = Math.min(numeroNotti, endArrivo.getDayOfMonth() - giornoArrivoInt + 1);
                        numeroNottiInAS = numeroNotti - numeroNottiInBS;
                    } else {
                        numeroNottiInAS = Math.min(numeroNotti, endArrivo.getDayOfMonth() - giornoArrivoInt + 1);
                        numeroNottiInBS = numeroNotti - numeroNottiInAS;
                    }

                    labelBs.setText("BS: " + numeroNottiInBS);
                    labelAs.setText("AS: " + numeroNottiInAS);

                // Mese di arrivo e mese di partenza nella stessa stagione ma in mezzo cambia la stagione
                //TODO: trova una condizione valida!
                } else {
                    int giornoArrivoInt = datePickerArrivo.getDate().getDayOfMonth();
                    int meseArrivoInt = datePickerArrivo.getDate().getMonthValue();
                    int annoArrivoInt = datePickerArrivo.getDate().getYear();

                    int giornoPartenzaInt = datePickerPartenza.getDate().getDayOfMonth();
                    int mesePartenzaInt = datePickerPartenza.getDate().getMonthValue();
                    int annoPartenzaInt = datePickerPartenza.getDate().getYear();

                    LocalDate initialArrivo = LocalDate.of(annoArrivoInt, meseArrivoInt, giornoArrivoInt).withDayOfMonth(1);
                    LocalDate endArrivo = initialArrivo.withDayOfMonth(initialArrivo.lengthOfMonth());
                    LocalDate initialPartenza = LocalDate.of(annoPartenzaInt, mesePartenzaInt, giornoPartenzaInt).withDayOfMonth(1);

                    int numeroNotti = Integer.parseInt(tfNumNotti.getText());
                    int numeroNottiInBS = 0;
                    int numeroNottiInAS = 0;

                    // Se il mese di arrivo è nella bassa stagione
                    if (Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo)) {
                        numeroNottiInBS = Math.min(numeroNotti, endArrivo.getDayOfMonth() - giornoArrivoInt + 1);

                        // Se il mese di partenza è nella bassa stagione
                        if (Arrays.asList(Stagione.BassaStagione.getMesi()).contains(mesePartenza)) {
                            numeroNottiInBS += numeroNotti - numeroNottiInBS;
                        } else {
                            numeroNottiInAS = numeroNotti - numeroNottiInBS;
                        }
                    } else {
                        numeroNottiInAS = Math.min(numeroNotti, endArrivo.getDayOfMonth() - giornoArrivoInt + 1);

                        // Se il mese di partenza è nella stagione alta
                        if (Arrays.asList(Stagione.BassaStagione.getMesi()).contains(mesePartenza)) {
                            numeroNottiInBS = numeroNotti - numeroNottiInAS;
                        } else {
                            numeroNottiInAS += numeroNotti - numeroNottiInAS;
                        }
                    }
                }
            }

            // Resetta il form nel caso in cui le date inserite siano errate
            private void resetForm(){
                tfNumNotti.setText("");
                datePickerArrivo.closePopup();
                datePickerPartenza.closePopup();
                datePickerPartenza.setText("");
            }
        };

        datePickerArrivo.addPropertyChangeListener("date", propertyChangeListener);
        datePickerPartenza.addPropertyChangeListener("date", propertyChangeListener);
    }

    // Calcolo totale
    private void calculateTotal(){
        btnCalcola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Imposta allineamento delle label dei totali
                labelCalcoloTotaleCampeggio.setHorizontalAlignment(SwingConstants.CENTER);
                labelCalcoloTassaSoggiorno.setHorizontalAlignment(SwingConstants.CENTER);
                labelCalcoloTotaleTassa.setHorizontalAlignment(SwingConstants.CENTER);

                // Controllo di aver inserito le date
                if (datePickerArrivo.getText().isEmpty() || datePickerPartenza.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "Inserire entrambe le date!",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                } else {

                    // Controllo di aver inserito almeno un valore
                    if (tfNumAdulti.getText().isEmpty() && tfNumBambini.getText().isEmpty() && tfNumAnimali.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(MenuCampeggio.this,
                                "Inserire il numero degli ospiti!",
                                "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Totali
                double totaleCampeggio = 0;
                double totaleCampeggioConTassa = 0;

                // Ricava i prezzi dal database
                PrezzarioGateway prezzarioGateway = new PrezzarioGateway();
                ArrayList<String> tipologie = new ArrayList<>();
                ArrayList<Double[]> prezzi = new ArrayList<>();
                try {
                    for (Map.Entry<String, Double[]> entrySet : prezzarioGateway.getPrices().entrySet()) {
                        tipologie.add(entrySet.getKey());
                        prezzi.add(entrySet.getValue());
                    }
                } catch (SQLException ex) {
                    System.err.print("Errore nel ricavare i prezzi dal database!");
                }

                int indexCamper = tipologie.indexOf("Camper");
                int indexTenda = tipologie.indexOf("Tenda");
                int indexAdulto = tipologie.indexOf("Adulto");
                int indexBambino = tipologie.indexOf("Bambino");
                int indexAnimale = tipologie.indexOf("Animali");
                int indexTaxSoggiorno = tipologie.indexOf("Tassa di soggiorno");

                // Accede ai prezzi e assegna alle variabili corrispondenti
                Double[] prezziCamper = prezzi.get(indexCamper);
                double prezzoCamperAs = prezziCamper[1];
                double prezzoCamperBs = prezziCamper[0];

                Double[] prezziTenda = prezzi.get(indexTenda);
                double prezzoTendaAs = prezziTenda[1];
                double prezzoTendaBs = prezziTenda[0];

                Double[] prezziAdulto = prezzi.get(indexAdulto);
                double prezzoAdultoAs = prezziAdulto[1];
                double prezzoAdultoBs = prezziAdulto[0];

                Double[] prezziBambino = prezzi.get(indexBambino);
                double prezzoBambinoAs = prezziBambino[1];
                double prezzoBambinoBs = prezziBambino[0];

                double prezzoAnimale = prezzi.get(indexAnimale)[0];
                double taxSoggiorno = prezzi.get(indexTaxSoggiorno)[0];

                // Numero notti
                int numNotti = Integer.parseInt(tfNumNotti.getText());
                int numNottiAs = Integer.parseInt(labelAs.getText().replace("AS: ", ""));
                int numNottiBs = Integer.parseInt(labelBs.getText().replace("BS: ", ""));

                // Numeri clienti
                int numAdulti = 0;
                int numBambini = 0;
                int numAnimali = 0;
                if(!Objects.equals(tfNumAdulti.getText(), ""))
                    numAdulti = Integer.parseInt(tfNumAdulti.getText());
                if(!Objects.equals(tfNumBambini.getText(), ""))
                    numBambini = Integer.parseInt(tfNumBambini.getText());
                if(!Objects.equals(tfNumAnimali.getText(), ""))
                    numAnimali = Integer.parseInt(tfNumAnimali.getText());

                // Camper o tenda
                boolean tenda = rbTenda.isSelected();
                boolean camper = rbCamper.isSelected();

                if(tenda || camper) {
                    if (tenda) {
                        totaleCampeggio = ((numAdulti * prezzoAdultoAs) + (numBambini * prezzoBambinoAs) + (numAnimali * prezzoAnimale) + prezzoTendaAs) * numNottiAs +
                                ((numAdulti * prezzoAdultoBs) + (numBambini * prezzoBambinoBs) + (numAnimali * prezzoAnimale) + prezzoTendaBs) * numNottiBs;
                    } else {
                        totaleCampeggio = ((numAdulti * prezzoAdultoAs) + (numBambini * prezzoBambinoAs) + (numAnimali * prezzoAnimale) + prezzoCamperAs) * numNottiAs +
                                ((numAdulti * prezzoAdultoBs) + (numBambini * prezzoBambinoBs) + (numAnimali * prezzoAnimale) + prezzoCamperBs) * numNottiBs;
                    }

                    // Calcolo tassa soggiorno
                    int noTax = 0;
                    if(!Objects.equals(tfNoTax.getText(), ""))
                        noTax = Integer.parseInt(tfNoTax.getText());

                    if(numNotti <= 4)
                        totaleCampeggioConTassa = ((numAdulti * taxSoggiorno) - (noTax * taxSoggiorno)) * numNotti;
                    if(numNotti > 4)
                        totaleCampeggioConTassa = ((numAdulti * taxSoggiorno) - (noTax * taxSoggiorno)) * 4;

                    // Calcolo eventuale extra
                    float extra = 0;
                    if(!Objects.equals(tfExtra.getText(), ""))
                        extra = Float.parseFloat(tfExtra.getText());

                    // Calcolo eventuale acconto
                    float acconto = 0;
                    if(!Objects.equals(tfAcconto.getText(), ""))
                        acconto = Float.parseFloat(tfAcconto.getText());

                    // Imposta i vari totale
                    labelCalcoloTotaleCampeggio.setText("€ " + Double.toString(totaleCampeggio));
                    labelCalcoloTassaSoggiorno.setText("€ " + Double.toString(totaleCampeggioConTassa));
                    labelCalcoloTotaleTassa.setText("€ " + Double.toString(totaleCampeggio + totaleCampeggioConTassa));
                    tfTotale.setText("€ " + Float.toString((float) (totaleCampeggio + totaleCampeggioConTassa + extra - acconto)));

                // Se non sono selezionati camper o tenda
                } else {
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "Selezionare camper oppure tenda",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Print della schermata
    private void setupPrintButton() {
        btnStampa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se è stato eseguito il calcolo del totale fa screen, altrimenti errore
                if (!Objects.equals(tfTotale.getText(), "")) {

                    // Panel per inserimento nome della prenotazione
                    JPanel panel = new JPanel();
                    JLabel label = new JLabel("Inserire il nome della prenotazione:");
                    JTextField tfNomePrenotazione = new JTextField(20);

                    panel.add(label);
                    panel.add(tfNomePrenotazione);

                    // Prende il valore inserito nella textField
                    int result = JOptionPane.showConfirmDialog(MenuCampeggio.this, panel, "Nome prenotazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        String nomePrenotazione = tfNomePrenotazione.getText();

                        try {
                            Screenshot.screenShot(pnInnerForm, nomePrenotazione);
                            JOptionPane.showMessageDialog(MenuCampeggio.this,
                                    "Screenshot salvato",
                                    "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "Impossibile eseguire lo screenshot, occorre prima calcolare il totale",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Setup del button per lo sconto
    private void setupScontoButton() {
        btnSconto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(datePickerArrivo.getText(), "")){
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "Occorre prima calcolare il totale per applicare lo sconto",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Panel per la scelta dello sconto
                    JPanel panel = new JPanel();
                    JLabel label = new JLabel("Sconto da applicare: ");
                    panel.add(label);

                    // Lista dei valori di sconto da applicare
                    String[] percentualiSconto = {"5%", "10%", "15%", "20%", "30%"};

                    JComboBox<String> comboBoxSconto = new JComboBox<>(percentualiSconto);
                    panel.add(comboBoxSconto);

                    int result = JOptionPane.showConfirmDialog(MenuCampeggio.this, panel, "Sconto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        String scontoSelezionato = (String) comboBoxSconto.getSelectedItem();

                        // Rimuove il simbolo percentuale e converti in intero
                        int percentuale = Integer.parseInt(scontoSelezionato.replace("%", ""));

                        // Applica la percentuale al totale precedentemente calcolato
                        float totale = Float.parseFloat(tfTotale.getText().replace("€ ", ""));
                        float totaleScontato = totale - ((totale / 100) * percentuale);
                        tfTotale.setText(Float.toString(totaleScontato) + " (" + percentuale + "%)");
                    }
                }


            }
        });
    }
}
