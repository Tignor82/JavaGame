//Joseph Vigil - Salazar
//jsala235@unm.edu
//CS 251, lab section 002
//Lab 9: Collapse_Full_Game
//InfoPanel.java
import java.awt.*;
import javax.swing.*;

/*
 * This class is used to contain the panel and its components that will
 * display the current score and the amount of blocks of groups removed. 
 */

public class InfoPanel extends JPanel{
	private JLabel scoreLabel; //JLabel for score
	private JLabel groupRemoved; //JLabel for amount of blocks removed
	//text field to display the amount of blocks removed
	private JTextField amtOfGroupsRemovedTxtBox; 
	private JTextField scoreTxtBox; //text field to display current score
	/*
	 * InfoPanel Constructor. 
	 * Initializes both JLabels and JTextFields and adds them to 
	 * the panel. 
	 */
	public InfoPanel(){
		setLayout(new GridLayout(8,1));
		setBorder(BorderFactory.createTitledBorder("Game info"));
		setPreferredSize(new Dimension(150,425));
		scoreLabel = new JLabel("Score");
		scoreTxtBox = new JTextField();
		groupRemoved = new JLabel("Groups removed");
		amtOfGroupsRemovedTxtBox = new JTextField();
		/*Both info displayed in text boxes can't be edited*/
		scoreTxtBox.setEditable(false);
		amtOfGroupsRemovedTxtBox.setEditable(false);
		/*Add the lables and text boxes to the panel*/
		add(scoreLabel);
		add(scoreTxtBox);
		add(groupRemoved);
		add(amtOfGroupsRemovedTxtBox);
		
		setBackground(new Color(142, 223, 144)); //Set background color to pink
	}
	
	/* Method to return the text box for the score */
	public JTextField getscoreTxtBox(){
		return scoreTxtBox;
	}
	
	/* Method to return the text box for the amount of blocks removed*/
	public JTextField getGroupAmtTxtBox(){
		return amtOfGroupsRemovedTxtBox;
	}
} //end class 
