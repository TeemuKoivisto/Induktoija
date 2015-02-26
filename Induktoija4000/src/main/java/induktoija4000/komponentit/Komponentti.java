package induktoija4000.komponentit;

import java.util.List;

public interface Komponentti {
    public Komponentti kopioi();
    public boolean supista();
    public List<Komponentti> palautaTulosListana();
    public String palautaTyyppi();
    public void muutaNegatiiviseksi(); // vaihdaEtumerkki()?
    public boolean summaa(Komponentti k);
    public boolean kerro(Komponentti k);
    public boolean jaa(Komponentti k);
    public boolean onkoTermi();
    public boolean onkoLaskutoimitus();
    public boolean onkoLauseke();
    public boolean onkoSumma();
    public boolean sisaltaakoMuuttujan();
    public boolean onkoSamanArvoinen(Komponentti k);
    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista);
}
