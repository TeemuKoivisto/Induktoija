/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.kayttoliittyma;

import induktoija4000.laskin.Laskin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Teemu
 */
public class TapahtumanKuuntelija implements ActionListener{

    private Laskin laskin;
    private JTextArea tulos;
    private JTextField syote;
    private JButton laske;
    private JButton tyhjenna;
    private JButton lopeta;
    
    public TapahtumanKuuntelija(Laskin l, JTextArea tulos, JTextField syote, JButton laske, JButton tyhjenna, JButton lopeta) {
        laskin=l;
        this.tulos=tulos;
        this.syote=syote;
        this.laske=laske;
        this.tyhjenna=tyhjenna;
        this.lopeta=lopeta;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == laske) {
            String teksti = syote.getText();
            laskin.annaSyote(teksti);
            laskin.laske();
            String ekatulos = "";
            String tokatulos = "";
            if (laskin.getTokajuuri()!=null) {
                ekatulos += "\n      " + laskin.getEkajuuri();
                tokatulos += "\n\n      " + laskin.getTokajuuri();
            }
            tulos.setText(ekatulos + tokatulos);
        }
        if (ae.getSource() == tyhjenna) {
            tulos.setText("");
            syote.setText("");
        }
        if (ae.getSource() == lopeta) {
            System.exit(0);
        }
    }
    
}
