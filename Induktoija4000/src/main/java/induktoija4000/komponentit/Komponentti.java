package induktoija4000.komponentit;

public interface Komponentti {
    public boolean supista();
    public String palautaTyyppi();
    public void muutaNegatiiviseksi();
    public boolean summaa(Komponentti k);
    public boolean kerro(Komponentti k);
    public boolean jaa(Komponentti k);
    public boolean onkoTermi();
    public boolean onkoLaskutoimitus();
    public boolean onkoLauseke();
}
