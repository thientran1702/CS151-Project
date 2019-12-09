
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author
 */
public class Game extends JPanel implements Runnable, Variables {

	/**
	 * 
	 */

	private Dimension d;
	private ArrayList aliens;
	private Player player;
	private Shot shot;
	private GameOver gameend;
	private WinScreen Winner;

	private int alienX = 150;
	private int alienY = 25;
	private int direction = -1;
	private int deaths = 0;

	private boolean ingame = true;
	private boolean paused = false;
	private boolean haveWon = true;
	private final String expl = "/img/explosion.png";
	private final String alienpix = "/img/badGuy.png";
	private String message = "Game Over";
	private Thread animator;

	/*
	 * Constructor
	 */
	public Game() {
		addKeyListener(new Controls());
		addMouseMotionListener(new ButtonMouseHoverAdapter());
		addMouseListener(new ButtonClickedAdapter());
		resetGame();
	}

	private void resetGame() {
		paused = false;
		setFocusable(true);
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
		setBackground(Color.black);

		gameInit();
		setDoubleBuffered(true);
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void gameInit() {
		aliens = new ArrayList();
		// Safer for thread access
		Collections.synchronizedList(aliens);
		ingame = true;

		ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				Alien alien = new Alien(alienX + 18 * j, alienY + 18 * i);
				alien.setImage(ii.getImage());
				aliens.add(alien);
			}
		}

		player = new Player();
		shot = new Shot();

		if (animator == null || !ingame) {
			animator = new Thread(this);
			animator.start();
		}

	}

	public void drawAliens(Graphics g) {
		Iterator it = aliens.iterator();

		while (it.hasNext()) {
			Alien alien = (Alien) it.next();

			if (alien.isVisible()) {
				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
			}

			if (alien.isDying()) {
				alien.die();
			}
		}
	}

	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}

		if (player.isDying()) {
			player.die();
			haveWon = false;
			ingame = false;
		}
	}

	public void drawGameEnd(Graphics g) {
		g.drawImage(gameend.getImage(), 0, 0, this);
	}

	public void drawShot(Graphics g) {
		if (shot.isVisible())
			g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
	}

	public void drawBulleting(Graphics g) {
		Iterator i3 = aliens.iterator();

		while (i3.hasNext()) {
			Alien a = (Alien) i3.next();

			Bullet b = a.getBullet();

			if (!b.isDestroyed()) {
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.black);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.green);

		if (ingame) {

			g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
			drawAliens(g);
			drawPlayer(g);
			drawShot(g);
			drawBulleting(g);
			// Pause button
			drawPauseButtons(g);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver() {
		Graphics g = this.getGraphics();

		gameend = new GameOver();
		Winner = new WinScreen();

		// g.setColor(Color.black);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		if (haveWon == true) {
			g.drawImage(Winner.getImage(), 0, 0, this);
		} else {
			g.drawImage(gameend.getImage(), 0, 0, this);
		}
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
		g.setColor(Color.white);
		g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2, BOARD_WIDTH / 2);

		drawNewGameButtons(Color.white);
	}

	// Draw new game button
	private void drawNewGameButtons(Color color) {
	
		Graphics g = this.getGraphics();
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 100, 50);
		g.setColor(color);
		g.drawRect(50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 100, 50);
		g.setColor(color);
		g.drawString("New Game?", (BOARD_WIDTH - 100) / 2, BOARD_WIDTH / 2 + 80);

	}

	// Draw pause/continue button
	private void drawPauseButtons(Graphics g) {
		String message = paused ? "Continue" : "Pause";
		Color color = paused ? Color.red : Color.white;
		g.setColor(new Color(0, 32, 48));
		g.fillRect(BOARD_WIDTH - 90, 5, 80, 45);
		g.setColor(color);
		g.drawRect(BOARD_WIDTH - 90, 5, 80, 45);
		g.setColor(color);
		g.drawString(message, BOARD_WIDTH - 50 - 50 / 2, 32);

	}

	public void animationCycle() {
		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
			ingame = false;
			message = "Winner!";
		}

		// player

		player.enablemove();

		// shot
		if (shot.isVisible()) {
			Iterator playersbullets = aliens.iterator();
			int shotX = shot.getX();
			int shotY = shot.getY();

			while (playersbullets.hasNext()) {
				Alien alien = (Alien) playersbullets.next();
				int alienX = alien.getX();
				int alienY = alien.getY();

				if (alien.isVisible() && shot.isVisible()) {
					if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) && shotY >= (alienY)
							&& shotY <= (alienY + ALIEN_HEIGHT)) {
						ImageIcon ii = new ImageIcon(getClass().getResource(expl));
						alien.setImage(ii.getImage());
						alien.setDying(true);
						deaths++;
						shot.die();
					}
				}
			}

			int y = shot.getY();
			y -= 8;
			if (y < 0)
				shot.die();
			else
				shot.setY(y);
		}

		// aliens

		Iterator alien = aliens.iterator();

		while (alien.hasNext()) {
			Alien a1 = (Alien) alien.next();
			int x = a1.getX();

			if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
				direction = -1;
				Iterator i1 = aliens.iterator();
				while (i1.hasNext()) {
					Alien a2 = (Alien) i1.next();
					a2.setY(a2.getY() + GO_DOWN);
				}
			}

			if (x <= BORDER_LEFT && direction != 1) {
				direction = 1;

				Iterator i2 = aliens.iterator();
				while (i2.hasNext()) {
					Alien a = (Alien) i2.next();
					a.setY(a.getY() + GO_DOWN);
				}
			}
		}

		Iterator it = aliens.iterator();

		while (it.hasNext()) {
			Alien aliens = (Alien) it.next();
			if (aliens.isVisible()) {

				int y = aliens.getY();

				if (y > GROUND - ALIEN_HEIGHT) {
					haveWon = false;
					ingame = false;
					message = "More Aliens";
				}

				aliens.act(direction);
			}
		}

		// Bullets

		Iterator i3 = aliens.iterator();
		Random generator = new Random();

		while (i3.hasNext()) {
			int shot = generator.nextInt(15);
			Alien a = (Alien) i3.next();
			Bullet b = a.getBullet();
			if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {

				b.setDestroyed(false);
				b.setX(a.getX());
				b.setY(a.getY());
			}

			int BulletX = b.getX();
			int BulletY = b.getY();
			int playerX = player.getX();
			int playerY = player.getY();

			if (player.isVisible() && !b.isDestroyed()) {
				if (BulletX >= (playerX) && BulletX <= (playerX + PLAYER_WIDTH) && BulletY >= (playerY)
						&& BulletY <= (playerY + PLAYER_HEIGHT)) {
					ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
					player.setImage(ii.getImage());
					player.setDying(true);
					b.setDestroyed(true);
				}
			}

			if (!b.isDestroyed()) {
				b.setY(b.getY() + 1);
				if (b.getY() >= GROUND - Bullet_HEIGHT) {
					b.setDestroyed(true);
				}
			}
		}
	}

	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (ingame) {
			repaint();
			if (!paused) {
				animationCycle();
			}

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
		gameOver();

	}

	private class Controls extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {

			player.keyPressed(e);

			int x = player.getX();
			int y = player.getY();

			if (ingame && !paused) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_SPACE) {

					if (!shot.isVisible())
						shot = new Shot(x, y);
				}
			}
		}
	}


	private class ButtonMouseHoverAdapter extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			if (ingame) {
				super.mouseMoved(e);
				return;
			}
			// New Game location

			int min_x = 50;
			int min_y = BOARD_WIDTH / 2 + 50;
			int max_x = min_x + BOARD_WIDTH - 100;
			int max_y = min_y + 50;
			Color color = Color.white;
			if (x >= min_x && x <= max_x && y >= min_y && y <= max_y) {
				color = Color.green;
			}
			drawNewGameButtons(color);
		}
	}

	// Handle mouse click on the buttons
	private class ButtonClickedAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			if (ingame) {
				// If click on the Pause/Continue
				int min_x = BOARD_WIDTH - 90;
				int min_y = 5;
				int max_x = min_x + 80;
				int max_y = min_y + 45;
				if (x >= min_x && x <= max_x && y >= min_y && y <= max_y) {
					paused = !paused;
				}
				return;
			}
			// If click on NEW GAME
			int min_x = 50;
			int min_y = BOARD_WIDTH / 2 + 50;
			int max_x = min_x + BOARD_WIDTH - 100;
			int max_y = min_y + 50;
			if (x >= min_x && x <= max_x && y >= min_y && y <= max_y) {
				animator = null;
				resetGame();
			}
		}
	}
}