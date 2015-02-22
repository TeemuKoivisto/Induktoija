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
    
    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista) {
        Lauseke lauseke = new Lauseke();
        return lauseke;
    }
    
    public void sijoitakplus1() {
        // sijoita muuttujan tilalle k+1
        for (int i = 0; i < summa.getSisalto().size(); i++) {
            Komponentti k = summa.getSisalto().get(i);
            // eihän tää vittu toimi
            // jos vaik lauseke, jos monta muuttujaa?
            if (k.sisaltaakoMuuttujan()) {
                k.summaa(new Termi(1, 0));
            }
        }
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
    public boolean sisaltaakoMuuttujan() {
        return summa.sisaltaakoMuuttujan();
    }
    
    public Komponentti kopioi() {
        return new Summa(ylaraja, alaraja, summa);
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
    
    @Override
    public boolean onkoSumma() {
        return true;
    }

    public int getAlaraja() {
        return alaraja;
    }
    
    public String toString() {
        return "sig(" + ylaraja.toString() + ",i=" + alaraja + "," + summa.toString() + ")";
    }

    @Override
    public List<Komponentti> palautaTulosListana() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
