/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.laskin;

import induktoija4000.komponentit.Termi;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Teemu
 */
public class TermiComparator implements Comparator<Termi>{

    @Override
    public int compare(Termi verrattava, Termi vertaaja) {
        if (verrattava.getMuuttuja() > vertaaja.getMuuttuja()) {
            return -1;
        } else if (verrattava.getMuuttuja() == vertaaja.getMuuttuja()) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
