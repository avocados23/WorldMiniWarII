/**
 * 
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * Bomb object; not honestly different from GameObject in terms of its code,
 * but it is easier to keep track of which object GameObject is, specifically.
 *
 * Shape that a Bomb should take is a square; a good ratio for its width and height is 1:1.
 * 
 */
import java.awt.Color;

public class Bomb extends GameObject {
	public Bomb(double w, double h, double x, double y, Color c) {
		super(w, h, x, y, c);
	}
}
