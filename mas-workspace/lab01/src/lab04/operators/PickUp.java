package lab04.operators;

import lab04.Block;
import lab04.Robot;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Hold;
import lab04.predicates.OnTable;

public class PickUp extends Operator{
	
	private Robot robot;
	private Block block;
	public PickUp(Robot r, Block x){
		super();
		
		robot = r;
		block = x;
		
		preconditions.add(new Clear(x));
		preconditions.add(new OnTable(x));
		preconditions.add(new ArmEmpty(r));
		
		eliminations.add(new OnTable(x));
		eliminations.add(new ArmEmpty(r));
		
		additions.add(new Hold(r, x));
	}

	@Override
	public String printInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("PickUp(").append(robot.name).
			append(", ").append(block.name).append(")");
		return sb.toString();
	}
}
