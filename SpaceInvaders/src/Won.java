import javax.swing.ImageIcon;

/**
 * 
 * @author 
 */
public class Won extends Sprite implements Commons{
    private final String won = "/img/youWon.jpg";
    private int width;

    /*
     * Constructor
     */
    public Won() {

        ImageIcon win = new ImageIcon(this.getClass().getResource(won));

        width = win.getImage().getWidth(null); 

        setImage(win.getImage());
        setX(0);
        setY(0);
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
