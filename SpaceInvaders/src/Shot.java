import javax.swing.ImageIcon;


public class Shot extends Sprite {

    private String shot = "/img/bullet.png";
    public Shot() {
    }

    public Shot(int x, int y) {

        ImageIcon bullet = new ImageIcon(this.getClass().getResource(shot));
        setImage(bullet.getImage());
        setX(x + 5);
        setY(y - 5);
    }
}