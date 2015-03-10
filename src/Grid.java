import java.awt.event.*;
import java.awt.Button;

import javax.swing.*;
public class Grid 
{	//instance properties
	//size stores the size of the grid 
	private int size;
		
	//gameOver stores whether or not the game has ended
	private boolean gameOver;
	
	//gameLost stores whether or not the game is lost by the user
	private boolean gameLost;
	
	//gameWon stores whether or not the game has is won by the user
	private boolean gameWon;
	
	//numOfMines stores the number of mines in the grid
	private int numOfMines;
	
	//myGameBoard is a two-dimensional array of type Square 
	Square[][] myGameBoard;
	
	//surroundingMines  is an array of integers, that indicate the number of mines in its surrounding cells
	private int[][] surroundingMines;
						
	//constructor takes an integer as parameter
	public Grid(int x)
	{	
		//instantiate the variables
		//the size of the grid is determined by the parameter passed to the constructor
		size= x;
		
		//the number of mines in the field is (12.5% of the no. of squares)+(size/6)
		numOfMines =(int) ((0.125*size*size)+size/6);
		
		//surroundingMines is a two-dimensional array that stores the number of mines in the surrounding squares, for each square
		surroundingMines = new int[size][size];
		
		//myGameBoard is a two-dimensional array of Square Objects 
		myGameBoard = new Square[size][size];
			
		// initialize the contents of myGameBoard and add mouseListeners to each of them
	 	for ( int i = 0; i < size; i++)
	 	{	
	 		//for each square in the grid
	 		for (int j = 0; j < size; j++){
	 			
	 			//create a new Square
	 			myGameBoard[i][j] = new Square();	
	 			 			
	 			//this part of the code is for creating and adding mouse event listener
	 			
	 			//declare constants within the loop so that they can be passed as parameter while declaring the MouseEvent
	 			final int p = i;
	 			final int k = j;
	 			
	 			//declare a mouseListener, that distinguishes between left and right clicks
				MouseListener RBL = new MouseListener(){ 
				public void mouseClicked(MouseEvent e){/*we will leave this empty*/}
				
				//for the mouseEvent of type Mouse Pressed
				public void mousePressed(MouseEvent e){
					
					//if the event is a right click
					if (e.getButton()== MouseEvent.BUTTON3){
						
						//invoke the method to add flag to the tile
						myGameBoard[p][k].flagtile();
						
						//update the logic of the game to see if there is win or loss
						updateGameLogic();}
					
					//else, if the event is a lift click
					else if (e.getButton()== MouseEvent.BUTTON1){
						
						//invoke the method to carry out actions based on whether or not it is mined
						squareAction(p,k);
						
						//update the logic of the game to see if there is win or loss
						updateGameLogic();}					
					}
				
				public void mouseEntered(MouseEvent e){/*we will leave this empty*/}
				public void mouseExited(MouseEvent e){/*we will leave this empty*/}
				public void mouseReleased(MouseEvent e){/*we will leave this empty*/}

 				};
				
 		//add the Mouse Event declared above to the square	
		myGameBoard[i][j].addMouseListener(RBL);
	 		}	 	
	 	}
	 	
		//place mines on the grid
	 	placeMines();
	 	
	 	//find mines in neighborhood
	 	calculateSurroundingMines();
	}//the constructor ends here
	
	//this method updates the logic of the game and carries out actions based on the state of the game
	public void updateGameLogic()
	{
		//invoke a method to keep track of the game; this method updates the booleans gameWon and gameLost
		trackGame();
		
		//invokes a method to show message to the user, based on the outcome of the game
		showMsg();
		
		//if the game is either won or lost, invoke a method to remove all the MouseListeners from the squares.
		if (gameLost||gameWon) removeAllMouseListener();
	}
	
	//this method sets certain randomly chosen squares to contain mines
	public void placeMines()
	{	
		//declare a local variable to keep count of mines placed
		int countOfMines= 0;
		
		//for each mine to be placed,
		while (countOfMines <numOfMines)
		{ 
			//assign a random value for row number
			int rowNum = (int) Math.round ( Math.random()*(size-1));
			
			//assign a random value for column number
			int columnNum = (int) Math.round( Math.random()*(size-1));	
	
			//if a mine has not placed already
			if (!myGameBoard[rowNum][columnNum].containMines)
			{
				//place mine in the cell
				myGameBoard[rowNum][columnNum].setContainMines(true);
				
				//increase the count of mines placed
				countOfMines++;
			}
		}
	}
	
	 	
	 //this method intializes the variable minedNeighbours for each of the Squares
	 public void calculateSurroundingMines()
	 {	
		//for each square in the grid 
	 	for (int i= 0; i<size; i++)
	 	{
			for (int j= 0; j<size; j++)
			{	
				//if the square does not contain mines
				if (!myGameBoard[i][j].containMines)
				{
				//set numbers on the square based on the number of mined squares in neighborhood
				myGameBoard[i][j].minedNeighbours = fixNumber(i,j);	
				
				}
			}		
	 	}
	 }	
	
	//this method returns the total number of mined-fields in the surrounding of any square with index[i][j]
	public int fixNumber(int i, int j)
	{	
		//declare a local varaibel to keep count of the number of mines found in the surrounding cell
		int num = 0;
	
		//ternary operator '?' ensures that values for cells in the border are calculated correctly
		//if i is 0, choose 0 as the starting point, else choose i-1; same logic for j
		//if i is size-1, choose i as the end point, else choose j+1; same logic for j
		for (int a = ((i == 0) ? 0 : i-1); a <= (i == size-1 ? i : i+1); a++)
			{
				for (int b = (j == 0 ? 0 : j-1); b <= (j == size-1 ? j : j+1); b++)
				{
					//if the square contains a mine, increase the value of num by 1
					if (myGameBoard[a][b].containMines) num++;
	                    
				}   
	        }
		//the number of bombs in the neighbouring cells is num
		return num;
	}
	
	//this method carries out the actions that need to be done when a user clicks(left-clicks) on a square
	public void squareAction(int a, int b)
	{			
		//if the square contains mines
		if (myGameBoard[a][b].containMines)
		{	
			//invoke mine action method
			myGameBoard[a][b].MineAction();
			
			//invoke the method to reveal all the other mined-fields in the grid
			revealAllMines();
		}
		
		//else if the square does not contain a mine and the number of mines in the surrounding is not zero,
		//invoke a method to display the number of mines in its surrounding
		else if (myGameBoard[a][b].minedNeighbours>=1) myGameBoard[a][b].SafeAction(); 
		
		//else the number of mines in the surrounding is zero, so invoke a method to reveal the surrounding squares too.
		else revealSur(a, b);
			
	}	
	
	//this method recursively displays the number of mines in the surrounding, for all the adjacent squares which have 0 mined-fields in its immediate surrounding 
	//the index of the square is passed as the parameter
	public void revealSur(int x, int y)
	{	
		//set the boolean clicked to true
		myGameBoard[x][y].clicked= true;
	
		//this is our base case
		if (myGameBoard[x][y].minedNeighbours>=1) myGameBoard[x][y].SafeAction();
		
		//this is the recurssive case
		else
		{
			//set the button icon to that of tile image
			java.net.URL imageURL0 =  getClass().getResource("tile1.jpg");
			myGameBoard[x][y].setIcon(new ImageIcon(imageURL0));
			
			
			//ternary operator '?' ensures that values for cells in the border are calculated correctly
			//if i is 0, choose 0 as the starting point, else choose i-1; same logic for j
			//if i is size-1, choose i as the end point, else choose j+1; same logic for j
			for (int i = ((x == 0) ? 0 : x-1); i <= (x == size-1 ? x : x+1); i++)
			{
				//for each cell in the surrounding,
				for (int j = (y == 0 ? 0 : y-1); j <= (y == size-1 ? y : y+1); j++)
				{
					//if it has not been clicked yet, invoke itself with the index of the cell passed as parameters
					if(!myGameBoard[i][j].clicked) revealSur(i, j);
				}	
					   
	        }
		}
		 
	}
	
	
	//this method reveals all the squares in the grid that have bene mined
	public void revealAllMines()
	{	
		//for each square on the grid
		for (int i= 0; i<size; i++)
		{
			for (int j= 0; j<size; j++)
			{	
				//if the square contains mines, invoke a method to carry out actions of mined field.
				if (myGameBoard[i][j].containMines) myGameBoard[i][j].MineAction();

			}
		}
			
	}
	
	//this method returns a boolean value, based on whether all the squares in the grid with mines have been flagged by the user
	public boolean allCorrectlyFlagged()
	{	
		boolean result=false;
	
		//for each square in the grid
		Outer: for (int i= 0; i<size; i++)
		{
			for (int j= 0; j<size; j++)
			{
				//if the square contains mine and if it has been flagged by the user, set result to true
				if (myGameBoard[i][j].flagged && myGameBoard[i][j].containMines) result = true;
				
				//if any of the tiles have been flagged incorrectly,return false and terminate the loop
				else if (myGameBoard[i][j].flagged && !myGameBoard[i][j].containMines){ result = false;
							break Outer;}
				//or if any mined squared has not been flagged yet, return false and terminate the loop
				else if (!myGameBoard[i][j].flagged && myGameBoard[i][j].containMines){ result = false;
							break Outer;}
			}
		}
		return result;
	}
 
	//this method keeps track of the game and updates the values of the booleans gameWon and gameLost
	public void trackGame()
	{
	 	for (int i= 0; i<size; i++)
		{
			for (int j= 0; j<size; j++)
			{	
				//if the user has clicked on any of the mines
				if (myGameBoard[i][j].mineChosen)
				{	
					//set gameLost to true;
					gameLost= true;
				}
			
			}
		}
	 	
	 	//else, if the user has correctly identified all the mined fields in the grid
	 	if (allCorrectlyFlagged())
		{
			//set gameWon to true;
			gameWon= true;
		}
	}
	
	//this method shows message in the form of dialog box, when a game is won or lost
	public void showMsg()
	{
		if(gameLost)
	
		{
			JOptionPane.showMessageDialog(null, "Game Over! You lost the game.");
		}
	
		else if(gameWon)
		{
			JOptionPane.showMessageDialog(null, "Congratulations! You won the game.");
		}
	}
	
	//this method removes all the mouse listeners that were added to the squares
	public void removeAllMouseListener()
	{		
		for (int i= 0; i<size; i++)
		{
			for (int j= 0; j<size; j++)
			{
				myGameBoard[i][j].removeMouseListener(myGameBoard[i][j].getMouseListeners()[1]);
			}
		}
	}
}//end of class definition
