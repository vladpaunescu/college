package lab03;

import java.awt.Color;
import java.util.Random;


public class ReactiveAgent extends Agent{

	public static Color AGENT_COLOR = new Color(0, 120, 255);
	Random rand;
	
	public ReactiveAgent(Board b, Coordinates pos, Orientation orient){
		super(b, pos, orient);
		totalScore = 0;
		rand = new Random();
	}

	public Action play(){

		if (board.hasObject(position)){
			Action action = new CollectAction();
			return action;
		}
		
		if (board.hasObjectForward(position, orientation)){
			Action action = new MoveForwardAction();
			return action;
		}
		
		Orientation orient = board.getSurroundingObject(orientation, position);
		if (orient != null){
			Action action = new TurnAction(orient);
			return action;
		}
		
		if(board.isFreeForward(position, orientation)){
			Action action = new MoveForwardAction();
			return action;
		} 

		int dir = rand.nextInt(2);
		if(dir==1){
			Action action = new TurnAction(Orientation.WEST);
			return action;
		}
		
		Action action = new TurnAction(Orientation.EAST);
		return action;

	}

	@Override
	public Color getColor() {
		return AGENT_COLOR;
	}

}
