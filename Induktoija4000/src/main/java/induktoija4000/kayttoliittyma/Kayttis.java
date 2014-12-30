package induktoija4000.kayttoliittyma;

import induktoija4000.laskin.Komponentti;
import induktoija4000.laskin.Osatekija;
import induktoija4000.laskin.turhat.Laskin;
import induktoija4000.laskin.Parser;
import induktoija4000.laskin.Yhtalo;
import java.util.Scanner;

public class Kayttis {
    
    private Laskin laskin;
    private Scanner lukija;
    
    public Kayttis() {
        laskin = new Laskin();
        lukija = new Scanner(System.in);
    }
    
    public void kaynnista() {
        while(true) {
            System.out.println("--------------");
            System.out.println("INDUKTOIJA4000");
            System.out.println("--------------");

            System.out.println("Abandon all yer hope who enter here.");
            System.out.println("INDUKTOIJA4000 is a small application for induction calculus.");
            System.out.println("Anna lauseke esim. n+3=n-12");
            System.out.println("Kayta muuttujana n:aa.");
            
            String syote = lukija.nextLine();
            /*
            laskin.annaSyote(syote);
            laskin.tulostaLauseke();
            laskin.laskeLauseke();
            laskin.tulostaLauseke();
            */
            Yhtalo yhtalo = new Yhtalo(syote);
            System.out.println("lisataan");
            yhtalo.lueKaikki();
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
//            Parser parser = new Parser();
//            System.out.println("" + parser.eval(syote));
            
//            laskin.laske();
//            tulostaTulokset();
            break;
        }
    }
}
