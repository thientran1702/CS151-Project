import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Bullet extends Sprite {

	private final String Bullet = "/img/Bomb.png";
	private boolean destroyed;

	/*
	 * Constructor
	 */
	public Bullet(int x, int y) {
		setDestroyed(true);
		this.x = x;
		this.y = y;
		ImageIcon bullets = new ImageIcon(this.getClass().getResource(Bullet));
		setImage(bullets.getImage());
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
