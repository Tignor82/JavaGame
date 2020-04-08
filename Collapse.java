//Joseph Vigil - Salazar
//jsala235@unm.edu
//CS 251, lab section 002
//Lab 9: Collapse_Full_Game
//Collapse.java

/*
 * This is the main class for the Collapse game.
 * Initializes an object of CollapseFrame, sets
 * frame size, and displays the frame. 
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Collapse {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				
				CollapseFrame frame = new CollapseFrame();
				frame.setSize(825,700); //Set size of frame
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true); //Display the frame
			}
		});

	}

} //end class
