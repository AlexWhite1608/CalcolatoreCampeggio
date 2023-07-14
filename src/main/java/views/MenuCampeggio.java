package views;

import com.github.lgooddatepicker.components.DatePicker;
import utils.Stagione;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
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

        // Inizializzazione button di cancellazione del form
        clearFormInitializer();

        // Calcolo numero di notti
        calculateNumNotti();

        add(mainPanelCampeggio);
        setVisible(true);
    }


    // Codice per cancellare tutto il form
    private void clearFormInitializer(){
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
                } else {    // Errore selezione date

                    //FIXME: fai una funzione apposta che resetta tutto!
                    tfNumNotti.setText("");
                    datePickerArrivo.closePopup();
                    datePickerPartenza.closePopup();
                    datePickerPartenza.setText("");


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
                    // Verifico la stagione
                    if(Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo))
                        labelBs.setText("BS: " + tfNumNotti.getText());
                    else
                        labelAs.setText("AS: " + tfNumNotti.getText());
                } else {
                    int giornoArrivoInt = datePickerArrivo.getDate().getDayOfMonth();
                    int meseArrivoInt = datePickerArrivo.getDate().getMonth().getValue();
                    int annoArrivoInt = datePickerArrivo.getDate().getYear();

                    LocalDate initialArrivo = LocalDate.of(annoArrivoInt, meseArrivoInt, giornoArrivoInt).withDayOfMonth(1);
                    LocalDate endArrivo = initialArrivo.with(lastDayOfMonth());
                    int rangeDiGiorniArrivo = endArrivo.getDayOfMonth() - giornoArrivoInt;

                    if(Arrays.asList(Stagione.BassaStagione.getMesi()).contains(meseArrivo)){
                        labelBs.setText("BS: " + rangeDiGiorniArrivo);
                        labelAs.setText("AS: " + (Integer.parseInt(tfNumNotti.getText()) - rangeDiGiorniArrivo));
                    } else {
                        labelBs.setText("BS: " + (Integer.parseInt(tfNumNotti.getText()) - rangeDiGiorniArrivo));
                        labelAs.setText("AS: " + rangeDiGiorniArrivo);
                    }
                }
            }
        };

        datePickerArrivo.addPropertyChangeListener("date", propertyChangeListener);
        datePickerPartenza.addPropertyChangeListener("date", propertyChangeListener);
    }

}
