package views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import utils.SampleHighlightPolicy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Inizializzazione
        createUIComponents();
        clearFormInitializer();

        add(mainPanelCampeggio);
        setVisible(true);
    }

    // Inizializza i datepickers
    //TODO: IMPLEMENTA HIGHLIGHT DELLA DATA ODIERNA
    private void initializeDatePickers(){
        DatePickerSettings dateSettingsArrivo = new DatePickerSettings();
        DatePickerSettings dateSettingsPartenza = new DatePickerSettings();

        dateSettingsArrivo.setHighlightPolicy(new SampleHighlightPolicy());
        dateSettingsPartenza.setHighlightPolicy(new SampleHighlightPolicy());

        datePickerArrivo = new DatePicker(dateSettingsArrivo);
        datePickerPartenza = new DatePicker(dateSettingsPartenza);
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

    private void createUIComponents() {
        initializeDatePickers();
    }
}
