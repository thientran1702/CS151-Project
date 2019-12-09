import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 
 * @author 
 */
public class WinScreen extends Sprite implements Variables{
    private final String won = "/img/youWon.jpg";


    /*
     * Constructor
     */
    public WinScreen() {

        ImageIcon win = new ImageIcon(this.getClass().getResource(won));
        setImage(win.getImage());
        setX(0);
        setY(0);
    }
}
