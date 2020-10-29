
public class PoliceEmergency extends Emergency {

	int policeAvailable = 15;
	  
	  public int dispatch() {
		    if (policeAvailable > 0) {
		    	policeAvailable = policeAvailable - 1;
		        System.out.println("\nPolice dispatched to: " + getLocation() + " \nRemaining units: " + policeAvailable);
		    } else {
		    	System.out.println("\nNo available police. Wait for cooldown.");
		    }

		    return policeAvailable;
		  }

	  public PoliceEmergency() { }
	
}
