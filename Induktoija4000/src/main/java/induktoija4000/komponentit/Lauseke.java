package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lauseke implements Komponentti{

    private List<Komponentti> sisalto;
    private boolean supistettu;
    
    public Lauseke() {
        sisalto = new ArrayList<Komponentti>();
        supistettu = false;
    }      
  
    public Lauseke(List<Komponentti> s) {
        sisalto = s;
        supistettu = false;
    }
    
    public void lisaa(Komponentti k) {
        sisalto.add(k);
    }
    
    public Lauseke sijoitaMuuttujanTilalle(List<Termi> lista) {
        Lauseke lauseke = new Lauseke();
        for (int i = 0; i < sisalto.size(); i++) {
            Lauseke tulos = sisalto.get(i).sijoitaMuuttujanTilalle(lista);
            if (!tulos.onkoTyhja()) {
                sisalto.add(i, tulos);
                sisalto.remove(i+1);
            }
        }
        return lauseke;
    }
    
    public boolean jaa(Termi t) {
        boolean onnistuiko = true;
        for (Komponentti kom : sisalto) {
            if (kom.jaa(t)==false) {
                onnistuiko = false;
            }
        }
        return onnistuiko;
    }
    
    public boolean kerro(Termi t) {
        boolean onnistuiko = true;
        for (Komponentti k : sisalto) {
            if (k.kerro(t)==false) {
                onnistuiko = false;
            }
        }
        return onnistuiko;
    }
    
    public boolean kerro(Komponentti k) {
        //this.supista();
        // (3-x)*7
        if (k.onkoTermi()) {
            boolean onnistuiko = true;
            for (Komponentti kom : sisalto) {
                if (kom.kerro(k)==false) {
                    onnistuiko = false;
                }
            }
            return onnistuiko;
        }
        // (3+x)*3/5        (3+x)*3*3 >> ((l * ot)t * ot)
        // ainoa vaihtoehto siis, että jo supistettu termi?
        if (k.onkoLaskutoimitus()) {
            Lauseke kertoja = new Lauseke(k.palautaTulosListana());
            return this.kerro(kertoja);
        }
        // (3+x)(3+x)
        if (k.onkoLauseke()) {
            Lauseke l = (Lauseke) k;
            boolean onnistuiko = true;
            List<Komponentti> tuloslista = new ArrayList<Komponentti>();
            for (Komponentti kom : sisalto) {
                for (Komponentti kom2 : l.getSisalto()) {
                    Komponentti tulos = kom.kopioi();
                    if (tulos.kerro(kom2)==false) {
                        onnistuiko = false;
                    }
                    tuloslista.add(tulos);
                }
            }
            sisalto = tuloslista;
            supista();
            return onnistuiko;
        }
        return false;
    }
    
    public boolean jaa(Komponentti k) {
        //this.supista();
        // (3-x)/7
        if (k.onkoTermi()) {
            boolean onnistuiko = true;
            for (Komponentti kom : sisalto) {
                if (kom.jaa(k)==false) {
                    onnistuiko = false;
                }
            }
            return onnistuiko;
            // enta jos (3+x)/3x >> 1/x + 1/3
        }
        // (3-x)/(2/(x+8)) >> l / l >> getSisalto >> l / la 
        // wolframin mukaan >> 1/2((3-x)(x+8)) = 1
        if (k.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) k;
            Laskutoimitus kertoja = new Laskutoimitus(new Termi(1, 0), '/', la.getEkatekija());
            kertoja.supista(); // jos kertoja onkin lauseke niin nyt ei 1/(x+1) hajota kaikkea
            this.kerro(la.getTokatekija());
            this.kerro(kertoja);
            return true;
        }
        // (3+x)/(3+x)
        // 3/(3+x) + x/(3+x)
        if (k.onkoLauseke()) {
            Lauseke l = (Lauseke) k;
            boolean onnistuiko = true;
            for (Komponentti kom : sisalto) {
                for (Komponentti kom2 : l.getSisalto()) {
                    if (kom.jaa(kom2)==false) {
                        onnistuiko = false;
                    }
                }
            }
            return onnistuiko;
        }
        return false;
    }
    
    public boolean summaa(Komponentti k) {
        sisalto.addAll(k.palautaTulosListana());
        return true;
    }
    
    public boolean supista() {
        boolean supistuiko = this.supistaSisalto();
        supistettu = true;
        this.uusisummaaSisallonTermitYhteen();
        return sisalto.size()==1;
//        if (supistuiko==true) {
//            this.uusisummaaSisallonTermitYhteen();
//            if (sisalto.size()==1) {
//                return true;
//            }
//        }
//        return false;
    }
    
    public boolean supistaSisalto() {
        boolean supistuiko = true;
        for (int i = 0; i < sisalto.size(); i++) {
            Komponentti k = sisalto.get(i);
            if (k.supista()==false) {
                // lisää sisällön perään, joten supistamattomat laskutoimitukset jumittavat viimeisiksi
                List<Komponentti> lista = k.palautaTulosListana();
                sisalto.remove(i);
                sisalto.addAll(lista);
                supistuiko = false;
            } else {
                // kenties voisin käyttää palautaTulosListana ja getata Termi mutta saattaa olla
                // epästabiilimpaa. Mene ja tiedä.
                if (k.onkoLaskutoimitus()) {
                    Laskutoimitus la = (Laskutoimitus) k;
                    sisalto.add(i, la.palautaTulos());
                    sisalto.remove(i+1);
                } else if (k.onkoLauseke()) {
                    Lauseke l = (Lauseke) k;
                    sisalto.add(i, l.palautaTulos());
                    sisalto.remove(i+1);
                }
            }
        }
        return supistuiko;
    }
    
    public void uusisummaaSisallonTermitYhteen() {
        for (int i = 0; i < sisalto.size(); i++) {
            Komponentti eka = sisalto.get(i);
            for (int j = 0; j < sisalto.size(); j++) {
                Komponentti toka = sisalto.get(j);
                if (i!=j && eka.summaa(toka)) {
                    sisalto.remove(j);
                }
            }
        }
    }
    
    public void summaaSisallonTermitYhteen() {
        for (int i = 0; i < sisalto.size(); i++) {
            Komponentti eka = sisalto.get(i);
            if (eka.onkoTermi()) {
                for (int j = 0; j < sisalto.size(); j++) {
                    Komponentti toka = sisalto.get(j);
                    if (i!=j && eka.onkoTermi() && eka.summaa(toka)) {
                        sisalto.remove(j);
                    }
                }
            }
        }
    }
    
    public List<Komponentti> palautaTulosListana() {
        List<Komponentti> lista = new ArrayList<>();
        for (int i = 0; i < sisalto.size(); i++) {
            lista.addAll(sisalto.get(i).palautaTulosListana());
        }
        return lista;
    }
    
    public Termi palautaTulos() {
        Komponentti tulos = sisalto.get(0);
        if (tulos.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) tulos;
            return la.palautaTulos();
        } else if (tulos.onkoLauseke()) {
            Lauseke l = (Lauseke) tulos;
            return l.palautaTulos();
        } else if (tulos.onkoTermi()) {
            return (Termi) tulos;
        } else {
            throw new RuntimeException("Lausekkeen palautaTuloksessa olikin Summa");
        }
    }
    
    public boolean sisaltaakoMuuttujan() {
        for (Komponentti k : sisalto) {
            if (k.sisaltaakoMuuttujan()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean eikoOleTyhja() {
        return !sisalto.isEmpty();
    }
    
    public boolean onkoTyhja() {
        return sisalto.isEmpty();
    }
    
    public boolean onkoSupistettu() {
        return supistettu;
    }
    
    public Komponentti kopioi() {
        Lauseke l = new Lauseke();
        for (Komponentti k : sisalto) {
            l.lisaa(k.kopioi());
        }
        return l;
    }
    
    public List<Komponentti> getSisalto() {
        return sisalto;
    }
    
    public String palautaTyyppi() {
        return "l";
    }
    
    public void muutaNegatiiviseksi() {
        for (Komponentti k : sisalto) {
            k.muutaNegatiiviseksi();
        }
    }
    
    public boolean onkoTermi() { return false; }
    
    public boolean onkoLaskutoimitus() { return false; }
    
    public boolean onkoLauseke() { return true; }
    
    public boolean onkoSumma() { return false; }
    
    public boolean onkoSamanArvoinen(Komponentti kom) {
        if (kom.onkoLauseke()) {
            Lauseke l = (Lauseke) kom.kopioi();
            int maara = 0;
            for (Komponentti k : sisalto) {
                for (int i = 0; i < l.getSisalto().size(); i++) {
                    if (k.onkoSamanArvoinen(l.getSisalto().get(i))) {
                        l.getSisalto().remove(i);
                        maara++;
                    }
                }
            }
            return maara==sisalto.size() && l.getSisalto().isEmpty();
        }
        return false;
    }
    
    public String toString() {
        String tuloste = "(";
        for (int i = 0; i < sisalto.size(); i++) {
            Komponentti k = sisalto.get(i);
            tuloste += k.toString();
            if ((k.onkoTermi() || k.onkoLaskutoimitus()) && i != sisalto.size()-1) {
                tuloste += " ";
            }
        }
        tuloste += ")";
        return tuloste;
    }
}
