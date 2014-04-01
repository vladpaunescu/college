package lab04.operators;


import lab04.Block;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Hold;
import lab04.predicates.On;


public class Stack extends Operator{
	
	public Stack(Block x, Block y){
		super();
		
		preconditions.add(new Clear(y));
		preconditions.add(new Hold(x));
		
		eliminations.add(new Clear(y));
		eliminations.add(new Hold(x));
		
		additions.add(new On(x,y));
		additions.add(ArmEmpty.getInstance());
	}
}
