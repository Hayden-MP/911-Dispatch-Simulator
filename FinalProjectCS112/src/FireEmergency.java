public class FireEmergency extends Emergency {

  int fireAvailable = 10;
  
  public int dispatch() {
    if (fireAvailable > 0) {
        fireAvailable = fireAvailable - 1;
        System.out.println("\nFire dispatched to: " + getLocation() + " \nRemaining units: " + fireAvailable);
    } else {
    	System.out.println("\nNo available fire. Wait for cooldown.");
    }
    return fireAvailable;
  }
  public FireEmergency() { }

}