/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * The Tank object, both which will be displayed in the first mini-game and the actual army.
 *
 */
public class Tank extends Troop {
	private int x;
	private int y;
	
	public Tank (String type, int damageValue, int w, int xValue, int yValue) {
		super(type, damageValue, w);
		x = xValue;
		y = yValue;
	}
	// accessor methods
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	// mutator methods
	public int move(int amount) {
		x += amount;
		return x;
	}
	
}
