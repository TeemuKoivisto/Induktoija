/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.turhat;

/**
 *
 * @author Teemu
 */
public class Luku implements Merkki{
    
    private double arvo;
    private double muuttuja;
    
    public Luku(double arvo) {
        this.arvo=arvo;
        muuttuja=0;
    }
    
    public Luku(double arvo, double a) {
        this.arvo=arvo;
        muuttuja=a;
    }
    
    public void lisaaMinus() {
        arvo *= -1;
    }
    
    public boolean plussaa(Luku luku) {
        if (luku.getMuuttuja()==this.muuttuja) {
            arvo += luku.getArvo();
            return true;
        }
        return false;
    }
    
    public boolean vahenna(Luku luku) {
        if (luku.getMuuttuja()==this.muuttuja) {
            arvo -= luku.getArvo();
            return true;
        }
        return false;
    }
    
    public void jaa(Luku luku) {
        arvo = arvo / luku.getArvo();
        muuttuja -= luku.getMuuttuja();
    }
    
    public void kerro(Luku luku) {
        arvo = arvo * luku.getArvo();
        muuttuja += luku.getMuuttuja();
    }
    
    public void kerro(Double d) {
        arvo = arvo * d;
    }
    
    public double getArvo() {
        return arvo;
    }
    
    public double getMuuttuja() {
        return muuttuja;
    }
    
    public Symboli getTyyppi() {
        if (muuttuja==0) {
            return Symboli.NUMERO;
        } else {
            return Symboli.MUUTTUJA;
        }
    }
    
    @Override
    public String toString() {
        if (muuttuja==0) {
            return "" + arvo;
        } else if (muuttuja==1) {
            return "" + arvo + "n";
        } else {
            return "" + arvo + "n^2";
        }
    }
}
