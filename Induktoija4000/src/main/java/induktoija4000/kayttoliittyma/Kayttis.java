package induktoija4000.kayttoliittyma;

import induktoija4000.laskin.Laskin;
import induktoija4000.turhat.Parser;
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
            
//            Yhtalo yhtalo = new Yhtalo("");
//            yhtalo.ratkaiseToisenAsteenYhtalo(2, 5, -3);
//            
            String syote = lukija.nextLine();
            
            laskin.annaSyote(syote);
            laskin.laske();
            break;
        }
    }
}
