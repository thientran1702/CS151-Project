import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;



/**
 * 
 * @author
 */
public class GameOver extends Sprite implements Variables {

	private final String gameOver = "/img/gameIsOver.png";
	/*
	 * Constructor
	 */
	public GameOver() {
		ImageIcon lose = new ImageIcon(this.getClass().getResource(gameOver));
		setImage(lose.getImage());
		setX(0);
		setY(0);
	}
}
