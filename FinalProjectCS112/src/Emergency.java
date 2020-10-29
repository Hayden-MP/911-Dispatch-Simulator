import java.io.*;
import java.util.*; 

public abstract class Emergency {
	
	  // The abstract method that all sub-classes use 
	  public abstract int dispatch();

	  // Location of emergency
	  private String location;

	  // Default Constructor
	  public Emergency() {
	    this.location = null;
	  }

	  // Full Constructor
	  public Emergency(String location) {
	    this.location = location;
	  }

	  // Set
	  public void setLocation(String location) {
	    this.location  = location;
	  }
	  // Get
	  public String getLocation() {
	    return location;
	  }
	  

}
