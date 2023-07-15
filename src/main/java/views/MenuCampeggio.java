package views;

import com.github.lgooddatepicker.components.DatePicker;
import utils.Stagione;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

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


    public MenuCampeggio() {

        // Setup textField (solo interi)
        setupTextFields();

        // Cancella il form quando si clicca su "cancella"
        clearFormOnCancel();

        // Calcolo numero di notti
        calculateNumNotti();

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
    private void clearFormOnCancel(){
        btnCancella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNumAdulti.setText("");
                tfNumBambini.setText("");
                tfNumAnimali.setText("");
                tfExtra.setText("");
                tfNoTax.setText("");
                rbCamper.setSelected(false);
                rbTenda.setSelected(false);

                //Cancella date
                datePickerArrivo.clear();
                datePickerPartenza.clear();
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

                //TODO: Mese di arrivo e mese di partenza nella stessa stagione ma in mezzo cambia la stagione
                } else if(false){

                // Mese di arrivo e mese di partenza in stagioni diverse
                } else {
                    int giornoArrivoInt = datePickerArrivo.getDate().getDayOfMonth();
                    int meseArrivoInt = datePickerArrivo.getDate().getMonth().getValue();
                    int annoArrivoInt = datePickerArrivo.getDate().getYear();

                    LocalDate initialArrivo = LocalDate.of(annoArrivoInt, meseArrivoInt, giornoArrivoInt).withDayOfMonth(1);
                    LocalDate endArrivo = initialArrivo.with(lastDayOfMonth());
                    int rangeDiGiorniArrivo = endArrivo.getDayOfMonth() - (endArrivo.getDayOfMonth() - giornoArrivoInt);

                    if(Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo)){
                        labelBs.setText("BS: " + (Integer.parseInt(tfNumNotti.getText()) - rangeDiGiorniArrivo));
                        labelAs.setText("AS: " + rangeDiGiorniArrivo);
                    } else {
                        labelBs.setText("BS: " + rangeDiGiorniArrivo);
                        labelAs.setText("AS: " + (Integer.parseInt(tfNumNotti.getText()) - rangeDiGiorniArrivo));
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
                //TODO: implementa logica di calcolo
            }
        });
    }
}
