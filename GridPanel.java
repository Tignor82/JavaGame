//Joseph Vigil - Salazar
//jsala235@unm.edu
//CS 251, lab section 002
//Lab 9: Collapse_Full_Game
//GridPanel.java

/*This class draws all the blocks needed to play the game on its panel. 
 The class also contains the mouse listener to handle game logic of 
 selecting a block on the panel. */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Rectangle;

public class GridPanel extends JPanel{
	BlockManager block_Manager;
	InfoPanel info_Panel;
	 
	private boolean isSelectEnabled = false;
	
	//constructor
	public GridPanel(BlockManager blockManager, InfoPanel infoPanel) {
		block_Manager = blockManager;
		info_Panel = infoPanel;
		setPreferredSize(new Dimension(650,675));
		setBackground(Color.BLACK);
		
		addMouseListener( new MouseAdapter() {
			@Override
		     public void mouseClicked( MouseEvent e ) {
			   //Test to make sure timer is not paused.
			   if(!isSelectEnabled){
					return;
				}
				//First get general square area, then test if not in
				//black dead zone around square
				int column = e.getX()/getColumnWidth();
			    int row = e.getY()/getRowHeight();
			    Rectangle rect = rectForSquare(row, column);
			    if(e.getX() >= rect.x && e.getX() < rect.x + rect.width &&
			       e.getY() >= rect.y && e.getY() < rect.y + rect.height &&
			    	block_Manager.selectBlock(row, column) >= block_Manager.getThresHold()){
			    		info_Panel.getGroupAmtTxtBox().setText(Integer.
			    				toString(block_Manager.getBlockGroupAmtRemoved()));
			    		info_Panel.getscoreTxtBox().setText(Integer.
			    				toString(block_Manager.getScore()));
			    		repaint();
			        }
			} });
	} //end constructor
	
	/*Private method to construct a new rectangle based on
	 * calculated deminsions. The return rectangle will be
	 * used to draw the blocks on the game board. 
	 */
	private Rectangle rectForSquare(int row, int col){
		int startX = (getWidth() % getColumnWidth())/2;
		int startY = (getHeight() % getRowHeight())/2;
		int size = Math.min((int)(getColumnWidth()*.8), (int)(getRowHeight()*.8));
		int x = startX + (getColumnWidth()-size)/2+(int)(getColumnWidth()*col);
		int y = startY + (getRowHeight()-size)/2+(int)(getRowHeight()*row);
		return new Rectangle(x, y, size, size);
	}
	
	/*Draw the blocks on the game board */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Color blockArray[][] = block_Manager.getGameBoard();
		for (int i = 0; i < block_Manager.getNumOfRows(); i+=1){
			for (int j = 0; j < block_Manager.getNumOfCols(); j+=1){
				g.setColor(blockArray[i][j]);
				Rectangle rect = rectForSquare(i, j);
				g.fillRect(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
	
	/*Method to retrieve the width of a column for the game grid */
	public int getColumnWidth(){
		return (int)(getWidth()/block_Manager.getNumOfCols());
	}
	/*Method to retrieve the heigth of a column for the game grid */
	public int getRowHeight(){
		return (int)(getHeight()/block_Manager.getNumOfRows());
	}
	
	public void setSelectEnabled(boolean enabled){
		this.isSelectEnabled = enabled;
	}
	
} //end class
