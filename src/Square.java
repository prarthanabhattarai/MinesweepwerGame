import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//the class Square is a superclass of JButton
public class Square extends JButton
{
	//instance properties
	//containMines stores whether or not the square contains mines
	boolean containMines;
	
	//clicked stores whether or not user has clicked on the square
	boolean clicked;
	
	//mineChosen stores whether or not the game is lost (it is lost when user chooses mined square)
	boolean mineChosen;
	
	//minedNeighbours stores the number of mined squares in its neighbourhood
	int minedNeighbours;
	
	//flagged stores whether or not a feild has been flagged by the user
	boolean flagged;
	
	//constructor
	public Square()
	{
			//initialize the variables
			containMines=false;
			clicked = false;
			mineChosen = false;
			minedNeighbours=0;	
			flagged=false;
			
	}
	
	//this method changes the value of boolean flagged to true, and changes the button-icon to flag
	//it is invoked when user right-clicks on a square
	public void flagtile()
	{	
		//set flagged to true
		flagged=true;
		
		//import the image that will be used as icon
		java.net.URL imageURLFlag =  getClass().getResource("flag.jpg");
		
		//set the image as button icon
		this.setIcon(new ImageIcon(imageURLFlag));
	}

	//this method contains all the actions that needs to be carried out when a mined-field is chosen.
	//it is invoked when the user clicks on a mined-field
	public void MineAction()
	{		
		//import the image that will be used as icon
		java.net.URL imageURL =  getClass().getResource("Mine.jpg");
	
		//set the image as button icon
		this.setIcon(new ImageIcon(imageURL));
		
		//set the boolean mineChosen to true		
		mineChosen = true;
		
		//set the boolean clicked to true
		clicked = true;
	}
	
	//this method contains all the actions that needs to be carried out when a safe-field is chosen
	//it is invoked when the user clicks on a safe-field
	public void SafeAction()
	{
		//System.out.println("You chose a safe mine");
		
		//boolean clicked is set to true
		clicked = true;
		
		//invoke chooseImage() method to set the correct image as icon on the button
		this.setIcon(chooseImage());
		
	}
	
	public ImageIcon chooseImage()
	{			
		//import all the image that will be used as icons
		java.net.URL imageURL0 =  getClass().getResource("tile1.jpg");
		java.net.URL imageURL1 =  getClass().getResource("one.jpg");
		java.net.URL imageURL2 =  getClass().getResource("two.jpg");
		java.net.URL imageURL3 =  getClass().getResource("three.jpg");
		java.net.URL imageURL4 =  getClass().getResource("four.jpg");
		java.net.URL imageURL5 =  getClass().getResource("five.jpg");
		java.net.URL imageURL6 =  getClass().getResource("six.jpg");//it is highly unlikely that these images beyond this, will be used
		java.net.URL imageURL7 =  getClass().getResource("seven.jpg");
		java.net.URL imageURL8 =  getClass().getResource("eight.jpg");
		
		
		//declare a variable to type ImageIcon to store the desired image
		ImageIcon correctImage = new ImageIcon("");
		
		//assign the appropriate image, based on the number of mined-fields in the neighborhood
		if (minedNeighbours==0) correctImage = new ImageIcon(imageURL0);
		else if (minedNeighbours==1) correctImage = new ImageIcon(imageURL1);
		else if (minedNeighbours==2) correctImage = new ImageIcon(imageURL2);
		else if (minedNeighbours==3) correctImage = new ImageIcon(imageURL3);
		else if (minedNeighbours==4) correctImage = new ImageIcon(imageURL4);
		else if (minedNeighbours==5) correctImage = new ImageIcon(imageURL5);
		else if (minedNeighbours==4) correctImage = new ImageIcon(imageURL6);
		else if (minedNeighbours==4) correctImage = new ImageIcon(imageURL7);
		else if (minedNeighbours==4) correctImage = new ImageIcon(imageURL8);
		
		//return the desired image
		return correctImage;
		
	}
	
	
	//getters and setters
	public boolean getContainMines() {
		return containMines;
	}

	public void setContainMines(boolean containMines) {
		this.containMines = containMines;
	}

	public int getMinedNeighbours() {
		return minedNeighbours;
	}

	public void setMinedNeighbours(int minedNeighbours) {
		this.minedNeighbours = minedNeighbours;
	}
	
}//end of class definition
