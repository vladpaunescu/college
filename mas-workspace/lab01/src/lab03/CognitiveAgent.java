package lab03;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class CognitiveAgent extends Agent{

	public static final Color AGENT_COLOR = new Color(255, 128, 0);
	private int side;
	private int[][] memory;
	private List<Coordinates> path;

	Random rand;


	public CognitiveAgent(Board b, Coordinates pos, Orientation orient){
		super(b, pos, orient);
		totalScore = 0;
		side = board.getWidth();
		path = new LinkedList<>();
		rand = new Random();

		memory = new int[side][side];

		for(int i = 0; i < side; ++i){
			for(int j = 0; j < side; ++j){
				memory[i][j] = Board.UNKNOWN;
			}
		}


	}

	public Action play(){

		int objects[][] = board.getSurroundingObjectsAt2(position);
		updateMemory(objects);

		printMemory();
		if(path.isEmpty()){
			path = findBestObject();
		}

		if (board.hasObject(position)){
			Action action = new CollectAction();
			return action;
		}
		if (path != null && !path.isEmpty()){
			Coordinates next = path.remove(0);
			if(isForward(next)){
				Action action = new MoveForwardAction();
				return action;
			}

			Orientation rotation = getRotation(next);
			if (rotation != null){
				Action action = new TurnAction(rotation);
				return action;
			}
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



	private boolean isForward(Coordinates next) {
		if(orientation == Orientation.NORTH){
			if(position.y - 1 == next. y && position.x == next.x){
				return true;
			}
		}

		if(orientation == Orientation.EAST){
			if(position.y == next. y && position.x + 1 == next.x){
				return true;
			}
		}

		if(orientation == Orientation.SOUTH){
			if(position.y + 1 == next. y && position.x == next.x){
				return true;
			}
		}

		if(orientation == Orientation.WEST){
			if(position.y == next. y && position.x - 1 == next.x){
				return true;
			}
		}

		return false;
	}

	private Orientation getRotation(Coordinates next) {
		if (next.y == position.y - 1 && next.x == position.x){
			// north
			if (orientation == Orientation.EAST){
				return Orientation.WEST;
			}
			return Orientation.EAST;
		}

		if (next.y == position.y && next.x == position.x + 1){
			// east
			if (orientation == Orientation.SOUTH){
				return Orientation.WEST;
			}
			return Orientation.EAST;
		}

		if (next.y == position.y + 1 && next.x == position.x){
			// south
			if (orientation == Orientation.WEST){
				return Orientation.WEST;
			}
			return Orientation.EAST;
		}

		if (next.y == position.y && next.x == position.x - 1){
			// west
			if (orientation == Orientation.NORTH){
				return Orientation.WEST;
			}
			return Orientation.EAST;
		}

		return null;
	}

	private List<Coordinates> findBestObject() {
		int marked[][] = new int[side][side];
		Coordinates parents[][] = new Coordinates[side][side];
		int dx[] = {0, 1, 0, -1};
		int dy[] = {-1, 0, 1, 0};

		Queue<Coordinates> q = new LinkedList<>();
		q.add(position);
		marked[position.y][position.x] = 1;
		parents[position.y][position.x] = null;
		Coordinates dest = null;
		while(!q.isEmpty()){
			Coordinates c = q.poll();
			int distance = marked[c.y][c.x];
			if(board.hasObject(c)){
				dest = c;
				break;
			}

			for(int i = 0; i < 4; i++){
				int x = c.x + dx[i];
				int y = c.y + dy[i];
				if(x >= 0 && x < side && y >= 0 && y < side && marked[y][x] == 0){
					Coordinates neigh = new Coordinates(x, y);
					if(!board.hasObstacle(neigh)){
						marked[y][x] = distance + 1;
						parents[y][x] = c;
						q.add(neigh);
					}
				}
			}
		}

		List<Coordinates> path = new LinkedList<>();
		while(dest != null && dest != position){
			path.add(0, dest);
			dest = parents[dest.y][dest.x];
		}

		//Collections.reverse(path);
		return path;
	}

	private void updateMemory(int[][] objects) {
		for(int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				if(Math.abs(i - 2) + Math.abs(j - 2) <= 2){
					if(position.y + i - 2 >= 0 && position.y + i - 2 < side &&
							position.x + j - 2 >= 0 && position.x + j - 2 < side){

						memory[position.y + i - 2][position.x + j - 2] = objects[i][j];
					}
				}
			}
		}

	}

	private void printMemory(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < side; i++){
			for(int j = 0; j < side; j++){
				if(memory[i][j] == Board.UNKNOWN){
					sb.append("?");
				}
				else sb.append(Board.MAP[memory[i][j]]);

			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}


	@Override
	public Color getColor() {
		return AGENT_COLOR;
	}

}
