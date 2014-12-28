/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.laskin;

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
      
    public boolean lisaa(char c) {
        if (c==')') {
            return false;
        }
        
//        Termi termi = sisalto.get(sisalto.size()-1);
//        termi.lisaa(c);
        return false;
    }
    
    public void lisaa(Komponentti k) {
        sisalto.add(k);
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
    
    public boolean onkoSupistettu() {
        return supistettu;
    }
    
    public List<Komponentti> getLauseke() {
        return sisalto;
    }
}
