package lab04.operators;

import lab04.Block;
import lab04.Robot;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Different;
import lab04.predicates.Hold;
import lab04.predicates.On;

public class Unstack extends Operator{
	
	private Robot robot;
	private Block above;
	private Block below;
	
	public Unstack(Robot r, Block x, Block y){
		super();
		
		robot = r;
		above = x;
		below = y;
		
		//preconditions.add(new Different(x,y));
		preconditions.add(new On(x, y));
		preconditions.add(new Clear(x));
		preconditions.add(new ArmEmpty(r));
		
		eliminations.add(new On(x,y));
		eliminations.add(new ArmEmpty(r));
		
		additions.add(new Hold(r, x));
		additions.add(new Clear(y));
		
	}
	
	@Override
	public String printInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("Unstack(").append(robot.name).
			append(", ").append(above.name).
			append(", ").append(below.name).
			append(")");
		return sb.toString();
	}
}
