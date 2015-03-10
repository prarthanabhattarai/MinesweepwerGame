import java.lang.Object;
import java.awt.*;

import javax.swing.*;

import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

public class MinesweeperApplication extends JPanel
{
	//instance properties
	Grid myGrid;
	Integer preferredGridSize;
	JComboBox difficulty;
	JFrame frame;
	JPanel gridPanel;
				
	//the constructor creates a new grid and steps up a frame to display the grid; 
	//the desired size of the grid is passed as the parameter
	public MinesweeperApplication(int x)
	{
		//create a new grid
		myGrid = new Grid(x);
		
		//invoke a method to setup the frame to display the grid
		setupFrame();
	}
	
	
	//this method creates a frame that displays squares as buttons
	public void setupFrame()
	{
		//create the frame and set its size
		frame = new JFrame ();
		frame.setSize(650,700);
		
		//make the frame non-resizable by the user
		frame.setResizable(false);
		
		//exit the game when the user closes the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//use BorderLayout to set the layout of contents in the frame
		frame.setLayout(new BorderLayout());
		
		//create a gridPanel
		gridPanel = createGridPanel();
	
		//add it as a content in the frame
		frame.add(gridPanel,BorderLayout.SOUTH);
		
		//create and add an optionsPanel in the frame
		frame.add(createOptionsPanel(),BorderLayout.NORTH);
		
		//display the frame
		frame.setVisible(true);			
	}
	
	//this method creates the display of grid panel
	public JPanel createGridPanel()
	{	
		//if preferredGridSize is null, set value of x to 9, else set the value of x to that of preferredGridSize
		int x= ((preferredGridSize == null) ? 9 : preferredGridSize);
		
		//create a new panel
		gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(600,600));
		
		//use GridLayout to set the layout of the grid
		gridPanel.setLayout(new GridLayout(x, x));
		
		//for each of the square created, 		
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<x; j++)
			{
				//add it to the grid panel
				gridPanel.add(myGrid.myGameBoard[i][j]);
			}
		}
		
		return gridPanel;
	}
	
	//this method creates an JPanel that has options like drop-down box and reset button
	public JPanel createOptionsPanel() 
	{
		//create a new panel
		JPanel optionsPanel = new JPanel();
		
		//use BoxLayout to set the lay out of this panel
		optionsPanel.setLayout(new BoxLayout (optionsPanel, BoxLayout.X_AXIS));
		
		//create labels to be displayed on the panel
		JLabel myLabel1 = new JLabel("Set difficulty level:   ");
		JLabel myLabel2 = new JLabel("Flag all the mined fields (by right-clicks) to win! ");
		
		//add the first label
		optionsPanel.add(myLabel1);
		
		//invoke a method to create drop-down menu option and add it to panel	
		optionsPanel.add(putDifficultyOption());
		
		//add a vertical separator so that components are separated properly
		optionsPanel.add(new JSeparator(SwingConstants.VERTICAL));
		
		//invoke a method to create a reset button and add it to panel	
		optionsPanel.add(createResetButton());
		
		//add a vertical separator so that components are separated properly
		optionsPanel.add(new JSeparator(SwingConstants.VERTICAL));
		
		//add the second label		
		optionsPanel.add(myLabel2);
		
		return optionsPanel;
	}
	
	//this method creates JButton that allows user to reset the game
	public JButton createResetButton()
	{
		//create a JButton
		JButton resetButton = new JButton("Reset");
		
		//define action listener for the button
		ActionListener RBL = new ActionListener(){
 			public void actionPerformed(ActionEvent e){
 				
 				//if preferredGridSize is null, set value of x to 9, else set the value of x to that of preferredGridSize
 				int x =((preferredGridSize == null) ? 9 : preferredGridSize);
 				
 				//create a new grid
 				myGrid = new Grid(x);
 				
 				//remove the previous grid from the grid panel
				frame.remove(gridPanel);
				
				//invoke createGridPanel() method to create a new grid panel for the newly created grid
				//add it to the current frame
				frame.add(createGridPanel(),BorderLayout.SOUTH);
				
				//display the frame
				frame.setVisible(true);	
 				
 				}
 				};
 					 			
			//this adds action listener to the reset button
 			resetButton.addActionListener( RBL);
		
		return  resetButton;
	}
	
	//this method creates JCombox that allows user to set the difficulty level of the game
	public JComboBox putDifficultyOption()
	{				
		//declare an array of strings to store the strings to show as options for the user
		String [] comboSelection = new String[]{"Beginner","Intermediate","Advanced"};

		//create a new JComboBox
		difficulty = new JComboBox(comboSelection);
							
		//define action listeners for the JComboBox 
		ActionListener comboListener= new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		String Choice= (String)difficulty.getSelectedItem();;
		
		//if the user chooses 'beginnner'
		if (Choice.equals("Beginner"))
			{
				preferredGridSize=9;
				
				//create a new grid by passing preferredGridSize as the parameter
				myGrid = new Grid(preferredGridSize);
				
				//remove the previous grid from the grid panel
				frame.remove(gridPanel);
				
				//invoke createGridPanel() method to create a new grid panel for the newly created grid
				//add it to the current frame
				frame.add(createGridPanel(),BorderLayout.SOUTH);
				
				//display the frame
				frame.setVisible(true);		

			}
				
		else if (Choice.equals("Intermediate"))
			{
				preferredGridSize=12;
									
				//create a new grid by passing preferredGridSize as the parameter
				myGrid = new Grid(preferredGridSize);
				
				//remove the previous grid from the grid panel
				frame.remove(gridPanel);
				
				//invoke createGridPanel() method to create a new grid panel for the newly created grid
				//add it to the current frame
				frame.add(createGridPanel(),BorderLayout.SOUTH);
				
				//display the frame
				frame.setVisible(true);		
			}
		else if (Choice.equals("Advanced"))
			{ 
				preferredGridSize=15;
				
				//create a new grid by passing preferredGridSize as the parameter
				myGrid = new Grid(preferredGridSize);
				
				//remove the previous grid from the grid panel
				frame.remove(gridPanel);
				
				//invoke createGridPanel() method to create a new grid panel for the newly created grid
				//add it to the current frame
				frame.add(createGridPanel(),BorderLayout.SOUTH);
				
				//display the frame
				frame.setVisible(true);		
				}	
		}
		};
		
		//add the action listener to the box
		difficulty.addActionListener(comboListener);
		 
		//return the JComboBox 
		return difficulty;
	}
	
	
	 public static void main(String[] args)
	 {
		 final int DEFAULT_SIZE=9;
		 
		 new MinesweeperApplication(DEFAULT_SIZE);
	 }

	
}