package views;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
                if (datePickerArrivo.getDate() != null && datePickerPartenza.getDate() != null) {
                    setNumNights();
                } else {
                    tfNumNotti.setText("");
                }
            }

            private void setNumNights() {
                LocalDate arrivo = datePickerArrivo.getDate();
                LocalDate partenza = datePickerPartenza.getDate();

                int nights = (int) ChronoUnit.DAYS.between(arrivo, partenza);
                tfNumNotti.setText(Integer.toString(nights));
            }
        };

        datePickerArrivo.addPropertyChangeListener("date", propertyChangeListener);
        datePickerPartenza.addPropertyChangeListener("date", propertyChangeListener);
    }

}
