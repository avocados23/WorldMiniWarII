/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * Creates a Mountain object.
 *
 */

import java.awt.Polygon;
import java.awt.Color;
import java.awt.Graphics;


public class Mountain {
	Polygon p;
	Color c;
   
	public Mountain(int[] x, int[] y, Color c) {
		p = new Polygon();
		p.xpoints = x;
		p.ypoints = y;
		p.npoints = x.length; // y.length is also valid.
		this.c = c;
	}
	public void drawPolygon(Graphics g) {
		g.setColor(c);
		g.drawPolygon(p);
	}
	public void fillPolygon(Graphics g, Color c) { // integral algorithm
		g.setColor(c);
		g.fillPolygon(p);
 		g.fillRect(0, 450, 500, 70);
	}
	public void translate(int deltaX, int deltaY) {
		p.translate(deltaX, deltaY);
	}
}
