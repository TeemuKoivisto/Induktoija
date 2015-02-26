package induktoija4000.main;

import induktoija4000.kayttoliittyma.Kayttis;
import induktoija4000.komponentit.*;
import induktoija4000.laskin.Laskin;
import java.util.Scanner;

public class App {

    public static Laskin laskin = new Laskin();
    
    public static void main(String[] args) {
//        testaamista();
        Kayttis ohjelma = new Kayttis();
        ohjelma.kaynnista();
    }
    
    public static void testaamista() {
        laskin.annaSyote("6*6 + 6/6 + 6*(6+6) + 6/(6+6) + (6+6)*6 + (6+6)/6 + (6+6)(6+6) + (6+6)/(6+6) + 6*6*6 + 6/6/6 + 6*6*(6+6) + 6*6/(6+6) = n");
        // t*t + t/t + t*l + t/l + l*t + l/t + l*l + l/l + la*t + la/t + la*l + la/l
        laskin.laske();
        Termi muuttuja = laskin.getYhtalo().getTermit().get(0);
        Termi vakio = laskin.getYhtalo().getTermit().get(1);
        // vastaus = n=979.666666666666
        boolean eka = muuttuja.onkoSamanArvoinen(new Termi(1, 1));
        boolean toka = vakio.onkoSamanArvoinen(new Termi(979.6666666666666, 0));
    }
}
