import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Alien extends Sprite {

    private Bullet Bullet;
    private final String alien = "/img/badGuy.png";

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        Bullet = new Bullet(x, y);
        ImageIcon enemy = new ImageIcon(this.getClass().getResource(alien));
        setImage(enemy.getImage());

    }

    public void act(int direction) {
        this.x += direction;
    }

    /*
     * Getters & Setters
     */
    
	public Bullet getBullet() {
		return Bullet;
	}

}