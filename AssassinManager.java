import java.util.List;

// Evelyn Salas
// CSE 143 AO with Stuart Reges
// Homework 3
// AssassinManager tracks kills in the Assassin Game

public class AssassinManager {
   private AssassinNode killRing; //players still in the game
   private AssassinNode graveyard; //players assissinated

   //Uses the names of players to contstruct AssassinManager
   //Throws and IllegalArgumentExcpetion if the list of names is empty
   public AssassinManager(List<String> names){
      if (names.isEmpty() || names.size() == 0) {
         throw new IllegalArgumentException("List of players is empty"); 
      }
      
      this.killRing = new AssassinNode (names.get(0));
      AssassinNode curr = killRing;
      for (int i = 1; i < names.size(); i++) {
         curr.next = new AssassinNode(names.get(i));
         curr = curr.next;
      }
   }
   
   //Gives list of players that are still in the kill ring
   public void printKillRing() {
      AssassinNode curr = this.killRing;
      while (curr.next != null) {
         System.out.println("    " + curr.name + " is stalking " + curr.next.name);
         curr = curr.next;
      }
      System.out.println("    " + curr.name + " is stalking " + this.killRing.name);
   }
   
   //Gives the players that have been killed
   public void printGraveyard() {
      AssassinNode curr = this.graveyard;
      while(curr != null) {
         System.out.println("    " + curr.name + " was killed by " + curr.killer);
         curr = curr.next;
      }
   }
   
   //Returns a boolean to see if a player is still in the game
   public boolean killRingContains(String name) {
      AssassinNode curr = this.killRing;
      if(gameOver()) {
         return name.toLowerCase().equals(curr.name.toLowerCase());
      }
      while (curr != null) {
         if(name.toLowerCase().equals(curr.name.toLowerCase())) {
            return true;
         } else {
            curr = curr.next;
         }
      }
      return false;
   }
   
   //Returns a boolean to see if a player is no longer in the game
   public boolean graveyardContains(String name) {
      if (graveyard != null) {
         AssassinNode curr = this.graveyard; 
         return name.toLowerCase().equals(curr.name.toLowerCase());
      } else {
         return false;
      }
   }
   
   //Returns a boolean to see if the game is over
   public boolean gameOver() {
      return this.killRing.next == null;
   }
   
   //Returns the name of the winner
   public String winner() {
      if(gameOver()) {
         return this.killRing.name;
      }
      return null;
   }
   
   //Kills person with the given name and manages the kill ring
   //Throws an IllegalStateException if the game is over
   //Throws an IllegalArgumentExeception if the name is not in the kill ring
   public void kill(String name) {
      if(gameOver()) {
         throw new IllegalStateException("The game is over.");
      }
      if(!killRingContains(name)) {
         throw new IllegalArgumentException("This name isn't in the kill ring");
      }
      AssassinNode curr = this.killRing;
      AssassinNode temp = this.killRing;
      if(name.toLowerCase().equals(this.killRing.name.toLowerCase())) {
         temp = this.killRing;
         this.killRing = this.killRing.next;
         temp.next = this.graveyard;
         this.graveyard = temp;
         this.graveyard.killer = curr.name;
      }
      while(curr.next != null) {
         if(name.toLowerCase().equals(curr.next.name.toLowerCase())) {
            temp = curr.next;
            temp.killer = curr.name;
            curr.next = curr.next.next;
            temp.next = this.graveyard;
            this.graveyard = temp;
         } else {
            curr = curr.next;
         }
      }
   
   }

}