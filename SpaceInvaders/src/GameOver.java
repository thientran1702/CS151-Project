import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;



/**
 * 
 * @author
 */
public class GameOver extends Sprite implements Commons {

	private final String gameOver = "/img/gameover.png";
	private int width;
	private JButton restartButton;

	/*
	 * Constructor
	 */
	public GameOver() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(gameOver));

		setWidth(ii.getImage().getWidth(null));

		setImage(ii.getImage());
		setX(0);
		setY(0);
		
		add(restartButton);
	}
	
	//add restart button
	
			public void addRestartButton() {
				 
			    restartButton = new JButton("New Game?");
			    restartButton.addActionListener(new ButtonListener());
			    restartButton.setBounds(800, 800, 200, 100);
			    add(restartButton);
			    
			}

			private void add(JButton restartButton2) {
		// TODO Auto-generated method stub
		
	}

			private class ButtonListener implements ActionListener {
				public void actionPerformed(ActionEvent event) {
					new SpaceInvaders();

				}
	}

	/*
	 * Getters & Setters
	 */

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
