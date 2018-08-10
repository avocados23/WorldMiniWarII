/**
 * @author Nam Tran
 * @author Jack Kelly
 * 
 * this is mainly for aesthetic; it is a lot easier to manage the code from an outside 
 * method than have it in the Main method, where the actual game should just run.
 *
 */
import java.util.ArrayList;

public class ArmyPack {
   
   public static Army processArmy(Army reference) {
	  System.out.println("Reference total attack: " + reference.getTotalAttack());
	  System.out.println("Reference size: " + reference.size());
      ArrayList<Army> armyPopulation = new ArrayList<Army>(); // creates population of Armies based on original reference.
      
      for (int i=0; i<100; i++) {
         Army copy = new Army(reference.getCountry()); // creates a bunch of Armies, copying the original.
         for (int j=0; j<reference.size(); j++) {
            copy.addTroop(reference.getTroop(j)); 
            //System.out.println(copy.size());
         }
         
         Army generated = new Army(reference.getCountry()); // new Army that will be apart of the population.
         
         int currentWeight = 0;
         int genSize = 0;
         int currentW = reference.getWeight();
         boolean canRun = true;
         // this is the process to find the best/fittest Army.
         
         while ((canRun == true) && (currentWeight <= reference.getMaxSize())) { 
            int randomNum = (int)(Math.random() * (((copy.size()-1) + 1)));
            //System.out.println("First random num: " + randomNum);
            int trial = currentWeight + copy.getTroop(randomNum).getWeight();
            int trial2 = reference.getMaxSize();
            System.out.println("Trial 1: " + trial);
            System.out.println("Trial 2: " + trial2);
            System.out.println(copy.getTroop(randomNum));
            if (((currentWeight + copy.getTroop(randomNum).getWeight()) < (reference.getMaxSize())) && (copy.getTroop(randomNum) != null)) {
               generated.addTroop(copy.getTroop(randomNum));
               currentWeight+=copy.getTroop(randomNum).getWeight();
               copy.removeTroop(randomNum); 
               System.out.println("added");
               genSize++;
            } else {
               canRun = false;
               System.out.println("did not add");
            }
         }
         armyPopulation.add(generated); 
        }
      System.out.println("Army pop: " + armyPopulation.size());
      //System.out.println(armyPopulation.get(0).size());
      //System.out.println(armyPopulation.get(1).size());
      //System.out.println(armyPopulation.get(2).size());
      
      int indexOfBest = 0;
      int valueOfBest = 0;
      System.out.println("Army population size: " + armyPopulation.size());
      for(int k=0; k<armyPopulation.size(); k++) { 
    	  	//System.out.println("Attack: " + armyPopulation.get(k).getTotalAttack());
            if (armyPopulation.get(k).getTotalAttack() > valueOfBest){
              indexOfBest = k; 
         }         
      }
      
      Army bestArmy = armyPopulation.get(indexOfBest);
      System.out.println("Best index: " + indexOfBest);
         
      //System.out.println("Generation 1: Best army has a size of " + bestArmy.size() + " and attack power of " + bestArmy.getTotalAttack()); 
      
      // Mutation portion of code
      boolean hasNotChanged = true; // has not changed in 100 generations
      int generationsUnchanged = 0;  // counts how many times the previous Army is equal to the newest Army/generated Army
      int stopIt = 0;
      
      while (hasNotChanged && (generationsUnchanged < 100)) {   
      for (int m=0; m<100; m++) {
         
         Army copy = new Army(reference.getCountry()); // copy will be an exact copy of reference, which means copy will inititally have all the items in it. It will
                                         // be used as a list for the algorithm to pick which item should go into the newly generated Army.
                                         // Copy is used, unlike reference, because we do not want to delete the items within the reference Army for future generations.
         for (int j=0; j<reference.size(); j++) {
            copy.addTroop(reference.getTroop(j));
         }
         
         Army copyBest = new Army(copy.getCountry()); // bestArmy's items are copied into the copyBest Army.
         
         for (int p=0; p<bestArmy.size(); p++) {
            copyBest.addTroop(bestArmy.getTroop(p));
         }
         //System.out.println(copyBest.size());
         //System.out.println("Best index: " + indexOfBest);
         
         Army generated = new Army(reference.getCountry()); // will create a Army to be put into the ArmyPopulation ArrayList
         boolean canRun = true; // boolean condition on if while() loop should run; blanket caution boolean
         
         // while loop will handle the creation of the new generated Army, which will be added into the ArmyPopulation ArrayList
         
         while (canRun && (generated.size() <= reference.getMaxSize())) { 
            int randomNum = (int)(Math.random() * (((copy.size()-1) + 1))); // picks a number that is [0, copy.size())
            int randomNumBest = (int)(Math.random() * (((copyBest.size()-1) + 1)));
            double randomNumB = (Math.random() * (((copyBest.size()-1) + 1)));
            //System.out.println("Random number: " + randomNum);
            //System.out.println("Random number best: " + randomNumBest);
            //System.out.println("Random num best (no cast): " + randomNumB);
            
            if (((randomNum < copy.size()) && (randomNumBest < copyBest.size())) && ((generated.size() + copy.getTroop(randomNum).getWeight()) < reference.getMaxSize()) && ((generated.size() + copyBest.getTroop(randomNumBest).getWeight()) < reference.getMaxSize())){
               
               int randomTwoThirds = (int)(Math.random() * 3); // will generate a number betwen [0, 3]
               
               if (randomTwoThirds >= 2) { // for every random number above >=2, it will be added into the Army; this is used to mutate and randomize the Armys
                  generated.addTroop(copy.getTroop(randomNum)); 
                  copy.removeTroop(randomNum); // this removes the same item from the Army copy to prevent duplicates
               } else {
                  generated.addTroop(copyBest.getTroop(randomNumBest)); // for every number < 2, it will just add the copyBest item that is referenced through copyBest[randomNum] 
                  copyBest.removeTroop(randomNumBest); // this removes the same item from the Army copyBest to prevent duplicates
               }
               
            } else {
               canRun = false; 
            }
         }
         armyPopulation.add(generated);      
      }
      indexOfBest = 0; // stores index of best/most fittest Army
      valueOfBest = 0; // stores value of best/most fittest Army
      for(int k=0; k<armyPopulation.size(); k++) {
         if (armyPopulation.get(k).getTotalAttack() > valueOfBest){ // if the newest Army is more fit than the current-pointed "most fit" Army, change it to the new one
            indexOfBest = k;
         }         
      }
      if (bestArmy.getTotalAttack() == armyPopulation.get(indexOfBest).getTotalAttack()) { // if the best stored Army has the same value as the most newly "fittest" Army
         generationsUnchanged++;                                                                 // in the nth generation, then generations unchanged + 1. 
         stopIt++;
      } else if (bestArmy.getTotalAttack() < armyPopulation.get(indexOfBest).getTotalAttack()) {
         bestArmy = armyPopulation.get(indexOfBest);                                     // if the newest "fittest" Army from newest population has a greater value, point it as most fit.
      }
      if (generationsUnchanged == 20){
         hasNotChanged = false;
      }
      System.out.println("Best army has a total of " + bestArmy.size() + " soldiers and attack value of " + bestArmy.getTotalAttack());
      if (bestArmy.size() == 1)  { // just for aesthetic/correct grammar
         System.out.println("There is " +  bestArmy.size() + " troop contained in the army.");  
      } else {
         System.out.println("There are " +  bestArmy.size() + " troops contained in the army.");
      }
         
      }
   return bestArmy;
   }
}
