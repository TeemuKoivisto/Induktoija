package induktoija4000.laskin;

import induktoija4000.komponentit.Yhtalo;
import induktoija4000.komponentit.Termi;
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
        
        System.out.println("\nsupistetaan termeiksi...");
        boolean induktoinko = yhtalo.supistaKaikkiTermeiksi();
        System.out.println(yhtalo);
        yhtalo.tulostaTyypit();
        
        System.out.println("\nlasketaan kaikki yhteen...");
        if (induktoinko==true) {
            this.laskeYhteenKaikkiTermeina();
        } else {
            System.out.println("SUPRISE INDUKTIO");
            this.induktoi();
            return;
        }
        System.out.println(yhtalo);
        
        System.out.println("\njarjestetaan kaikki...");
        this.jarjestaTermit();
        System.out.println(yhtalo);
        
        System.out.println("\nratkaistaan yhtalo...");
        this.ratkaiseYhtalo();
        //System.out.println(yhtalo);
    }
    
    public void induktoi() {
        
    }
    
    public void ratkaiseYhtalo() {
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
        double pieninMuuttuja = termit.get(1).getVariable();
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
        vakio.jaa(n.getValue());
        n.jaa(n.getValue());
        
        System.out.println("\tn= " + vakio.getValue());
    }
    
    public void ratkaiseKolmenTerminYhtalo() {
        List<Termi> termit = yhtalo.getTermit();
        double pieninMuuttuja = termit.get(2).getVariable();
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
            termit.get(i).jaa(termit.get(0).getValue());
        }
        System.out.println("välitulostus ratkaiseKolmenOsatekijanYhtalo() metodissa");
        System.out.println(yhtalo);
        
        if (termit.get(0).getVariable()==2) {
            double n2 = termit.get(0).getValue();
            double n = termit.get(1).getValue();
            double vakio = termit.get(2).getValue();
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
        List<Termi> osatekijat = yhtalo.getTermit();
        for (int i = 0; i < osatekijat.size(); i++) {
            Termi eka = osatekijat.get(i);
            for (int j = 0; j < osatekijat.size(); j++) {
                if (i!=j && eka.summaa(osatekijat.get(j))) {
                    osatekijat.add(i, eka);
                    osatekijat.remove(i + 1);
                    osatekijat.remove(j);
                    j--;
                }
            }
        }
    }
    
    public void jarjestaTermit() {
        Collections.sort(yhtalo.getTermit(), new TermiComparator());
        // lisää tähän mahdollinen suurimman muuttujan arvolla jako?
        // nyt jakolasku tapahtuu kahdesti molemmissi ratkaiseYhtalOsatekijoilla -metodeissa
    }
    /*
    public void jarjestaYhtalo() {
        List<Komponentti> vasenpuoli=yhtalo.getVasenpuoli(), oikeapuoli=yhtalo.getOikeapuoli();
        List<Komponentti> jarjestettyoikea = jarjestaYhtalonPuoli(vasenpuoli, true);
        List<Komponentti> jarjestettyvasen = jarjestaYhtalonPuoli(oikeapuoli, false);
        vasenpuoli.addAll(jarjestettyvasen);
        oikeapuoli.addAll(jarjestettyoikea);
    }
    
    public List<Komponentti> jarjestaYhtalonPuoli(List<Komponentti> lista, boolean onkoVasenPuoli) {
        List<Komponentti> palautettava = new ArrayList<Komponentti>();
        for (int i = 0; i < lista.size(); i++) {
            Komponentti k = lista.get(i);
            if (k.getClass().equals(new Termi(0,0).getClass())) {
                Termi ot = (Termi) k;
                if ((ot.getVariable()==0 && onkoVasenPuoli) || (ot.getVariable()>0 && onkoVasenPuoli==false)) {
                    ot.muutaNegatiiviseksi();
                    palautettava.add(ot);
                    lista.remove(i);
                    i--;
                }
            }
        }
        return palautettava;
    }
    */
    
    public Yhtalo getYhtalo() {
        return yhtalo;
    }
}
