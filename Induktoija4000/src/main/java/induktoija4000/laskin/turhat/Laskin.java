package induktoija4000.laskin.turhat;

import static induktoija4000.laskin.turhat.Symboli.*;
import java.util.ArrayList;
import java.util.List;

public class Laskin {
    
    private String syote;
    private int paikka;
    private double vakio;
    private double muuttuja;
    private boolean onkoLaskettava;
    private List<Merkki> lauseke;
    
    public void annaSyote(String s) {
        syote=s;
        paikka=0;
        vakio=0;
        muuttuja=0;
        lauseke = new ArrayList<Merkki>();
        onkoLaskettava=lueSyote();
    }
    
    public boolean lueSyote() {
        Symboli viimeisinTyyppi = NULL;
        while(paikka<syote.length()) {
            String smerkki = Character.toString(syote.charAt(paikka));
            Symboli tyyppi = tarkistaTyyppi(smerkki);
            if (tyyppi==Symboli.NULL) {
                System.out.println("Syötteesi oli perseestä. Kirjaimellisesti.");
                return false;
            } else if (tyyppi==Symboli.NUMERO) {
                Luku luku = lueKunnesNumerotLopppuu();
                lauseke.add(luku);
                viimeisinTyyppi = NUMERO;
            } else if (tyyppi==MUUTTUJA) {
                viimeisinTyyppi = MUUTTUJA;
                paikka++;
            } else {
                paikka++;
                if (viimeisinTyyppi == NUMERO) {
                    lauseke.add(new Operaattori(tyyppi));
                    viimeisinTyyppi=tyyppi;
                } else if (viimeisinTyyppi == MUUTTUJA) {
                    lauseke.add(new Operaattori(tyyppi));
                    viimeisinTyyppi=tyyppi;
                } else {
                    System.out.println("Laskutoimitus paskana. GZ.");
                    return false;
                }
            }
        }
        paikka = 0;
        return true;
    }
    
    public boolean lueSyoteNew() {
        Symboli viimeisinTyyppi = NULL;
        boolean negatiivinen = false;
        while(paikka<syote.length()) {
            String smerkki = Character.toString(syote.charAt(paikka));
            Symboli tyyppi = tarkistaTyyppi(smerkki);
            if (tyyppi==Symboli.NULL) {
                System.out.println("Syötteesi oli perseestä. Kirjaimellisesti.");
                return false;
            } else if (tyyppi==Symboli.NUMERO) {
                Luku luku = lueKunnesNumerotLopppuu();
                if (negatiivinen) {
                    luku.lisaaMinus();
                    negatiivinen = false;
                }
                lauseke.add(luku);
                viimeisinTyyppi = NUMERO;
            } else if (tyyppi==MUUTTUJA) {
                viimeisinTyyppi = MUUTTUJA;
                paikka++;
            } else if (tyyppi==KERTO || tyyppi==JAKO) {
            } else {
                paikka++;
                if (viimeisinTyyppi == NUMERO || viimeisinTyyppi == MUUTTUJA) {
                    lauseke.add(new Operaattori(tyyppi));
                    viimeisinTyyppi=tyyppi;
                } else {
                    System.out.println("Laskutoimitus paskana. GZ.");
                    return false;
                }
            }
        }
        paikka = 0;
        return true;
    }
    
    public void laskeLauseke() {
        if (onkoLaskettava==false) {
            return;
        }
        boolean laskettu = false;
        while(laskettu==false) {
            laskeKertoJaJakoLaskut();
            this.tulostaLauseke();
            laskePlusJaMinusLaskut();
            this.tulostaLauseke();
            jarjestaLauseke();
            laskettu=true;
        }
    }
    
    public void laskeKertoJaJakoLaskut() {
        for (int i = 0; i < lauseke.size(); i++) {
            Merkki merkki = lauseke.get(i);
            Symboli tyyppi = merkki.getTyyppi();
            if (tyyppi==KERTO||tyyppi==JAKO) {
                Luku luku = (Luku) lauseke.get(i-1);
                Luku laku = (Luku) lauseke.get(i+1);
                if (tyyppi==JAKO) {
                    luku.jaa(laku);
                    lauseke.set(i-1, luku);
                    lauseke.remove(i);
                    lauseke.remove(i);
                    i--;
                } else {
                    luku.kerro(laku);
                    lauseke.set(i-1, luku);
                    lauseke.remove(i);
                    lauseke.remove(i);
                    i--;
                }
            }
        }
    }
    
    public void laskePlusJaMinusLaskut() {
        for (int i = 0; i < lauseke.size(); i++) {
            Merkki merkki = lauseke.get(i);
            Symboli tyyppi = merkki.getTyyppi();
            if (tyyppi==PLUS||tyyppi==MINUS) {
                Luku luku;
                Luku laku;
                try {
                    luku = (Luku) lauseke.get(i-1);
                    laku = (Luku) lauseke.get(i+1);
                }
                catch (Exception e) {
                    System.out.println("Vaara MINUS tai PLUS lasku");
                    continue;
                }
                if (tyyppi==PLUS) {
                    boolean onnistuiko = luku.plussaa(laku);
                    if (onnistuiko) {
                        lauseke.set(i-1, luku);
                        lauseke.remove(i);
                        lauseke.remove(i);
                        i--;
                    }
                } else {
                    boolean onnistuiko = luku.vahenna(laku);
                    if (onnistuiko) {
                        lauseke.set(i-1, luku);
                        lauseke.remove(i);
                        lauseke.remove(i);
                        i--;
                    }
                }
            }
        }
    }
    
    public void jarjestaLausekeNew() {
        boolean oikeaPuoli = false;
        for (int i = 0; i < lauseke.size(); i++) {
            Merkki merkki = lauseke.get(i);
            Symboli tyyppi = merkki.getTyyppi();
            if (tyyppi==YHTAKUIN) {
                oikeaPuoli=true;
                continue;
            }
            if (tyyppi==NUMERO) {
                Luku luku = (Luku) merkki;
                Operaattori etumerkki = new Operaattori(NULL);
                try {
                    etumerkki = (Operaattori) lauseke.get(i-1);
                }
                catch (Exception e) {
                    luku.kerro(-1.0);
                    lauseke.add(luku);
                    lauseke.remove(i);
                    i--;
                }
                if (etumerkki.getTyyppi()==PLUS) {
                    luku.kerro(-1.0);
                    lauseke.add(luku);
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    i--;
                } else if (etumerkki.getTyyppi()==MINUS) {
                    lauseke.add(luku);
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    i--;
                } else if (etumerkki.getTyyppi()==YHTAKUIN) {
                    oikeaPuoli=true;
                }
            }
            if (tyyppi==MUUTTUJA&&oikeaPuoli) {
                Luku luku = (Luku) merkki;
                Operaattori sign = new Operaattori(NULL);
                try {
                    sign = (Operaattori) lauseke.get(i-1);
                }
                catch (Exception e) {
                    luku.kerro(-1.0);
                    lauseke.remove(i);
                    lauseke.add(0, luku);
                }
                if (sign.getTyyppi()==PLUS) {
                    luku.kerro(-1.0);
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    lauseke.add(0, luku);
                } else if (sign.getTyyppi()==MINUS) {
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    lauseke.add(0, luku);
                } else if (sign.getTyyppi()==YHTAKUIN) {
                    luku.kerro(-1.0);
                    lauseke.remove(i);
                    lauseke.add(0, luku);
                }
            }
        }    
    }
    
    public void jarjestaLauseke() {
        boolean oikeaPuoli = false;
        for (int i = 0; i < lauseke.size(); i++) {
            Merkki merkki = lauseke.get(i);
            Symboli tyyppi = merkki.getTyyppi();
            if (tyyppi==YHTAKUIN) {
                oikeaPuoli=true;
                continue;
            }
            if (tyyppi==NUMERO&&oikeaPuoli==false) {
                Luku luku = (Luku) merkki;
                Operaattori sign = new Operaattori(NULL);
                try {
                    sign = (Operaattori) lauseke.get(i-1);
                }
                catch (Exception e) {
                    luku.kerro(-1.0);
                    lauseke.add(luku);
                    lauseke.remove(i);
                    i--;
                }
                if (sign.getTyyppi()==PLUS) {
                    luku.kerro(-1.0);
                    lauseke.add(luku);
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    i--;
                } else if (sign.getTyyppi()==MINUS) {
                    lauseke.add(luku);
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    i--;
                } else if (sign.getTyyppi()==YHTAKUIN) {
                    oikeaPuoli=true;
                }
            }
            if (tyyppi==MUUTTUJA&&oikeaPuoli) {
                Luku luku = (Luku) merkki;
                Operaattori sign = new Operaattori(NULL);
                try {
                    sign = (Operaattori) lauseke.get(i-1);
                }
                catch (Exception e) {
                    luku.kerro(-1.0);
                    lauseke.remove(i);
                    lauseke.add(0, luku);
                }
                if (sign.getTyyppi()==PLUS) {
                    luku.kerro(-1.0);
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    lauseke.add(0, luku);
                } else if (sign.getTyyppi()==MINUS) {
                    lauseke.remove(i);
                    lauseke.remove(i-1);
                    lauseke.add(0, luku);
                } else if (sign.getTyyppi()==YHTAKUIN) {
                    luku.kerro(-1.0);
                    lauseke.remove(i);
                    lauseke.add(0, luku);
                }
            }
        }    
    }
    
    public void ratkaiseLauseke() {
        //while kunnes n==1
    }
    
    public boolean tarkistaSyote() {
        for (int i = 0; i < syote.length(); i++) {
            String smerkki = Character.toString(syote.charAt(i));
            if (tarkistaTyyppi(smerkki)==Symboli.NULL) {
                System.out.println("Paska syöte. Vedä käteen homo.");
                return false;
                //lisaa kahden perakkaisen laskuoperaattorin tarkistus
                //ja n3n -tyyppisten mokailujen
                //eli numerorivit paattyvat aina toiseen operaattoriin
                //ja jos tuo operaattori on n, niin seuraavan on oltava
                //joku laskuoperaattori
            }
        }
        System.out.println("Everything seems in order.");
        return true;
    }
    
    public Luku lueKunnesNumerotLopppuu() {
        String numeroita = "";
        Luku luku;
        double muuttuja = 0;
        while(paikka<syote.length()) {
            String merkki = Character.toString(syote.charAt(paikka));
            Symboli tyyppi = tarkistaTyyppi(merkki);
            if (tyyppi.equals(Symboli.NUMERO)) {
               numeroita += merkki;
               paikka++;
            } else if (tyyppi.equals(MUUTTUJA)){
                muuttuja++;
                if (numeroita.isEmpty()) {
                    numeroita += "1";
                }
                break;
            } else {
                break;
            }
        }
        if (!numeroita.isEmpty()) {
            return new Luku(Double.parseDouble(numeroita), muuttuja);
        }
        return new Luku(0);
    }
    
    public Symboli tarkistaTyyppi(String merkki) {
        if (merkki.matches("[0-9]")) {
            return Symboli.NUMERO;
        } else if (merkki.equals("+")) {
            return Symboli.PLUS;
        } else if (merkki.equals("-")) {
            return Symboli.MINUS;
        } else if (merkki.equals("/")) {
            return Symboli.JAKO;
        } else if (merkki.equals("*")) {
            return Symboli.KERTO;
        } else if (merkki.equals("=")) {
            return Symboli.YHTAKUIN;
        } else if (merkki.equals("n")) {
            return Symboli.MUUTTUJA;
        } else {
            return Symboli.NULL;
        }
    }
    
    public void tulostaLauseke() {
        System.out.println(lauseke);
    }
    
    public void tarkistaOperaattoriTaiEtumerkki() {
        
    }
    
    public String lueMerkkiKohdasta(int i) {
        try {
             return Character.toString(syote.charAt(i));
        }
        catch (NullPointerException e) {
            return "nullero";
        }
    }
}
