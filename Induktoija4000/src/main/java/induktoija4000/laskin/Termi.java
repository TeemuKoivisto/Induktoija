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
public class Termi implements Komponentti{
    
    private Komponentti ekaTekija;
    private Komponentti tokaTekija;
    private Osatekija tulos;
    private boolean supistettu;
    private char tyyppi;
    
    public Termi() {
        supistettu = false;
    }
    
    public Termi(Komponentti k, char c) {
        ekaTekija = k;
        tyyppi = c;
        supistettu = false;
    }
    
    public void lisaaTokatekija(Komponentti k) {
        tokaTekija = k;
    }
    
    public boolean summaa(Osatekija ot) {
        if (supistettu) {
            if (tulos.summaa(ot)==true) {
                return true;
            }
        }
        return false;
    }
    
    
    public boolean toimi() {
        Osatekija ekaOt;
        Osatekija tokaOt;
        try {
            //tässä virhe
            ekaOt = (Osatekija) ekaTekija;
            tokaOt = (Osatekija) tokaTekija;
        } catch (Exception e) {
            return false;
        }
        if (tyyppi == '*' || tyyppi == '(') {
            tulos = ekaOt.kerro(tokaOt);
            supistettu = true;
            return true;
        } else {
            tulos = ekaOt.jaa(tokaOt);
            supistettu = true;
            return true;
        }
    }
    
    public boolean supista() {
        if (ekaTekija.supista()) {
            if (tokaTekija.supista()) {
                return toimi();
            }
        }
        return false;
    }
    
    /*
    public boolean supista() {
        if (ekaTekija.getClass().equals(new Osatekija().getClass())) {
            Osatekija ekaOt = (Osatekija) ekaTekija;
            if (tokaTekija.getClass().equals(new Osatekija().getClass())) {
                Osatekija tokaOt = (Osatekija) tokaTekija;
                if (tyyppi == '*' || tyyppi == '(') {
                    tulos = ekaOt.kerro(tokaOt);
                    return true;
                } else {
                    tulos = ekaOt.jaa(tokaOt);
                    return true;
                }
            } else {
                if (tokaTekija.getClass().equals(this.getClass())) {
                    Termi tokaTermi = (Termi) tokaTekija;
                    if (tokaTermi.supista()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    Lauseke tokaLause = (Lauseke) tokaTekija;
                    if (tokaLause.supista()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            if (ekaTekija.getClass().equals(this.getClass())) {
                Termi ekaTermi = (Termi) ekaTekija;
                if (ekaTermi.supista()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                Lauseke tokaLause = (Lauseke) tokaTekija;
                if (tokaLause.supista()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }*/
    
    public Osatekija getTulos() {
        if (supistettu) {
            return tulos;
        } else {
            System.out.println("hei mitä vittua");
            return new Osatekija();
        }
    }
    
    public boolean onkoSupistettu() {
        return supistettu;
    }
    
    public boolean lisaa(char c) {
        return true;
    }
    
    public String toString() {
        if (supistettu) {
            return "" + tulos;
        }
        return "" + ekaTekija + tyyppi + tokaTekija;
    }
}
