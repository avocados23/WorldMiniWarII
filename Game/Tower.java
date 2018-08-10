/**
 * 
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * Tower object; not honestly different from GameObject in terms of its code,
 * but it is easier to keep track of which object GameObject is, specifically.
 *
 * Shape that the Tower should take is a tall rectangle; a good ratio for its width and height is 1:3.
 * 
 */
import java.awt.Color;

public class Tower extends GameObject {
	
	private int health = 100;
	
	public Tower(double w, double h, double x, double y, Color c) {
		super(w, h, x, y, c);
	}
	public void adjustHealth(int amount) {
		health+=amount;
	}
}
