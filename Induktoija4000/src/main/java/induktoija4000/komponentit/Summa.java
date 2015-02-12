package induktoija4000.komponentit;

import java.util.List;

public class Summa implements Komponentti {
    
    private Termi ylaraja;
    private int alaraja;
    private Lauseke summa;
    
    public Summa(Termi m, int i, Lauseke l) {
        ylaraja = m;
        alaraja = i;
        summa = l;
    }
    
    public void sijoitakja1() {
        // sijoita muuttujan tilalle k+1
    }
    
    @Override
    public boolean supista() {
        return summa.supista();
    }

    @Override
    public boolean summaa(Komponentti k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String palautaTyyppi() {
        return "sig";
    }

    @Override
    public void muutaNegatiiviseksi() {
        summa.muutaNegatiiviseksi();
    }

    @Override
    public boolean kerro(Komponentti k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean jaa(Komponentti k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean onkoTermi() {
        return false;
    }

    @Override
    public boolean onkoLaskutoimitus() {
        return false;
    }

    @Override
    public boolean onkoLauseke() {
        return false;
    }
    
    public String toString() {
        return "sig(" + ylaraja.toString() + ",i=" + alaraja + "," + summa.toString() + ")";
    }
}
