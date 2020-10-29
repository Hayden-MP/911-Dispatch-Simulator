import java.util.*;
import java.io.*;

public class Dialogue implements Serializable {

	ArrayList<String> dialogueOption = new ArrayList<String>();
	ArrayList<String> callerScript = new ArrayList<String>();
	ArrayList<String> transcript = new ArrayList<>();

	public Dialogue() {
		inputBinaryDialogue("dialogue.dat");
		inputBinaryCallerScript("callerScript.dat");
		transcript.add(0, "START TRANSCRIPT:");
		//addAllOptions();
		//addCallerScript();
	}

  public String printEntireArrayList(ArrayList<String> nameOfArrayList) {
    String output = "Size of ArrayList: "+ nameOfArrayList.size() + "\nAll options:";
    String output2 = "";

    for(int i = 0; i < nameOfArrayList.size(); i++) {
     output2 = "\n" + i + ": " + nameOfArrayList.get(i);
    }
    return output + output2;
  }
  
  // Output Dialogue to Binary File so we can delete from program
  public void outputObjectArray(String outputFileName, ArrayList<String> outputArrayList)
  {
    try{
      // Open binary output file
      ObjectOutputStream arrayFile = new ObjectOutputStream(new FileOutputStream(outputFileName, false));
      
      arrayFile.writeObject(outputArrayList);
      
      System.out.println("\nDialogue Written to File");
      arrayFile.close();

    } catch (IOException e) {
      System.out.println("outputObjectArray IOException caught");
    } 
  }
  
  // Input binary dialogueOption data
  public ArrayList<String> inputBinaryDialogue(String inputFileName) {		
	ObjectInputStream inputStream = null;
    String dialogueString;
		
	try {
		
		inputStream = new ObjectInputStream(new FileInputStream(inputFileName));
		ArrayList<String> inputList = (ArrayList<String>)inputStream.readObject();

		for (int i = 0; i < inputList.size(); i++) {
			dialogueString = inputList.get(i);
			dialogueOption = new ArrayList<String>(inputList);
		}	
		
		inputStream.close();
		
    } catch (ClassNotFoundException e) {
			System.out.println("Cannot read object from " + inputFileName);
	} catch (FileNotFoundException e) {
            System.out.println("Cannot find file " + inputFileName);
    } catch (IOException e) {
            System.out.println("Problem with file input from " + inputFileName);
    } catch (IndexOutOfBoundsException e) {
            System.out.println("Index Out of Bounds Exception");
    }
    return dialogueOption;
  }
  
  // Input binary callerScript data
  public ArrayList<String> inputBinaryCallerScript(String inputFileName) {		
	ObjectInputStream inputStream = null;
    String callerString;
		
	try {
		
		inputStream = new ObjectInputStream(new FileInputStream(inputFileName));
		ArrayList<String> inputList = (ArrayList<String>)inputStream.readObject();

		for (int i = 0; i < inputList.size(); i++) {
			callerString = inputList.get(i);
			callerScript = new ArrayList<String>(inputList);
		}		
		
		inputStream.close();
		
    } catch (ClassNotFoundException e) {
			System.out.println("Cannot read object from " + inputFileName);
	} catch (FileNotFoundException e) {
            System.out.println("Cannot find file " + inputFileName);
    } catch (IOException e) {
            System.out.println("Problem with file input from " + inputFileName);
    } catch (IndexOutOfBoundsException e) {
            System.out.println("Index Out of Bounds Exception");
    }
    return callerScript;
  }
  
	
  // Uses formatString() method to store String into transcript ArrayList
  public void addToTranscript(String dialogueOption, String callerScript)  
  {
    transcript.add(formatString(dialogueOption, callerScript));
    //System.out.println("\n" + formatString(dialogueOption, callerScript));
    
    exportTranscript(transcript);
  }
  
  // Takes two Strings and merges them into one to enter transcript ArrayList as ONE String
  public String formatString(String dispatcher, String caller) {
	  return "Dispatcher (You): " + dispatcher + "\nCaller: " + caller;
  }
  
  // Export transcript to text file
  public void exportTranscript(ArrayList<String> transcript)  {
	  PrintWriter outputFile = null;
	  
	  try { 
	    outputFile = new PrintWriter(new FileOutputStream("call-transcript.txt"));
	      
	  }	catch(FileNotFoundException e) {
		  System.out.println("Cannot open output file");
	  }
	  for(int i = 0; i < transcript.size(); i++) {
	    outputFile.println(transcript.get(i));
	  }
	    outputFile.close();
  }
	  
}