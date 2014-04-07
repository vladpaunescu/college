package lab04.operators;

import lab04.Block;
import lab04.Robot;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Hold;
import lab04.predicates.OnTable;

public class PickUp extends Operator{
	
	public PickUp(Robot r, Block x){
		super();
		
		preconditions.add(new Clear(x));
		preconditions.add(new OnTable(x));
		preconditions.add(new ArmEmpty(r));
		
		eliminations.add(new OnTable(x));
		eliminations.add(new ArmEmpty(r));
		
		additions.add(new Hold(r, x));
	}
}
