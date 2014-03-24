package lab03;

import java.awt.*;

import static java.awt.Color.*;

import java.awt.geom.*;

import javax.swing.*;

import static javax.swing.JFrame.*;

@SuppressWarnings("serial")
public class BoardView extends JPanel {
	
	public BoardView() {
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				double width = 1.0 * getWidth() / 10;
				double height = 1.0 * getHeight() / 10;
				double left = c * width;
				double top = r * height;
				g2.setColor(GREEN);
				g2.draw(new Rectangle2D.Double(left, top, width, height));
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.add(new BoardView());
		frame.setVisible(true);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}