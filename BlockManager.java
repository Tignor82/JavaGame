

//Joseph Vigil - Salazar
//jsala235@unm.edu
//CS 251, lab section 002
//Lab 8: BlockManager
//BlockManager.java

/*This class is used to remove blocks for the game Collapse.
 * Blocks are then moved down to fill in any empty spaces, and 
 * columns are shift inward to fill in any empty columns. 
 */

import java.awt.Color;
import java.util.*;

public class BlockManager {
	
	private int numOfRows = 0; //number of rows for the grid
	private int numOfCols = 0; //number of columns for the grid
	private int thresHold = 0; //minimum amount of blocks to be removed
	private int blockRemovedCount = 0; //amount of blocks that were removed
	private int blockGroupRemoved = 0;
	private int playerScore = 0; 
	private boolean isBlockOffGrid = false;
	
	
	//2d array to hold the game board. 
	Color[][] gameBoard;
	//2d array to hold an updated version of the game board 
	Color[][] updatedGameBoard;
	
	/*
	 * Constructor for the BlockManager class. Takes in row amount, column
	 * amount, threshold, and which fill pattern to use as parameters.
	 * Calls set methods to set row amount, column amount, and threshold.
	 */
	public BlockManager(int rowAmt,int colAmt, int thresHold){
		setNumOfRows(rowAmt);//set the number of rows
		setNumOfCols(colAmt);//set the number of columns
		setThresHold(thresHold); //set the threshold
		//create the game board with number of rows and columns
		gameBoard = new Color[getNumOfRows()][getNumOfCols()];
		updatedGameBoard = null; //set newGameBoard to null
		fillBottom3RowsAtStartUp();//Method to fill bottom three rows only
	}
	
	/*
	 * This method, at start up, fills the bottom three rows 
	 * of the game board with blocks of random colors. 
	 */
	private void fillBottom3RowsAtStartUp(){
		Random rand = new Random();
		int randomColorNumber = 0;
		
		for(int i = 0; i<getNumOfRows(); i++){
			for(int j = 0; j<getNumOfCols(); j++){
				if(i<getNumOfRows() - 3)
					gameBoard[i][j] = Color.BLACK;
				else{
					randomColorNumber = rand.nextInt((3 - 1) + 1) + 1;
					if(randomColorNumber == 1)
						gameBoard[i][j] = Color.RED;
					else if(randomColorNumber == 2)
						gameBoard[i][j] = Color.GREEN;
					else
						gameBoard[i][j] = Color.BLUE;
				}
			}
		}
	}
	
	/*Private method to shift columns to fill in empty columns from 
	 * removed blocks. Is called from collapseBlocks() method.
	 */
	private void shiftBlocks(int column){
		//shift all columns to left of column by 1 to the right
				//then, set left-most column to all EMPTY
		for(int i=0;i< getNumOfRows(); i++)
			for(int j=column; j >= 1; j-=1)
				gameBoard[i][j] = gameBoard[i][j-1];

		for(int i=0;i< getNumOfRows(); i++)
			gameBoard[i][0] = Color.BLACK;
	}
	
	/*Private method to collapse the blocks. Drops blocks from above
	 * to fill in blocks that were set to empty. Calls the shiftBlocks() method 
	 * if needed.
	 */
	private void collapseBlocks(){
		//start at bottom row, first column
		//go up the rows but in the same column
		//move on to the next column.
		//count the empty blocks
		//empty block count is amount of empty blocks we will fill on top
		for(int col=0;col< getNumOfCols();col+=1){
			int emptyBlockCount = 0;
			int destRow = getNumOfRows()-1; // this is where a non-empty will be collapsed to
			for(int row = getNumOfRows()-1; row>=0; row-=1){
				if(gameBoard[row][col] == Color.BLACK){
					emptyBlockCount +=1;
				}
				else {
					gameBoard[destRow][col] = gameBoard[row][col];
					destRow -= 1;
				}
			}
			if(emptyBlockCount == getNumOfRows())
				shiftBlocks(col);//pass in the column
			else{
			    for (int row = 0; row < emptyBlockCount; row+=1) {
				    gameBoard[row][col] = Color.BLACK;
			    }
			}
		}
		
	}
	
	/*Private method to return the number of blocks select to be removed.
	 * Uses getBlockCountRecursive() method to count the number of blocks.
	 */
	private int getBlockCount(int row, int col){
		/*if not on gameboard then return count of 0 */
		if (row < 0 || row >= getNumOfRows() || col < 0 || col >= getNumOfCols())
			return 0;

		/* create next state temporary game board
		 * this new board will have EMPTY placed in all touching squares along same color
		 * if the returned count is >= threshold, this new board will be turn into the current board
		 * otherwise, this board is just temporary and will be thrown away
		 */
		updatedGameBoard = new Color[getNumOfRows()][getNumOfCols()];
		for (int i = 0; i < getNumOfRows(); i+=1)
			for (int j = 0; j < getNumOfCols(); j+=1)
				updatedGameBoard[i][j] = gameBoard[i][j];

		/* return result of recursive block count */
		return getBlockCountRecursive(updatedGameBoard[row][col], row, col);
	}
	
	/*Private method that uses recursion to count the number of blocks to be
	 * removed. also sets those blocks to empty. 
	 */
	private int getBlockCountRecursive(Color bc, int row, int col){
		/*if not on gameboard then return count of 0 */
		if (row < 0 || row >= getNumOfRows() || col < 0 || col >= getNumOfCols())
			return 0;
		/*skip over if its not the same color or empty*/
		if (updatedGameBoard[row][col] != bc || updatedGameBoard[row][col] == Color.BLACK)
			return 0;
		/* set the block to black so that recursion doesn't
		 * revisit and recount this square
		 */
		updatedGameBoard[row][col] = Color.BLACK;
		
		/* return this square plus counts rooted at 4 surrounding squares */
		return 1 + getBlockCountRecursive(bc, row+1, col)
			 + getBlockCountRecursive(bc, row-1, col)
			 + getBlockCountRecursive(bc, row, col+1)
			 + getBlockCountRecursive(bc, row, col-1);
	}
	
	/*private method to update the score*/
	private void updateScore(){
		playerScore += 5*Math.pow(2,Math.max(0, blockRemovedCount-getThresHold()));
	}
	
	/*Return the number of rows */
	public int getNumOfRows() {
		return numOfRows;
	}
	
	
	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}

	public void setNumOfCols(int numOfCols) {
		this.numOfCols = numOfCols;
	}

	public void setThresHold(int thresHold) {
		this.thresHold = thresHold;
	}

	/*return the current threshold */ 
	public int getThresHold() {
		return thresHold;
	}
	
	/*Return the number of columns */
	public int getNumOfCols() {
		return numOfCols;
	}
	
	/*Return the current player score*/
	public int getScore() {
		return playerScore;
	}
	
	public int getBlockGroupAmtRemoved() {
		return this.blockGroupRemoved;
	}
	
	public Color[][] getGameBoard() {
		return gameBoard;
	}

	/*
	 * This method selects at block at a given location. uses getBlockCount() 
	 * method to determine if any blocks were removed. 
	 */
	public int selectBlock(int row, int col){
		
	    updatedGameBoard = null;
	    blockRemovedCount = getBlockCount(row,col);
		if(blockRemovedCount >= thresHold){
			gameBoard = updatedGameBoard; //some blocks were set to empty
			collapseBlocks(); //collapse the blocks
			blockGroupRemoved++;
			updateScore(); //calculates the score 
			return blockRemovedCount;
		}
		return 0; //no blocks were removed
	}
	
	/* method to return whether colored block is off grid */
	public boolean isGameOver(){ 
		return isBlockOffGrid;
	}
	
	/*This method is used to add the incoming row of random blocks
	 * to the gbottom row of the game board. Also sets boolean to 
	 * determine if any red,blue,or green blocks have exceeded the game
	 * board, if so then game is over.
	 */
	public void addIncomingRowToMainGrid(Color[] incomingRow){
	
		for (int i = 0; i < getNumOfCols();i+=1){
			//test to see if top block of column was
			//filled by a color, if so then game is over. 
			if(gameBoard[0][i]!=Color.BLACK)
				isBlockOffGrid = true;
			//shifting the blocks of same columns up one row
			for(int j=1;j<this.getNumOfRows();j+=1)
				gameBoard[j-1][i] = gameBoard[j][i];
			
            //add the incoming row block to bottom row
			gameBoard[getNumOfRows()-1][i] = incomingRow[i];
		}
	}
} //end class
