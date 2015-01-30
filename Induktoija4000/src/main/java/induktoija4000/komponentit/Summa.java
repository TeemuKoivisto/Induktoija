package induktoija4000.komponentit;

import java.util.List;

public class Summa implements Komponentti {
    
    private List<Komponentti> mara;
    private Osatekija maara;
    private int summausindeksi;
    private Lauseke summa;
    
    public Summa(Osatekija m, int i, Lauseke l) {
        maara = m;
        summausindeksi = i;
        summa = l;
    }

    public Summa(List<Komponentti> m, int i) {
        mara = m;
        summausindeksi = i;
    }
    
    public void sijoitakja1() {
        // sijoita jokaisen muuttujan tilalle k+1
    }
    
    @Override
    public boolean supista() {
        return summa.supista();
    }

    @Override
    public boolean summaa(Osatekija ot) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String palautaTyyppi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean onkoOsatekija() {
        return false;
    }

    @Override
    public boolean onkoTermi() {
        return false;
    }

    @Override
    public boolean onkoLauseke() {
        return false;
    }
}
