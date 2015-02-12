package induktoija4000.komponentit;

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
        if (this.muuttuja==t.getVariable()) {
            arvo += t.getValue();
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
        this.arvo /= t.getValue();
        this.muuttuja -= t.getVariable();
        return true;
    }
    
    public boolean kerro(Termi t) {
        this.arvo *= t.getValue();
        this.muuttuja += t.getVariable();
        return true;
    }
    
    public Komponentti getEksponentti() {
        return eksponentti;
    }
    
    public double getValue() {
        return arvo;
    }
    
    public double getVariable() {
        return muuttuja;
    }
    
    public boolean onkoTermi() { return true; }
    
    public boolean onkoLaskutoimitus() { return false; }
    
    public boolean onkoLauseke() { return false; }
    
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
