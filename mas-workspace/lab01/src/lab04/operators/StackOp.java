package lab04.operators;


import lab04.Block;
import lab04.Robot;
import lab04.predicates.ArmEmpty;
import lab04.predicates.Clear;
import lab04.predicates.Different;
import lab04.predicates.Hold;
import lab04.predicates.On;


public class StackOp extends Operator{
	
	public StackOp(Robot r, Block x, Block y){
		super();
		
		//preconditions.add(new Different(x, y));
		preconditions.add(new Clear(y));
		preconditions.add(new Hold(r, x));
		
		eliminations.add(new Clear(y));
		eliminations.add(new Hold(r, x));
		
		additions.add(new On(x,y));
		additions.add(new ArmEmpty(r));
	}
}
