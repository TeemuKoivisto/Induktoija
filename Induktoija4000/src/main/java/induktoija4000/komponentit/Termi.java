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
    
    public List<Komponentti> palautaTulosListana() {
        List<Komponentti> lista = new ArrayList<>();
        lista.add(this);
        return lista;
    }
    
    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista) {
        Lauseke lauseke = new Lauseke();
        if (lista.size()>1) {
            // 3n -> k+1 = 3*k + 3*1 
            for (int i = 0; i < lista.size(); i++) {
                List<Termi> pikkulista = new ArrayList<Termi>();
                pikkulista.add(lista.get(i));
                Termi t = (Termi) this.kopioi();
                Lauseke l = t.sijoitaMuuttujanTilalle(pikkulista);
                lauseke.getSisalto().addAll(l.palautaTulosListana());
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
    
    public boolean summaa(Komponentti k) {
        List<Komponentti> lista = k.palautaTulosListana();
        Termi t;
        if (lista.size()>1 || lista.get(0).onkoLaskutoimitus() || lista.get(0).onkoLauseke()) {
            return false;
        } else {
            t = (Termi) lista.get(0);
        }
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
        List<Komponentti> jakaja = k.palautaTulosListana();
        if (jakaja.size()>1 || jakaja.get(0).onkoLaskutoimitus()) {
            return false;
        } else {
            Termi t = (Termi) jakaja.get(0);
            return this.jaa(t);
        }
        // 6/6
        // jos supistettu laskutoimitus?
        // 6/((6+x)*6)) ??
        // 6/(6+x)
    }
    
    public boolean kerro(Komponentti k) {
        Komponentti kertoja = k.palautaTulosListana().get(0);
        if (k.palautaTulosListana().size()>1 || kertoja.onkoLaskutoimitus() || kertoja.onkoLauseke()) {
            return false;
        } else {
            Termi t = (Termi) kertoja;
            return this.kerro(t);
        }
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
