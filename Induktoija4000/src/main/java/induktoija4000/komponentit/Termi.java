package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Termi implements Komponentti{
    
    private double arvo;
    private double muuttuja;
    private char variable;
    private Komponentti eksponentti;
    
    public Termi(double a, double d, char var) {
        arvo = a;
        muuttuja = d;
        variable = var;
    }
    
    public Termi(double a, double m) {
        arvo = a;
        muuttuja = m;
        variable = 'n';
    }
    
    public void lisaaEksponentti(Komponentti e) {
        eksponentti = e;
    }
    
    public void alusta(double a, double m) {
        arvo = a;
        muuttuja = m;
    }
    
    public void muutaNegatiiviseksi() {
        arvo *= -1.0;
    }
    
    public boolean supista() {
        return true;
    }
    
    public String palautaTyyppi() {
        return "t";
    }
    
    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista) {
        Lauseke lauseke = new Lauseke();
        if (lista.size()>1) {
            for (int i = 0; i < lista.size(); i++) {
                List<Termi> pikkulista = new ArrayList<Termi>();
                pikkulista.add(lista.get(i));
                Termi t = (Termi) this.kopioi();
                t.sijoitaMuuttujanTilalle(pikkulista);
                lauseke.lisaa(t);
            }
        } else {
            Termi t = lista.get(0);
            // teen tämän joskus paremmin
            // 3n^2 -> 5n = 3 * (5n * 5n)
            if (t.sisaltaakoMuuttujan()) {
                Termi tulos = (Termi) t.kopioi();
                for (int i = 0; i < muuttuja; i++) {
                    tulos.kerro(t);
                }
                tulos.kerro(new Termi(arvo, 0));
                this.alusta(tulos.getArvo(), tulos.getMuuttuja());
            } else {
                // 3n^2 -> 5 = 3 * 5^2
                arvo = arvo * Math.pow(t.getArvo(), muuttuja);
            }
            lauseke.lisaa(this);
        }
        return lauseke;
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
        if (!k.supista()) {
            return false;
        }
        Termi t = muutaKomponenttiTermiksi(k);
        if (this.muuttuja==t.getMuuttuja()) {
            arvo += t.getArvo();
            if (arvo==0) {
                alusta(0, 0);
            }
            return true;
        }
        return false;
    }
    
    public boolean jaa(Komponentti k) {
        Termi t = muutaKomponenttiTermiksi(k);
        return this.jaa(t);
        // 6/6
        // jos supistettu laskutoimitus?
        // 6/((6+x)*6)) ??
        // 6/(6+x)
    }
    
    public boolean kerro(Komponentti k) {
        Termi t = muutaKomponenttiTermiksi(k);
        return this.kerro(t);
        // 6*6
        // 6*6*6 ??? ei? 6*((6+x)*6)) ???
        // 6*(6+x)
    }
    
    public Termi jaa(double d) {
        this.arvo /= d;
        return this;
    }
    
    public boolean jaa(Termi t) {
        this.arvo /= t.getArvo();
        this.muuttuja -= t.getMuuttuja();
        return true;
    }
    
    public boolean kerro(Termi t) {
        this.arvo *= t.getArvo();
        this.muuttuja += t.getMuuttuja();
        return true;
    }
    
    public boolean sisaltaakoMuuttujan() {
        return variable!=0;
    }
    
    public Komponentti getEksponentti() {
        return eksponentti;
    }
    
    public double getArvo() {
        return arvo;
    }
    
    public double getMuuttuja() {
        return muuttuja;
    }
    
    public Komponentti kopioi() {
        Termi t = new Termi(arvo, muuttuja);
        return t;
    }
    
    public boolean onkoTermi() { return true; }
    
    public boolean onkoLaskutoimitus() { return false; }
    
    public boolean onkoLauseke() { return false; }
    
    public boolean onkoSumma() { return false; }
    
    public String toString() {
        if (muuttuja == 0) {
            return "" + arvo;
        } else if (arvo == 1 || arvo == -1) {
            if (muuttuja == 1 && arvo == 1) {
                return "n";
            } else if (muuttuja == 1 && arvo ==-1) {
                return "-n";
            } else if (arvo == 1) {
                return "n^" + muuttuja;
            } else {
                return "-n^" + muuttuja;
            }
        } else if (muuttuja == 1) {
            return "" + arvo + "n";
        } else {
            return "" + arvo + "n^" + muuttuja;
        }
    }
}
