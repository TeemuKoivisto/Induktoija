package induktoija4000.laskin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import induktoija4000.komponentit.*;
import induktoija4000.laskin.*;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Teemu
 */
public class LaskinTest {
    
    private Laskin laskin;
    private Lukija lukija;
    private Yhtalo yhtalo;
    
    public LaskinTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        laskin = new Laskin();
        lukija = new Lukija();
        yhtalo = new Yhtalo();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testaaLukijaLukeeOsatekijoistaKoostuvanSyotteen() {
        lukija.annaSyote("3=n");
        yhtalo = lukija.lueKaikki();
        assertTrue(yhtalo.toString().equals("3.0 = n"));
    }
    
    @Test
    public void testaaLukijaLukeeOsatekijoistaJaTermeistaKoostuvanSyotteen() {
        lukija.annaSyote("3*3=n");
        yhtalo = lukija.lueKaikki();
        assertTrue(yhtalo.toString().equals("3.0*3.0 = n"));
    }
    
    @Test
    public void testaaLukijaMuuntaaTyypitOikein() {
        lukija.annaSyote("3*3 = x");
        yhtalo = lukija.lueKaikki();
        assertTrue(yhtalo.getVasenpuoli().get(0).onkoLaskutoimitus());
        assertTrue(yhtalo.getOikeapuoli().get(0).onkoTermi());
    }
    
    @Test
    public void testaaTermienYhteenlasku() {
        Termi ot = new Termi(4, 0);
        ot.summaa(new Termi(2, 0));
        assertEquals(6.0, ot.getArvo(), 0);
    }
    
    @Test
    public void testaaKahdenTerminJaKahdenKertolaskunSupistus() {
        Laskutoimitus la = new Laskutoimitus(new Termi(3, 0), '*', new Termi(3, 0));
        la.supista();
        Termi t = (Termi) la.palautaTulosListana().get(0);
        assertEquals(9.0, t.getArvo(), 0);
    }
    
    @Test
    public void testaaKahdestaTermistaKoostuvanJakolaskunSupistus() {
        Laskutoimitus la = new Laskutoimitus(new Termi(3, 0), '/', new Termi(3, 0));
        la.supista();
        Termi t = (Termi) la.palautaTulosListana().get(0);
        assertEquals(1.0, t.getArvo(), 0);
    }
    
//    @Test
//    public void testaaYhtalonTermienSupistus() {
//        lukija.annaSyote("6/3=x*4");
//        yhtalo = lukija.lueKaikki();
//        assertTrue(yhtalo.s);
//    }
    
    @Test
    public void testaaLaskimenYhtalonYhteenlasku() {
        laskin.annaSyote("6/3+3=x*4-x");
        laskin.getYhtalo().supistaSiirtamatta();
        laskin.getYhtalo().siirraKaikkiVasemmalle();
        laskin.laskeMolemmatPuoletYhteenSiirtamatta();
//        lukija.annaSyote("6/3+3=x*4-x");
//        yhtalo = lukija.lueKaikki();
//        yhtalo.supista();
//        laskin.annaYhtalo(yhtalo);
//        laskin.laskeYhteenKaikkiTermeina();
        assertTrue(laskin.getYhtalo().toString().equals("5.0 -3.0n = 0"));
    }
    
    @Test
    public void testaaMonenmuotoistaDebuggausYhtaloa() {
        laskin.annaSyote("6*6 + 6/6 + 6*(6+6) + 6/(6+6) + (6+6)*6 + (6+6)/6 + (6+6)(6+6) + (6+6)/(6+6) + 6*6*6 + 6/6/6 + 6*6*(6+6) + 6*6/(6+6) = n");
        // t*t + t/t + t*l + t/l + l*t + l/t + l*l + l/l + la*t + la/t + la*l + la/l
        laskin.laske();
        Termi muuttuja = laskin.getYhtalo().getTermit().get(0);
        Termi vakio = laskin.getYhtalo().getTermit().get(1);
        // vastaus = n=979.666666666666
        boolean eka = muuttuja.onkoSamanArvoinen(new Termi(1, 1));
        boolean toka = vakio.onkoSamanArvoinen(new Termi(979.6666666666666, 0));
        assertTrue(eka && toka);
    }
    
    @Test
    public void testaaYksinkertaistaInduktiota() {
        laskin.annaSyote("sig(n,0,n(n+1)) = (n(n+1)(n+2))/3");
        boolean onnistuiko = laskin.laske();
        assertTrue(onnistuiko);
    }
    
    @Test
    public void testaaToistaYksinkertaistaInduktiota() {
        laskin.annaSyote("sig(n,0,n+1) = ((n+1)((n+1)+1))/2");
        boolean onnistuiko = laskin.laske();
        assertTrue(onnistuiko);
    }
}
