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
public class LausekeTest {

    public LausekeTest() {
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
    public void testaaLausekkeenKertominenTermilla() {
        // (n+1)*2
        Lauseke l = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        Termi kertoja = new Termi(2, 0);
        // 6*(n+1)
        l.lisaa(muuttuja);
        l.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(l, '*', kertoja);
        la.supista();
        l = (Lauseke) la.getEkatekija();
        assertTrue(l.toString().equals("(2.0n 2.0)"));
    }

    @Test
    public void testaaLausekkeenKertominenLaskutoimituksella() {
        // (n+1)*(n/(n+1)) supistuu >> n
        Lauseke kerrottava = new Lauseke();
        Lauseke kertoja = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        Termi vakio2 = new Termi(2, 0);
        kerrottava.lisaa(muuttuja);
        kerrottava.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(muuttuja, '/', kerrottava);
        kertoja.lisaa(la);
        Laskutoimitus lasku = new Laskutoimitus(kerrottava, '*', kertoja);
        lasku.supista();
        // (n^2 + n)/(n + 1)
        Termi vastaus = lasku.palautaTulos();
//        assertTrue(vastaus.toString().equals("n"));
    }

    @Test
    public void testaaLausekkeenKertominenLausekkeella() {
        // (n+1)*(n+1)
        Lauseke lauseke = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        lauseke.lisaa(muuttuja);
        lauseke.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(lauseke, '*', lauseke);
        la.supista();
        // (n^2+2n+1)
        Lauseke vastaus = (Lauseke) la.getEkatekija();
        assertTrue(vastaus.toString().equals("(n^2.0 2.0n 1.0)"));
    }
    
    @Test
    public void testaaLausekkeenJakaminenTermilla() {
        // (n+1)/2
        Lauseke l = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        Termi jakaja = new Termi(2, 0);
        l.lisaa(muuttuja);
        l.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(l, '/', jakaja);
        la.supista();
        l = (Lauseke) la.getEkatekija();
        assertTrue(l.toString().equals("(0.5n 0.5)"));
    }

    @Test
    public void testaaLausekkeenJakaminenLaskutoimituksella() {
        // (n+1)/(2/(n+1)) supistuu >> (1/2)*((n+1)(n+1))
        Lauseke jaettava = new Lauseke();
        Lauseke jakaja = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        Termi vakio2 = new Termi(2, 0);
        jaettava.lisaa(muuttuja);
        jaettava.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(vakio2, '/', jaettava);
        jakaja.lisaa(la);
        Laskutoimitus lasku = new Laskutoimitus(jaettava, '/', jakaja);
        lasku.supista();
        // (n^2 + n)/(n + 1)
        Lauseke vastaus = (Lauseke) lasku.getEkatekija();
//        assertTrue(vastaus.toString().equals("n"));
    }

    @Test
    public void testaaSupistuvaLausekkeenJakaminenLausekkeella() {
        // (n+1)/(n+1)
        Lauseke lauseke = new Lauseke();
        Termi muuttuja = new Termi(1, 1);
        Termi vakio = new Termi(1, 0);
        lauseke.lisaa(muuttuja);
        lauseke.lisaa(vakio);
        Laskutoimitus la = new Laskutoimitus(lauseke, '*', lauseke);
        la.supista();
        Lauseke vastaus = (Lauseke) la.getEkatekija();
//        assertTrue(vastaus.toString().equals("(n^2.0 2.0n 1.0)"));
    }
}
