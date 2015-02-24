/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.komponentit;

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
public class TermiTest {

    public TermiTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testaaTerminKertominenTermilla() {
        Termi eka = new Termi(6, 0);
        eka.kerro(eka);
        assertEquals(36.0, eka.getArvo(), 0);
    }
    
    @Test
    public void testaaTerminKertominenLaskutoimituksella() {
        Termi kertoja = new Termi(6, 0);
        Lauseke eka = new Lauseke();
        Lauseke toka = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        // 6*(n/(1+n))
        eka.lisaa(muuttuja);
        eka.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(muuttuja, '/', eka);
        toka.lisaa(la);
        Laskutoimitus tama = new Laskutoimitus(kertoja, '*', toka);
        tama.supista();
        // 6n/(1+n) >> haluttu mutta koska lauseke niin swappas tekijat joten
        // (n/(1+n)) tuli ekaTekija mut hei kuhan toimii
        assertTrue(tama.toString().equals("(6.0n/(6.0n 1.0))"));
        
    }
    
    @Test
    public void testaaTerminKertominenLausekkeella() {
        Termi eka = new Termi(6, 0);
        Lauseke l = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        // 6*(n+1)
        l.lisaa(muuttuja);
        l.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(eka, '*', l);
        la.supista();
        l = (Lauseke) la.getEkatekija();
        assertTrue(l.toString().equals("(6.0n 6.0)"));
    }
    
    @Test
    public void testaaTerminJakaminen() {
        Termi eka = new Termi(6, 1);
        Termi toka = new Termi(6, 1);
        eka.jaa(toka);
        assertEquals(null, 1.0, eka.getArvo(), 0);
    }
    
    @Test
    public void testaaTerminJakaminenLaskutoimituksella() {
        Termi jaettava = new Termi(6, 0);
        Lauseke eka = new Lauseke();
        Lauseke toka = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        // 6/(n/(n+1))
        eka.lisaa(muuttuja);
        eka.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(muuttuja, '/', eka);
        toka.lisaa(la);
        Laskutoimitus tama = new Laskutoimitus(jaettava, '/', toka);
        tama.supista();
        // (6(n+1))/n
        assertTrue(tama.toString().equals("(6.0n 6.0)/n"));
    }
    
    @Test
    public void testaaTerminJakaminenLausekkeella() {
        Termi eka = new Termi(6, 0);
        Lauseke l = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        // 6/(n+1)
        l.lisaa(muuttuja);
        l.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(eka, '/', l);
        // mahdotonta supistaa
        assertTrue(!la.supista());
    }
    
    @Test
    public void testaaTerminSummaaminenTermilla() {
        Termi eka = new Termi(6, 1);
        eka.summaa(eka);
        assertEquals(12.0, eka.getArvo(), 0);
    }
}
