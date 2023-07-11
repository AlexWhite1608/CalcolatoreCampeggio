package views;

import javax.swing.*;

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

    public MenuCampeggio() {
        add(mainPanel);
        setVisible(true);

    }

}
