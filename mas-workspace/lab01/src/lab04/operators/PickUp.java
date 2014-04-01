package lab04.operators;

import lab04.Block;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Hold;
import lab04.predicates.OnTable;

public class PickUp extends Operator{
	
	public PickUp(Block x){
		super();
		
		preconditions.add(new Clear(x));
		preconditions.add(new OnTable(x));
		preconditions.add(ArmEmpty.getInstance());
		
		eliminations.add(new OnTable(x));
		eliminations.add(ArmEmpty.getInstance());
		
		additions.add(new Hold(x));
	}

}
