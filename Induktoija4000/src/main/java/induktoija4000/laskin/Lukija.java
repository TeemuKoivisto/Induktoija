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
                Termi t = lueTermiLoppuun();
                if (nega) {
                    t.muutaNegatiiviseksi();
                    nega = false;
                }
                lista.add(t);
            } else if (c == '-') {
                nega = true;
            } else if (c == '*' || c=='/' || c == '(') {
                char e = lueMerkki(paikka-1);
//                boolean mitvit = (!(e >= '0' && e <= '9') && e != 'n' && e != 'x') && e!=')';
                if (c == '(' && (lista.isEmpty() || (!(e >= '0' && e <= '9') && e != 'n' && e != 'x') && e!=')')) {
                    Lauseke lause = lueLauseke();
                    if (nega) {
                        lause.muutaNegatiiviseksi();
                        nega = false;
                    }
                    lista.add(lause);
                } else {
//                    if (c=='(') {
//                        c='*';
//                    }
                    Laskutoimitus la = new Laskutoimitus(lista.get(lista.size()-1), c, lueLaskutoimitusLoppuun(c));
                    lista.remove(lista.size()-1);
                    lista.add(la);
                }
            } else if (c == '=' && merkki == '=') {
                return lista;
            } else if (c == ')' && merkki == ')') {
                return lista;
            } else if (c == '+') {
            } else if (c == 's' && lueMerkki() == 'i' && lueMerkki() == 'g') {
                lueMerkki();
                Summa summa = lueSummaLoppuun();
                lista.add(summa);
            } else {
                System.out.println("paskaa syötit. hyi hyi.");
            }
        }
        return lista;
    }
    
    public Lauseke lueLauseke() {
        List<Komponentti> lista = lueMerkkiinAsti(')');
        Lauseke lauseke = new Lauseke(lista);
        return lauseke;
    }
    
    // sig(n,0,n+3)
    public Summa lueSummaLoppuun() {
        Termi ylaraja = new Termi(0, 0), alaraja = new Termi(0, 0);
        if (lueMerkki(paikka)=='(') {
            lueMerkki();
        }
        ylaraja = lueTermiLoppuun();
        lueMerkki();
        if (lueMerkki(paikka)==',') {
            lueMerkki();
        }
        alaraja = lueTermiLoppuun();
        lueMerkki();
        Lauseke lauseke = lueLauseke();
        return new Summa(ylaraja, (int) alaraja.getArvo(), lauseke);
    }
        
    public Komponentti lueLaskutoimitusLoppuun(char c) {
        if (c == '(') {
            return lueLauseke();
        } else if (lueMerkki(paikka + 1) == '(') {
            // c=='/' || c=='*'
            lueMerkki();
            return lueLauseke();
        } else {
            lueMerkki();
            return lueTermiLoppuun();
        }
    }
    
    public Termi lueTermiLoppuun() {
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
            return new Termi(1, 1);
        } else if (muuttuja.isEmpty()) {
            return new Termi(Double.parseDouble(arvo), 0);
        } else {
            return new Termi(Double.parseDouble(arvo), 1);
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