package lab03;

public class TurnAction implements Action {
	
	public Orientation turnDirection;
	
	public TurnAction(Orientation orientation){
		turnDirection = orientation;
	}
	
	@Override
	public void execute(Context context) {
		
		context.rotateAgent(turnDirection);
	}

}
