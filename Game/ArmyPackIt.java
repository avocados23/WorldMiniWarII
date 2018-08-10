import java.util.ArrayList;
//Nam Tran and Jack Kelly (2)

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ArmyPackIt {

public static void processArmy(Army reference) {
   ArrayList<Army> ArmyPopulation = new ArrayList<Army>();
   // creates initial population to find Fittest to compare to later
   
   for (int i=0; i<100; i++) {
      Army copy = new Army(reference.getCountry());
      for (int j=0; j<reference.size(); j++) {
         copy.addTroop(reference.getTroop(j)); // is a direct copy of reference; reference Army stores all the items we get
                                     // so that we still have a list of all items in the file, but copy is the one that
                                     // we will work with, and remove items from it in order to avoid making Armys
                                     // with duplicate items.
      }
      Army generated = new Army(reference.getCountry()); // new Army that will be apart of the population
      boolean canRun = true;
      int currentWeight = 0;
      int itemsContained = 0;
      
      // this is the process to find the best/fittest Army, which will be used later to compare to
      while (canRun && (currentWeight <= reference.getMaxSize())) { 
      // loop can only run if currentWeight is less than the maximum Army weight value
      //canRun is a cautionary boolean to aid in it going over the weight
         int randomNum = (int)(Math.random() * (((copy.size()-1) + 1))); // random index used for which item to grab from the reference
         if ((currentWeight + copy.getTroop(randomNum).getWeight()) < reference.getMaxSize() && (copy.getTroop(randomNum) != null)) {
         // code will only run if adding the item's weight will still have it below than the maximum Army weight
         // must also pass statement that the item actually exists
            currentWeight += copy.getTroop(randomNum).getWeight();
            generated.addTroop(copy.getTroop(randomNum)); 
            copy.removeTroop(randomNum); 
            itemsContained++;
         } else {
            canRun = false;
            System.out.println("SQUEAL");
         }
      }
      ArmyPopulation.add(generated); // will add each new Army to the population
   }
   
   int indexOfBest = 0;
   int valueOfBest = 0;
   for(int k=0; k<ArmyPopulation.size(); k++) { // loop compares the Army Population to the current-tracked best Army
      if (ArmyPopulation.get(k).getTotalAttack() > valueOfBest){ // if new Army found in population is "more fit", replace
         indexOfBest = k; 
         valueOfBest = ArmyPopulation.get(k).getTotalAttack();
      }         
   }
   
   Army bestArmy = ArmyPopulation.get(indexOfBest); // best Army found
   
   // Print out results, weight, and value of overall best Army.
      
   //System.out.println("Generation 1: Best Army has a weight of " + bestArmy.getWeight() + " and value of " + bestArmy.getTotalAttack());
   /*if (bestArmy.size() == 1) 
      System.out.println("There is " +  bestArmy.size() + " item contained in the Army.");  
   else
      System.out.println("There are " +  bestArmy.size() + " items contained in the Army.");*/
   
   // Mutation portion of code, comparing it to the fittest Army we found earlier 
   boolean hasNotChanged = true; // has not changed in 80 generations
   int generationsUnchanged = 0;  // counts how many times the previous Army is equal to the newest Army/generated Army
   int stopIt = 0; // provides an extra layer to stop code from running
   
   while (hasNotChanged && (generationsUnchanged < 30)) {   
   for (int m=0; m<100; m++) {
      
      Army copy = new Army(reference.getCountry()); // copy will be an exact copy of reference, which means copy will inititally have all the items in it. It will
                                      // be used as a list for the algorithm to pick which item should go into the newly generated Army.
                                      // Copy is used, unlike reference, because we do not want to delete the items within the reference Army for future generations.
      for (int j=0; j<reference.size(); j++) {
         copy.addTroop(reference.getTroop(j));
      }
      
      Army copyBest = new Army(reference.getCountry()); // bestArmy's items are copied into the copyBest Army.
      for (int p=0; p<bestArmy.size(); p++) {
         copyBest.addTroop(bestArmy.getTroop(p));
      }
      
      Army generated = new Army(reference.getCountry()); // will create a Army to be put into the ArmyPopulation ArrayList
      boolean canRun = true; // boolean condition on if while() loop should run; blanket caution boolean
      int currentWeight = 0; // current weight within the generated Army
      int itemsContained = 0; // amount of items contained in the generated Army
      
      // while loop will handle the creation of the new generated Army, which will be added into the ArmyPopulation ArrayList
      
      while (canRun && (currentWeight <= reference.getMaxSize())) { 
         int randomNum = (int)(Math.random() * (((copy.size()-1) + 1))); // picks a number that is [0, copy.size())
         int randomNumBest = (int)(Math.random() * (((copyBest.size()-1) + 1)));
         if (((randomNum < copy.size()) && (randomNumBest < copyBest.size())) && ((currentWeight + copy.getTroop(randomNum).getWeight()) < reference.getMaxSize()) && ((currentWeight + copyBest.getTroop(randomNumBest).getWeight()) < reference.getMaxSize())){
            
            int randomTwoThirds = (int)(Math.random() * 3); // will generate a number betwen [0, 3]
            
            if (randomTwoThirds >= 2) { // for every random number above >=2, it will be added into the Army; this is used to mutate and randomize the Armys.
               //currentWeight += copy.get(randomNum).getWeight();
               generated.addTroop(copy.getTroop(randomNum)); 
               copy.removeTroop(randomNum); // this removes the same item from the Army copy to prevent duplicates
            } else {
               currentWeight += copyBest.getTroop(randomNumBest).getWeight(); 
               generated.addTroop(copyBest.getTroop(randomNumBest)); // for every number < 2, it will just add the copyBest item that is referenced through copyBest[randomNum] 
               copyBest.removeTroop(randomNumBest); // this removes the same item from the Army copyBest to prevent duplicates
            }
            
            itemsContained++;
            
         } else {
            canRun = false; 
         }
      }
      ArmyPopulation.add(generated);      
   }
   indexOfBest = 0; // stores index of best/most fittest Army
   valueOfBest = 0; // stores value of best/most fittest Army
   for(int k=0; k<ArmyPopulation.size(); k++) {
      if (ArmyPopulation.get(k).getTotalAttack() > valueOfBest){ // if the newest Army is more fit than the current-pointed "most fit" Army, change it to the new one
         indexOfBest = k;
      }         
   }
   if (bestArmy.getTotalAttack() == ArmyPopulation.get(indexOfBest).getTotalAttack()) { // if the best stored Army has the same value as the most newly "fittest" Army
      generationsUnchanged++;                                                                 // in the nth generation, then generations unchanged + 1. 
      stopIt++;
   } else if (bestArmy.getTotalAttack() < ArmyPopulation.get(indexOfBest).getTotalAttack()) {
      bestArmy = ArmyPopulation.get(indexOfBest);                                     // if the newest "fittest" Army from newest population has a greater value, point it as most fit.
   }
   if(generationsUnchanged == 30){
      hasNotChanged = false;
   }
   System.out.println("Best Army has a weight of " + bestArmy.getWeight() + " and value of " + bestArmy.getTotalAttack());
   /*if (bestArmy.size() == 1)  { // just for aesthetic/correct grammar
      System.out.println("There is " +  bestArmy.size() + " item contained in the Army.");  
      // ...
   } else {
      System.out.println("There are " +  bestArmy.size() + " items contained in the Army.");
      // ...
   }*/
      
   }
}
}
