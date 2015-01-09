package induktoija4000.laskin.komponentit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Teemu
 */
public class Lauseke implements Komponentti{

    private List<Komponentti> sisalto;
    private boolean supistettu;
    
    public Lauseke() {
        sisalto = new ArrayList<Komponentti>();
        supistettu = false;
    }      
  
    public void lisaa(Komponentti k) {
        sisalto.add(k);
    }
    
    public boolean kerro(Osatekija ot) {
        boolean onnistuiko = true;
        for (Komponentti k : sisalto) {
            if (k.kerro(k)==false) {
                onnistuiko = false;
            }
        }
        return onnistuiko;
    }
    
    public boolean kerro(Komponentti k) {
        // (3-x)*7
        if (k.getClass().equals(new Osatekija(0,0).getClass())) {
            return kerro((Osatekija) k);
        }
        // (3+x)*3/5        (3+x)*3*3 >> ((l * ot)t * ot)
        // ainoa vaihtoehto siis, että jo supistettu termi?
        if (k.getClass().equals(new Termi(k, '*', k).getClass())) {
            Termi t = (Termi) k;
            if (t.supista()) {
                return kerro(t.getTulos());
            } else {
                return false;
            }
        }
        // (3+x)(3+x)
        if (k.getClass().equals(this.getClass())) {
            Lauseke l = (Lauseke) k;
            boolean onnistuiko = true;
            for (Komponentti kom : sisalto) {
                for (Komponentti kom2 : l.getLauseke()) {
                    if (kom.kerro(kom2)==false) {
                        onnistuiko = false;
                    }
                }
            }
            return onnistuiko;
        }
        return false;
    }
    
    public boolean jaa(Komponentti k) {
        // (3-x)/7
        if (k.getClass().equals(new Osatekija(0,0).getClass())) {
            return jaa((Osatekija) k);
            // enta jos (3+x)/3x >> 1/x + 1/3
        }
        // jo supistettu termi?
        if (k.getClass().equals(new Termi(k, '*', k).getClass())) {
            Termi t = (Termi) k;
            if (t.supista()) {
                return jaa(t.getTulos());
            } else {
                return false;
            }
        }
        // (3+x)/(3+x)
        // 3/3 + 3/x + x/3 + x/x
        // 1 + x^-1/3 + 1/3x + 1
        //
        // 3/(3+x) + x/(3+x)
        if (k.getClass().equals(this.getClass())) {
            Lauseke l = (Lauseke) k;
            boolean onnistuiko = true;
            for (Komponentti kom : sisalto) {
                for (Komponentti kom2 : l.getLauseke()) {
                    if (kom.kerro(kom2)==false) {
                        onnistuiko = false;
                    }
                }
            }
            return onnistuiko;
        }
        return false;
    }
    
    public boolean summaa(Osatekija ot) {
        if (supistettu) {
            //nyt lausekkeen pitaisi olla pelkka osatekija
            
        }
        return false;
    }
    
    public boolean supista() {
        boolean supistuiko = true;
        for (Komponentti k : sisalto) {
            if (k.supista()==false) {
                supistuiko = false;
            }
        }
        supistettu = true;
        return supistuiko;
    }
    
    public boolean onkoLausekeYksiKomponentti() {
        return sisalto.size()==1;
    }
    
    public boolean onkoSupistettu() {
        return supistettu;
    }
    
    public List<Komponentti> getLauseke() {
        return sisalto;
    }
    
    public String palautaTyyppi() {
        return "l";
    }
    
    public String toString() {
        String tuloste = "(";
        for (Komponentti k : sisalto) {
            if (k.palautaTyyppi().equals("t")) {
                tuloste += " ";
            }
            tuloste += k.toString();
            
        }
        tuloste += ")";
        return tuloste;
    }
}