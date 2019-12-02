import javax.swing.ImageIcon;


public class Shot extends Sprite {

    private String shot = "/img/bullet.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        ImageIcon bullet = new ImageIcon(this.getClass().getResource(shot));
        setImage(bullet.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}