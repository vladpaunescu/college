package lab04.operators;

import lab04.Block;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Hold;
import lab04.predicates.On;

public class Unstack extends Operator{
	
	public Unstack(Block x, Block y){
		super();
		
		preconditions.add(new On(x, y));
		preconditions.add(new Clear(x));
		preconditions.add(ArmEmpty.getInstance());
		
		eliminations.add(new On(x,y));
		eliminations.add(ArmEmpty.getInstance());
		
		additions.add(new Hold(x));
		additions.add(new Clear(y));
		
	}
}
