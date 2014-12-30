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
    
    private List<Komponentti> vasenpuoli;
    private List<Komponentti> oikeapuoli;
    private int paikka;
    private String s;
    
    public Yhtalo(String s) {
        vasenpuoli = new ArrayList<Komponentti>();
        oikeapuoli = new ArrayList<Komponentti>();
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
    
    public void lueKaikki() {
        List<Komponentti> lisattavalista = vasenpuoli;
        boolean nega = false;
        while (paikka<s.length()) {
            char c = lueMerkki();
            if ((c >= '0' && c <= '9')|| c == 'n' || c == 'x') {
                Osatekija ot = lueOsatekijaLoppuun();
                if (nega) {
                    ot.lisaaMinus();
                    nega = false;
                }
                lisattavalista.add(ot);
            } else if (c == '-') {
                nega = true;
            } else if (c == '*' || c == '(' || c=='/') {
                Termi termi = new Termi(lisattavalista.get(lisattavalista.size()-1), c);
                Komponentti k = lueTermiLoppuun(c);
                termi.lisaaTokatekija(k);
                lisattavalista.remove(lisattavalista.size()-1);
                lisattavalista.add(termi);
            } else if (c == '=') {
                lisattavalista = oikeapuoli;
            } else if (c == '+') {    
                
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
        char c = s.charAt(paikka);
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
            return new Osatekija(1, 1);
        } else if (var.isEmpty()) {
            return new Osatekija(Double.parseDouble(arvo), 0);
        } else {
            return new Osatekija(Double.parseDouble(arvo), 1);
        }
    }
   
    public boolean supistaKaikki() {
        return supista(vasenpuoli) && supista(oikeapuoli);
    }
    
    public boolean supista(List<Komponentti> lista) {
        boolean supistuiko = true;
        for (int i = 0; i < lista.size(); i++) {
            Komponentti k = lista.get(i);
            if (k.supista()==false) {
                supistuiko = false;
            } else {
                if (k.getClass().equals(new Termi().getClass())) {
                    Termi t = (Termi) k;
                    lista.add(i, t.getTulos());
                    lista.remove(i+1);
                }
            }
        }
        return supistuiko;
    }
      
    public void laskeYhteenKaikki() {
        laskeYhteen(vasenpuoli);
        laskeYhteen(oikeapuoli);
    }
    
    public void laskeYhteen(List<Komponentti> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Komponentti k = lista.get(i);
            for (int j = 0; j < lista.size(); j++) {
                if (i!=j && k.getClass().equals(new Osatekija(0,0).getClass())) {
                    try {
                        Osatekija eka = (Osatekija) k;
                        Osatekija toka = (Osatekija) lista.get(j);
                        if (eka.summaa(toka)) {
                            lista.add(i, eka);
                            lista.remove(i+1);
                            lista.remove(j);
                            j--;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("vitttttttuuuuu");
                    }
                }
            }
        }
    }
    
    public void jarjestaYhtalo() {
        List<Komponentti> jarjestettyoikea = jarjestaYhtalonPuoli(vasenpuoli, true);
        List<Komponentti> jarjestettyvasen = jarjestaYhtalonPuoli(oikeapuoli, false);
        vasenpuoli.addAll(jarjestettyvasen);
        oikeapuoli.addAll(jarjestettyoikea);
    }
    
    public List<Komponentti> jarjestaYhtalonPuoli(List<Komponentti> lista, boolean onkoVasenPuoli) {
        List<Komponentti> palautettava = new ArrayList<Komponentti>();
        for (int i = 0; i < lista.size(); i++) {
            Komponentti k = lista.get(i);
            if (k.getClass().equals(new Osatekija(0,0).getClass())) {
                Osatekija ot = (Osatekija) k;
                if ((ot.getVariable()==0 && onkoVasenPuoli) || (ot.getVariable()>0 && onkoVasenPuoli==false)) {
                    ot.lisaaMinus();
                    palautettava.add(ot);
                    lista.remove(i);
                    i--;
                }
            }
        }
        return palautettava;
    }
    
    public void ratkaiseYhtalo() {
        if (vasenpuoli.size()==1 && oikeapuoli.size()==1) {
            try {
                Osatekija eka = (Osatekija) oikeapuoli.get(0);
                Osatekija toka = (Osatekija) vasenpuoli.get(0);
                eka.jaa(toka.getValue());
                toka.alusta(1, 1);
            }
            catch (Exception e) {
                System.out.println("ratkaisu meni nenilleen");
            }
        }
    }
    
    public List<Komponentti> getYhtalo() {
        return vasenpuoli;
    }
    
    public void tulostaTyypit() {
        for (Komponentti k : vasenpuoli) {
            System.out.print(k.palautaTyyppi()+" ");
        }
        System.out.print("= ");
        for (Komponentti k : oikeapuoli) {
            System.out.print(k.palautaTyyppi() +" ");
        }
        
        System.out.println("");
    }
    
    public void tulostaKaikki() {
        for (Komponentti k : vasenpuoli) {
            System.out.print(k+" ");
        }
        System.out.print("= ");
        for (Komponentti k : oikeapuoli) {
            System.out.print(k+" ");
        }
        
        System.out.println("");
    }
}
