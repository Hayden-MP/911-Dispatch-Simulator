public class MedicalEmergency extends Emergency {

	int medAvailable = 10;

	  public int dispatch() {
		  if (medAvailable > 0) {
			  medAvailable = medAvailable - 1;
		      System.out.println("\nMedical dispatched to: " + getLocation() + " \nRemaining units: " + medAvailable);
		  } else {
			  System.out.println("\nNo available ambulances. Wait for cooldown.");
		  }
		  return medAvailable;
	  }

  public MedicalEmergency() {}
  
}