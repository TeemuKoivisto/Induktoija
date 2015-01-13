package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Termi implements Komponentti{
    
    private Komponentti ekaTekija;
    private Komponentti tokaTekija;
    private Osatekija tulos;
    private boolean supistettu;
    private char tyyppi;
    
    public Termi(Komponentti k, char c, Komponentti k2) {
        ekaTekija = k;
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
    
    public boolean summaa(Osatekija ot) {
        if (supistettu) {
            return tulos.summaa(ot);
        }
        return false;
    }
    
    public boolean kerro(Osatekija ot) {
        if (supistettu) {
            return tulos.kerro(ot);
        } else {
            return ekaTekija.kerro(ot);
        }
    }
    
    public boolean jaa(Osatekija ot) {
        if (supistettu) {
            return tulos.jaa(ot);
        } else {
            return ekaTekija.jaa(ot);
        }
    }
    
    public boolean kerro(Komponentti k) {
        if (k.onkoOsatekija()) {
            // 3*3*3 >> 3*3 termi kertaa osatekija 3
            return kerro((Osatekija) k);
        }
        if (k.onkoTermi()) {
            // tarkoittaa nyt, että termi on supistettu
            // 3*3*3 >> 9*3 >> termi * termi
            Termi t = (Termi) k;
            if (t.supista()) {
                kerro(t.getTulos());
            } else {
                return false;
            }
            
        }
        if (k.onkoLauseke()) {
            // 4*3(34x+3)
            Lauseke l = (Lauseke) k;
            boolean onnistuiko = true;
            for (Komponentti kom : l.getLauseke()) {
                if (ekaTekija.kerro(kom)==false) {
                    onnistuiko = false;
                }
            }
            return onnistuiko;
        }
        return false;
    }
    
    public boolean jaa(Komponentti k) {
        if (k.onkoOsatekija()) {
            // 3*3/3
            return jaa((Osatekija) k);
        }
        if (k.onkoTermi()) {
            // tarkoittaa nyt, että termi on supistettu
            // 3*3/3 >> 9/3 >> termi / termi
            Termi t = (Termi) k;
            if (t.supista()) {
                jaa(t.getTulos());
            } else {
                return false;
            }
            
        }
        if (k.onkoLauseke()) {
            // 4*3/(34x+3)
            Lauseke l = (Lauseke) k;
            boolean onnistuiko = true;
            for (Komponentti kom : l.getLauseke()) {
                if (ekaTekija.jaa(kom)==false) {
                    onnistuiko = false;
                }
            }
            return onnistuiko;
        }
        return false;
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
                    if (ekaTekija.onkoOsatekija()) { tulos = (Osatekija) ekaTekija; }
                    else if (ekaTekija.onkoTermi()) { Termi t = (Termi) ekaTekija; tulos = t.getTulos(); }
                    else { Lauseke l = (Lauseke) ekaTekija; tulos = l.getTulos(); }
                    return true;
                }
            }
        }
        return false;
    }
        
    public Osatekija getTulos() {
        if (supistettu) {
            return tulos;
        } else {
            System.out.println("termin " + toString() + " getTulos feilas");
            return new Osatekija(0, 0);
        }
    }
    
    public boolean onkoSupistettu() {
        return supistettu;
    }
    
    public String palautaTyyppi() {
        return "t";
    }
    
    public void muutaNegatiiviseksi() {
        if (supistettu) {
            tulos.muutaNegatiiviseksi();
        } else {
            ekaTekija.muutaNegatiiviseksi();
            tokaTekija.muutaNegatiiviseksi();
        }
    }
    
    public boolean onkoOsatekija() { return false; }
    
    public boolean onkoTermi() { return true; }
    
    public boolean onkoLauseke() { return false; }
    
    public String toString() {
        if (supistettu) {
            return "" + tulos;
        }
        return "" + ekaTekija + tyyppi + tokaTekija;
    }
}
