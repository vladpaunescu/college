package lab04.operators;

import lab04.Block;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Hold;
import lab04.predicates.OnTable;

public class PutDown extends Operator{
	
	public PutDown(Block x){
		super();
		
		preconditions.add(new Hold(x));
		
		eliminations.add(new Hold(x));
		
		additions.add(new OnTable(x));
		additions.add(ArmEmpty.getInstance());
	}
}
