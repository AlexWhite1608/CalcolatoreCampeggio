package views;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import utils.CustomDateLabelFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class MenuCampeggio extends JPanel {
    private JPanel mainPanel;
    private JTextField tfNumNotti;
    private JTextField tfNumAdulti;
    private JTextField tfNoTax;
    private JTextField tfNumBambini;
    private JTextField tfNumAnimali;
    private JRadioButton rbCamper;
    private JRadioButton rbTenda;
    private JLabel numNottiLabel;
    private JLabel numAdultiLabel;
    private JLabel numBambiniLabel;
    private JLabel numAnimaliLabel;
    private JLabel altaStagioneLabel;
    private JLabel bassaStagioneLabel;
    private JLabel noTaxLabel;
    private JLabel totaleCampeggioLabel;
    private JLabel taxSoggiornoLabel;
    private JLabel totaleConTassaLabel;
    private JLabel extraLabel;
    private JLabel totaleLabel;
    private JButton btnCalcola;
    private JButton btnCancella;
    private JPanel pnlButtons;
    private JTextField tfExtra;
    private JPanel pnDatePickerArrivo;
    private JPanel pnDatePickerPartenza;
    private JDatePicker dpArrivo;
    private JDatePicker dpPartenza;

    public MenuCampeggio() {

        //Inizializzazione
        datePickersInitializer();
        clearFormInitializer();

        add(mainPanel);

        setVisible(true);

    }

    //Codice per cancellare tutto il form
    private void clearFormInitializer(){
        btnCancella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNumAdulti.setText("");
                tfNumBambini.setText("");
                tfNumAnimali.setText("");
                tfExtra.setText("");
                tfNoTax.setText("");
            }
        });
    }

    //Inizializzazione date pickers
    private void datePickersInitializer(){
        pnDatePickerArrivo.setLayout(new FlowLayout());
        pnDatePickerPartenza.setLayout(new FlowLayout());
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel model = new UtilDateModel();

        JDatePanelImpl datePanelArrivo = new JDatePanelImpl(model, p);
        JDatePickerImpl datePickerArrivo = new JDatePickerImpl(datePanelArrivo, new CustomDateLabelFormatter());

        JDatePanelImpl datePanelPartenza = new JDatePanelImpl(model, p);
        JDatePickerImpl datePickerPartenza = new JDatePickerImpl(datePanelPartenza, new CustomDateLabelFormatter());

        pnDatePickerArrivo.add(new JLabel("DIOCANE"));
        pnDatePickerArrivo.add(datePickerArrivo);

        pnDatePickerPartenza.add(new JLabel("DIOMERDA"));
        pnDatePickerPartenza.add(datePickerPartenza);

        add(pnDatePickerArrivo);
        add(pnDatePickerPartenza);
    }

}
