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
public class LaskutoimitusTest {

    public LaskutoimitusTest() {
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
    public void testaaLaskutoimituksenKertominenTermilla() {
        // (x/(x+1))*2
    }

    @Test
    public void testaaLaskutoimituksenKertominenLaskutoimituksella() {
        // (x/(x+1))*(x/(x+2))
    }
    
    @Test
    public void testaaLaskutoimituksenKertominenLausekkeella() {
        // (x/(x+1))*(x+2)
    }
    
    @Test
    public void testaaLaskutoimituksenJakaminenTermilla() {
        // (x/(x+1))/2
    }
    
    @Test
    public void testaaLaskutoimituksenJakaminenLaskutoimituksella() {
        // (x/(x+1))/(x/(x+2))
    }
    
    @Test
    public void testaaLaskutoimituksenJakaminenLausekkeella() {
        // (x/(x+1))/(x+2)
    }
}
