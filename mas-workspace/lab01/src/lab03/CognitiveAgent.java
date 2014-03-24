package lab03;

import java.util.Random;

public class CognitiveAgent {

	Board board;
	int totalScore;
	Random rand;
		
	private int memory[][];
	private int maxRightCol;
	private int maxBottomRow;
	private int cellsStored;
	Coordinates position;
	
	
	public CognitiveAgent(Board b){
		board = b;
		totalScore = 0;
		rand = new Random();
		memory = new int[Board.MAX_SIDE][Board.MAX_SIDE];
		for(int i = 0; i < Board.MAX_SIDE; i++)
			for(int j = 0; j < Board.MAX_SIDE; j++)
				memory[i][j] = -1;
		cellsStored = 0;
		position = new Coordinates(0, 0);
		maxBottomRow = 0;
		maxRightCol = 0;
	}
	
	public void play(){
//		while(true){
//			updateMemory(position, Board.FREE);
//			if (board.hasObject(position)){
//				totalScore += board.collectObject(position);
//			}
//			
//			Orientation orient = board.getAgentOrientation();
//
//			if(board.isFreeForward(position, orient)){
//				totalScore += board.moveAgentForward(orient);
//				position = position.getNext(orient);
//			} else {
//				Coordinates forward = position.getNext(orient);
//				if(properCoordinate(forward))
//					updateMemory(forward, Board.OBSTACLE);
//				
//				int dir = rand.nextInt(2);
//				if(dir==1){
//					totalScore += board.turnLeft();
//				}
//				else{
//					totalScore += board.turnRight();
//				}
//			}
//			
//			System.out.println(totalScore);
//			
//			board.draw();
//			showMemory();
//			
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	
	private boolean properCoordinate(Coordinates forward) {
		return forward.x >= 0 && forward.y >= 0;
	}

	private void updateMemory(Coordinates position, int type){
		memory[position.y][position.x] = type;
		if(position.x > maxRightCol) maxRightCol = position.x;
		if(position.y > maxBottomRow) maxBottomRow = position.y;
		cellsStored++;
		totalScore -=0.1;
	}
	
	
	private void showMemory() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i <= maxBottomRow; i++){
			for(int j = 0; j <= maxRightCol; j++){
				if(memory[i][j] == -1){
					sb.append("?");
				}
				else sb.append(Board.MAP[memory[i][j]]);
				
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		
	}

	public static void main(String[] args){
		Board board = new Board(10);
		board.generate();
		CognitiveAgent agent = new CognitiveAgent(board);
		agent.play();
	}

}
