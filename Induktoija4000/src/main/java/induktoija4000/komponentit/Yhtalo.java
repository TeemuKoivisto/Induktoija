package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Yhtalo {
    
    private List<Komponentti> vasenpuoli;
    private List<Komponentti> oikeapuoli;
    private List<Termi> termeina;
    
    public Yhtalo() {
        vasenpuoli = new ArrayList<Komponentti>();
        oikeapuoli = new ArrayList<Komponentti>();
    }
    
    public boolean supistaKaikkiTermeiksi() {
        if (supista(vasenpuoli) && supista(oikeapuoli)) {
            this.muutaYhtaloTermeiksi();
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
                if (k.onkoLaskutoimitus()) {
                    Laskutoimitus la = (Laskutoimitus) k;
                    lista.add(i, la.getTulos());
                    lista.remove(i+1);
                } else if (k.onkoLauseke()) {
                    Lauseke l = (Lauseke) k;
                    lista.add(i, l.getTulos());
                    lista.remove(i+1);
                }
            }
            
        }
        return supistuiko;
    }
    
    public void muutaYhtaloTermeiksi() {
        termeina = new ArrayList<Termi>();
        for (Komponentti k : vasenpuoli) {
            termeina.add((Termi) k);
        }
        for (Komponentti k : oikeapuoli) {
            k.muutaNegatiiviseksi();
            termeina.add((Termi) k);
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
    
    public List<Termi> getTermit() {
        return termeina;
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
        if (this.termeina == null) {
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
            for (Termi t : termeina) {
                tuloste += t + " ";
            }
            tuloste += "= 0";
        }
        return tuloste;
    }
}