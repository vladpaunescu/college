package lab04;


import static java.awt.Color.*;

import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.*;

import static javax.swing.JFrame.*;

@SuppressWarnings("serial")
public class StateView extends JPanel {

	State state;

	public StateView(State s) {
		state = s;
	}

	@Override
	public void paintComponent(Graphics g) {
		List< Stack<Block> > tiles = new ArrayList<>();
		List<Block> blocksOnTable = state.getBlocksOnTable();
		
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		double width = 1.0 * getWidth() / board.getWidth();
		double height = 1.0 * getHeight() / board.getWidth();

		for (int r = 0; r < board.getWidth(); r++) {
			for (int c = 0; c < board.getWidth(); c++) {
				Coordinates coord = new Coordinates(c, r);

				double left = c * width;
				double top = r * height;

				if (board.hasObject(coord)){
					g2.setColor(GREEN);
					g2.fill(new Rectangle2D.Double(left, top, width, height));

				}
				if (board.hasObstacle(coord)){
					g2.setColor(RED);
					g2.fill(new Rectangle2D.Double(left, top, width, height));
				}


				g2.setColor(BLACK);
				g2.draw(new Rectangle2D.Double(left, top, width, height));

			}
		}

		for(Agent a: board.getAgents()){

			double left = a.position.x * width;
			double top = a.position.y * height;

			g2.setColor(a.getColor());
			int xpoints[] = new int[3];
			int ypoints[] = new int[3];
			if (a.orientation == Orientation.SOUTH){
				xpoints[0] = (int) left;
				xpoints[1] = (int) (left + width);
				xpoints[2] = (int) (left + width / 2);

				ypoints[0] = (int) top;
				ypoints[1] = (int) top;
				ypoints[2] = (int) (top + height);
			} else if (a.orientation == Orientation.EAST){
				xpoints[0] = (int) left;
				xpoints[1] = (int) (left + width);
				xpoints[2] = (int) (left);

				ypoints[0] = (int) top;
				ypoints[1] = (int) (top + height / 2);
				ypoints[2] = (int) (top + height);
			} else 	if (a.orientation == Orientation.NORTH){
				xpoints[0] = (int) (left + width/2);
				xpoints[1] = (int) (left + width);
				xpoints[2] = (int) (left);

				ypoints[0] = (int) top;
				ypoints[1] = (int) (top + height);
				ypoints[2] = (int) (top + height);
			} else if (a.orientation == Orientation.WEST){
				xpoints[0] = (int) left;
				xpoints[1] = (int) (left + width);
				xpoints[2] = (int) (left + width);

				ypoints[0] = (int) (top + height / 2);
				ypoints[1] = (int) top;
				ypoints[2] = (int) (top + height);
			}
			g2.fill(new Polygon(xpoints, ypoints, 3));
			g2.setColor(Color.BLACK);
			g2.draw(new Polygon(xpoints, ypoints, 3));
			g2.drawString(a.totalScore + "", (int) (left + width/2), (int) (top + height / 2));
		}
	}
	

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		int width = 10;
		Board board = new Board(width);
//		Agent a1 = new ReactiveAgent(board, 
//				new Coordinates(0, 0), Orientation.SOUTH);
		Agent a2 = new CognitiveAgent(board, 
				new Coordinates(width - 1, 0), Orientation.SOUTH);
//		Agent a3 = new CognitiveAgent(board, 
//				new Coordinates(0, width-1), Orientation.NORTH);
//		Agent a4 = new ReactiveAgent(board, 
//				new Coordinates(width - 1, width - 1), Orientation.NORTH);
		board.generate();
		board.addAgent(a2);
//		board.addAgent(a2);
//		board.addAgent(a3);
//		board.addAgent(a4);

		frame.add(new BoardView(board));
		frame.setVisible(true);

		try {
			while (true){
				Thread.sleep(200);
				board.play();
				frame.repaint();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}