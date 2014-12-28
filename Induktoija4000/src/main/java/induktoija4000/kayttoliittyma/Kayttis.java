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
            System.out.println(yhtalo.getClass());
            yhtalo.lisaaKaikki();
            yhtalo.tulostaKaikki();
            yhtalo.supistaKaikki();
            yhtalo.tulostaKaikki();
            
//            Parser parser = new Parser();
//            System.out.println("" + parser.eval(syote));
            
//            laskin.laske();
//            tulostaTulokset();
            break;
        }
    }
}
