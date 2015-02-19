package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Laskutoimitus implements Komponentti {

    private Komponentti ekaTekija;
    private Komponentti tokaTekija;
    private Termi tulos;
    private boolean supistettu;
    private boolean jaettu;
    private char tyyppi;

    public Laskutoimitus(Komponentti k, char c, Komponentti k2) {
        ekaTekija = k;
        if (c == '(') {
            c = '*';
            jaettu = true;
        } else {
            jaettu = false;
        }
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
    
    public Termi muutaKomponenttiTermiksi(Komponentti k) {
        Termi t = new Termi(0, 0);
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
        if (tulos!=null) {
            return tulos.kerro(ot);
        } else {
            return ekaTekija.kerro(ot);
        }
    }

    public boolean jaa(Termi ot) {
        if (tulos!=null) {
            return tulos.jaa(ot);
        } else {
            return ekaTekija.jaa(ot);
        }
    }

    public boolean kerro(Komponentti k) {
        if (tulos!=null) {
            Termi t = muutaKomponenttiTermiksi(k);
            return this.tulos.kerro(t);
        } else {
            return ekaTekija.kerro(k);
        }
        // 6*6*6
        // periaatteessa ei koskaan. kenties jos supistettu laskutoimitus.
        // 6*6*(6+x)
    }

    public boolean jaa(Komponentti k) {
        if (tulos!=null) {
            Termi t = muutaKomponenttiTermiksi(k);
            jaettu = this.tulos.jaa(t);
        } else {
            jaettu = ekaTekija.jaa(k);
        }
        return jaettu;
        // 6/6/6
        // (n/(n+1))/(n/(n+1)) >> l / l >> getSisalto >> la / la
        // 6*6/(6+x)
    }

    public boolean supista() {
        ekaTekija.supista();
        tokaTekija.supista();
        if (tyyppi == '/') {
            supistettu = ekaTekija.jaa(tokaTekija);
        } else {
            // vaihdetaan termi kertojaksi, jotta elämä olisi helpompaa
            if (ekaTekija.onkoTermi() && tokaTekija.onkoLauseke()) {
                Komponentti eka = ekaTekija;
                ekaTekija = tokaTekija;
                tokaTekija = eka;
            }
            supistettu = ekaTekija.kerro(tokaTekija);
        }
        if (supistettu && ekaTekija.onkoLauseke()) {
            Lauseke l = (Lauseke) ekaTekija;
            if (l.getSisalto().size() > 1 || l.getSisalto().get(0).onkoLaskutoimitus()) {
                return false;
            }
        }
        if (supistettu) {
            // jos muutankin ekanTekijan termiksi? ei tarvita tulosta
            tulos = this.muutaKomponenttiTermiksi(ekaTekija);
            return true;
        }
        return false;
    }

    public boolean vanhaSupista() {
        if (ekaTekija.supista()) {
            if (tokaTekija.supista()) {
                if (tyyppi == '/') {
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
        if (supistettu && tulos==null) {
            if (ekaTekija.onkoLauseke()) {
                Lauseke l = (Lauseke) ekaTekija;
                return l.palautaTulosListana();
            } else if (ekaTekija.onkoLaskutoimitus()) {
                Laskutoimitus la = (Laskutoimitus) ekaTekija;
                return la.palautaTulosListana();
            } else {
                lista.add(ekaTekija);
                return lista;
            }
        } else {
            lista.add(this);
            return lista;
        }
    }
    
    public Komponentti palautaTulos() {
        if (tulos != null) {
            return tulos;
        } else if (supistettu) {
            if (ekaTekija.onkoLaskutoimitus()) {
                Laskutoimitus la = (Laskutoimitus) ekaTekija;
                return la.palautaTulos();
            }
            return ekaTekija;
        } else {
            return this;
        }
    }

    public Termi getEkatekija() {
        return (Termi) ekaTekija;
    }
    
    public Termi getTulos() {
        if (tulos != null) {
            return tulos;
        } else {
            System.out.println("laskutoimituksen " + toString() + " getTulos feilas");
            return new Termi(0, 0);
        }
    }
    
    public boolean sisaltaakoMuuttujan() {
        if (tulos!=null) {
            return tulos.sisaltaakoMuuttujan();
        } else if (supistettu) {
            return ekaTekija.sisaltaakoMuuttujan();
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
        if (tulos != null) {
            tulos.muutaNegatiiviseksi();
        } else if (supistettu) {
            ekaTekija.muutaNegatiiviseksi();
        } else {
            ekaTekija.muutaNegatiiviseksi();
          //tokaTekija.muutaNegatiiviseksi();
        }
    }

    public Komponentti kopioi() {
        Laskutoimitus t = new Laskutoimitus(ekaTekija, tyyppi, tokaTekija);
        t.supista();
        return t;
    }

    public boolean onkoTermi() { return false; }

    public boolean onkoLaskutoimitus() { return true; }

    public boolean onkoLauseke() { return false; }
    
    public boolean onkoSumma() { return false; }
    
    public String toString() {
        if (tulos != null) {
            return "" + tulos;
        }
        if (supistettu) {
            return "" + ekaTekija;
        }
        return "" + ekaTekija + tyyppi + tokaTekija;
    }
}
