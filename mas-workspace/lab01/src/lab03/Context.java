package lab03;

public class Context {
	
	private Agent agent;

	public Context(Agent agent){
		this.agent = agent;
	}
	
	public Orientation getOrientation() {
		return agent.orientation;
	}

	public void moveAgent(Orientation orientation) {
		if(orientation == Orientation.NORTH){
			agent.position.y--;
		}

		if(orientation == Orientation.EAST){
			agent.position.x++;
		}

		if(orientation == Orientation.SOUTH){
			agent.position.y++;
		}

		if(orientation == Orientation.WEST){
			agent.position.x--;
		}
		
	}
	

	public void rotateAgent(Orientation turnDirection) {
		if (turnDirection == Orientation.EAST){
			turnRight();
		}
		else {
			turnLeft();
		}
		
	}
	
	private void turnLeft() {
		if(agent.orientation == Orientation.NORTH){
			agent.orientation = Orientation.WEST;
		}
		
		else if(agent.orientation == Orientation.EAST){
			agent.orientation = Orientation.NORTH;
		}
		
		else if(agent.orientation == Orientation.SOUTH){
			agent.orientation = Orientation.EAST;
		}
		
		else if(agent.orientation == Orientation.WEST){
			agent.orientation = Orientation.SOUTH;
		}
		
	}
	
	private void turnRight() {
		if(agent.orientation == Orientation.NORTH){
			agent.orientation = Orientation.EAST;
		}
		
		else if(agent.orientation == Orientation.EAST){
			agent.orientation = Orientation.SOUTH;
		}
		
		else if(agent.orientation == Orientation.SOUTH){
			agent.orientation = Orientation.WEST;
		}
		
		else if(agent.orientation == Orientation.WEST){
			agent.orientation = Orientation.NORTH;
		}
		
	}

	public void collectObject() {
		agent.totalScore += agent.board.collectObject(agent.position);
	}
	

}
