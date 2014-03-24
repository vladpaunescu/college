package lab03;

import java.awt.Color;

public abstract class Agent {
	
	public Coordinates position;
	public Orientation orientation;
	public Board board;
	public int totalScore;
	
	public Agent(Board b, Coordinates coord, Orientation orient){
		board = b;
		position = coord;
		orientation = orient;
	}
	
	public abstract Action play();

	public abstract Color getColor();
	

}
