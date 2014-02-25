package lab01;

import java.util.Random;

public class Agent {

	Board board;
	int totalScore;
	Random rand;
	
	
	
	public Agent(Board b){
		board = b;
		totalScore = 0;
		rand = new Random();
	}
	
	public void play(){
		while(true){
			Coordinates c = board.getAgentPosition();
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
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args){
		Board board = new Board(10,10, 10);
		board.generate();
		Agent agent = new Agent(board);
		agent.play();
	}

}
