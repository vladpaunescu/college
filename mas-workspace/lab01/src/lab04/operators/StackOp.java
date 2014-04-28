package lab04.operators;


import lab04.Block;
import lab04.Robot;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Different;
import lab04.predicates.Hold;
import lab04.predicates.On;


public class StackOp extends Operator{
	
	private Robot robot;
	private Block above;
	private Block below;
	
	public StackOp(Robot r, Block x, Block y){
		super();
		
		robot = r;
		above = x;
		below = y;
		
		//preconditions.add(new Different(x, y));
		preconditions.add(new Clear(y));
		preconditions.add(new Hold(r, x));
		
		eliminations.add(new Clear(y));
		eliminations.add(new Hold(r, x));
		
		additions.add(new On(x,y));
		additions.add(new ArmEmpty(r));
	}
	
	@Override
	public String printInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stack(").append(robot.name).
			append(", ").append(above.name).
			append(", ").append(below.name).
			append(")");
		return sb.toString();
	}
}
