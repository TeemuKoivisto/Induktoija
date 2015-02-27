/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.kayttoliittyma;

import induktoija4000.laskin.Laskin;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Teemu
 */
public class Grafiikka implements Runnable{

    private JFrame ruutu;
    private Laskin laskin;
    private int leveys;
    private int korkeus;
    
    public Grafiikka(int leveys, int korkeus, Laskin l) {
        this.leveys=leveys;
        this.korkeus=korkeus;
        this.laskin=l;
    }
    
    @Override
    public void run() {
        ruutu = new JFrame("Induktoija4000");
        ruutu.setPreferredSize(new Dimension(leveys, korkeus));
        
        ruutu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        this.luoKomponentit(ruutu.getContentPane());
        
        ruutu.pack();
        ruutu.setVisible(true);
    }
    
    public void luoKomponentit(Container container) {
        GridLayout layout = new GridLayout(1, 2, 2, 0);
        container.setLayout(layout);
        
        JTextArea tulos = new JTextArea("");
        tulos.setEnabled(true);
        JTextField syote = new JTextField("");
        
        JPanel nappulapuoli = new JPanel(new GridLayout(3, 1));
        JPanel namikat = new JPanel(new GridLayout(1, 3));
        JButton laske = new JButton("laske");
        JButton tyhjenna = new JButton("tyhjenna");
        JButton lopeta = new JButton("lopeta");
        
        namikat.add(laske);
        namikat.add(tyhjenna);
        namikat.add(lopeta);
        
        JTextArea isotulos = new JTextArea("");
        TapahtumanKuuntelija kuuntelija = new TapahtumanKuuntelija(laskin, tulos, isotulos, syote, laske, tyhjenna, lopeta);
        
        laske.addActionListener(kuuntelija);
        tyhjenna.addActionListener(kuuntelija);
        lopeta.addActionListener(kuuntelija);
        
//        Ikkuna ikkuna = new Ikkuna(laskin);
        
        nappulapuoli.add(tulos);
        nappulapuoli.add(syote);
        nappulapuoli.add(namikat);
        
        container.add(nappulapuoli);
//        container.add(ikkuna);
        container.add(isotulos);
    }
}
