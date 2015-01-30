package induktoija4000.laskin;

import induktoija4000.komponentit.*;
import java.util.ArrayList;
import java.util.List;

public class Lukija {
    
    private String s;
    private int paikka;
    private Yhtalo yhtalo;
    
    public Lukija() {
        paikka=-1;
        yhtalo = new Yhtalo();
    }
    
    public Lukija(String s) {
        s=s;
        paikka=-1;
        yhtalo = new Yhtalo();
    }
    
    public void annaSyote(String s) {
        this.s=s;
    }
    
    public Yhtalo lueKaikki() {
        List<Komponentti> lista = lueMerkkiinAsti('=');
        yhtalo.annaVasenPuoli(lista);
        lista = lueMerkkiinAsti('Å');
        yhtalo.annaOikeaPuoli(lista);
        return yhtalo;
    }
    
    public List<Komponentti> lueMerkkiinAsti(char merkki) {
        List<Komponentti> lista = new ArrayList<>();
        boolean nega = false;
        while (paikka<s.length()) {
            char c = lueMerkki();
            if ((c >= '0' && c <= '9')|| c == 'n' || c == 'x') {
                Osatekija ot = lueOsatekijaLoppuun();
                if (nega) {
                    ot.muutaNegatiiviseksi();
                    nega = false;
                }
                lista.add(ot);
            } else if (c == '-') {
                nega = true;
            } else if (c == '*' || c=='/' || c == '(') {
                if (c == '(' && (lueMerkki(paikka-1)=='+' || lueMerkki(paikka-1)=='-' || lueMerkki(paikka-1)=='=')) {
                    Lauseke lause = lueLauseke();
                    lista.add(lause);
                } else {
                    Termi termi = new Termi(lista.get(lista.size()-1), c, lueTermiLoppuun(c));
                    lista.remove(lista.size()-1);
                    lista.add(termi);
                }
            } else if (c == '=' && merkki == '=') {
                return lista;
            } else if (c == ')' && merkki == ')') {
                return lista;
            } else if (c == '+') {
            } else if (c == 's' && lueMerkki() == 'i' && lueMerkki() == 'g') {
                Summa summa = lueSummaLoppuun();
                lista.add(summa);
            } else {
                System.out.println("paskaa syötit. hyi hyi.");
            }
        }
        return lista;
    }
    
    public Lauseke lueLauseke() {
        Lauseke lauseke = new Lauseke();
        List<Komponentti> lista = lauseke.getLauseke();
        lista = lueMerkkiinAsti(')');
        return lauseke;
    }
    
    /*
    public Yhtalo lue() {
        List<Komponentti> lista = yhtalo.getVasenpuoli();
        boolean nega = false;
        while (paikka<s.length()) {
            char c = lueMerkki();
            if ((c >= '0' && c <= '9')|| c == 'n' || c == 'x') {
                Osatekija ot = lueOsatekijaLoppuun();
                if (nega) {
                    ot.muutaNegatiiviseksi();
                    nega = false;
                }
                lista.add(ot);
            } else if (c == '-') {
                nega = true;
            } else if (c == '*' || c=='/' || c == '(') {
                if (c == '(' && (lueMerkki(paikka-1)=='+' || lueMerkki(paikka-1)=='-' || lueMerkki(paikka-1)=='=')) {
                    Lauseke lause = lueLausekeLoppuun();
                    lista.add(lause);
                } else {
                    Termi termi = new Termi(lista.get(lista.size()-1), c, lueTermiLoppuun(c));
                    lista.remove(lista.size()-1);
                    lista.add(termi);
                }
            } else if (c == '=') {
                lista = yhtalo.getOikeapuoli();
            } else if (c == '+' ) {
            } else {
                System.out.println("paskaa syötit. hyi hyi.");
            }
        }
        return yhtalo;
    }
    
    public Lauseke lueLausekeLoppuun() {
        Lauseke lauseke = new Lauseke();
        List<Komponentti> lista = lauseke.getLauseke();
        boolean nega = false;
        while (paikka<s.length()) {
            char c = lueMerkki();
            if ((c >= '0' && c <= '9')|| c == 'n' || c == 'x') {
                Osatekija ot = lueOsatekijaLoppuun();
                if (nega) {
                    ot.muutaNegatiiviseksi();
                    nega = false;
                }
                lista.add(ot);
            } else if (c == '-') {
                nega = true;
            } else if (c == '*' || c=='/' || c == '(') {
                if (c == '(' && (lueMerkki(paikka-1)=='+' || lueMerkki(paikka-1)=='-' || lueMerkki(paikka-1)=='=')) {
                    Lauseke lause = lueLausekeLoppuun();
                    lista.add(lause);
                } else {
                    Termi termi = new Termi(lista.get(lista.size()-1), c, lueTermiLoppuun(c));
                    lista.remove(lista.size()-1);
                    lista.add(termi);
                }
            } else if (c == ')') {
                return lauseke;
            } else if (c == '+') {
            } else {
                System.out.println("paskaa syötit. hyi hyi.");
            }
        }
        System.out.println("puuttuva )-sulku(!)");
        return lauseke;
    }*/
    
    // sig(n+1,i=3,n+3)
    public Summa lueSummaLoppuun() {
        List<Komponentti> lista = new ArrayList<>();
        Osatekija ot = new Osatekija(0,0);
        if (lueMerkki(paikka)=='(') {
            lueMerkki();
        }
        while (s.charAt(paikka) != ',') {
            Osatekija ott = lueOsatekijaLoppuun();
            lista.add(ott);
        }
        paikka++;
        if (lueMerkki(paikka)=='i' && lueMerkki(paikka+1) == '=') {
            paikka++;
            paikka++;
            ot = lueOsatekijaLoppuun();
        }
        Summa summa = new Summa(lista, (int) ot.getValue());
        return summa;
    }
        
    public Komponentti lueTermiLoppuun(char c) {
        if (c == '(' || lueMerkki(paikka + 1) == '(') {
            lueMerkki();
            return lueLauseke();
        } else {
            lueMerkki();
            return lueOsatekijaLoppuun();
        }
        
    }
    
    public Osatekija lueOsatekijaLoppuun() {
        String arvo = "", muuttuja = "";
        char c = s.charAt(paikka);
        while ((c >= '0' && c <= '9') || c == '.') {
            arvo += c;
            c = lueMerkki();
        }
        while (c == 'n' || c == 'x') {
            muuttuja += c;
            c = lueMerkki();
        }
//        if (c == '^') {
//            lueExponentti();
//        }
        if (paikka!=s.length()) {
            paikka--;
        }
        if (arvo.isEmpty()) {
            return new Osatekija(1, 1);
        } else if (muuttuja.isEmpty()) {
            return new Osatekija(Double.parseDouble(arvo), 0);
        } else {
            return new Osatekija(Double.parseDouble(arvo), 1);
        }
    }
    
    public char lueMerkki() {
        paikka++;
        if (paikka<s.length()) {
            while (Character.isWhitespace(s.charAt(paikka))) {
                paikka++;
            }
            return s.charAt(paikka);
        }
        return 'Q';
    }
    
    public char lueMerkki(int i) {
        if (i<s.length() && i>=0) {
            while (Character.isWhitespace(s.charAt(i))) {
                i--;
            }
            return s.charAt(i);
        }
        return 'Q';
    }
}
