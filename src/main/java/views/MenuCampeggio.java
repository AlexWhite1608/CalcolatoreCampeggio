package views;

import com.github.lgooddatepicker.components.DatePicker;
import utils.Screenshot;
import utils.Stagione;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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


    public MenuCampeggio() throws IOException {
        // Setup textField (solo interi)
        setupTextFields();

        // Setup button per stampare schermata
        setupPrintButton();

        // Cancella il form quando si clicca su "cancella"
        clearFormOnCancel();

        // Calcolo numero di notti
        calculateNumNotti();

        // Calcolo totale
        calculateTotal();

        add(mainPanelCampeggio);
        setVisible(true);
    }

    // Setup textField
    private void setupTextFields() {

        // Permette solo numeri interi
        DocumentFilter numberFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        ((AbstractDocument) tfNumAdulti.getDocument()).setDocumentFilter(numberFilter);
        ((AbstractDocument) tfNoTax.getDocument()).setDocumentFilter(numberFilter);
        ((AbstractDocument) tfNumBambini.getDocument()).setDocumentFilter(numberFilter);
        ((AbstractDocument) tfNumAnimali.getDocument()).setDocumentFilter(numberFilter);
        ((AbstractDocument) tfExtra.getDocument()).setDocumentFilter(numberFilter);
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

                // Controllo di aver inserito le date
                if(Objects.equals(datePickerArrivo.getText(), "") || Objects.equals(datePickerArrivo.getText(), ""))
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "Inserire le date!",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);

                // Controllo di aver inserito almeno un valore
                if(Objects.equals(tfNumAdulti.getText(), "") && Objects.equals(tfNumBambini.getText(), "") && Objects.equals(tfNumAnimali.getText(), "")){
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "Inserire il numero degli ospiti!",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Totali
                float totaleCampeggio = 0;
                float totaleCampeggioConTassa = 0;

                //TODO: Ricava i prezzi
                float prezzoCamperAs = 16;
                float prezzoCamperBs = 13;
                float prezzoTendaAs = 12;
                float prezzoTendaBs = 9;
                float prezzoAdultoAs = 10;
                float prezzoAdultoBs = 8;
                float prezzoBambinoAs = 6;
                float prezzoBambinoBs = 4.5F;
                float prezzoAnimale = 3;
                float taxSoggiorno = 1.5F;

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
                } else {
                    JOptionPane.showMessageDialog(MenuCampeggio.this,
                            "Selezionare camper oppure tenda",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Calcolo tassa soggiorno
                int noTax = 0;
                if(!Objects.equals(tfNoTax.getText(), ""))
                    noTax = Integer.parseInt(tfNoTax.getText());

                if(numAdulti <= 4)
                    totaleCampeggioConTassa = ((numAdulti * taxSoggiorno) - (noTax * taxSoggiorno)) * numNotti;
                if(numNotti > 4)
                    totaleCampeggioConTassa = ((numAdulti * taxSoggiorno) - (noTax * taxSoggiorno)) * 4;

                // Calcolo eventuale extra
                int extra = 0;
                if(!Objects.equals(tfExtra.getText(), ""))
                    extra = Integer.parseInt(tfExtra.getText());

                // Imposta i vari totale
                labelCalcoloTotaleCampeggio.setText(Float.toString(totaleCampeggio));
                labelCalcoloTassaSoggiorno.setText(Float.toString(totaleCampeggioConTassa));
                labelCalcoloTotaleTassa.setText(Float.toString(totaleCampeggio + totaleCampeggioConTassa));
                tfTotale.setText(Float.toString(totaleCampeggio + totaleCampeggioConTassa + extra));
            }
        });
    }

    // Print della schermata
    private void setupPrintButton() throws IOException {
        btnStampa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

                    // Se è stato eseguito il calcolo del totale fa screen, altrimenti errore
                    if (!Objects.equals(tfTotale.getText(), "")) {
                        try {
                            Screenshot.screenShot(pnInnerForm, nomePrenotazione);
                            JOptionPane.showMessageDialog(MenuCampeggio.this,
                                    "Screenshot salvato",
                                    "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(MenuCampeggio.this,
                                "Impossibile eseguire lo screenshot, occorre prima calcolare il totale",
                                "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

}
