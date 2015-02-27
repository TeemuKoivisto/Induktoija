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
    
    public Yhtalo(List<Komponentti> vasen, List<Komponentti> oikea) {
        vasenpuoli = vasen;
        oikeapuoli = oikea;
    }
    
    public void supistaSiirtamatta() {
        vasenpuoli = this.supistaListaKomponentteina(vasenpuoli);
        oikeapuoli = this.supistaListaKomponentteina(oikeapuoli);
    }
    
    public List<Komponentti> supistaListaKomponentteina(List<Komponentti> lista) {
        List<Komponentti> valmis = new ArrayList<>();
        for (Komponentti k : lista) {
            k.supista();
            valmis.addAll(k.palautaTulosListana());
        }
        return valmis;
    }
    
    public void siirraKaikkiVasemmalle() {
        for (Komponentti k : oikeapuoli) {
            k.muutaNegatiiviseksi();
        }
        vasenpuoli.addAll(oikeapuoli);
        oikeapuoli = new ArrayList<>();
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
    
    public void sijoitaMuuttujantilalle(List<Termi> lista) {
        this.sijoitaListaan(lista, vasenpuoli);
        this.sijoitaListaan(lista, oikeapuoli);
    }
    
    public void sijoitaListaan(List<Termi> lista, List<Komponentti> puoli) {
        for (int i = 0; i < puoli.size(); i++) {
            Lauseke l = puoli.get(i).sijoitaMuuttujanTilalle(lista);
            if (l.eikoOleTyhja()) {
                puoli.add(i, l);
                puoli.remove(i+1);
            }
        }
    }
    
    public Yhtalo kopioi() {
        List<Komponentti> vasen = new ArrayList<>();
        for (Komponentti k : vasenpuoli) {
            vasen.add(k.kopioi());
        }
        List<Komponentti> oikea = new ArrayList<>();
        for (Komponentti k : oikeapuoli) {
            oikea.add(k.kopioi());
        }
        return new Yhtalo(vasen, oikea);
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