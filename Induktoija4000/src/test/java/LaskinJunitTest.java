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
public class LaskinJunitTest {
    
    private Laskin laskin;
    private Lukija lukija;
    private Yhtalo yhtalo;
    
    public LaskinJunitTest() {
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
        //puuttuva lausekkeen toiminallisuus
        lukija.annaSyote("3*3 = x");
        yhtalo = lukija.lueKaikki();
        assertTrue(yhtalo.getVasenpuoli().get(0).onkoTermi());
        assertTrue(yhtalo.getOikeapuoli().get(0).onkoOsatekija());
    }
    
    @Test
    public void testaaOsatekijoidenYhteenlasku() {
        Osatekija ot = new Osatekija(4, 0);
        ot.summaa(new Osatekija(2, 0));
        assertEquals(6.0, ot.getValue(), 0);
    }
    
    @Test
    public void testaaOsatekijoidenKertolasku() {
        Osatekija ot = new Osatekija(4, 0);
        ot.kerro(new Osatekija(2, 0));
        assertEquals(8.0, ot.getValue(), 0);
    }
    
    @Test
    public void testaaOsatekijoidenJakolasku() {
        Osatekija ot = new Osatekija(4, 0);
        ot.jaa(new Osatekija(2,0));
        assertEquals(2.0, ot.getValue(), 0);
    }
    
    @Test
    public void testaaKahdenOsatekijanKertoTerminSupistus() {
        Termi t = new Termi(new Osatekija(3, 0), '*', new Osatekija(3, 0));
        t.supista();
        assertEquals(9.0, t.getTulos().getValue(), 0);
    }
    
    @Test
    public void testaaKahdenOsatekijanJakoTerminSupistus() {
        Termi t = new Termi(new Osatekija(3, 0), '/', new Osatekija(3, 0));
        t.supista();
        assertEquals(1.0, t.getTulos().getValue(), 0);
    }
    
    @Test
    public void testaaYhtalonTermienSupistus() {
        lukija.annaSyote("6/3=x*4");
        yhtalo = lukija.lueKaikki();
        assertTrue(yhtalo.supistaKaikkiOsatekijoiksi());
    }
    
    @Test
    public void testaaLaskimenYhtalonYhteenlasku() {
        lukija.annaSyote("6/3+3=x*4-x");
        yhtalo = lukija.lueKaikki();
        yhtalo.supistaKaikkiOsatekijoiksi();
        laskin.annaYhtalo(yhtalo);
        laskin.laskeYhteenKaikkiOsatekijoina();
        assertTrue(laskin.getYhtalo().toString().equals("5.0 -3.0n = 0"));
    }
}
