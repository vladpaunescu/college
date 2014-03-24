package lab03;

import java.util.Random;

import lab01.Board;
import lab01.Coordinates;
import lab01.Orientation;

public class ReactiveAgent implements Agent{

	Board board;
	int totalScore;
	Random rand;
	
	Coordinates position;
	Orientation orientation;
	
	public ReactiveAgent(Board b, Coordinates pos){
		board = b;
		totalScore = 0;
		rand = new Random();
		position = pos;
	}
	
	public Action play(){
			if (board.hasObject(position)){
				totalScore += board.collectObject(position);
			}
			if(board.isFreeForward(position, orientation)){
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
