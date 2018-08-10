import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Color;

public class MountainUI extends Canvas {
	private Mountain mountain;
	private Mountain mountainf;
	private Mountain hidden;
	
	private Font font;
	
	private int[] x1 = {0,30,60,80,90,130,170,210,230,270,285,290,305,310,315,320,340,360,380,400,420,430,470,490,530,560,580,590,620,630,660,680,700,710,720,740,790,860,940,960,990,1000,1010,1040,1060,1100,1110,1140,1280};
	private int[] y1 = {450,450,410,360,390,410,450,380,300,330,360,380,390,410,445,430,410,430,400,380,430,440,410,380,350,440,430,425,410,430,440,445,410,390,300,330,390,440,410,400,440,430,410,400,390,400,430,450,450};
	private int[] x2 = {30, 1140};
	private int[] y2 = {450, 450};
	
	private BufferStrategy strategy;
	
	public MountainUI() throws IOException {
	  mountain = new Mountain(x1, y1, Color.WHITE);
	  mountainf = new Mountain(x1, y1, Color.GRAY);
	  hidden = new Mountain(x2, y2, Color.BLACK);
	}
	public void paint(Graphics g) {
		mountain.drawPolygon(g);
		hidden.drawPolygon(g);
		mountain.fillPolygon(g, Color.BLACK);
		//g.setFont(font);
		
		
	}
   public void render() { // taken from the Asteroids game to help with rendering frame rate
                          //renders # of frames in the background then shows them in order
                          //the parameter is the number of frames, that are cycled through
      createBufferStrategy(2);
      strategy = getBufferStrategy();
      Graphics g = null;
      do {
         try{
            g =  strategy.getDrawGraphics();

         } finally {
            paint(g);
         }
         strategy.show();
         g.dispose();
      } while (strategy.contentsLost());
      Toolkit.getDefaultToolkit().sync();
   }
   public static void main(String[] args) throws IOException {
		new MountainUI();
	}
}
