//Joseph Vigil - Salazar
//jsala235@unm.edu
//CS 251, lab section 002
//Lab 9: Collapse_Full_Game
//CollapseFrame.java

/*
 * This class contains the main frame that the
 * game will be played on. All panels and buttons
 * are added to the frame. This class also contains the 
 * follow of this game. 
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CollapseFrame extends JFrame {
	
	private final int COLUMN_AMT = 12; //Amount of columns
	private final int ROW_AMT    = 15; //Amount of rows
	private final String START_WORD = "Start"; //String for button
	private final String PAUSE_WORD = "Pause"; //String for button
	//String JOptionPane message when game is over
	private final String GAMEOVER_MESSAGE = "Game Over!! Blocks went off grid";
	private final int THERSHOLD = 3; //minimum threshold amount
	
	Timer timer; //timer object
	GridPanel gp; //GridPanel object
	IncomingBlockPanel ib; //IncomingBlockPanel object
	BlockManager bm; //BlockManager object
	InfoPanel ip; //InfoPanel object
	JPanel startBtnPanel; //JPanel that contains the start/pause button
	JButton srt_puaseBtn; //JButton to start/pause the game.
	
	//Default constructor
	public CollapseFrame(){
		ip = new InfoPanel();
		ib = new IncomingBlockPanel();
		bm = new BlockManager(ROW_AMT,COLUMN_AMT,THERSHOLD);
		gp = new GridPanel(bm,ip); //pass in BlockManager & InfoPanel to GridPanel
		//ActionListener for timer
		timer = new Timer(250,new ActionListener(){
			  public void actionPerformed(ActionEvent e)
			  {
				/*first check if the incoming random block row is full.
				 * If it is then give that filled array of random blocks to the
				 * BlockManager.   
				 */
				if(ib.isInComingRowFull()){
				   bm.addIncomingRowToMainGrid(ib.getIncomingBlocks());
				   ib.clearIncomingBlocksArray();
				   gp.repaint(); //repaint the grid panel
				   if(bm.isGameOver()){ //test if game is over
					  srt_puaseBtn.setVisible(false); //disable button
					  timer.stop();//stop timer
					  gp.setSelectEnabled(false); //Grid panel becomes unselectable 
					  //Display the game over message. 
					 JOptionPane.showMessageDialog (null, GAMEOVER_MESSAGE,
								 "Game Over",JOptionPane.INFORMATION_MESSAGE);
					}
				 }
				 else //keep adding random blocks
					 ib.addRandomBlock();
			   }
			});
		timer.setRepeats(true); //set timer to continually repeat
		
		startBtnPanel = new JPanel(new BorderLayout());
		startBtnPanel.setBorder(BorderFactory.createTitledBorder("Press to start"));
		startBtnPanel.setPreferredSize(new Dimension(150,300)); //dimensions of startBtnPanel
		startBtnPanel.setBackground(new Color(92,153,192)); //color of startBtnPanel
	    srt_puaseBtn = new JButton(); //create the start/pause button
		srt_puaseBtn.setText(START_WORD); //set button text to start
		startBtnPanel.add(srt_puaseBtn,BorderLayout.SOUTH);//add button to panel
	
		/*
		 * New JPanel where InfoPanel and startBtnPanel
		 * will be added to
		 */
		JPanel combinedPanelOne = new JPanel(new BorderLayout());
		combinedPanelOne.add(ip,BorderLayout.NORTH);
		combinedPanelOne.add(startBtnPanel,BorderLayout.SOUTH);

		/*
		 * ActionListener for when the mouse is clicked. 
		 */
		srt_puaseBtn.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  /*
			   * This portion will pause the game. 
			   * If timer is running then stop the timer.
			   * Set the button text to "Start".
			   * Nothing in the grid can be clicked on. 
			   */
			  if (timer.isRunning()){
				  timer.stop();
				  srt_puaseBtn.setText(START_WORD);
				  gp.setSelectEnabled(false);
			  }
			  else{
				  /*
				   * Start's or resumes the game. 
				   * Starts the timer. 
				   * Set's button text to "Pause".
				   * blocks in the grid panel can be selected.
				   */
				  timer.start();
				  srt_puaseBtn.setText(PAUSE_WORD);
				  gp.setSelectEnabled(true);
			  }
		  }
		});

		setTitle("Joseph Vigil-Salazar, Lab 9: Collapse Game");
		/*
		 * Create a new panel to house the grid panel and incoming block
		 * panels. Add both those new panels to the new panel.
		 */
		JPanel combinedPanelTwo = new JPanel(new BorderLayout());
		combinedPanelTwo.add(gp, BorderLayout.CENTER);
		combinedPanelTwo.add(ib, BorderLayout.SOUTH);

		getContentPane().add(combinedPanelTwo, BorderLayout.CENTER);
		getContentPane().add(combinedPanelOne, BorderLayout.EAST);
		pack(); //pack the frame contents
	}
} //end class
