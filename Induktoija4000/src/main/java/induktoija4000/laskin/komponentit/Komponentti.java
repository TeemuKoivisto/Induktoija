package induktoija4000.laskin.komponentit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Teemu
 */
public interface Komponentti {
    public boolean supista();
    public boolean summaa(Osatekija ot);
    public String palautaTyyppi();
    //public List<Osatekija> palautaArvo;
    //public Komponentti kerro(Lauseke l);
    //public Komponentti kerro(Osatekija ot);
    public boolean kerro(Komponentti k);
    public boolean jaa(Komponentti k);
    /*public boolean onkoOsatekija(Komponentti k);
    public boolean onkoTermi(Komponentti k);
    public boolean onkoLauseke(Komponentti k);
    */
}
