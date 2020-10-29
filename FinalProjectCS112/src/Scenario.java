import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

// Use this class to create JFrame

public class Scenario extends JFrame {
	
	// constants
	private static final int WIDTH = 650;
	private static final int HEIGHT = 550;
	
	// Instance Variables
	private JTextField locationInput = null;
	private JTextField nameInput = null;
	private JTextField callbackNumberInput = null;
	private JTextField ageInput = null;
	private JTextField appearanceInput = null;
	private JCheckBox injuriesInput = null;
	private JCheckBox intoxicationInput = null;
	private JCheckBox weaponsInput = null;
	private JButton goodChoiceButton = null;
	private JButton neutralChoiceButton = null;
	private JButton badChoiceButton = null;
	private JButton showAllPersons = null;
	
	
	// String for the ComboBox
	String[] personTypes = {"Caller", "Suspect", "Victim"};
	final JComboBox<String> personChoice = new JComboBox<String>(personTypes);
	
	// Starting Score
	private int score = 50;
	
	// Creating all objects to be used throughout GUI
	 Dialogue dialogue = new Dialogue();
	 Person person = new Person();
	 Person callerPerson = new Caller();
	 Person suspectPerson = new Suspect();
	 Person victimPerson = new Victim();
	 Emergency fire = new FireEmergency();
	 Emergency medical = new MedicalEmergency();
	 Emergency police = new PoliceEmergency();
	
	public Scenario() {
		// Set title of window
		super("911 Dispatch Simulator");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Size of window
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		//Use a BorderLayout
		setLayout(new BorderLayout());
		
		// Adding Dispatch Control to Header Region (NORTH)
		add(dispatchControl(), BorderLayout.NORTH);
		add(infoControl(), BorderLayout.EAST);
		add(dialogueOptions(), BorderLayout.SOUTH);
		add(dialogueBox(), BorderLayout.CENTER);
		
		
	}
	
	// NORTH
	private JPanel dispatchControl() {
		
		// JPANEL + AESTHETICS 
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		// LAYOUT FOR THIS JPANEL
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		// COMPONENTS + actionListeners
		JLabel dispatchTitle = new JLabel("Dispatch Control");
		JButton dispatchPolice = new JButton("Dispatch Police");
		dispatchPolice.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(police.getLocation() != null) {
						police.dispatch();
					} else {
						System.out.println("Enter a valid location!");
					}
				}
			});
			
		JButton dispatchMed = new JButton("Dispatch Medical");
		dispatchMed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(medical.getLocation() != null) {
					medical.dispatch();
				} else {
					System.out.println("Enter a valid location!");
				}
			}
		});
		
		JButton dispatchFire = new JButton("Dispatch Fire");
		dispatchFire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fire.getLocation() != null) {
					fire.dispatch();
				} else {
					System.out.println("Enter a valid location!");
				}
			}
		});
		
		// LAYOUT POSITIONING SPECS
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(dispatchTitle)
				.addComponent(dispatchPolice)
				.addComponent(dispatchMed)
				.addComponent(dispatchFire)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))			
		);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(dispatchTitle)		
				.addComponent(dispatchPolice)
				.addComponent(dispatchMed)
				.addComponent(dispatchFire))
		);
		
		return panel;
	}
	
	// EAST
	private JPanel infoControl() {
			
		// JPANEL + AESTHETICS 
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// COMPONENTS + actionListeners & itemListeners		
		JLabel infoTitle = new JLabel("Information Control");
		
		//                                                       - location input field -
		JLabel locationLabel = new JLabel("Location: ");
		locationInput = new JTextField();
		locationInput.setPreferredSize( new Dimension(100, 24));
		locationInput.addActionListener(new ActionListener() { // LOCATION IS INDEPENDENT FROM PERSON
			
			public void actionPerformed(ActionEvent e) {
				String location = locationInput.getText();
				fire.setLocation(location);
				police.setLocation(location);
				medical.setLocation(location); 
			}
		});
		
		//													- sets text fields to appropriate person -
		personChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextFields();
			}
		});
		
		//                                                        - name input field -
		JLabel nameLabel = new JLabel("Name: ");
		nameInput = new JTextField();
		nameInput.setPreferredSize( new Dimension(100, 24));
		nameInput.addActionListener(new FieldsListener());
		
		//                                                     - callback input field -
		JLabel callbackLabel = new JLabel("Callback #: ");
		callbackNumberInput = new JTextField();
		callbackNumberInput.setPreferredSize( new Dimension(100, 24));
		callbackNumberInput.addActionListener(new ActionListener() { // ONLY CALLER HAS CALLBACK NUMBER
			public void actionPerformed(ActionEvent e) {
				String phoneNumber = callbackNumberInput.getText();
				long convertToLong = Long.parseLong(phoneNumber, 10);
				if(person.personsInvolved.isEmpty()) {
					try {
						callerPerson.setCallbackNumber(convertToLong);
						person.personsInvolved.add(callerPerson);
					} catch (InvalidNumberException e1) {
						System.out.println(e1.getMessage());
					} 		
				} else {
					int updateExisting = person.personsInvolved.indexOf(callerPerson);
					try {
						person.personsInvolved
							.get(updateExisting)
							.setCallbackNumber(convertToLong);;
					} catch (InvalidNumberException e1) {
						System.out.println(e1.getMessage());
					} 
				}
			 }
		});
				
		//                                                       - age input field -
		JLabel ageLabel = new JLabel("Age: ");
		ageInput = new JTextField();
		ageInput.setPreferredSize( new Dimension(100, 24));
		ageInput.addActionListener(new FieldsListener());
		
		//                                                    - appearance input field -
		JLabel appearanceLabel = new JLabel("Appearance: ");
		appearanceInput = new JTextField();
		appearanceInput.setPreferredSize( new Dimension(100, 24));
		appearanceInput.addActionListener(new FieldsListener());
		
		//                                                        - injuries box -
		JLabel injuriesLabel = new JLabel("Injuries: ");
		injuriesInput = new JCheckBox();
		injuriesInput.addItemListener(new CheckBoxListener());
		
		//                                                          - intoxication box -
		JLabel intoxicationLabel = new JLabel("Intoxication: ");
		intoxicationInput = new JCheckBox();
		intoxicationInput.addItemListener(new CheckBoxListener());
		
		//                                                        - weapons box -
		JLabel weaponsLabel = new JLabel("Weapons: ");
		weaponsInput = new JCheckBox();
		weaponsInput.addItemListener(new CheckBoxListener());
		
		showAllPersons = new JButton("Show All");
		showAllPersons.addActionListener(new FieldsListener());
		
		// LAYOUT FOR THIS JPANEL
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.gridx = 0; // column
		gbc.gridy = 0; // row
		panel.add(infoTitle, gbc);
		
		gbc.gridx = 0; gbc.gridy = 1;
		panel.add(personChoice, gbc);
		gbc.gridx = 0; gbc.gridy = 2; 
		panel.add(locationLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 2;
		panel.add(locationInput, gbc);
		gbc.gridx = 0; gbc.gridy = 3;
		panel.add(nameLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 3;
		panel.add(nameInput, gbc);
		gbc.gridx = 0; gbc.gridy = 4;
		panel.add(callbackLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 4;
		panel.add(callbackNumberInput, gbc);
		gbc.gridx = 0; gbc.gridy = 5;
		panel.add(ageLabel, gbc);
		gbc.gridx = 1;gbc.gridy = 5;
		panel.add(ageInput, gbc);
		gbc.gridx = 0; gbc.gridy = 6;
		panel.add(appearanceLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 6;
		panel.add(appearanceInput, gbc);
		gbc.gridx = 0; gbc.gridy = 7;
		panel.add(injuriesLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 7;
		panel.add(injuriesInput, gbc);
		gbc.gridx = 0; gbc.gridy = 8;
		panel.add(intoxicationLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 8;
		panel.add(intoxicationInput, gbc);
		gbc.gridx = 0; gbc.gridy = 9;
		panel.add(weaponsLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 9;
		panel.add(weaponsInput, gbc);
		gbc.gridx = 0; gbc.gridy = 10;
		panel.add(showAllPersons, gbc);
				
		return panel;
	}
	
	// Listen to the fields to use ActionListener
	class FieldsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// 0 = caller
			// 1 = suspect
			// 2 = victim

			if (e.getSource() == nameInput) {
				String name = nameInput.getText();
				if (personChoice.getSelectedIndex() == 0) { // IF CALLER IS SELECTED	
					if(person.personsInvolved.contains(callerPerson)) {
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting).setName("C: " + name);
					} else {
						callerPerson.setName("C: " + name);
						person.personsInvolved.add(callerPerson);
					}					
				} else if (personChoice.getSelectedIndex() == 1) { // IF SUSPECT IS SELECTED	
					if(person.personsInvolved.contains(suspectPerson)) {
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting).setName("S: " + name);
					} else {
						suspectPerson.setName("S: " + name);
						person.personsInvolved.add(suspectPerson);
					}		
				} else if (personChoice.getSelectedIndex() == 2) { // IF VICTIM IS SELECTED		
					if(person.personsInvolved.contains(victimPerson)) {
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting).setName("V: " + name);
					} else {
						victimPerson.setName("V: " + name);
						person.personsInvolved.add(victimPerson);
					}
				}	
			}
			else if (e.getSource() == ageInput) {
				String age = ageInput.getText();
				int convertedAge = Integer.parseInt(age, 10);
				
				if (personChoice.getSelectedIndex() == 0) { // IF CALLER IS SELECTED
					if(person.personsInvolved.contains(callerPerson)) {
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						try {
							person.personsInvolved
								.get(updateExisting)
								.setAge(convertedAge);
						} catch (NegativeAgeException e1) {
							System.out.println(e1.getMessage());	
						}
					} else {		
						try {
							callerPerson.setAge(convertedAge);
							person.personsInvolved.add(callerPerson);		
						} catch (NegativeAgeException e1) {
							System.out.println(e1.getMessage());	
						}
					}
				} else if (personChoice.getSelectedIndex() == 1) { // IF SUSPECT IS SELECTED
					if(person.personsInvolved.contains(suspectPerson)) {
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						try {
							person.personsInvolved
								.get(updateExisting)
								.setAge(convertedAge);
						} catch (NegativeAgeException e1) {
							System.out.println(e1.getMessage());	
						}
					} else {		
						try {
							suspectPerson.setAge(convertedAge);
							person.personsInvolved.add(suspectPerson);		
						} catch (NegativeAgeException e1) {
							System.out.println(e1.getMessage());	
						}
					}
				} else if (personChoice.getSelectedIndex() == 2) { // IF VICTIM IS SELECTED
					if(person.personsInvolved.contains(victimPerson)) {
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						try {
							person.personsInvolved
								.get(updateExisting)
								.setAge(convertedAge);
						} catch (NegativeAgeException e1) {
							System.out.println(e1.getMessage());	
						}
					} else {		
						try {
							victimPerson.setAge(convertedAge);
							person.personsInvolved.add(victimPerson);		
						} catch (NegativeAgeException e1) {
							System.out.println(e1.getMessage());	
						}
					}
				}
			}
			else if (e.getSource() == appearanceInput) {
				String appearance = appearanceInput.getText();
				
				if (personChoice.getSelectedIndex() == 0) { // IF CALLER IS SELECTED
					if(person.personsInvolved.contains(callerPerson)) {
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting)
								.setAppearance(appearance);
					} else {
						callerPerson.setAppearance(appearance);
						person.personsInvolved.add(callerPerson);
					}		
				} else if (personChoice.getSelectedIndex() == 1) { // IF SUSPECT IS SELECTED
					if(person.personsInvolved.contains(suspectPerson)) {
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting)
								.setAppearance(appearance);
					} else {
						suspectPerson.setAppearance(appearance);
						person.personsInvolved.add(suspectPerson);
					}	
				} else if (personChoice.getSelectedIndex() == 2) { // IF VICTIM IS SELECTED
					if(person.personsInvolved.contains(victimPerson)) {
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting)
								.setAppearance(appearance);
					} else {
						victimPerson.setAppearance(appearance);
						person.personsInvolved.add(victimPerson);
					}	
				}
			}
			else if (e.getSource() == showAllPersons) {
				person.printList(person.personsInvolved);
			}
		}
	}
	
	// Listen to the checkboxes to use ItemListener
	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if (e.getSource() == injuriesInput) {
				if (personChoice.getSelectedIndex() == 0) {
					
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(callerPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting)
								.setInjuries(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						callerPerson.setInjuries(true);
						person.personsInvolved.add(callerPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(callerPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting)
								.setInjuries(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						callerPerson.setInjuries(false);
						person.personsInvolved.add(callerPerson);
					}
				} 
				else if (personChoice.getSelectedIndex() == 1) {
					
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(suspectPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting)
								.setInjuries(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						suspectPerson.setInjuries(true);
						person.personsInvolved.add(suspectPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(suspectPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting)
								.setInjuries(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						suspectPerson.setInjuries(false);
						person.personsInvolved.add(suspectPerson);
					}
				}
				else if (personChoice.getSelectedIndex() == 2) {
					
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(victimPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting)
								.setInjuries(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						victimPerson.setInjuries(true);
						person.personsInvolved.add(victimPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(victimPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting)
								.setInjuries(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						victimPerson.setInjuries(false);
						person.personsInvolved.add(victimPerson);
					}
				}
			}
			else if (e.getSource() == intoxicationInput) {
				if (personChoice.getSelectedIndex() == 0) {
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(callerPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting)
								.setIntoxication(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						callerPerson.setIntoxication(true);
						person.personsInvolved.add(callerPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(callerPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting)
								.setIntoxication(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						callerPerson.setIntoxication(false);
						person.personsInvolved.add(callerPerson);
					}
				} 
				else if (personChoice.getSelectedIndex() == 1) {
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(suspectPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting)
								.setIntoxication(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						suspectPerson.setIntoxication(true);
						person.personsInvolved.add(suspectPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(suspectPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting)
								.setIntoxication(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						suspectPerson.setIntoxication(false);
						person.personsInvolved.add(suspectPerson);
					}
				}
				else if (personChoice.getSelectedIndex() == 2) {
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(victimPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting)
								.setIntoxication(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						victimPerson.setIntoxication(true);
						person.personsInvolved.add(victimPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(victimPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting)
								.setIntoxication(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						victimPerson.setIntoxication(false);
						person.personsInvolved.add(victimPerson);
					}
				}
			}
			else if (e.getSource() == weaponsInput) {
				if (personChoice.getSelectedIndex() == 0) {
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(callerPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting)
								.setWeapons(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						callerPerson.setWeapons(true);
						person.personsInvolved.add(callerPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(callerPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(callerPerson);
						person.personsInvolved.get(updateExisting)
								.setWeapons(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						callerPerson.setWeapons(false);
						person.personsInvolved.add(callerPerson);
					}
				} 
				else if (personChoice.getSelectedIndex() == 1) {
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(suspectPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting)
								.setWeapons(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						suspectPerson.setWeapons(true);
						person.personsInvolved.add(suspectPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(suspectPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(suspectPerson);
						person.personsInvolved.get(updateExisting)
								.setWeapons(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						suspectPerson.setWeapons(false);
						person.personsInvolved.add(suspectPerson);
					}
				}
				else if (personChoice.getSelectedIndex() == 2) {
					if(e.getStateChange()== ItemEvent.SELECTED && person.personsInvolved.contains(victimPerson)) {	
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting)
								.setWeapons(true);
	
					} else if (e.getStateChange()==ItemEvent.SELECTED) {	
						victimPerson.setWeapons(true);
						person.personsInvolved.add(victimPerson);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED && person.personsInvolved.contains(victimPerson)) {		
						int updateExisting = person.personsInvolved.indexOf(victimPerson);
						person.personsInvolved.get(updateExisting)
								.setWeapons(false);
						
					} else if (e.getStateChange()==ItemEvent.DESELECTED) {
						victimPerson.setWeapons(false);
						person.personsInvolved.add(victimPerson);
					}
				}
			}
		}
		
	}
	
	// Method that changes the text fields upon JComboBox personChoice selection
	public void setTextFields() {
		int indexOfPerson = personChoice.getSelectedIndex();
		
		if (indexOfPerson == 0) {

			nameInput.setText(callerPerson.getName());
			String convertFromLong = callerPerson.getCallbackNumber() + "";
			callbackNumberInput.setText(convertFromLong);
			String convertFromInt = callerPerson.getAge() + "";
			ageInput.setText(convertFromInt);
			appearanceInput.setText(callerPerson.getAppearance());
			injuriesInput.setSelected(callerPerson.getInjuries());
			intoxicationInput.setSelected(callerPerson.getIntoxication());
			weaponsInput.setSelected(callerPerson.getWeapons());
			
		} else if (indexOfPerson == 1) {

			nameInput.setText(suspectPerson.getName());
			String convertFromInt = suspectPerson.getAge() + "";
			ageInput.setText(convertFromInt);
			appearanceInput.setText(suspectPerson.getAppearance());
			injuriesInput.setSelected(suspectPerson.getInjuries());
			intoxicationInput.setSelected(suspectPerson.getIntoxication());
			weaponsInput.setSelected(suspectPerson.getWeapons());
			
		} else if (indexOfPerson == 2) {

			nameInput.setText(victimPerson.getName());
			String convertFromInt = victimPerson.getAge() + "";
			ageInput.setText(convertFromInt);
			appearanceInput.setText(victimPerson.getAppearance());
			injuriesInput.setSelected(victimPerson.getInjuries());
			intoxicationInput.setSelected(victimPerson.getIntoxication());
			weaponsInput.setSelected(victimPerson.getWeapons());
			
		}	
	}
	
	// CENTER
	private JPanel dialogueBox() {
		System.out.println("Normal output to console window");
		
		// JPANEL + AESTHETICS 
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		// COMPONENTS 
		JTextArea textArea = new JTextArea(5, 30);
		// Stream for output to gui
		GuiOutputStream rawout = new GuiOutputStream(textArea);
		// Set new stream for System.out
		System.setOut(new PrintStream(rawout, true));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setViewportView(textArea);
			
		// LAYOUT FOR THIS JPANEL
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		
		return panel;
	}
	
	// SOUTH
	private JPanel dialogueOptions() {
		
		// JPANEL + AESTHETICS 
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		// LAYOUT FOR THIS JPANEL
		GridLayout grid = new GridLayout(4,2);
		panel.setLayout(grid);
						
		// Add a label - JLabel used for static text
		panel.add(new JLabel("Dialogue Options"), BorderLayout.CENTER);
		
		goodChoiceButton = new JButton();
		neutralChoiceButton = new JButton();
		badChoiceButton = new JButton();
	
		// Setting initial text
		goodChoiceButton.setText(dialogue.dialogueOption.get(0));
		neutralChoiceButton.setText(dialogue.dialogueOption.get(11));
		badChoiceButton.setText(dialogue.dialogueOption.get(22));
		
		// Now to implement the actions
		goodChoiceButton.addActionListener(new DialogueListener());
		neutralChoiceButton.addActionListener(new DialogueListener());
		badChoiceButton.addActionListener(new DialogueListener());
			
		// ADD COMPONENTS TO PANEL
		panel.add(goodChoiceButton, BorderLayout.NORTH);
		panel.add(badChoiceButton, BorderLayout.CENTER);
		panel.add(neutralChoiceButton, BorderLayout.SOUTH);
						
		return panel;
	}
	
	
	// ACTION LISTENERS FOR DIALOGUE
	class DialogueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == goodChoiceButton) {
				System.out.println(dialogue.formatString(goodChoiceButton.getText(), 
						returnCallerScript(dialogue.dialogueOption.indexOf(goodChoiceButton.getText()))));
				
				score += 1;
				
				int currentGoodIndex = dialogue.dialogueOption.indexOf(goodChoiceButton.getText());
				int currentNeutralIndex = dialogue.dialogueOption.indexOf(neutralChoiceButton.getText());
				int currentBadIndex = dialogue.dialogueOption.indexOf(badChoiceButton.getText());
				
				dialogue.addToTranscript(goodChoiceButton.getText(), 
						returnCallerScript(dialogue.dialogueOption.indexOf(goodChoiceButton.getText())));	
				
				goodChoiceButton.setText(switchGoodButtonText(dialogue.dialogueOption, currentGoodIndex));
				neutralChoiceButton.setText(switchNeutralButtonText(dialogue.dialogueOption, currentNeutralIndex));
				badChoiceButton.setText(switchBadButtonText(dialogue.dialogueOption, currentBadIndex));
				
			} else if (e.getSource() == neutralChoiceButton) {
				System.out.println(dialogue.formatString(neutralChoiceButton.getText(), 
						returnCallerScript(dialogue.dialogueOption.indexOf(neutralChoiceButton.getText()))));
				
				score += 0;
				
				int currentGoodIndex = dialogue.dialogueOption.indexOf(goodChoiceButton.getText());
				int currentNeutralIndex = dialogue.dialogueOption.indexOf(neutralChoiceButton.getText());
				int currentBadIndex = dialogue.dialogueOption.indexOf(badChoiceButton.getText());
				
				dialogue.addToTranscript(neutralChoiceButton.getText(), 
						returnCallerScript(dialogue.dialogueOption.indexOf(neutralChoiceButton.getText())));
				
				goodChoiceButton.setText(switchGoodButtonText(dialogue.dialogueOption, currentGoodIndex));
				neutralChoiceButton.setText(switchNeutralButtonText(dialogue.dialogueOption, currentNeutralIndex));
				badChoiceButton.setText(switchBadButtonText(dialogue.dialogueOption, currentBadIndex));
						
			} else if (e.getSource() == badChoiceButton) {
				System.out.println(dialogue.formatString(badChoiceButton.getText(), 
						returnCallerScript(dialogue.dialogueOption.indexOf(badChoiceButton.getText()))));
				
				score -= 1;
				
				int currentGoodIndex = dialogue.dialogueOption.indexOf(goodChoiceButton.getText());
				int currentNeutralIndex = dialogue.dialogueOption.indexOf(neutralChoiceButton.getText());
				int currentBadIndex = dialogue.dialogueOption.indexOf(badChoiceButton.getText());
				
				dialogue.addToTranscript(badChoiceButton.getText(), 
						returnCallerScript(dialogue.dialogueOption.indexOf(badChoiceButton.getText())));
				
				goodChoiceButton.setText(switchGoodButtonText(dialogue.dialogueOption, currentGoodIndex));
				neutralChoiceButton.setText(switchNeutralButtonText(dialogue.dialogueOption, currentNeutralIndex));
				badChoiceButton.setText(switchBadButtonText(dialogue.dialogueOption, currentBadIndex));		
			}
		}
	}
	
	// Method to determine next dialogue button text for GOOD options
	public String switchGoodButtonText(ArrayList<String> inputArrayList, int index) {
		String outputString = "switchGoodButtonText method error";
		
		if(index < 10) {
			index += 1;
			outputString = inputArrayList.get(index);
		} else {
			goodChoiceButton.setVisible(false);
			System.out.println("\nEnding: " + calculateEnding(score));
			System.out.println("Score: " + score + "/61");
		}
		return outputString;
	}
	
	// Method to determine next dialogue button text for NEUTRAL options
	public String switchNeutralButtonText(ArrayList<String> inputArrayList, int index) {
		String outputString = "switchNeutralButtonText method error";
		
			if(index > 10 && index < 21) {
				index += 1;
				outputString = inputArrayList.get(index);
			} else {
				neutralChoiceButton.setVisible(false);
			}	
		return outputString;
	}
	
	// Method to determine next dialogue button text for BAD options
	public String switchBadButtonText(ArrayList<String> inputArrayList, int index) {
		String outputString = "switchBadButtonText method error";
		
		if(index > 21 && index < 32) {
			index += 1;
			outputString = inputArrayList.get(index);
		} else {
			badChoiceButton.setVisible(false);
		}
		return outputString;
	}
	
	// method to return callerScript 
	public String returnCallerScript(int index) {
		return dialogue.callerScript.get(index);
	}
	
	// Highest Score = 61
	// Middle Score = 49-53
	// Lowest Score = 39
	public String calculateEnding(int score) {
		String ending = "";
		if (score >= 53) {
			ending = dialogue.callerScript.get(33);
		} else if (score >= 49 && score <= 53) {
			ending = dialogue.callerScript.get(34);
		} else if (score < 53) {
			ending = dialogue.callerScript.get(35);
		}
		return ending;
	}
	
	// Will output from console window to JScrollPane
	private class GuiOutputStream extends OutputStream {
		JTextArea textArea;

	    public GuiOutputStream(JTextArea textArea) {
	    	this.textArea = textArea;
	    }
	     
	    @Override
	    public void write(int data) throws IOException {
	    	textArea.append(new String(new byte[] { (byte) data }));
	    }
	}		  
}
