package induktoija4000.laskin;

import induktoija4000.komponentit.Yhtalo;
import induktoija4000.komponentit.Osatekija;
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
    
    public void laske() {
        System.out.println("\nluetaan");
        System.out.println(yhtalo);
        yhtalo.tulostaTyypit();
        
        System.out.println("\nsupistetaan osatekijoiksi");
        yhtalo.supistaKaikkiOsatekijoiksi();
        System.out.println(yhtalo);
        yhtalo.tulostaTyypit();
        
        System.out.println("\nlasketaan kaikki yhteen");
        this.laskeYhteenKaikkiOsatekijoina();
        System.out.println(yhtalo);
        
        System.out.println("\njarjestetaan kaikki");
        this.jarjestaOsatekijat();
        System.out.println(yhtalo);
        
        System.out.println("\nratkaistaan yhtalo");
        this.ratkaiseYhtalo();
        System.out.println(yhtalo);
    }
    
    public void ratkaiseYhtalo() {
        List<Osatekija> osatekijat = yhtalo.getOsatekijat();
        if (osatekijat.size()==2) {
            this.ratkaiseKahdenOsatekijanYhtalo();
        } else if (osatekijat.size()==3) {
            this.ratkaiseKolmenOsatekijanYhtalo();
        } else {
            System.out.println("vituix men ratkaiseYhtalo()");
        }
    }
    
    public void ratkaiseKahdenOsatekijanYhtalo() {
        List<Osatekija> osatekijat = yhtalo.getOsatekijat();
        double pieninMuuttuja = osatekijat.get(1).getVariable();
        if (pieninMuuttuja<0) {
            for (Osatekija ot : osatekijat) {
                ot.kerro(new Osatekija(1, pieninMuuttuja*-1));
            }
        } else if (pieninMuuttuja>0) {
            for (Osatekija ot : osatekijat) {
               ot.jaa(new Osatekija(1, pieninMuuttuja));
            }
        }
        this.jarjestaOsatekijat();
        System.out.println("välitulostus ratkaiseKahdenOsatekijanYhtalo() metodissa");
        System.out.println(yhtalo);
        
        Osatekija n = osatekijat.get(0);
        Osatekija vakio = osatekijat.get(1);
        vakio.jaa(n.getValue());
        n.jaa(n.getValue());
    }
    
    public void ratkaiseKolmenOsatekijanYhtalo() {
        List<Osatekija> osatekijat = yhtalo.getOsatekijat();
        double pieninMuuttuja = osatekijat.get(2).getVariable();
        if (pieninMuuttuja<0) {
            for (Osatekija ot : osatekijat) {
                ot.kerro(new Osatekija(1, pieninMuuttuja*-1));
            }
        } else if (pieninMuuttuja>0) {
            for (Osatekija ot : osatekijat) {
               ot.jaa(new Osatekija(1, pieninMuuttuja));
            }
        }
        this.jarjestaOsatekijat();
        for (int i = osatekijat.size()-1; i >= 0; i--) {
            osatekijat.get(i).jaa(osatekijat.get(0).getValue());
        }
        System.out.println("välitulostus ratkaiseKolmenOsatekijanYhtalo() metodissa");
        System.out.println(yhtalo);
        
        if (osatekijat.get(0).getVariable()==2) {
            double n2 = osatekijat.get(0).getValue();
            double n = osatekijat.get(1).getValue();
            double vakio = osatekijat.get(2).getValue();
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
    
    public void laskeYhteenKaikkiOsatekijoina() {
        List<Osatekija> osatekijat = yhtalo.getOsatekijat();
        for (int i = 0; i < osatekijat.size(); i++) {
            Osatekija eka = osatekijat.get(i);
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
    
    public void jarjestaOsatekijat() {
        Collections.sort(yhtalo.getOsatekijat(), new OsatekijaComparator());
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
            if (k.getClass().equals(new Osatekija(0,0).getClass())) {
                Osatekija ot = (Osatekija) k;
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
}
