package induktoija4000.main;

import induktoija4000.kayttoliittyma.Kayttis;
import induktoija4000.komponentit.*;

public class App {

    public static void main(String[] args) {
//        testaamista();
        Kayttis ohjelma = new Kayttis();
        ohjelma.kaynnista();
    }

    public static void testaamista() {
        Lauseke lauseke = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        lauseke.lisaa(muuttuja);
        lauseke.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(lauseke, '*', lauseke);
        la.supista();
        // (n^2+2n+1)
        Lauseke vastaus = (Lauseke) la.getEkatekija();
//        assertTrue(vastaus.toString().equals("(n^2.0 + 2.0n + 1.0)"));
    }
}
