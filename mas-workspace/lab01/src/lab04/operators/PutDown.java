package lab04.operators;

import lab04.Block;
import lab04.Robot;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Hold;
import lab04.predicates.OnTable;

public class PutDown extends Operator{
	
	private Robot robot;
	private Block block;
	
	public PutDown(Robot r, Block x){
		super();
		
		robot = r;
		block = x;
		
		preconditions.add(new Hold(r, x));
		
		eliminations.add(new Hold(r, x));
		
		additions.add(new OnTable(x));
		additions.add(new ArmEmpty(r));
	}
	
	@Override
	public String printInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("PutDown(").append(robot.name).
			append(", ").append(block.name).append(")");
		return sb.toString();
	}
}
