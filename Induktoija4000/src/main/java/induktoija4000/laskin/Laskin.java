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
    
    public void lyhempiLaske() {
        System.out.println("\nluetaan...");
        System.out.println(yhtalo);
        yhtalo.tulostaTyypit();
        
        if (yhtalo.getVasenpuoli().get(0).onkoSumma()) {
            this.induktioLaske();
            return;
        }
        
        System.out.println("\nsupistetaan...");
        yhtalo.supistaSiirtamatta();
        yhtalo.siirraKaikkiVasemmalle();
        System.out.println(yhtalo);
        yhtalo.tulostaTyypit();
        
        System.out.println("\nlasketaan kaikki yhteen...");
        this.laskeMolemmatPuoletYhteenSiirtamatta();
        System.out.println(yhtalo);
        
        System.out.println("\njarjestetaan kaikki...");
        this.jarjestaKomponentit();
        System.out.println(yhtalo);
        
        System.out.println("\nratkaistaan yhtalo...");
        this.ratkaiseKomponenteistaKoostuvaYhtalo();
        System.out.println(yhtalo);
    }
    
    public void induktioLaske() {
        System.out.println("\nlasketaan induktioaskel arvolla 0...");
        this.induktioAskelNollalla();
        System.out.println(yhtalo);
        
        System.out.println("\njatketaan vaikkei tarkistettu pätikö...");
        System.out.println("ensin lisätään vasemmalle puolelle n + 1, joka siirretään oikealle puolelle");
        List<Komponentti> vasen = this.laskekplus1("vasen");
        
        System.out.println("\nsitten lasketaan oikeapuoli");
        List<Komponentti> oikea = this.laskekplus1("oikea");
        
        System.out.println("\njos sitten vasen ja oikea ovat samat niin gz. induktio successfull");
        System.out.println(vasen);
        System.out.println(oikea);
        System.out.println(this.tarkistaOnkoPuoletSamat(vasen, oikea));
//        System.out.println(this.tarkistaOnkoPuoletSamatMuuttamattaPuolia(vasen, oikea));
    }
    
    public List<Komponentti> laskekplus1(String puoli) {
        List<Termi> sijoitus = new ArrayList<>();
        sijoitus.add(new Termi(1, 1));
        sijoitus.add(new Termi(1, 0));
        Yhtalo y = yhtalo.kopioi();
        System.out.println(y);
        List<Komponentti> oikeapuoli = y.getOikeapuoli();
        if (puoli.equals("vasen")) {
            y.sijoitaListaan(sijoitus, y.getVasenpuoli());
            oikeapuoli.addAll(y.getVasenpuoli());
        } else {
            y.sijoitaListaan(sijoitus, oikeapuoli);
        }
        y.annaVasenPuoli(new ArrayList<Komponentti>());
        y.supistaSiirtamatta();
        this.laskeListaYhteen(y.getOikeapuoli());
        this.jarjestaLista(y.getOikeapuoli());
        System.out.println(y);
        return y.getOikeapuoli();
    }
    
    public void induktioAskelNollalla() {
        List<Termi> sijoitus = new ArrayList<>();
        sijoitus.add(new Termi(0, 0));
        Yhtalo y = yhtalo.kopioi();
        System.out.println(y);
        y.sijoitaMuuttujantilalle(sijoitus);
        y.supistaSiirtamatta();
        System.out.println("onko puolet samat =" + this.tarkistaOnkoPuoletSamatMuuttamattaPuolia(y.getVasenpuoli(), y.getOikeapuoli()));
        System.out.println(y);
    }
    
    public List<Komponentti> laskeListaYhteen(List<Komponentti> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Komponentti eka = lista.get(i);
            for (int j = 0; j < lista.size(); j++) {
                Komponentti toka = lista.get(j);
                if (i!=j && eka.summaa(toka)) {
                    lista.remove(j);
                    j--;
                }
            }
            // poistetaan tyhjät
            if (eka.onkoTermi()) {
                Termi t = (Termi) eka;
                if (t.getArvo()==0) {
                    lista.remove(i);
                    i--;
                }
            }
        }
        return lista;
    }
    
    public void laskeMolemmatPuoletYhteenSiirtamatta() {
        this.laskeListaYhteen(yhtalo.getVasenpuoli());
        this.laskeListaYhteen(yhtalo.getOikeapuoli());
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
//            induktoi();
        } else if (jakamatonlaskutoimitus) {
            //kerro kunnes paska on tasan
        } else {
            yhtalo.supistaSiirtamatta();
            yhtalo.supistaPuoletYhteenTermeina();
            this.jarjestaTermit();
            this.ratkaiseTermeistaKoostuvaYhtalo();
        }
    }
    
    public void ratkaiseTermeistaKoostuvaYhtalo() {
        List<Termi> termit = yhtalo.getTermit();
        if (termit.size()==0 || termit.size()==1) {
            System.out.println("\tn= 0");
        } else if (termit.size()==2) {
            this.ratkaiseKahdenTerminYhtalo();
        } else if (termit.size()==3) {
            this.ratkaiseKolmenTerminYhtalo();
        } else {
            System.out.println("vituix men ratkaiseYhtalo()");
            System.out.println("termejä >3");
        }
    }
    
    public void ratkaiseKahdenTerminYhtalo() {
        // pitää ottaa huomioon jos kaikki muuttujia niin
        // r1 = 0
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
    
//    public void laskeYhteenKaikkiTermeina() {
//        List<Termi> termit = yhtalo.getTermit();
//        for (int i = 0; i < termit.size(); i++) {
//            Termi eka = termit.get(i);
//            for (int j = 0; j < termit.size(); j++) {
//                if (i!=j && eka.summaa(termit.get(j))) {
//                    termit.add(i, eka);
//                    termit.remove(i + 1);
//                    termit.remove(j);
//                    j--;
//                }
//            }
//        }
//        // poistetaan tyhjät termit
//        for (int i = 0; i < termit.size(); i++) {
//            Termi eka = termit.get(i);
//            if (eka.getArvo()==0) {
//                termit.remove(i);
//            }
//        }
//    }
    
    public boolean tarkistaOnkoPuoletSamat(List<Komponentti> ekapuoli, List<Komponentti> tokapuoli) {
        for (int i = 0; i < ekapuoli.size(); i++) {
            Komponentti eka = ekapuoli.get(i);
            for (int j = 0; j < tokapuoli.size(); j++) {
                Komponentti toka = tokapuoli.get(j);
                if (eka.onkoSamanArvoinen(toka)) {
                    ekapuoli.remove(i);
                    tokapuoli.remove(j);
                    i--;
                    j--;
                }
            }
        }
        return ekapuoli.isEmpty() && tokapuoli.isEmpty();
    }
    
    public boolean tarkistaOnkoPuoletSamatMuuttamattaPuolia(List<Komponentti> ekapuoli, List<Komponentti> tokapuoli) {
        List<Komponentti> ekavalmis = new ArrayList<>();
        List<Komponentti> tokavalmis = new ArrayList<>();
        for (int i = 0; i < ekapuoli.size(); i++) {
            Komponentti eka = ekapuoli.get(i);
            for (int j = 0; j < tokapuoli.size(); j++) {
                Komponentti toka = tokapuoli.get(j);
                if (eka.onkoSamanArvoinen(toka)) {
                    ekavalmis.add(eka);
                    tokavalmis.add(toka);
                    ekapuoli.remove(i);
                    tokapuoli.remove(j);
                    i--;
                    j--;
                }
            }
        }
        boolean onko = ekapuoli.isEmpty() && tokapuoli.isEmpty();
        ekapuoli.addAll(ekavalmis);
        tokapuoli.addAll(tokavalmis);
        return onko;
    }
    
    public void jarjestaLista(List<Komponentti> lista) {
        Collections.sort(lista, new KomponenttiComparator());
    }
    
    public void jarjestaKomponentit() {
        Collections.sort(yhtalo.getVasenpuoli(), new KomponenttiComparator());
    }
    
    public void jarjestaTermit() {
        Collections.sort(yhtalo.getTermit(), new TermiComparator());
    }
    
    public Yhtalo getYhtalo() {
        return yhtalo;
    }
}
