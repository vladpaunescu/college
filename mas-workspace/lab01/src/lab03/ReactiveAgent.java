package lab03;

import java.util.Random;

import lab01.Board;
import lab01.Coordinates;
import lab01.Orientation;

public class ReactiveAgent implements Agent{

	Board board;
	int totalScore;
	Random rand;
	
	
	public ReactiveAgent(Board b){
		board = b;
		totalScore = 0;
		rand = new Random();
	}
	
	public void play(){
			Coordinates c = board.getAgentPosition(this);
			if (board.hasObject(c)){
				totalScore += board.collectObject(c);
			}
			Orientation orient = board.getAgentOrientation();
			if(board.isFreeForward(c, orient)){
				totalScore += board.moveAgentForward(orient);
			} else {
				int dir = rand.nextInt(2);
				if(dir==1){
					System.out.println("Turn Left");
					totalScore += board.turnLeft();
				}
				else{
					System.out.println("Turn Right");
					totalScore += board.turnRight();
				}
			}
			System.out.println(totalScore);
			board.draw();
			
		
		
	}
	
	public static void main(String[] args){
		Board board = new Board(10,10, 10);
		board.generate();
		ReactiveAgent agent = new ReactiveAgent(board);
		agent.play();
	}

}
