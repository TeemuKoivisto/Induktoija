package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Laskutoimitus implements Komponentti {

    private Komponentti ekaTekija;
    private Komponentti tokaTekija;
    private boolean supistettu;
    private boolean supistettuOnnistuneesti;
    private boolean jaettu;
    private char tyyppi;

    public Laskutoimitus(Komponentti k, char c, Komponentti k2) {
        ekaTekija = k;
        if (c == '(' || c=='*') {
            c = '*';
            jaettu = true;
        } else {
            jaettu = false;
        }
        tyyppi = c;
        tokaTekija = k2;
        supistettu = false;
    }

    public boolean supista() {
        this.supistaTekijat();
        if (tyyppi=='/') {
            // 4/(6/(n+1)) >> 4(n+1)/6
            if (ekaTekija.onkoTermi() && tokaTekija.onkoLaskutoimitus()) {
                Termi ekaT = (Termi) ekaTekija;
                Laskutoimitus la = (Laskutoimitus) tokaTekija;
                tokaTekija = la.getEkatekija().kopioi();
                ekaTekija = la.getTokatekija();
                ekaTekija.kerro(ekaT);
            } else {
                jaettu = ekaTekija.jaa(tokaTekija);
            }
        } else {
            // käännetään jos kerrotaan Termia Lausekkeella. Termi kun ei osaa palauttaa lauseketta...
            if (ekaTekija.onkoTermi() && (tokaTekija.onkoLauseke() || tokaTekija.onkoLaskutoimitus())) {
                Komponentti ekaT = ekaTekija;
                ekaTekija = tokaTekija;
                tokaTekija = ekaT;
            }
            ekaTekija.kerro(tokaTekija);
        }
        supistettu = true;
        // pitäisi estää typeriä supistusvirheitä
        if (jaettu) {
            tokaTekija = new Termi(1, 0);
        }
        // true jos supistettu yhdeksi termiksi
        return jaettu && ekaTekija.palautaTulosListana().size()==1 && !ekaTekija.onkoLaskutoimitus();
    }

    public void supistaTekijat() {
        ekaTekija.supista();
        tokaTekija.supista();
        // korvaa supistetut laskutoimitukset termeilla
        if (ekaTekija.palautaTulosListana().size()==1) ekaTekija=ekaTekija.palautaTulosListana().get(0);
        if (tokaTekija.palautaTulosListana().size()==1) tokaTekija=tokaTekija.palautaTulosListana().get(0);
    }
    
    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista) {
        Lauseke eka = ekaTekija.sijoitaMuuttujanTilalle(lista);
        Lauseke toka = tokaTekija.sijoitaMuuttujanTilalle(lista);
        if (!eka.onkoTyhja()) {
            ekaTekija = eka;
        }
        if (!toka.onkoTyhja()) {
            tokaTekija = toka;
        }
        return new Lauseke();
    }
    
    public boolean summaa(Komponentti k) {
        if (supistettu && jaettu) {
            return ekaTekija.summaa(k);
        }
        return false;
    }

    public boolean kerro(Termi t) {
        return ekaTekija.kerro(t);
    }

    public boolean jaa(Termi t) {
        if (supistettu && jaettu) {
            return ekaTekija.jaa(t);
        }
        if (supistettu) {
            return tokaTekija.kerro(t);
        }
        return false;
    }

    public boolean kerro(Komponentti k) {
        return ekaTekija.kerro(k);
        // 6*6*6
        // periaatteessa ei koskaan. kenties jos supistettu laskutoimitus.
        // 6*6*(6+x)
        // 6*(n/(1+n))
    }

    public boolean jaa(Komponentti k) {
        if (!jaettu && (k.onkoLauseke() || k.onkoTermi())) {
            return tokaTekija.jaa(k);
        }
        if (jaettu && (k.onkoLauseke() || k.onkoTermi())) {
            return ekaTekija.jaa(k);
        }
        return false;
        // 6/6/6
        // (n/(n+1))/(n/(n+1)) >> l / l >> getSisalto >> la / la
        // 6*6/(6+x)
    }

    public Komponentti palautaJakaja() {
        if (!jaettu) {
            return tokaTekija;
        } else {
            return new Termi(1, 0);
        }
    }
    
    public boolean onkoJaettu() {
        return jaettu;
    }
    
    public List<Komponentti> palautaTulosListana() {
        List<Komponentti> lista = new ArrayList<Komponentti>();
        if (supistettu && jaettu) {
            lista.addAll(ekaTekija.palautaTulosListana());
        } else {
            lista.add(this);
        }
        return lista;
    }
    
//    public Termi palautaTulos() {
//        if (ekaTekija.onkoLaskutoimitus()) {
//            Laskutoimitus la = (Laskutoimitus) ekaTekija;
//            return la.palautaTulos();
//        } else if (ekaTekija.onkoLauseke()) {
//            Lauseke l = (Lauseke) ekaTekija;
//            return l.palautaTulos();
//        } else if (ekaTekija.onkoTermi()) {
//            return (Termi) ekaTekija;
//        } else {
//            throw new RuntimeException("Laskutoimituksen palautaTulos sai Summan. Nam");
//        }
//    }
    
    public Komponentti getEkatekija() {
        return ekaTekija;
    }
    
    public Komponentti getTokatekija() {
        return tokaTekija;
    }
    
    public boolean sisaltaakoMuuttujan() {
        if (supistettu && jaettu) {
            return (ekaTekija.sisaltaakoMuuttujan());
        } else {
            return (ekaTekija.sisaltaakoMuuttujan() || tokaTekija.sisaltaakoMuuttujan());
        }
    }
    
    public boolean onkoSupistettu() {
        return supistettu;
    }

    public String palautaTyyppi() {
        return "la";
    }

    public void muutaNegatiiviseksi() {
        ekaTekija.muutaNegatiiviseksi();
    }

    public Komponentti kopioi() {
        Laskutoimitus la = new Laskutoimitus(ekaTekija.kopioi(), tyyppi, tokaTekija.kopioi());
        return la;
    }

    public boolean onkoTermi() { return false; }

    public boolean onkoLaskutoimitus() { return true; }

    public boolean onkoLauseke() { return false; }
    
    public boolean onkoSumma() { return false; }
    
    public boolean onkoSamanArvoinen(Komponentti kom) {
        if (kom.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) kom.kopioi();
            this.supista();
            la.supista();
            if (jaettu) {
                return ekaTekija.onkoSamanArvoinen(la.getEkatekija());
            } else {
                return ekaTekija.onkoSamanArvoinen(la.getEkatekija()) && tokaTekija.onkoSamanArvoinen(la.getTokatekija());
            }
        }
        return false;
    }
    
    public String toString() {
        if (supistettu && jaettu) {
            return "" + ekaTekija;
        }
        return "" + ekaTekija + tyyppi + tokaTekija;
    }
}
