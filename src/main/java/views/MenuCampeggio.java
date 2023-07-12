package views;

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
    private JPanel pnDatePickers;
    private JPanel pnDatePickerArrivo;
    private JPanel pnDatePickerPartenza;
    private JPanel pnNotti;

    private GridLayout mainGridLayout;

    private JTextField tfNumNotti;
    private JTextField tfNumAdulti;
    private JTextField tfNoTax;
    private JTextField tfNumBambini;
    private JTextField tfNumAnimali;
    private JRadioButton rbCamper;
    private JRadioButton rbTenda;
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


    public MenuCampeggio() {
        // Layout principale
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Inizializzazione pannelli
        datePickersInitializer();
        numNottiInitializer();
        //clearFormInitializer();

        add(mainPanel);
        setVisible(true);
    }

    // Inizializzazione date pickers
    private void datePickersInitializer() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel model = new UtilDateModel();

        // Date picker arrivo
        pnDatePickerArrivo = new JPanel();
        pnDatePickerArrivo.setLayout(new FlowLayout());
        JDatePanelImpl datePanelArrivo = new JDatePanelImpl(model, p);
        JDatePickerImpl datePickerArrivo = new JDatePickerImpl(datePanelArrivo, new CustomDateLabelFormatter());
        pnDatePickerArrivo.add(new JLabel("Arrivo:"));
        pnDatePickerArrivo.add(datePickerArrivo);

        // Date picker partenza
        pnDatePickerPartenza = new JPanel();
        pnDatePickerPartenza.setLayout(new FlowLayout());
        JDatePanelImpl datePanelPartenza = new JDatePanelImpl(model, p);
        JDatePickerImpl datePickerPartenza = new JDatePickerImpl(datePanelPartenza, new CustomDateLabelFormatter());
        pnDatePickerPartenza.add(new JLabel("Partenza:"));
        pnDatePickerPartenza.add(datePickerPartenza);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(pnDatePickerArrivo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(pnDatePickerPartenza, gbc);
    }

    // Inizializza panel numero notti
    private void numNottiInitializer() {
        pnNotti = new JPanel();
        pnNotti.setLayout(new FlowLayout());

        JPanel pnNumNotti = new JPanel();
        pnNumNotti.setLayout(new FlowLayout());
        pnNumNotti.add(new JLabel("N° Notti"));
        tfNumNotti = new JTextField(10);
        tfNumNotti.setEditable(false);
        pnNumNotti.add(tfNumNotti);

        JPanel pnAsBs = new JPanel();
        pnAsBs.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
        altaStagioneLabel = new JLabel("AS: ");
        bassaStagioneLabel = new JLabel("BS: ");
        pnAsBs.add(altaStagioneLabel);
        pnAsBs.add(bassaStagioneLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(pnNumNotti, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(pnAsBs, gbc);
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
            }
        });
    }

}
