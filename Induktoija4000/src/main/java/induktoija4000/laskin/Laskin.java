/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.laskin;

import induktoija4000.laskin.komponentit.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author Teemu
 */
public class Laskin {
    
    private Yhtalo yhtalo;
    private Lukija lukija;
    
    public Laskin() {
        yhtalo = new Yhtalo();
        lukija = new Lukija();
    }
    
    public void annaSyote(String s) {
        lukija.annaSyote(s);
        yhtalo = lukija.lueKaikki();
    }
    
    public void laske() {
        System.out.println("lisataan");
        yhtalo.tulostaKaikki();
        yhtalo.tulostaTyypit();
        System.out.println("supistetaan");
        yhtalo.supistaKaikki();
        yhtalo.tulostaKaikki();
        yhtalo.tulostaTyypit();
        System.out.println("lasketaan kaikki yhteen");
        yhtalo.laskeYhteenKaikki();
        yhtalo.tulostaKaikki();
        yhtalo.tulostaTyypit();
        System.out.println("jarjestetaan kaikki");
        yhtalo.jarjestaYhtalo();
        yhtalo.tulostaKaikki();
        yhtalo.tulostaTyypit();
        System.out.println("lasketaan vielä kerran yhteen");
        yhtalo.laskeYhteenKaikki();
        yhtalo.tulostaKaikki();
        yhtalo.tulostaTyypit();
        System.out.println("ratkaistaan yhtalo");
        yhtalo.ratkaiseYhtalo();
        yhtalo.tulostaKaikki();
    }
    
    public void ratkaiseYhtalo() {
        List<Komponentti> vasenpuoli=yhtalo.getVasenpuoli(), oikeapuoli=yhtalo.getOikeapuoli();
        if (vasenpuoli.size()==1 && oikeapuoli.size()==1) {
            try {
                Osatekija eka = (Osatekija) oikeapuoli.get(0);
                Osatekija toka = (Osatekija) vasenpuoli.get(0);
                eka.jaa(toka.getValue());
                toka.jaa(toka.getValue());
            }
            catch (Exception e) {
                System.out.println("ratkaisu meni nenilleen");
            }
        }
        if (vasenpuoli.size()==2 && oikeapuoli.size()==1) {
            try {
                Osatekija n2 = (Osatekija) vasenpuoli.get(0);
                Osatekija n = (Osatekija) vasenpuoli.get(1);
                Osatekija vakio = (Osatekija) oikeapuoli.get(0);
                vakio.lisaaMinus();
                this.ratkaiseToisenAsteenYhtalo(n2.getValue(), n.getValue(), vakio.getValue());
            }
            catch (Exception e) {
                System.out.println("ratkaisu meni nenilleen");
            }
        } 
    }
    
    public void ratkaiseToisenAsteenYhtalo(double a, double b, double c) {
        double determinantti, r1=0, r2=0;
        boolean mahdollinen = true;
        determinantti = b*b-4*a*c;
        if (determinantti > 0) {
            r1 = (-b+Math.sqrt(determinantti))/(2*a);
            r2 = (-b-Math.sqrt(determinantti))/(2*a);
        } else if (determinantti == 0) {
            r1 = r2 = -b/(2*a);
        } else {
            r1 = -b/(2*a);
            r2 = Math.sqrt(-determinantti)/(2*a);
            mahdollinen = false;
        }
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);
        
        if (mahdollinen) {
            System.out.println("metodimme laskee vastauksiksi:\n\tr1= " + df.format(r1) + "\n\tr2= " + df.format(r2));
        } else {
            System.out.println("metodimme laskee mahdottomiksi vastauksiksi:\n\tr1= " +
            df.format(r1) + "+" + df.format(r2) +"i\n\tr2= " + df.format(r1) + "-" + df.format(r2) +"i");
        }
    }
}
