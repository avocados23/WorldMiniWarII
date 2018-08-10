/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * Star object to be displayed in the second mini-game.
 *
 */
public class Star {
	private int x;
	private int y;
	
	public Star(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	// accessor methods
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int changeX(int num) {
		return x+=num;
	}
}