package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Yhtalo {
    
    private List<Komponentti> vasenpuoli;
    private List<Komponentti> oikeapuoli;
    private List<Osatekija> osatekijoina;
    
    public Yhtalo() {
        vasenpuoli = new ArrayList<Komponentti>();
        oikeapuoli = new ArrayList<Komponentti>();
    }
    
    public boolean supistaKaikkiOsatekijoiksi() {
        if (supista(vasenpuoli) && supista(oikeapuoli)) {
            this.muutaYhtaloOsatekijoiksi();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean supista(List<Komponentti> lista) {
        boolean supistuiko = true;
        for (int i = 0; i < lista.size(); i++) {
            Komponentti k = lista.get(i);
            if (k.supista()==false) {
                supistuiko = false;
            } else {
                if (k.onkoTermi()) {
                    Termi t = (Termi) k;
                    lista.add(i, t.getTulos());
                    lista.remove(i+1);
                }
                // tähän kenties if (k.onkoLauseke()) {
                // jos lausekekin on nyt supistettu
            }
            
        }
        return supistuiko;
    }
    
    public void muutaYhtaloOsatekijoiksi() {
        osatekijoina = new ArrayList<Osatekija>();
        for (Komponentti k : vasenpuoli) {
            osatekijoina.add((Osatekija) k);
        }
        for (Komponentti k : oikeapuoli) {
            k.muutaNegatiiviseksi();
            osatekijoina.add((Osatekija) k);
        }
    }
    
    public void annaVasenPuoli(List<Komponentti> l) { vasenpuoli = l; }
    
    public void annaOikeaPuoli(List<Komponentti> l) { oikeapuoli = l; }
    
    public List<Komponentti> getVasenpuoli() {
        return vasenpuoli;
    }
    
    public List<Komponentti> getOikeapuoli() {
        return oikeapuoli;
    }
    
    public List<Osatekija> getOsatekijat() {
        return osatekijoina;
    }
    
    public void tulostaTyypit() {
        for (Komponentti k : vasenpuoli) {
            System.out.print(k.palautaTyyppi()+" ");
        }
        System.out.print("= ");
        for (Komponentti k : oikeapuoli) {
            System.out.print(k.palautaTyyppi() +" ");
        }
        System.out.println("");
    }
    
    public String toString() {
        String tuloste = "";
        if (this.osatekijoina == null) {
            for (Komponentti k : vasenpuoli) {
                tuloste += k + " ";
            }
            tuloste += "=";
            if (oikeapuoli.size()==0) {
                tuloste += " 0";
            } else {
                for (Komponentti k : oikeapuoli) {
                    tuloste += " " + k;
                }
            }
        } else {
            for (Osatekija ot : osatekijoina) {
                tuloste += ot + " ";
            }
            tuloste += "= 0";
        }
        return tuloste;
    }
}