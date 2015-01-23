/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.main;

import induktoija4000.laskin.*;
import induktoija4000.komponentit.*;
import org.junit.*;

public class LaskinTesti {
    
    private Laskin laskin;
    private Lukija lukija;
    private Yhtalo yhtalo;
    
    @Before
    public void setUp() {
        laskin = new Laskin();
        lukija = new Lukija();
        yhtalo = new Yhtalo();
    }
    
    @Test
    public void testaaLukijaa() {
        lukija.annaSyote("3=x");
        yhtalo = lukija.lueKaikki();
//        AssertEquals(yhtalo.toString(), "3.0 = x");
    }
}
