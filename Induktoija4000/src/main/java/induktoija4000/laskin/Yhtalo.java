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
public class Yhtalo {
    
    private List<Komponentti> sisalto;
    private int paikka;
    private String s;
    
    public Yhtalo(String s) {
        sisalto = new ArrayList<Komponentti>();
        paikka = -1;
        this.s=s;
    }
    
    public char lueMerkki() {
        paikka++;
        if (paikka<s.length()) {
            return s.charAt(paikka);
        }
        return 'Q';
    }
    
    public void lisaaKaikki() {
        boolean nega = false;
        while (paikka<s.length()) {
            char c = lueMerkki();
            if ((c >= '0' && c <= '9')|| c == 'n' || c == 'x') {
                Osatekija ot = lueOsatekijaLoppuun();
                if (nega) {
                    ot.lisaaMinus();
                    nega = false;
                }
                sisalto.add(ot);
            } else if (c == '-') {
                nega = true;
            } else if (c == '*' || c == '(' || c=='/') {
                Termi termi = new Termi(sisalto.get(sisalto.size()-1), c);
                Komponentti k = lueTermiLoppuun(c);
                termi.lisaaTokatekija(k);
                sisalto.remove(sisalto.size()-1);
                sisalto.add(termi);
            } else if (c == '=' || c == '+') {
                // periaatteessa vain uusi lauseke, joka negatiivinen 
                // sekä muista ')'
            } else {
                System.out.println("paskaa syötit. hyi hyi.");
            }
        }
    }
    /*
    public Lauseke lisaaLauseke() {
        Lauseke lauseke = new Lauseke();
        List<Komponentti> laussisalto = lauseke.getLauseke();
        while (paikka<s.length()) {
            paikka++;
            char c = s.charAt(paikka);
            if (c >= '0' && c <= '9'|| c == 'n' || c == 'x') {
                Osatekija ot = lueOsatekijaLoppuun(c);
                laussisalto.add(ot);
            } else if (c == '-') {
                Osatekija ot = lueOsatekijaLoppuun(c);
                ot.lisaaMinus();
                laussisalto.add(ot);
            } else if (c == '*' || c == '(' || c=='/') {
                Termi termi = new Termi(laussisalto.get(laussisalto.size()-1), c);
                
                
            }
        }
        return lauseke;
    }*/
    
    public Komponentti lueTermiLoppuun(char c) {
        if (c == '(') {
            lueMerkki();
            return lueOsatekijaLoppuun();
            // return lueLausekeLoppuun();
        } else {
            lueMerkki();
            return lueOsatekijaLoppuun();
        }
        
    }
    
    public Osatekija lueOsatekijaLoppuun() {
        String arvo = "";
        String var = "";
        arvo += s.charAt(paikka);
        char c = lueMerkki();
        while ((c >= '0' && c <= '9') || c == '.') {
            arvo += c;
            c = lueMerkki();
        }
        while (c == 'n' || c == 'x') {
            var += c;
            c = lueMerkki();
        }
        if (paikka!=s.length()) {
            paikka--;
        }
        if (arvo.isEmpty()) {
            return new Osatekija(0, 1);
        } else if (var.isEmpty()) {
            return new Osatekija(Double.parseDouble(arvo), 0);
        } else {
            return new Osatekija(Double.parseDouble(arvo), 1);
        }
    }
   
    public boolean supistaKaikki() {
        boolean supistuiko = true;
        for (Komponentti k : sisalto) {
            if (k.supista()==false) {
                supistuiko = false;
            }
        }
        return supistuiko;
    }
    
    public void laskeYhteen() {
        for (Komponentti k : sisalto) {
            k.summaa(null);
        }
    }
    
    public List<Komponentti> getYhtalo() {
        return sisalto;
    }
    
    public void tulostaKaikki() {
        System.out.println("perkele");
        for (Komponentti k : sisalto) {
            System.out.print(k+" ");
        }
        System.out.println("");
    }
}
