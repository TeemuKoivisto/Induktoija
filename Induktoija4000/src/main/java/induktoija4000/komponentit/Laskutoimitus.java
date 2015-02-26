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
        boolean eka = ekaTekija.supista();
        boolean toka = tokaTekija.supista();
        if (eka) {
            List<Komponentti> tulos = ekaTekija.palautaTulosListana();
            // pitäisi olla aina kooltaan 1!!!!
            ekaTekija = tulos.get(0);
        }
        if (toka) {
            tokaTekija = tokaTekija.palautaTulosListana().get(0);
        }
        // tyyppi=='/' && !toka == x/(x+1) tai vastaavaa...
        if (tyyppi=='/') {
            Komponentti jakaja = tokaTekija.palautaTulosListana().get(0);
            if (ekaTekija.onkoTermi() && jakaja.onkoLaskutoimitus()) {
                Termi ekaT = (Termi) ekaTekija;
                Laskutoimitus la = (Laskutoimitus) jakaja;
                tokaTekija = la.getEkatekija().kopioi();
                ekaTekija = la.getTokatekija();
                ekaTekija.kerro(ekaT);
            } else {
                jaettu = ekaTekija.jaa(tokaTekija);
            }
        } else {
            // tarkistetaan kerrotaanko Termia Lausekkeella. Koska se ei ole kivaa...
            if (ekaTekija.onkoTermi() && (tokaTekija.onkoLauseke())) { // || ekaTekija.onkoLaskutoimitus())
                Komponentti ekaT = ekaTekija;
                ekaTekija = tokaTekija;
                tokaTekija = ekaT;
            }
            ekaTekija.kerro(tokaTekija);
        }
        // pitäisi estää typeriä supistusvirheitä
        if (jaettu) {
            tokaTekija = new Termi(1, 0);
        }
        supistettu = true;
        supistettuOnnistuneesti = (eka && toka);
        return supistettuOnnistuneesti;
    }

    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista) {
        Lauseke lauseke = new Lauseke();
//        if (tulos!=null) {
//            tulos.sijoitaMuuttujanTilalle(lista);
//        } else if (supistettu) {
//            ekaTekija.sijoitaMuuttujanTilalle(lista);
//        } else {
        Lauseke eka = ekaTekija.sijoitaMuuttujanTilalle(lista);
        Lauseke toka = tokaTekija.sijoitaMuuttujanTilalle(lista);
        if (!eka.onkoTyhja()) {
            ekaTekija = eka;
        }
        if (!toka.onkoTyhja()) {
            tokaTekija = toka;
        }
        return lauseke;
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
    
    public Termi palautaTulos() {
        if (ekaTekija.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) ekaTekija;
            return la.palautaTulos();
        } else if (ekaTekija.onkoLauseke()) {
            Lauseke l = (Lauseke) ekaTekija;
            return l.palautaTulos();
        } else if (ekaTekija.onkoTermi()) {
            return (Termi) ekaTekija;
        } else {
            throw new RuntimeException("Laskutoimituksen palautaTulos sai Summan. Nam");
        }
    }
    
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
