package induktoija4000.laskin.komponentit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Teemu
 */
public class Yhtalo {
    
    private List<Komponentti> vasenpuoli;
    private List<Komponentti> oikeapuoli;
    
    public Yhtalo() {
        vasenpuoli = new ArrayList<Komponentti>();
        oikeapuoli = new ArrayList<Komponentti>();
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
                toka.jaa(toka.getValue());
            }
            catch (Exception e) {
                System.out.println("ratkaisu meni nenilleen");
            }
        }
        if (vasenpuoli.size()==2 && oikeapuoli.size()==1) {
            try {
                Osatekija n2 = (Osatekija) vasenpuoli.get(0);
                Osatekija n = (Osatekija) vasenpuoli.get(1);
                Osatekija vakio = (Osatekija) oikeapuoli.get(0);
                vakio.lisaaMinus();
                this.ratkaiseToisenAsteenYhtalo(n2.getValue(), n.getValue(), vakio.getValue());
            }
            catch (Exception e) {
                System.out.println("ratkaisu meni nenilleen");
            }
        } 
    }
    
    public void ratkaiseToisenAsteenYhtalo(double a, double b, double c) {
        double determinantti, r1=0, r2=0;
        boolean mahdollinen = true;
        determinantti = b*b-4*a*c;
        if (determinantti > 0) {
            r1 = (-b+Math.sqrt(determinantti))/(2*a);
            r2 = (-b-Math.sqrt(determinantti))/(2*a);
        } else if (determinantti == 0) {
            r1 = r2 = -b/(2*a);
        } else {
            r1 = -b/(2*a);
            r2 = Math.sqrt(-determinantti)/(2*a);
            mahdollinen = false;
        }
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);
        
        if (mahdollinen) {
            System.out.println("metodimme laskee vastauksiksi:\n\tr1= " + df.format(r1) + "\n\tr2= " + df.format(r2));
        } else {
            System.out.println("metodimme laskee mahdottomiksi vastauksiksi:\n\tr1= " +
            df.format(r1) + "+" + df.format(r2) +"i\n\tr2= " + df.format(r1) + "-" + df.format(r2) +"i");
        }
    }
    
    public List<Komponentti> getVasenpuoli() {
        return vasenpuoli;
    }
    
    public List<Komponentti> getOikeapuoli() {
        return oikeapuoli;
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
