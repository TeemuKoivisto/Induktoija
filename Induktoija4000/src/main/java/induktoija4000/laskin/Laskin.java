package induktoija4000.laskin;

import induktoija4000.komponentit.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    
    public void annaYhtalo(Yhtalo y) {
        yhtalo = y;
    }
    
    public void laske() {
        System.out.println("\nluetaan...");
        System.out.println(yhtalo);
        yhtalo.tulostaTyypit();
        
        System.out.println("\nsupistetaan...");
        boolean induktoinko = yhtalo.supista();
        System.out.println(yhtalo);
        yhtalo.tulostaTyypit();
        
        System.out.println("\nlasketaan kaikki yhteen...");
        if (induktoinko==true) {
            this.laskeYhteenKaikkiTermeina();
        } else {
            System.out.println("SUPRISE INDUKTIO");
            this.laskeYhteenKaikkiKomponentteina();
            System.out.println(yhtalo);
            System.out.println("\njarjestetaan kaikki...");
            this.jarjestaKomponentit();
            System.out.println(yhtalo);
            this.ratkaiseKomponenteistaKoostuvaYhtalo();
            return;
        }
        System.out.println(yhtalo);
        
        System.out.println("\njarjestetaan kaikki...");
        this.jarjestaTermit();
        System.out.println(yhtalo);
        
        System.out.println("\nratkaistaan yhtalo...");
        this.ratkaiseTermeistaKoostuvaYhtalo();
        //System.out.println(yhtalo);
    }
    
    public void induktoi() {
        // laskeInduktioAskel(Summa summa)
        // pura eri komponenteista koostuva yhtalo osiin
        // jotka sitten kerrot/jaat tarpeen mukaan
        // kunnes jäljellä on pelkkä kaunis ratkaisu
    }
    
    public void laskeInduktioAskel(Summa summa) {
        int alku = summa.getAlaraja();
        
    }
    
    public void laskeYhteenKaikkiKomponentteina() {
        for (int i = 0; i < yhtalo.getVasenpuoli().size(); i++) {
            Komponentti eka = yhtalo.getVasenpuoli().get(i);
            for (int j = 0; j < yhtalo.getVasenpuoli().size(); j++) {
                Komponentti toka = yhtalo.getVasenpuoli().get(j);
                if (i!=j && eka.summaa(toka)) {
                    yhtalo.getVasenpuoli().remove(j);
                }
            }
        }
        // poistetaan tyhjät termit
        for (int i = 0; i < yhtalo.getVasenpuoli().size(); i++) {
            Komponentti eka = yhtalo.getVasenpuoli().get(i);
            if (eka.onkoTermi()) {
                Termi t = (Termi) eka;
                if (t.getArvo()==0) {
                    yhtalo.getVasenpuoli().remove(i);
                }
            }
        }
    }
    
    public void ratkaiseKomponenteistaKoostuvaYhtalo() {
        List<Komponentti> vasenpuoli = yhtalo.getVasenpuoli();
        boolean summa = false;
        boolean jakamatonlaskutoimitus = false;
        for (int i = 0; i < vasenpuoli.size(); i++) {
            if (vasenpuoli.get(i).onkoSumma()) {
                summa = true;
            } else if (vasenpuoli.get(i).onkoLaskutoimitus()) {
                jakamatonlaskutoimitus = true;
            }
        }
        if (summa) {
            // jos sisaltaa jakamattoman laskutoimituksen?? eee
            induktoi();
        } else if (jakamatonlaskutoimitus) {
            //kerro kunnes paska on tasan
        } else {
            yhtalo.supista();
            this.jarjestaTermit();
            this.ratkaiseTermeistaKoostuvaYhtalo();
        }
    }
    
    public void ratkaiseTermeistaKoostuvaYhtalo() {
        List<Termi> termit = yhtalo.getTermit();
        if (termit.size()==2) {
            this.ratkaiseKahdenTerminYhtalo();
        } else if (termit.size()==3) {
            this.ratkaiseKolmenTerminYhtalo();
        } else {
            System.out.println("vituix men ratkaiseYhtalo()");
            System.out.println("termejä joko 1 tai >3");
        }
    }
    
    public void ratkaiseKahdenTerminYhtalo() {
        List<Termi> termit = yhtalo.getTermit();
        double pieninMuuttuja = termit.get(1).getMuuttuja();
        if (pieninMuuttuja<0) {
            for (Termi ot : termit) {
                ot.kerro(new Termi(1, pieninMuuttuja*-1));
            }
        } else if (pieninMuuttuja>0) {
            for (Termi ot : termit) {
               ot.jaa(new Termi(1, pieninMuuttuja));
            }
        }
        this.jarjestaTermit();
        System.out.println("välitulostus ratkaiseKahdenOsatekijanYhtalo() metodissa");
        System.out.println(yhtalo);
        
        Termi n = termit.get(0);
        Termi vakio = termit.get(1);
        vakio.muutaNegatiiviseksi(); // siirto oikealle puolelle
        vakio.jaa(n.getArvo());
        n.jaa(n.getArvo());
        
        System.out.println("\tn= " + vakio.getArvo());
    }
    
    public void ratkaiseKolmenTerminYhtalo() {
        List<Termi> termit = yhtalo.getTermit();
        double pieninMuuttuja = termit.get(2).getMuuttuja();
        if (pieninMuuttuja<0) {
            for (Termi t : termit) {
                t.kerro(new Termi(1, pieninMuuttuja*-1));
            }
        } else if (pieninMuuttuja>0) {
            for (Termi t : termit) {
               t.jaa(new Termi(1, pieninMuuttuja));
            }
        }
        this.jarjestaTermit();
        for (int i = termit.size()-1; i >= 0; i--) {
            termit.get(i).jaa(termit.get(0).getArvo());
        }
        System.out.println("välitulostus ratkaiseKolmenOsatekijanYhtalo() metodissa");
        System.out.println(yhtalo);
        
        if (termit.get(0).getMuuttuja()==2) {
            double n2 = termit.get(0).getArvo();
            double n = termit.get(1).getArvo();
            double vakio = termit.get(2).getArvo();
            this.ratkaiseToisenAsteenYhtalo(n2,n,vakio);
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
        df.setMaximumFractionDigits(6);
        
        if (mahdollinen) {
            System.out.println("metodimme laskee vastauksiksi:\n\tr1= " + df.format(r1) + "\n\tr2= " + df.format(r2));
        } else {
            System.out.println("metodimme laskee mahdottomiksi vastauksiksi:\n\tr1= " +
            df.format(r1) + "+" + df.format(r2) +"i\n\tr2= " + df.format(r1) + "-" + df.format(r2) +"i");
        }
    }
    
    public void laskeYhteenKaikkiTermeina() {
        List<Termi> termit = yhtalo.getTermit();
        for (int i = 0; i < termit.size(); i++) {
            Termi eka = termit.get(i);
            for (int j = 0; j < termit.size(); j++) {
                if (i!=j && eka.summaa(termit.get(j))) {
                    termit.add(i, eka);
                    termit.remove(i + 1);
                    termit.remove(j);
                    j--;
                }
            }
        }
        // poistetaan tyhjät termit
        for (int i = 0; i < termit.size(); i++) {
            Termi eka = termit.get(i);
            if (eka.getArvo()==0) {
                termit.remove(i);
            }
        }
    }
    
    public void jarjestaKomponentit() {
        Collections.sort(yhtalo.getVasenpuoli(), new KomponenttiComparator());
    }
    
    public void jarjestaTermit() {
        Collections.sort(yhtalo.getTermit(), new TermiComparator());
        // lisää tähän mahdollinen suurimman muuttujan arvolla jako?
        // nyt jakolasku tapahtuu kahdesti molemmissi ratkaiseYhtalOsatekijoilla -metodeissa
    }
    
    public Yhtalo getYhtalo() {
        return yhtalo;
    }
}
