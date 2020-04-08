//Joseph Vigil - Salazar
//jsala235@unm.edu
//CS 251, lab section 002
//Lab 9: Collapse_Full_Game
//IncomingBlockPanel.java

/*
 * This class fills an array of 12 colors with random
 * blocks of either red, green, or blue. This class also
 * draws the 12 blocks in its panel individually according
 * to the timer from CollapseFrame class. 
 */
import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class IncomingBlockPanel extends JPanel{
	
	private final int COLUMN_AMT = 12; //column amount
	private final int FIRST_COLOR = 1;//red
	private final int SECOND_COLOR = 2;//green
	private final int THIRD_COLOR = 3;//blue
	private int incomingArray[];// = new int[12];
	private Color incomingArrayBlockColors[];
	private int amountOfBlocks = 0;
	/*array of double numbers used in the paintComponents method
	 * draw the 12 random blocks. 
	 */
	private double graphicsPtsArray[] = {.25,1.25,2.25,3.25,4.25,5.25,6.25,
					     7.25,8.25,9.25,10.25,11.25};
	
	/*Default constructor */
	public IncomingBlockPanel(){ 
		//set title border
		setBorder(BorderFactory.createTitledBorder("Incoming blocks"));
		setPreferredSize(new Dimension(825,50)); //set size of panel
		setBackground(Color.LIGHT_GRAY); //set panel background color
		incomingArray = new int[COLUMN_AMT]; //initialize incoming array
		incomingArrayBlockColors = new Color[COLUMN_AMT];
		fillIncomingArray(FIRST_COLOR,THIRD_COLOR);	
	}
	
	/* Private method that fills an array with random
	 * numbers between 1 and 3. Calls fillColorArray method 
	 *
	 */
	private void fillIncomingArray(int min, int max){
		Random rand = new Random();
		int randomNumber = 0;
		for(int i=0;i<incomingArray.length;i++){
			randomNumber = rand.nextInt((max - min) + 1) + min;
			incomingArray[i] = randomNumber;
		}
		fillColorArray();
	}
	
	/*
	 * Private method that fill array with color objects based
	 * on the random numbers from the fillIncomingArray method
	 */
	private void fillColorArray(){
		for(int i=0;i<incomingArray.length;i++){
			if(incomingArray[i]==FIRST_COLOR)
				incomingArrayBlockColors[i] = Color.RED;
			else if(incomingArray[i]==SECOND_COLOR)
				incomingArrayBlockColors[i] = Color.GREEN;
			else
				incomingArrayBlockColors[i] = Color.BLUE;
		}
	}
	
	/*Method to draw draw blocks within the panel */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0;i<amountOfBlocks;i++){
			g.setColor(incomingArrayBlockColors[i]);
			g.fillRect((int)(getColumnWidth()*graphicsPtsArray[i]),getHeight()/4, 
					getColumnWidth()/2, (int)(getRowHeight()/1.5));
		}
	}
	
	/*Method to return incomingArrayBlockColors color object array */
	public Color[] getIncomingBlocks(){
		return incomingArrayBlockColors;
	}
	
	/*Method to return the width of each column within the panel*/
	public int getColumnWidth(){
		return getWidth()/COLUMN_AMT;
	}
	
	/*Method to return the row height within the panel */
	public int getRowHeight(){
		return getHeight();
	}
	
	public void addRandomBlock(){
		/*
		 * if the incoming row is not already full, then:
		 * 1. get block color based on random number from 1 to 3 where
		 *    1 -> RED, 2 -> GREEN, and 3 -> BLUE
		 * 2. add the random block color to the end
		 * 3. increment the amountOfBlock
		 * 
		*/
		if (!isInComingRowFull()) {
			Random rand = new Random();
			int number = rand.nextInt((THIRD_COLOR - FIRST_COLOR) + 1) + FIRST_COLOR;
			if(number == FIRST_COLOR)
				incomingArrayBlockColors[amountOfBlocks] = Color.RED;
			else if(number == SECOND_COLOR)
				incomingArrayBlockColors[amountOfBlocks] = Color.GREEN;
			else
				incomingArrayBlockColors[amountOfBlocks] = Color.BLUE;
			
			amountOfBlocks+=1;
			repaint();
		}
	}

	/*Method to return whether the 
	 * incomingArrayBlockColors array 
	 * is full.
	 */
	public boolean isInComingRowFull(){
		return amountOfBlocks >= COLUMN_AMT;
	}
	
	/*
	 * Method to set all blocks within 
	 * incomingArrayBlockColors array to 
	 * black. 
	 */
	public void clearIncomingBlocksArray(){
		for(int i=0;i<COLUMN_AMT;i+=1){
			incomingArrayBlockColors[i] = Color.BLACK;
		}
		amountOfBlocks = 0;
		repaint();
	}
} //end class
