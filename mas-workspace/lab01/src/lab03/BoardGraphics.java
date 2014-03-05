package lab03;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardGraphics {
	
	private final static int CELL_WIDTH = 40;

	private static final int PADDING = 40;
	
	private int width;
	private int height;
	private int board[][];
	private int boardWidthInPx;
	private int boardHeightInPx;
	private JPanel panel;
	private JFrame frame;
	private Graphics gr;
	
	
	public BoardGraphics(int w, int h){
		width = w;
		height = h;
		
		board = new int[h][w];
		
		boardWidthInPx = width * CELL_WIDTH;
		boardHeightInPx = height * CELL_WIDTH;
		initializeComponent();
	}
	
	private void initializeComponent() {
		panel = new JPanel();
		Dimension expectedDimension = new Dimension(CELL_WIDTH * width + PADDING, CELL_WIDTH * height + PADDING);

		panel.setPreferredSize(expectedDimension);
		panel.setMaximumSize(expectedDimension);
		panel.setMinimumSize(expectedDimension);

		frame = new JFrame();
		frame.add(panel);
		frame.setSize(expectedDimension);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setMinimumSize(frame.getMinimumSize());   // cannot be resized-

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		gr = frame.getGraphics();
		
	}

	public void draw(){
		drawBackground();
		panel.repaint();
				
	}

	private void drawBackground() {
		gr.setColor(new Color(255,255, 0));
		gr.drawRect(0, 0, boardWidthInPx, boardHeightInPx);
		gr.setColor(Color.BLACK);
		for(int i = 0; i <= width; i++){
			gr.drawLine(i * CELL_WIDTH, 0, i * CELL_WIDTH, boardHeightInPx);
		}
		
		for(int i = 0; i <= height; i++){
			gr.drawLine(0, i * CELL_WIDTH, boardWidthInPx, i * CELL_WIDTH);
		}
		
	}

	public static void main(String[] args){
		BoardGraphics bg = new BoardGraphics(10, 10);
		bg.draw();
	

	}

}

