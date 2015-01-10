/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.laskin;

import induktoija4000.komponentit.Osatekija;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Teemu
 */
public class OsatekijaComparator implements Comparator<Osatekija>{
/*
    private List<Osatekija> osatekijat;
    
    public OsatekijaComparator(List<Osatekija> ot) {
        osatekijat=ot;
    }
  */  
    @Override
    public int compare(Osatekija verrattava, Osatekija vertaaja) {
        if (verrattava.getVariable() > vertaaja.getVariable()) {
            return -1;
        } else if (verrattava.getVariable() == vertaaja.getVariable()) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
