import java.util.ArrayList;

public class Person {
		
	// Store all people
	ArrayList<Person> personsInvolved = new ArrayList<>();
	
	// Instance variables  
	private String name;
	private int age;
	private String appearance;
	private long callBackNumber;
	private boolean intoxication;
	private boolean weapons;
	private boolean injuries;

	// Default constructor
	public Person() {
		this.name = " ";
		this.age = 0;
		this.appearance = " ";
		this.callBackNumber = 0;
	    this.intoxication = false;
	    this.weapons = false;
	    this.injuries = false;
	}

	// Constructor 
	public Person(String name, int age, String appearance, long callBackNumber, boolean intoxication, 
			  boolean weapons, boolean injuries) {
	    this.name = name;
	    this.age = age;
	    this.appearance = appearance;
	    this.callBackNumber = callBackNumber;
	    this.intoxication = intoxication;
	    this.weapons = weapons;
	    this.injuries = injuries;
	}

	// Set
	public void setName(String name) {
	    this.name = name;
	}
	public void setAge(int age) throws NegativeAgeException { 
		if (age >= 0) {
			this.age = age;
		} else {
			throw new NegativeAgeException();
		}
	}
	public void setAppearance(String appearance) {
	   this.appearance = appearance;
	}
	public void setCallbackNumber(long phoneNumber) throws InvalidNumberException {
			
		int length = 0;
		long compare = 1;
		while (compare <= phoneNumber) {
			length++;
			compare *= 10;
		}			
		if (length == 10) {
			this.callBackNumber = phoneNumber;
			System.out.println("Callback Number Successfully Set");
		} else {
			throw new InvalidNumberException();
		}			
	}
	public void setIntoxication(boolean intoxication) {
		this.intoxication = intoxication;
	}
	public void setWeapons(boolean weapons) {
		this.weapons = weapons;
	}
	public void setInjuries(boolean injuries) {
	    this.injuries = injuries;
	}

	// Get
	public String getName() {
		return name;
	}
	public int getAge() {
	    return age;
	}
	public String getAppearance() {
		return appearance;
	}
	public long getCallbackNumber() {
		return callBackNumber;
	}
	public boolean getIntoxication() {
	    return intoxication;
	}
	public boolean getWeapons() {
		return weapons;
	}
	public boolean getInjuries() {
		return injuries;
	}
	  
	public String toStirng() {
	    System.out.println("\nDescription of Person: /n");
	    return "Name: " + this.name + "\nAge: " + this.age + "\nAppearance: " 
	    		+ this.appearance + "\nIs intoxicated: " + this.intoxication 
	    		+ "\nHas Weapons: " + this.weapons + "\nHas Injuries: " + this.injuries;
	}
	  
	// Making a method that throws/catches NegativeAgeException for me
	public void checkAge(int inputAge) {
		try {
			setAge(inputAge);
		} catch (NegativeAgeException e) {
		    System.out.println(e);
		}
	}
	  
	// Making a method that throws/catches InvalidNumberException for me
	public void checkPhoneNumber(long inputNum) {
		try {
			setCallbackNumber(inputNum);
		} catch (InvalidNumberException e) {
			System.out.println(e);
		}
	}
	  
	// Print personsInvolved list 
	public void printList(ArrayList<Person> personsInvolved) {
		  
		  if(personsInvolved.isEmpty()) {
			  System.out.println("No Persons in List");
		  } else {
			  for(int i = 0; i < personsInvolved.size(); i++) {
				  name = personsInvolved.get(i).getName();
				  age = personsInvolved.get(i).getAge();
				  appearance = personsInvolved.get(i).getAppearance();
				  callBackNumber = personsInvolved.get(i).getCallbackNumber();
				  intoxication = personsInvolved.get(i).getIntoxication();
				  weapons = personsInvolved.get(i).getWeapons();
				  injuries = personsInvolved.get(i).getInjuries();
				  
				  System.out.println("\nName: " + name 
						  			+ "\nAge: " + age
						  			+ "\nAppearance: " + appearance
						  			+ "\nCallback Number: " + callBackNumber
						  			+ "\nIntoxication: " + intoxication
						  			+ "\nWeapons: " + weapons
						  			+ "\nInjuries: " + injuries);
			  }
		  }
	}
}
