package induktoija4000.komponentit;

import java.util.ArrayList;
import java.util.List;

public class Lauseke implements Komponentti{

    private List<Komponentti> sisalto;
    private Termi tulos;
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
    
    public boolean kerro(Termi ot) {
        boolean onnistuiko = true;
        for (Komponentti k : sisalto) {
            if (k.kerro(k)==false) {
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
            Laskutoimitus t = (Laskutoimitus) k;
            if (t.supista()) {
                return kerro(t.getTulos());
            } else {
                return false;
            }
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
    
    public boolean jaaNko(Komponentti k) {
        
        if (k.onkoTermi()) {
            
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
        // jo supistettu laskutoimitus?
        // (3-x)/5/8
        if (k.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) k;
            if (la.supista()) {
                return jaa(la.getTulos());
            } else {
                return false;
            }
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
        if (k.onkoTermi()) {
            return tulos.summaa(k);
        } else if (k.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) k;
            return tulos.summaa(la.getTulos());
        } else  {
            Lauseke l = (Lauseke) k;
            return tulos.summaa(l.getTulos());
        }
    }
    
    public boolean supista() {
        boolean supistuiko = this.supistaSisalto();
        if (supistuiko==true) {
            this.summaaSisallonTermitYhteen();
            if (sisalto.size()==1) {
                Termi t = (Termi) sisalto.get(0);
                tulos = t;
                return true;
            }
        }
        return false;
    }
    
    public boolean supistaSisalto() {
        boolean supistuiko = true;
        for (int i = 0; i < sisalto.size(); i++) {
            Komponentti k = sisalto.get(i);
            if (k.supista()==false) {
                supistuiko = false;
            } else {
                // la.palautaTulos??
                if (k.onkoLaskutoimitus()) {
                    Laskutoimitus la = (Laskutoimitus) k;
                    sisalto.add(i, la.getTulos());
                    sisalto.remove(i+1);
                } else if (k.onkoLauseke()) {
                    Lauseke l = (Lauseke) k;
                    sisalto.add(i, l.getTulos());
                    sisalto.remove(i+1);
                }
            }
        }
        return supistuiko;
    }
    
    public void summaaSisallonTermitYhteen() {
        // hivenen epästabiili. kutsuu summaa()-metodia,
        // joka toimii vain termeille/supistetuille
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
    
    public Termi muutaKomponenttiTermiksi(Komponentti k) {
        Termi t = new Termi(0,0);
        if (k.onkoLaskutoimitus()) {
            Laskutoimitus la = (Laskutoimitus) k;
            t = la.getTulos();
        } else if (k.onkoLauseke()) {
            Lauseke l = (Lauseke) k;
            t = l.getTulos();
        } else if (k.onkoTermi()) {
            t = (Termi) k;
        } else {
            throw new RuntimeException("se olikin summa. hups");
        }
        return t;
    }
    
    public Termi getTulos() {
        if (tulos == null) {
            System.out.println("syö paskaa, lauseke ei ole valmis");
        } else {
            return tulos;
        }
        throw new NullPointerException("lausekkeen getTulos");
    }
    
    public boolean sisaltaakoMuuttujan() {
        for (Komponentti k : sisalto) {
            if (k.sisaltaakoMuuttujan()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean onkoTyhja() {
        return sisalto.isEmpty();
    }
    
    public boolean onkoSupistettu() {
        return supistettu;
    }
    
    public Komponentti kopioi() {
        Lauseke l = new Lauseke(sisalto);
        l.supista();
        return l;
    }
    
    public List<Komponentti> palautaTulosListana() {
        List<Komponentti> lista = new ArrayList<Komponentti>();
        for (int i = 0; i < sisalto.size(); i++) {
            Komponentti k = sisalto.get(i);
            if (k.onkoLaskutoimitus()) {
                Laskutoimitus la = (Laskutoimitus) k;
                lista.addAll(la.palautaTulosListana());
            } else if (k.onkoLauseke()) {
                Lauseke l = (Lauseke) k;
                lista.addAll(l.palautaTulosListana());
            } else {
                lista.add(k);
            }
        }
        return lista;
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
