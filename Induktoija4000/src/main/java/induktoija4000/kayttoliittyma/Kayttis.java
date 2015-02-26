package induktoija4000.kayttoliittyma;

import induktoija4000.laskin.Laskin;
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
            System.out.println("Anna lauseke esim. n*3+19=n/n-12/8");
            System.out.println("Tai induktiolause kuten:"
                    + "\nsig(n,0,n+1) = ((n+1)((n+1)+1))/2"
                    + "\nsig(n,0,n(n+1)) = (n(n+1)(n+2))/3");
            System.out.println("Käytä muuttujana n:ää.");
            
            String syote = lukija.nextLine();
            
            laskin.annaSyote(syote);
            laskin.laske();
            break;
        }
    }
}
