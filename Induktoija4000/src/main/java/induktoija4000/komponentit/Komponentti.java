package induktoija4000.komponentit;

public interface Komponentti {
    public boolean supista();
    public boolean summaa(Osatekija ot);
    public String palautaTyyppi();
    public void muutaNegatiiviseksi();
    public boolean kerro(Komponentti k);
    public boolean jaa(Komponentti k);
    public boolean onkoOsatekija();
    public boolean onkoTermi();
    public boolean onkoLauseke();
}
