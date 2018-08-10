/**
 * 
 * @author Nam Tran
 * @author Jack Kelly
 *
 * Helicopter object; it technically extends off Mountain, but that is because
 * it makes it easier to write the collision method between the Helicopter
 * and the Mountain.
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.*;

public class Helicopter extends Mountain {
	private Polygon tri;
	private Polygon p;
	private Polygon w;
	private Polygon t;
	private Polygon b;
	private Polygon center;
	private int xb[] = {400,400,600};
	private int yb[] = {200,400,400};
	private int xb2[] = {300,300};
	private int yb2[] = {150,200};
	private int xb3[] = {0,60};
	private int yb3[] = {45,45};
	private int[] x4 = {200,200,400,400,200,400,200,400,0};
	private int[] y4 = {200,400,200,400,200,200,400,400,200};
	private int[] centerX = new int[2];
	private int[] centerY = new int[2];
	
	private Color c;
	private int fillRectX = 20;
	private int fillRectY = 50;
	private int centerXPos;
	private int centerYPos;
	
	private ArrayList<Integer> x = new ArrayList<Integer>();
	private ArrayList<Integer> y = new ArrayList<Integer>();
	
	public Helicopter(int[] x, int[] y, Color c, int centerXPos, int centerYPos) {
		super(x, y, c);
		this.centerXPos = centerXPos;
		this.centerYPos = centerYPos;
		
		// stores the center in an array, but is separated with a +/- 1 increment for painting it.
		centerX[0] = centerXPos - 1;
		centerX[1] = centerXPos + 1;
		centerY[0] = centerYPos - 1;
		centerY[1] = centerYPos + 1;
		
		for (int i=0; i<xb.length; i++) { // B
			xb[i] /= 10;
			yb[i] /= 10;
			yb[i] += 30; // ...
		}
		for (int j=0; j<xb2.length; j++) { 
			xb2[j] /= 10;
			yb2[j] /= 10;
			yb2[j] += 30; // ...
		}
		for (int d=0; d<x.length; d++) {
			x[d] /= 10;
			y[d] /= 10;
			y[d] += 30; // ...
			
			this.x.add(new Integer(x[d]));
			this.y.add(new Integer(y[d]));
		}
		for (int m=0; m<x4.length; m++) {
			x4[m] /= 10;
			y4[m] /= 10;
			y4[m] += 30; // ...
		}
		b = new Polygon(x4, y4, x4.length);
		p = new Polygon(x, y, x.length);
		tri = new Polygon(xb, yb, xb.length); // triangle
		w = new Polygon(xb2, yb2, xb2.length);
		t = new Polygon(xb3, yb3, xb3.length);
		center = new Polygon(centerX, centerY, centerX.length);
	}
	public void drawPolygon(Graphics g) {
		g.setColor(c);
		g.drawPolygon(p);
		g.drawPolygon(tri);
		g.drawPolygon(w);
		g.drawPolygon(t);
		g.drawPolygon(b);
		g.setColor(Color.BLACK);
		g.fillRect(fillRectX, fillRectY, 20, 20);
		g.fillPolygon(b);
		g.fillPolygon(p);
		g.fillPolygon(tri);
		g.setColor(Color.WHITE);
		g.drawPolygon(center);
	}
	// Because moving things was such a hassle earlier, here's a move(X) method...
	public void moveX (int amount) {
		for (int i=0; i<xb.length; i++) { // B
			xb[i]+=amount;
		}
		for (int j=0; j<xb2.length; j++) { 
			xb2[j]+=amount;
		}
		for (int d=0; d<this.x.size(); d++) {
			Integer orig = this.x.get(d);
			Integer amt = Integer.valueOf(amount);
			this.x.set(d, (orig+amt));
		}
		for (int m=0; m<x4.length; m++) {
			x4[m]+=amount;
		}
		centerXPos += amount;
		for (int k=0; k<2; k++) {
			centerX[k] += amount;
		}
		fillRectX+=amount;
	}
	public void moveY(int amount) {
		for (int i=0; i<xb.length; i++) { 
			yb[i]+=amount;
		}
		for (int j=0; j<xb2.length; j++) { 
			yb2[j]+=amount;
		}
		for (int d=0; d<this.x.size(); d++) {
			Integer orig = this.y.get(d);
			Integer amt = Integer.valueOf(amount);
			this.y.set(d, (orig+amt));
		}
		for (int m=0; m<x4.length; m++) {
			y4[m]+=amount;
		}
		centerYPos += amount;
		for (int k=0; k<2; k++) {
			centerY[k] += amount;
		}
		fillRectY+=amount;
	}
	// accessor methods
	public int XB_get(int i) {
		return xb[i];
	}
	public int XB2_get(int i) {
		return xb2[i];
	}
	public int XB3_get(int i) {
		return xb3[i];
	}
	public int X4_get(int i) {
		return x4[i];
	}
	public int YB_get(int i) {
		return yb[i];
	}
	public int YB2_get(int i) {
		return yb2[i];
	}
	public int YB3_get(int i) {
		return yb3[i];
	}
	public int Y4_get(int i) {
		return y4[i];
	}
	public int getCenterX() {
		return this.centerXPos;
	}
	public int getCenterY() {
		return this.centerYPos;
	}
}
