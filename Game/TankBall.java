/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * For the creation of the TankBall objects for the mini-games.
 * 
 */

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TankBall extends Troop {
	private String direction;
	private int damage;
	private BufferedImage img;
	private int xPos;
	private int yPos;
	private Point pos;
	private int width;
	private int height;
	
	// tank image values
	private int tankWidth = 178;
	private int tankHeight = 105;
	
	public TankBall(String type, int damageValue, int w, String d, int x, int y) {
		super(type, damageValue, w);
		direction = d;
		xPos = x;
		yPos = y;
		pos = new Point((int)xPos, (int)yPos);
		
		try {
			if (d.equals("R"))
				img = ImageIO.read(new File("images/bullet.png"));
			else if (d.equals("L"))
				img = ImageIO.read(new File("images/bullet_l.png"));
			else if (d.equals("U"))
				img = ImageIO.read(new File("images/bullet_u.png"));
			else if (d.equals("D")) 
				img = ImageIO.read(new File("images/bullet_d.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*if (type.equals("Missile")) {
			this.width = img.getWidth(null) / 2;
			this.height = img.getHeight(null) / 2;
		}*/
	}
	// mutator methods
	public void addDamage(int amount) {
		damage+=amount;
	}
	public void move(int amount) {
		if (direction.equals("U")) {
			yPos-=amount;
		} else if (direction.equals("L")) {
			xPos-=amount;
		} else if (direction.equals("R")) {
			xPos+=amount;
		} else if (direction.equals("D")) {
			yPos+=amount;
		}
	}
	public void paint(Graphics g) {
		// Reserved for Missiles only.
		g.drawImage(img, this.xPos, this.yPos, getWidth(), getHeight(), null);
	}
	// accessor methods
	public BufferedImage getImg() {
		return img;
	}
	public int getWidth() {
		return img.getWidth(null) / 4;
	}
	public int getHeight() {
		return img.getHeight(null) / 4;
	}
	public int getX() {
		return xPos;
	}
	public int getY() {
		return yPos;
	}
	public int getDamage() {
		return damage;
	}
	public boolean collides(Rectangle2D.Double other) {
		if (other == null) {
			return false;
		}
		if (((this.getX() >= (other.getX() - 30)) && (this.getX() <= (other.getX() + 30))) && ((this.getY() >= (other.getY() - 20)) && (this.getY() <= (other.getY() + 20)))) {
			return true;
		}
		return false;
	}
}