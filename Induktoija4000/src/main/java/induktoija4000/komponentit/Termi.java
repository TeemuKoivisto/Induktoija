package induktoija4000.komponentit;

public class Termi implements Komponentti{
    
    private Komponentti ekaTekija;
    private Komponentti tokaTekija;
    private Osatekija tulos;
    private boolean supistettu;
    private char tyyppi;
    
    public Termi() {
        supistettu = false;
    }
    
    public Termi(Komponentti k, char c, Komponentti k2) {
        ekaTekija = k;
        tyyppi = c;
        tokaTekija = k2;
        supistettu = false;
    }
    
    public Termi(Komponentti k, char c) {
        ekaTekija = k;
        tyyppi = c;
        supistettu = false;
    }
    
    public void lisaaTokatekija(Komponentti k) {
        tokaTekija = k;
    }
    
    public boolean summaa(Osatekija ot) {
        if (supistettu) {
            return tulos.summaa(ot);
        }
        return false;
    }
    
    public boolean kerro(Osatekija ot) {
        supista();
        if (supistettu) {
            return tulos.kerro(ot);
        } else {
            return ekaTekija.kerro(ot);
        }
    }
    
    public boolean jaa(Osatekija ot) {
        supista();
        if (supistettu) {
            return tulos.jaa(ot);
        } else {
            return ekaTekija.jaa(ot);
        }
    }
    
    public boolean kerro(Komponentti k) {
        if (k.getClass().equals(new Osatekija(0,0).getClass())) {
            // 3*3*3 >> 3*3 termi kertaa osatekija 3
            return kerro((Osatekija) k);
        }
        if (k.getClass().equals(this.getClass())) {
            // tarkoittaa nyt, että termi on supistettu
            // 3*3*3 >> 9*3 >> termi * termi
            Termi t = (Termi) k;
            if (t.supista()) {
                kerro(t.getTulos());
            } else {
                return false;
            }
            
        }
        if (k.getClass().equals(new Lauseke().getClass())) {
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
        if (k.getClass().equals(new Osatekija(0,0).getClass())) {
            // 3*3/3
            return jaa((Osatekija) k);
        }
        if (k.getClass().equals(this.getClass())) {
            // tarkoittaa nyt, että termi on supistettu
            // 3*3/3 >> 9/3 >> termi / termi
            Termi t = (Termi) k;
            if (t.supista()) {
                jaa(t.getTulos());
            } else {
                return false;
            }
            
        }
        if (k.getClass().equals(new Lauseke().getClass())) {
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
    
    public boolean lisaa(char c) {
        return true;
    }
    
    public String palautaTyyppi() {
        return "t";
    }
    
    public void muutaNegatiiviseksi() {
        if (supista()) {
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
