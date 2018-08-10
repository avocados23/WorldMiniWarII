/**
 * 
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * The Troop object.
 *
 */
public class Troop {

   private String t = "";
   private int dV; // short for damage value
   private int soldiersUsed = 0; 
   private int weight;
   private int xPos;
   private int yPos;
   
   // files...these are dimensions to reference.
   // tankWidth = 178;
   // tankHeight = 105;
   
   public Troop (String type, int damageValue, int w) {
      t = type;
      dV = damageValue;
      weight = w;
   }
   // accessor methods
   public int getSoldiersUsed(){
      return soldiersUsed;
   }
   
   public int getDamageValue() {
      return this.dV;
   }
   public String getType() {
      return this.t;
   }
   public int getWeight() {
      return this.weight;
   }
	public int getX() {
		return xPos;
	}
	public int getY() {
		return yPos;
	}
	// Mini-game methods
	public boolean collides(Troop other) {
		if (other == null) {
			return false;
		}
		if (((this.getX() >= (other.getX() - 30)) && (this.getX() <= (other.getX() + 30))) && ((this.getY() >= (other.getY() - 20)) && (this.getY() <= (other.getY() + 20)))) {
			return true;
		}
		return false;
	}
      
}