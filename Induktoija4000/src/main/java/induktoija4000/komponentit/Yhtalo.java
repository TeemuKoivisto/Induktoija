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
    
    public boolean supista() {
        boolean vasen = supistaJosMahdollistaTermeiksi(vasenpuoli);
        if (supistaJosMahdollistaTermeiksi(oikeapuoli) && vasen) {
            this.supistaPuoletYhteenTermeina();
            return true;
        } else {
            this.supistaPuoletYhteenKomponentteina();
            return false;
        }
    }
    
    public boolean supistaJosMahdollistaTermeiksi(List<Komponentti> lista) {
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
    
    public void supistaPuoletYhteenKomponentteina() {
        // ehkä parempi siirtaa vain oikealle puolelle ja sitten käydä läpi
        // säästyy hieman koodia
        for (int i = 0; i < vasenpuoli.size(); i++) {
            Komponentti k = vasenpuoli.get(i);
            if (k.onkoLaskutoimitus()) {
                Laskutoimitus la = (Laskutoimitus) k;
                vasenpuoli.addAll(la.palautaTulosListana());
                vasenpuoli.remove(i);
            } else if (k.onkoLauseke()) {
                Lauseke l = (Lauseke) k;
                vasenpuoli.addAll(l.palautaTulosListana());
                vasenpuoli.remove(i);
            }
        }
        for (Komponentti k : oikeapuoli) {
            k.muutaNegatiiviseksi();
            if (k.onkoLaskutoimitus()) {
                Laskutoimitus la = (Laskutoimitus) k;
                vasenpuoli.addAll(la.palautaTulosListana());
            } else if (k.onkoLauseke()) {
                Lauseke l = (Lauseke) k;
                vasenpuoli.addAll(l.palautaTulosListana());
            } else {
                vasenpuoli.add(k);
            }
        }
        oikeapuoli = new ArrayList<Komponentti>();
    }
    
    public void supistaPuoletYhteenTermeina() {
        termeina = new ArrayList<Termi>();
        for (Komponentti k : vasenpuoli) {
            termeina.add((Termi) k);
        }
        for (Komponentti k : oikeapuoli) {
            k.muutaNegatiiviseksi();
            termeina.add((Termi) k);
        }
    }
    
//    public void sijoitaMuuttujantilalle(List<Komponentti> lista) {
//        for (int i = 0; i < arr.length; i++) {
//            Object arr = arr[i];
//            
//        }
//    }
    
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