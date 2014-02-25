package lab01;

import java.util.Random;

public class Board {
	public static int FREE = 0;
	public static int OBJECT = 1;
	public static int OBSTACLE = 2;

	public int width;
	public int height;
	private int _board[][];
	private char _map[] = {'-','*', '#'};
	private int _obstacleCount;
	private Random _rand;
	private Coordinates _agentPosition; 
	private Orientation _agentOrientation;

	public Board(int w, int h, int obstacles){
		width = w; height = h;
		_board = new int[h][w];
		_obstacleCount = obstacles;
		_rand = new Random();
		_agentPosition = new Coordinates();
		_agentPosition.x = 0;
		_agentPosition.y = 0;
		_agentOrientation = Orientation.SOUTH;

	}

	public void generate(){
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(obstaclesAround(i,j)){
					_board[i][j] = _rand.nextInt(2);
				} else {
					_board[i][j] =_rand.nextInt(3);
				}
			}
		}

		_board[0][0] = FREE; // start position is free
	}

	private boolean obstaclesAround(int i, int j){
		if (i > 0 && _board[i-1][j] == OBSTACLE) return true;
		if (i < height - 1 && _board[i + 1][j] == OBSTACLE) return true;
		if (j > 0 && _board[i][j-1] == OBSTACLE) return true;
		if (j < width - 1 && _board[i][j+1] == OBSTACLE) return true;

		return false;
	}

	public void draw(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(i == _agentPosition.y && j == _agentPosition.x){
					if(_agentOrientation == Orientation.EAST)
						sb.append(">");
					else if(_agentOrientation == Orientation.SOUTH)
						sb.append("v");
					else if(_agentOrientation == Orientation.WEST)
						sb.append("<");
					else if(_agentOrientation == Orientation.NORTH)
						sb.append("^");
				} else sb.append(_map[_board[i][j]]);
				
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}


	public static void main(String[] args){
		Board board = new Board(10, 10, 10);
		board.generate();
		board.draw();
	}

	public Coordinates getAgentPosition() {
		return _agentPosition;
	}

	public boolean hasObject(Coordinates c) {
		return _board[c.y][c.x] == OBJECT;
	}

	public int collectObject(Coordinates c) {
		if (_board[c.y][c.x] == OBJECT){
			_board[c.y][c.x] = FREE;
			return 100;
		}
		return -1;
	}

	public Orientation getAgentOrientation(){
		return _agentOrientation;
	}

	public boolean isFreeForward(Coordinates c, Orientation orient) {
		if(orient == Orientation.NORTH){
			if (c.y > 0){
				return _board[c.y-1][c.x] != OBSTACLE;
			}
			return false;
		}

		if(orient == Orientation.EAST){
			if (c.x < width - 1){
				return _board[c.y][c.x + 1] != OBSTACLE;
			}
			return false;
		}

		if(orient == Orientation.SOUTH){
			if (c.y < height - 1){
				return _board[c.y + 1][c.x] != OBSTACLE;
			}
			return false;
		}

		if(orient == Orientation.WEST){
			if (c.x > 0){
				return _board[c.y][c.x - 1] != OBSTACLE;
			}
			return false;
		}
		return false;
	}

	public int moveAgentForward(Orientation orient) {

		if(orient == Orientation.NORTH){
			_agentPosition.y--;
		}

		if(orient == Orientation.EAST){
			_agentPosition.x++;
		}

		if(orient == Orientation.SOUTH){
			_agentPosition.y++;
		}

		if(orient == Orientation.WEST){
			_agentPosition.x--;
		}
		
		return -1;
	}

	public int turnLeft() {
		if(_agentOrientation == Orientation.NORTH){
			_agentOrientation = Orientation.WEST;
		}
		
		else if(_agentOrientation == Orientation.EAST){
			_agentOrientation = Orientation.NORTH;
		}
		
		else if(_agentOrientation == Orientation.SOUTH){
			_agentOrientation = Orientation.EAST;
		}
		
		else if(_agentOrientation == Orientation.WEST){
			_agentOrientation = Orientation.SOUTH;
		}
		
		return -1;
	}
	
	public int turnRight() {
		if(_agentOrientation == Orientation.NORTH){
			_agentOrientation = Orientation.EAST;
		}
		
		else if(_agentOrientation == Orientation.EAST){
			_agentOrientation = Orientation.SOUTH;
		}
		
		else if(_agentOrientation == Orientation.SOUTH){
			_agentOrientation = Orientation.WEST;
		}
		
		else if(_agentOrientation == Orientation.WEST){
			_agentOrientation = Orientation.NORTH;
		}
		
		return -1;
	}


}
