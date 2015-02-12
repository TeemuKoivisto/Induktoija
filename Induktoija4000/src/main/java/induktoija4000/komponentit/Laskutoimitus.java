package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Laskutoimitus implements Komponentti{
    
    private Komponentti ekaTekija;
    private Komponentti tokaTekija;
    private Termi tulos;
    private boolean supistettu;
    private char tyyppi;
    
    public Laskutoimitus(Komponentti k, char c, Komponentti k2) {
        ekaTekija = k;
        if (c=='(') { c='*'; }
        tyyppi = c;
        tokaTekija = k2;
        supistettu = false;
    }
    
    public List<Komponentti> hajotaTekijoihin() {
        List<Komponentti> sisalto = new ArrayList<>();
        
        // entä jos juuri tämä (x+1)/(x+2)
        // 
        
        // ja tässä nyt pilkotaan osiksi koko paska
        // huh huh
        // eli tämä tapahtuu vain, jos jompikumpi tekijöistä
        // on lauseke
        // eli jokainen lausekkeen jäsenistä jaetaan tai kerrotaan
        // tyypin mukaan ja paska palautetaan uuteena lausekkeena/
        // listana, jonka jokainen jäsen on jaettu tai kerrottu
        // kenties kaikki summataan kerran? helpottais...
        return sisalto;
    }
    
    public Termi muutaKomponenttiTermiksi(Komponentti k) {
        Termi t = new Termi(0,0);
        if (k.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) k;
            t = la.getTulos();
        } else if (k.onkoLauseke()) {
            Lauseke l = (Lauseke) k;
            t = l.getTulos();
        } else if (k.onkoTermi()) {
            t = (Termi) k;
        } else {
            throw new RuntimeException("se olikin summa. hups");
        }
        return t;
    }
    
    public boolean summaa(Komponentti k) {
        Termi t = muutaKomponenttiTermiksi(k);
        return tulos.summaa(t);
    }
    
    public boolean kerro(Termi ot) {
        if (supistettu) {
            return tulos.kerro(ot);
        } else {
            return ekaTekija.kerro(ot);
        }
    }
    
    public boolean jaa(Termi ot) {
        if (supistettu) {
            return tulos.jaa(ot);
        } else {
            return ekaTekija.jaa(ot);
        }
    }
    
    public boolean kerro(Komponentti k) {
        Termi t = muutaKomponenttiTermiksi(k);
        return this.tulos.kerro(t);
        // 6*6*6
        // supistettu? 6*6*6 >> 36*6 NOPE
        // 6*6*(6+x)
    }
    
    public boolean jaa(Komponentti k) {
        Termi t = muutaKomponenttiTermiksi(k);
        return this.tulos.jaa(t);
        // 6/6/6
        // ???
        // 6*6/(6+x)
    }
    
    public boolean supista() {
        if (ekaTekija.supista()) {
            if (tokaTekija.supista()) {
                if (tyyppi=='/') {
                    supistettu = ekaTekija.jaa(tokaTekija);
                } else {
                    supistettu = ekaTekija.kerro(tokaTekija);
                }
                if (supistettu) {
                    tulos = this.muutaKomponenttiTermiksi(ekaTekija);
                    return true;
                }
            }
        }
        return false;
    }
        
    public Termi getTulos() {
        if (supistettu) {
            return tulos;
        } else {
            System.out.println("laskutoimituksen " + toString() + " getTulos feilas");
            return new Termi(0, 0);
        }
    }
    
    public boolean onkoSupistettu() {
        return supistettu;
    }
    
    public String palautaTyyppi() {
        return "la";
    }
    
    public void muutaNegatiiviseksi() {
        if (supistettu) {
            tulos.muutaNegatiiviseksi();
        } else {
            ekaTekija.muutaNegatiiviseksi();
            tokaTekija.muutaNegatiiviseksi();
        }
    }
    
    public boolean onkoTermi() { return false; }
    
    public boolean onkoLaskutoimitus() { return true; }
    
    public boolean onkoLauseke() { return false; }
    
    public String toString() {
        if (supistettu) {
            return "" + tulos;
        }
        return "" + ekaTekija + tyyppi + tokaTekija;
    }
}
