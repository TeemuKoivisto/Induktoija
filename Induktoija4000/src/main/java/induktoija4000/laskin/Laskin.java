package induktoija4000.laskin;

import induktoija4000.komponentit.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Laskin {
    
    private Yhtalo yhtalo;
    private Lukija lukija;
    private String ekajuuri;
    private String tokajuuri;
    private StringBuilder sb;
    
    public Laskin() {
        yhtalo = new Yhtalo();
        lukija = new Lukija();
        sb = new StringBuilder();
    }
    
    public void annaSyote(String s) {
        lukija.annaSyote(s);
        yhtalo = lukija.lueKaikki();
        ekajuuri = "";
        tokajuuri = "";
        sb = new StringBuilder();
    }
    
    public void annaYhtalo(Yhtalo y) {
        yhtalo = y;
    }
    
    public boolean laske() {
        sb.append("luetaan...\n");
        sb.append(yhtalo);
        
        if (!yhtalo.getVasenpuoli().isEmpty() && yhtalo.getVasenpuoli().get(0).onkoSumma()) {
            sb.append("\n\nensimmäinen komponentti oli summa");
            sb.append("\ninduktoidaan:");
            return this.induktioLaske();
        }
        
        sb.append("\n\nsupistetaan...\n");
        yhtalo.supistaSiirtamatta();
        yhtalo.siirraKaikkiVasemmalle();
        sb.append(yhtalo);
        
        sb.append("\n\nlasketaan kaikki yhteen...\n");
        this.laskeMolemmatPuoletYhteenSiirtamatta();
        sb.append(yhtalo);
        
        sb.append("\n\njarjestetaan kaikki...\n");
        this.jarjestaKomponentit();
        sb.append(yhtalo);
        
        sb.append("\n\nratkaistaan yhtalo...\n");
        this.ratkaiseKomponenteistaKoostuvaYhtalo();
        return false;
    }
    
    public boolean induktioLaske() {
        Summa summa = (Summa) yhtalo.getVasenpuoli().get(0);
        sb.append("\n\nlasketaan induktioaskel arvolla " + summa.getAlaraja() + "...");
        boolean pateeko = this.laskeInduktioAskel(summa.getAlaraja());
        
        if (!pateeko) {
            sb.append("\n\nheh heh induktioaskel ei pätenytkään");
            return false;
        }
        sb.append("\n\ninduktio-oletus: oletetaan, että yhtalo pätee luvulla n=k, k<N"
                + "\ninduktioväitteen mukaan yhtälön on pädettävä myös luvulla k+1"
                + "\ntodistetaan, että induktioväite pätee"
                + "\n(asioiden helpottamiseksi k:ta esitetään n:lla)");
        sb.append("\n\nensin lisätään vasemmalle puolelle n + 1,"
                + "\njoka lisätään oikeaan puoleen (muuttamatta negatiiviseksi)");
        List<Komponentti> vasen = this.laskekplus1("vasen");
        
        sb.append("\n\nsitten lisätään oikealle puolelle n + 1 koskematta vasempaan");
        List<Komponentti> oikea = this.laskekplus1("oikea");
        
        sb.append("\n\njos puolet ovat samat on induktioväite tosi");
        sb.append("\n" + vasen);
        sb.append("\n" + oikea);
        pateeko = this.tarkistaOnkoPuoletSamat(vasen, oikea);
        sb.append("\nonko induktioväite tosi =" + pateeko);
        return pateeko;
    }
    
    public List<Komponentti> laskekplus1(String puoli) {
        List<Termi> sijoitus = new ArrayList<>();
        sijoitus.add(new Termi(1, 1));
        sijoitus.add(new Termi(1, 0));
        Yhtalo y = yhtalo.kopioi();
        sb.append("\n" + y);
        List<Komponentti> oikeapuoli = y.getOikeapuoli();
        if (puoli.equals("vasen")) {
            y.sijoitaListaan(sijoitus, y.getVasenpuoli());
            oikeapuoli.addAll(y.getVasenpuoli());
        } else {
            y.sijoitaListaan(sijoitus, oikeapuoli);
        }
        y.getVasenpuoli().clear();
        sb.append("\n" + y);
        y.supistaSiirtamatta();
        sb.append("\n" + y);
        this.laskeListaYhteen(y.getOikeapuoli());
        this.jarjestaLista(y.getOikeapuoli());
        sb.append("\n" + y);
        return y.getOikeapuoli();
    }
    
    public boolean laskeInduktioAskel(Termi t) {
        List<Termi> sijoitus = new ArrayList<>();
        sijoitus.add(t);
        Yhtalo y = yhtalo.kopioi();
        sb.append("\n" + y);
        y.sijoitaMuuttujantilalle(sijoitus);
        sb.append("\n" + y);
        y.supistaSiirtamatta();
        sb.append("\n" + y);
        boolean vastaus = this.tarkistaOnkoPuoletSamatMuuttamattaPuolia(y.getVasenpuoli(), y.getOikeapuoli());
        sb.append("\npätikö induktioaskel =" + vastaus);
        return vastaus;
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
            ekajuuri="n= 0";
            sb.append("\t" + ekajuuri);
        } else if (termit.size()==2) {
            this.ratkaiseKahdenTerminYhtalo();
        } else if (termit.size()==3) {
            this.ratkaiseKolmenTerminYhtalo();
        } else {
            sb.append("\nvituix men ratkaiseYhtalo koska termejä >3");
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
//        System.out.println("välitulostus ratkaiseKahdenOsatekijanYhtalo() metodissa");
//        System.out.println(yhtalo);
        
        Termi n = termit.get(0);
        Termi vakio = termit.get(1);
        vakio.muutaNegatiiviseksi(); // siirto oikealle puolelle
        vakio.jaa(n.getArvo());
        n.jaa(n.getArvo());
        
        ekajuuri = "n= " + vakio.getArvo();
        sb.append("\t" + ekajuuri);
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
//        System.out.println("välitulostus ratkaiseKolmenOsatekijanYhtalo() metodissa");
//        System.out.println(yhtalo);
        
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
            ekajuuri = "n= " + df.format(r1);
            tokajuuri = "n= " + df.format(r2);
            sb.append("metodimme laskee vastauksiksi:\n\t" + ekajuuri + "\n\t" + tokajuuri);
        } else {
            ekajuuri = "n= " + df.format(r1) + "+" + df.format(r2) + "i";
            tokajuuri = "n= " + df.format(r1) + "-" + df.format(r2) + "i";
            sb.append("metodimme laskee mahdottomiksi vastauksiksi:\n\t" + ekajuuri + "\n\t"+ tokajuuri);
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
    
    public String getEkajuuri() {
        return ekajuuri;
    }
    
    public String getTokajuuri() {
        return tokajuuri;
    }
    
    public String getTuloste() {
        return sb.toString();
    }
}
