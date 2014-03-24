package lab03;

public class MoveForwardAction implements Action{

	@Override
	public void execute(Context context) {
		
		Orientation orientation = context.getOrientation();
		context.moveAgent(orientation);
	}
	
	

}
