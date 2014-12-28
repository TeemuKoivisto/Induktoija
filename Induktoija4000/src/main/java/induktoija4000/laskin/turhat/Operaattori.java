/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.laskin.turhat;

/**
 *
 * @author Teemu
 */
public class Operaattori implements Merkki{
    
    private Symboli tyyppi;
    
    public Operaattori(Symboli t) {
        tyyppi=t;
    }
    
    public Symboli getTyyppi() {
        return tyyppi;
    }
    
    public String toString() {
        if (tyyppi.equals(Symboli.PLUS)) {
            return "+";
        } else if (tyyppi.equals(Symboli.MINUS)) {
            return "-";
        } else if (tyyppi.equals(Symboli.JAKO)) {
            return "/";
        } else if (tyyppi.equals(Symboli.KERTO)) {
            return "*";
        } else if (tyyppi.equals(Symboli.YHTAKUIN)) {
            return "=";
        } else {
            return "nullprkle";
        }
    }
}
