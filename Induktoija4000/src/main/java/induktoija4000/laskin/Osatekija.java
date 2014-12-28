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
public class Osatekija implements Komponentti{
    
    private double arvo;
    private double muuttuja;
    private char variable;
    private boolean supistettu;
    private String sisalto;
    
    public Osatekija() {
        sisalto = "";
        arvo=0;
        muuttuja=0;
    }
    
    public Osatekija(double a, double m) {
        sisalto = "";
        arvo = a;
        muuttuja = m;
    }
    
    public void alusta(double a, double m) {
        arvo = a;
        muuttuja = m;
    }
    
    public boolean lisaa(char c) {
        sisalto += c;
        return true;
    }
    
    public void lisaaMinus() {
        sisalto = "-" + sisalto;
    }
    
    public boolean supista() {
        if (supistettu) {
            return true;
        }
        muutaArvoksi();
        return true;
    }
    
    public boolean summaa(Osatekija ot) {
        this.supista();
        ot.supista();
        if (this.muuttuja==ot.getVariable()) {
            arvo += ot.getValue();
            return true;
        }
        return false;
    }
    
    public Osatekija jaa(Osatekija ot) {
        this.supista();
        ot.supista();
        this.arvo /= ot.getValue();
        this.muuttuja -= ot.getVariable();
        return this;
    }
    
    public Osatekija kerro(Osatekija ot) {
        this.supista();
        ot.supista();
        this.arvo *= ot.getValue();
        this.muuttuja += ot.getVariable();
        return this;
    }
    
    public void muutaArvoksi() {
        String sarvo = "";
        String smuuttuja = "";
        boolean neg = false;
        for (int i = 0; i < sisalto.length(); i++) {
            char c = sisalto.charAt(i);
            if ((c >= '0' && c<= '9') || c == '.') {
                sarvo += c;
            } else if (c == '-') {
              neg = true;  
            } else {
                smuuttuja += c;
            }
        }
        if (sarvo.length()!=0) {
            arvo = Double.parseDouble(sarvo);
            if (neg) {
                arvo *= -1.0;
            }
        }
        if (smuuttuja.length()==1) {
            //variable = smuuttuja.charAt(0);
            muuttuja = 1;
        }
        supistettu = true;
    }
    
    public double getValue() {
        return arvo;
    }
    
    public double getVariable() {
        return muuttuja;
    }
    
    public String toString() {
        return sisalto;
    }
}
