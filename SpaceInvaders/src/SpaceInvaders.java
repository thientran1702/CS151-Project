import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author 
*/
public class SpaceInvaders extends JFrame implements Variables {

	/**
	 * 
	 */
	

	private JButton start, help;
	
	/*
	 * Introduction
	 */
	private static final String TOP_MESSAGE = "Space Invaders";
	private static final String INITIAL_MESSAGE = "Space Invaders, Project Group 42";
	/*
	 * Help
	 */
	private static final String HELP_TOP_MESSAGE = "About";
	private static final String HELP_MESSAGE = "Instruction: " 
							+"Arrow Keys to move and Space bar to shoot";

	JFrame frame = new JFrame("Space Invaders");
	JFrame frame2 = new JFrame("Space Invaders");
	JFrame frame3 = new JFrame("Help Center");

	/*
	 * Constructor
	 */
	public SpaceInvaders() {
		String topmessage = TOP_MESSAGE;
		String message = INITIAL_MESSAGE;
		start = new JButton("Start Game");
		start.addActionListener(new ButtonListener());
		start.setBounds(800, 800, 200, 100);
		help = new JButton("Help");
		help.addActionListener(new HelpButton());
		JLabel test = new JLabel(message, SwingConstants.CENTER);
		JLabel header = new JLabel(topmessage, SwingConstants.CENTER);
	frame2.setTitle("Space Invaders");
		frame2.add(test);
		frame2.add(header, BorderLayout.PAGE_START);
		JPanel Menu = new JPanel();
		Menu.add(help);
		Menu.add(start);
		frame2.add(Menu, BorderLayout.PAGE_END);
		frame2.setSize(500, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		frame2.setResizable(false);

	}

	public void closeIntro() {
		frame2.dispose();
		frame3.dispose();
	}

	public void closeHelp() {
		frame3.dispose();
	}
	
	
		
	
	/*
	 * Main
	 */
	public static void main(String[] args) {
		new SpaceInvaders();
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
			frame.getContentPane().add(new Game());
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			closeIntro();

		}
	}

	private class CloseHelp implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			closeHelp();
		}
	}

	private class HelpButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton close = new JButton("Quit");
			close.addActionListener(new CloseHelp());
			String topmessage = HELP_TOP_MESSAGE;
			String message = "<html>" + HELP_MESSAGE + "</html> ";
			JLabel body = new JLabel(message, SwingConstants.CENTER);
			JLabel header = new JLabel(topmessage, SwingConstants.CENTER);
			frame3.add(body);
			frame3.add(header, BorderLayout.PAGE_START);
			frame3.add(close, BorderLayout.PAGE_END);
			frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame3.setSize(250, 290);
			frame3.setResizable(false);
			frame3.setLocationRelativeTo(null);
			frame3.setVisible(true);
		}
	}
}