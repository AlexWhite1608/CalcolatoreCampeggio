package views;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

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
    private JLabel labelTotale;
    private JPanel pnButtons;
    private JPanel pnInnerForm;


    public MenuCampeggio() {

        // Inizializzazione pannelli
        //clearFormInitializer();

        add(mainPanelCampeggio);
        setVisible(true);
    }

    // Codice per cancellare tutto il form
//    private void clearFormInitializer(){
//        btnCancella.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                tfNumAdulti.setText("");
//                tfNumBambini.setText("");
//                tfNumAnimali.setText("");
//                tfExtra.setText("");
//                tfNoTax.setText("");
//            }
//        });
//    }

}
