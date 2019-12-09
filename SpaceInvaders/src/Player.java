import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * 
 * @author
 */
public class Player extends Sprite implements Variables {
	private final String player = "/img/rocket.png";
	private int width;

	/*
	 * Constructor
	 */
	public Player() {
		ImageIcon user = new ImageIcon(this.getClass().getResource(player));
		width = user.getImage().getWidth(null);
		setImage(user.getImage());
		setX(400);
		setY(400);
	}

	public void enablemove() {
		x += dx;
		if (x <= 2)
			x = 2;
		if (x >= BOARD_WIDTH - 2 * width)
			x = BOARD_WIDTH - 2 * width;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = -5;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 5;
		}
}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
	}
}