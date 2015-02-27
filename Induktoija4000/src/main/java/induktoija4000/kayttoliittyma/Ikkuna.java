/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package induktoija4000.kayttoliittyma;

import induktoija4000.laskin.Laskin;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Teemu
 */
public class Ikkuna extends JPanel {
    
    private Laskin laskin;
    
    public Ikkuna(Laskin l) {
        laskin=l;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.black);
        
        g.drawString("Voi vittu saatana", 10, 10);
    }
    
    public void refresh() {
        
    }
}
