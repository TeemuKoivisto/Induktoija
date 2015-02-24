/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.laskin;

import induktoija4000.komponentit.*;
import java.util.Comparator;

public class KomponenttiComparator implements Comparator<Komponentti>{

    @Override
    public int compare(Komponentti verrattava, Komponentti vertaaja) {
        if (verrattava.sisaltaakoMuuttujan() && !vertaaja.sisaltaakoMuuttujan()) {
            return -1;
        } else if (verrattava.sisaltaakoMuuttujan() && vertaaja.sisaltaakoMuuttujan()) {
            // kumman muuttuja on isompi?
            if (verrattava.onkoTermi() && vertaaja.onkoTermi()) {
                Termi eka = (Termi) verrattava;
                Termi toka = (Termi) vertaaja;
                if (eka.getMuuttuja() > toka.getMuuttuja()) {
                    return -1;
                } else if (eka.getMuuttuja() == toka.getMuuttuja()) {
                    return 0;
                } else {
                    return 1;
                }
            }
            return 0;
        } else if (!verrattava.sisaltaakoMuuttujan() && vertaaja.sisaltaakoMuuttujan()) {
            return 1;
        } else {
            return 0;
        }
    }
    
}
