/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * Objects created in this class will be simple squares/rectangles. These will be used
 * to serve as bombs and towers in the second mini-game.
 *
 */

import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;

public class GameObject {
	
	private Color c;
	private Rectangle2D.Double r;
	
	public GameObject(double w, double h, double x, double y, Color c) {
		r = new Rectangle2D.Double();
		r.width = w;
		r.height = h;
		r.x = x;
		r.y = y;
		this.c = c;
	}
	public Rectangle2D.Double getObj() {
		return r;
	}
	public boolean intersects(double x, double y, double width, double height) {
		if (r.intersects(x,y,width,height) == true)
			return true;
		return false;
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
		g2.draw(r);
		g2.setColor(Color.BLACK);
		g2.fill(r);
	}
	public void moveX(int amount) {
		r.x+=amount;
	}
	public void moveY(int amount) {
		r.y+=amount;
	}
	public boolean contains(int x, int y) {
		if (r.contains(x, y) == true)
			return true;
		return false;
	}
	public double getX() {
		return r.x;
	}
	public double getY() {
		return r.y;
	}
	public double getWidth() {
		return r.width;
	}
	public double getHeight() {
		return r.height;
	}
}
