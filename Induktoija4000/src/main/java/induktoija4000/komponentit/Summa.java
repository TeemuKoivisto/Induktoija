package induktoija4000.komponentit;

import java.util.List;

public class Summa implements Komponentti {
    
    private Termi ylaraja;
    private Termi alaraja;
    private Lauseke summa;
    
    public Summa(Termi m, Termi a, Lauseke l) {
        ylaraja = m;
        alaraja = a;
        summa = l;
    }
    
    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista) {
//        Lauseke lauseke = new Lauseke();
//        summa.sijoitaMuuttujanTilalle(lista);
//        return lauseke;
        return summa.sijoitaMuuttujanTilalle(lista);
    }
    
    @Override
    public boolean supista() {
        return summa.supista();
    }

    @Override
    public boolean summaa(Komponentti k) {
        summa.getSisalto().addAll(k.palautaTulosListana());
        return true;
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
        return summa.kerro(k);
    }

    @Override
    public boolean jaa(Komponentti k) {
        return summa.jaa(k);
    }

    @Override
    public boolean sisaltaakoMuuttujan() {
        return summa.sisaltaakoMuuttujan();
    }
    
    public Komponentti kopioi() {
        return new Summa((Termi) ylaraja.kopioi(), (Termi) alaraja.kopioi(), (Lauseke) summa.kopioi());
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

    public Lauseke getLauseke() {
        return summa;
    }
    
    public Termi getAlaraja() {
        return alaraja;
    }
    
    public boolean onkoSamanArvoinen(Komponentti kom) {
        if (kom.onkoSumma()) {
            Summa sig = (Summa) kom;
            return sig.getLauseke().onkoSamanArvoinen(this.summa); // vertaa muita arvoja?? naaah
        }
        return false;
    }
    
    public String toString() {
        return "sig(" + ylaraja.toString() + ",i=" + alaraja + "," + summa.toString() + ")";
    }

    @Override
    public List<Komponentti> palautaTulosListana() {
        return summa.palautaTulosListana();
    }
}
