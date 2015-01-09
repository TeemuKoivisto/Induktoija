package induktoija4000.laskin.komponentit;

public class Osatekija implements Komponentti{
    
    private double arvo;
    private double muuttuja;
    private char variable;
    private boolean supistettu;
    
    public Osatekija(double a, double d, char var) {
        arvo = a;
        muuttuja = d;
        variable = var;
    }
    
    public Osatekija(double a, double m) {
        arvo = a;
        muuttuja = m;
        variable = 'n';
    }
    
    public void alusta(double a, double m) {
        arvo = a;
        muuttuja = m;
    }
    
    public void lisaaMinus() {
        arvo *= -1.0;
    }
    
    public boolean supista() {
        return true;
        /*
        if (supistettu) {
            return true;
        }
        supistettu = true;
        return true;*/
    }
    
    public String palautaTyyppi() {
        return "ot";
    }
    
    public boolean summaa(Osatekija ot) {
        if (this.muuttuja==ot.getVariable()) {
            arvo += ot.getValue();
            return true;
        }
        return false;
    }
    
    public boolean jaa(Komponentti k) {
        if (k.getClass().equals(this.getClass())) {
            // 6/5
            Osatekija ot = (Osatekija) k;
            return jaa(ot);
        }
        if (k.getClass().equals(new Termi(k, '/', k).getClass())) {
            // mahdollinen jos vain supistettu termi
            // 3/(5+x)*6 ???
            Termi t = (Termi) k;
            boolean onko = t.supista();
            if (onko) {
                return jaa(t.getTulos());
            } else {
                return false;
            }
        }
        if (k.getClass().equals(new Lauseke().getClass())) {
            // 3/(3+x)
            Lauseke l = (Lauseke) k;
            return l.jaa(this);
        }
        return false;
    }
    
    public boolean kerro(Komponentti k) {
        if (k.getClass().equals(this.getClass())) {
            // 6*5
            Osatekija ot = (Osatekija) k;
            return kerro(ot);
        }
        if (k.getClass().equals(new Termi(k, '*', k).getClass())) {
            // mahdollinen jos vain supistettu termi
            // 3*(5+x)/6 ???
            Termi t = (Termi) k;
            boolean onko = t.supista();
            if (onko) {
                return kerro(t.getTulos());
            } else {
                return false;
            }
        }
        if (k.getClass().equals(new Lauseke().getClass())) {
            // 3(3+x)
            Lauseke l = (Lauseke) k;
            return l.kerro(this);
        }
        return false;
    }
    
    public Osatekija jaa(double d) {
        this.arvo /= d;
        return this;
    }
    
    public boolean jaa(Osatekija ot) {
        this.arvo /= ot.getValue();
        this.muuttuja -= ot.getVariable();
        return true;
    }
    
    public boolean kerro(Osatekija ot) {
        this.arvo *= ot.getValue();
        this.muuttuja += ot.getVariable();
        return true;
    }
    /*
    public Osatekija jaa(Osatekija ot) {
        this.supista();
        ot.supista();
        this.arvo /= ot.getValue();
        this.muuttuja -= ot.getVariable();
        return this;
    }
    
    public Osatekija kerro(Osatekija ot) {
        this.supista();
        ot.supista();
        this.arvo *= ot.getValue();
        this.muuttuja += ot.getVariable();
        return this;
    }*/
    
    public double getValue() {
        return arvo;
    }
    
    public double getVariable() {
        return muuttuja;
    }
    
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
