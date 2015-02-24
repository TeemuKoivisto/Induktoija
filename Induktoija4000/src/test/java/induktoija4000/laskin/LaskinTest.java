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
        Laskutoimitus t = new Laskutoimitus(new Termi(3, 0), '*', new Termi(3, 0));
        t.supista();
        assertEquals(9.0, t.palautaTulos().getArvo(), 0);
    }
    
    @Test
    public void testaaKahdestaTermistaKoostuvanJakolaskunSupistus() {
        Laskutoimitus t = new Laskutoimitus(new Termi(3, 0), '/', new Termi(3, 0));
        t.supista();
        assertEquals(1.0, t.palautaTulos().getArvo(), 0);
    }
    
    @Test
    public void testaaYhtalonTermienSupistus() {
        lukija.annaSyote("6/3=x*4");
        yhtalo = lukija.lueKaikki();
        assertTrue(yhtalo.supista());
    }
    
    @Test
    public void testaaLaskimenYhtalonYhteenlasku() {
        lukija.annaSyote("6/3+3=x*4-x");
        yhtalo = lukija.lueKaikki();
        yhtalo.supista();
        laskin.annaYhtalo(yhtalo);
        laskin.laskeYhteenKaikkiTermeina();
        assertTrue(laskin.getYhtalo().toString().equals("5.0 -3.0n = 0"));
    }
}
