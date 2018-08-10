/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * The Army object.
 * 
 * A tank takes a space of 10 units.
 * A soldier takes a space of 1 unit.
 * A cavalry tales a space of 5 units.
 *
 */
import java.util.ArrayList;

public class Army {

   private ArrayList<Troop> troops;
   private int countryNum;
   private int attackValue;
   private int max_num_troops = 100;
   private int weight;
   
   public Army(int country) {
	  weight = 0;
      countryNum = country;
      troops = new ArrayList<Troop>();
   }
   
   // accessor methods
   public int getTotalAttack() {
	   return this.attackValue;
   }
   public void addWeight(int weight) {
	   this.weight+=weight;
   }
   public void subtractWeight(int weight) {
	   this.weight-=weight;
   }
   public int size() { 
      // making it synonymous makes it a lot easier to code
      return troops.size();
   }
   public int getCountry() {
      // ...fill in later. Check the enum to reference the return values.
      return 0;
   }
   public Troop getTroop(int id) {
	   return troops.get(id);
   }
   public int getWeight() {
	   return this.weight;
   }
   // mutator methods
   public void changeArmy(int countryNum) {
	   this.countryNum = countryNum;
   }
   public void addAttack(int amount) {
      this.attackValue += amount;
   }
   
   public void subtractAttack(int amount) {
     this.attackValue -= amount;
   }
   public void addTroop(Troop troop) {
	   troops.add(troop);
	   this.attackValue+=troop.getDamageValue();
	   this.weight+=troop.getWeight();
   }
   public void removeTroop(int x) {
	   attackValue-=troops.get(x).getDamageValue();
	   this.weight-=troops.get(x).getWeight();
	   troops.remove(x);
   }
   public void setMaxWeight(int weight) {
	   max_num_troops = weight;
   }
   // misc method
   
   public int getMaxSize() { // might come in handy later...
      return max_num_troops;
   }
   
}