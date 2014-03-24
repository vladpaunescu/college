package lab03;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board {

	public static int FREE = 0;
	public static int OBJECT = 1;
	public static int OBSTACLE = 2;

	public static int MAX_SIDE = 20;
	public static char MAP[] = {'-','*', '#'};

	public int side;
	public int maxObjectCount[];

	private int _board[][];

	private int objectCounts[];

	private Random _rand;
	private Coordinates _agentPosition; 
	private Orientation _agentOrientation;

	private List<Agent> _agents;

	public Board(int n){
		side = n;
		maxObjectCount = new int[3];
		maxObjectCount[OBJECT] = n*n/3;
		maxObjectCount[OBSTACLE] = n;
		_board = new int[n][n];
		objectCounts = new int[3];
		_rand = new Random();
		_agentPosition = new Coordinates(0, 0);
		_agentOrientation = Orientation.SOUTH;
		_agents = new ArrayList<>();

	}

	public void addAgent(Agent a){
		_agents.add(a);
	}

	public void play(){
		for(Agent a : _agents){
			Action action = a.play();
			action.execute(new Context(a));
		}
	}


	public void generate(){
		for(int i = 0; i < side; i++){
			for(int j = 0; j < side; j++){
				if (onCorner(i, j)){
					_board[i][j] = FREE;
					continue;
				}

				int type;
				if(obstaclesAround(i,j)){
					if(objectCounts[OBJECT] < maxObjectCount[OBJECT]){
						type = _rand.nextInt(2);
						++objectCounts[type];
					}
					else type = FREE;

				} else {
					int count = 1;
					if(objectCounts[OBJECT] < maxObjectCount[OBJECT]) {
						count++;
					}

					if(objectCounts[OBSTACLE] < maxObjectCount[OBSTACLE]) {
						count++;
					}
					type =_rand.nextInt(count);
					if(type == 1){
						if (objectCounts[OBJECT] > maxObjectCount[OBJECT]){
							type++;
						}
					}
					objectCounts[type]++;
				}
				_board[i][j] = type;
			}
		}

	}
	
	public List<Agent> getAgents() {
		return _agents;
	}


	private boolean onCorner(int i, int j){
		return i == 0 && j == 0 ||
				i == side - 1 && j == 0 ||
				i == 0 && j == side - 1 ||
				i == side - 1 && j == side - 1;
	}

	private boolean obstaclesAround(int i, int j){
		if (i > 0 && _board[i-1][j] == OBSTACLE) return true;
		if (i < side - 1 && _board[i + 1][j] == OBSTACLE) return true;
		if (j > 0 && _board[i][j-1] == OBSTACLE) return true;
		if (j < side - 1 && _board[i][j+1] == OBSTACLE) return true;

		return false;
	}

	public void draw(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < side; i++){
			for(int j = 0; j < side; j++){
				if(i == _agentPosition.y && j == _agentPosition.x){
					if(_agentOrientation == Orientation.EAST)
						sb.append(">");
					else if(_agentOrientation == Orientation.SOUTH)
						sb.append("v");
					else if(_agentOrientation == Orientation.WEST)
						sb.append("<");
					else if(_agentOrientation == Orientation.NORTH)
						sb.append("^");
				} else sb.append(MAP[_board[i][j]]);

			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}



	public boolean hasObject(Coordinates c) {
		return _board[c.y][c.x] == OBJECT;
	}

	public boolean hasObstacle(Coordinates c) {
		return _board[c.y][c.x] == OBSTACLE;
	}

	public int getWidth() {
		return side;
	}


	public int collectObject(Coordinates c) {
		if (_board[c.y][c.x] == OBJECT){
			_board[c.y][c.x] = FREE;
			return 100;
		}
		return -1;
	}

	public boolean isFreeForward(Coordinates c, Orientation orient) {
		if(orient == Orientation.NORTH){
			if (c.y > 0){
				return _board[c.y-1][c.x] != OBSTACLE;
			}
			return false;
		}

		if(orient == Orientation.EAST){
			if (c.x < side - 1){
				return _board[c.y][c.x + 1] != OBSTACLE;
			}
			return false;
		}

		if(orient == Orientation.SOUTH){
			if (c.y < side - 1){
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
	
	public boolean hasObjectForward(Coordinates position,
			Orientation orientation) {
		if(orientation == Orientation.NORTH){
			if (position.y > 0){
				return _board[position.y-1][position.x] == OBJECT;
			}
			return false;
		}

		if(orientation == Orientation.EAST){
			if (position.x < side - 1){
				return _board[position.y][position.x + 1] == OBJECT;
			}
			return false;
		}

		if(orientation == Orientation.SOUTH){
			if (position.y < side - 1){
				return _board[position.y + 1][position.x] == OBJECT;
			}
			return false;
		}

		if(orientation == Orientation.WEST){
			if (position.x > 0){
				return _board[position.y][position.x - 1] == OBJECT;
			}
			return false;
		}
		return false;
	}

	
	public Orientation getSurroundingObject(Orientation orientation, Coordinates position) {
		if (position.y > 0 && _board[position.y-1][position.x]  == OBJECT){
			if (orientation == Orientation.EAST){
				return Orientation.WEST;
			}
			return Orientation.EAST;
		}
		
		if (position.x > 0 && _board[position.y][position.x - 1]  == OBJECT){
			if (orientation == Orientation.NORTH){
				return Orientation.WEST;
			}
			return Orientation.EAST;
		}
		
		if (position.y < side - 1 && _board[position.y + 1][position.x]  == OBJECT){
			if (orientation == Orientation.EAST){
				return Orientation.EAST;
			}
			return Orientation.WEST;
		}
		
		if (position.x < side - 1 && _board[position.y][position.x + 1]  == OBJECT){
			if (orientation == Orientation.NORTH){
				return Orientation.EAST;
			}
			return Orientation.WEST;
		}
		
		return null;
	}

	
	

}
