package lab04;

import java.util.LinkedList;
import java.util.List;

import lab04.operators.Operator;
import lab04.operators.PickUp;
import lab04.operators.PutDown;
import lab04.operators.StackOp;
import lab04.operators.Unstack;
import lab04.predicates.ArmEmpty;
import lab04.predicates.On;
import lab04.predicates.OnTable;

public class Robot {
	
	public boolean armEmpty;
	
	public Block arm;
	
	public State initialState;
	public State goalState;
	public State beliefState;
	
	public String name;
	
	
	public Robot(String n){
		name = n;
		armEmpty = true;
		arm = null;
	}
	
	public void setArm(Block block){
		if (armEmpty){
			arm = block;
			armEmpty = false;
		}
		else {
			System.err.println("Robot arm not EMPTY!!!");
		}
	}
	
	public void clearArm(){
		if(!armEmpty){
			arm = null;
			armEmpty = true;
		}
		else {
			System.err.println("Robot arm already EMPTY!!!");
		}
	}
	
	public boolean armEmpty(){
		return armEmpty;
	}
	
	private List<Operator> plan() {
		// TODO Auto-generated method stub
		List<Operator> plan = stateForwardSearch();
		return plan;
	}
	
	private List<Operator> stateForwardSearch(List<State> visitedStates){
		
		if(beliefState.satisfies(goalState)){
			return new LinkedList<>();
		}
		
		List<Operator> allowedActions = getAllowedActions();
		
		for(Operator operator : allowedActions){
			State nextState = new State(beliefState);
			//beliefState = operator.apply();
		}
		
		
	}
	
	private List<Operator> getAllowedActions() {
		List<Operator> actions = new LinkedList<>();
		
		for (Block block : beliefState.blocks){
			
			PickUp pickup = new PickUp(this, block);
			if(pickup.canApply()){
				actions.add(pickup);
			}
			
			PutDown putDown = new PutDown(this, block);
			if(putDown.canApply()){
				actions.add(putDown);
			}
			
			StackOp stack = new StackOp(this, arm, block);
			if(stack.canApply()){
				actions.add(stack);
			}
		}
		
		for(int i = 0; i < beliefState.blocks.size(); ++i){
			for (int j = 0; j < beliefState.blocks.size(); ++j){
				if (i != j){
					Unstack unstack = new Unstack(this, 
							beliefState.blocks.get(i),
							beliefState.blocks.get(j));
					
					if (unstack.canApply()){
						actions.add(unstack);
					}
				}
			}
		}
		
		return actions;
	}

	public boolean holds(Block block) {
		return block.equals(arm);
	}
	
	
	public static void main(String[] args){
		Robot robot = new Robot("R1");
		
		State initialState = new State();	
		
		Block a = new Block.Builder("A").onTable().build();
		Block b = new Block.Builder("B").below(a).build();
		a.setAboveBlock(b);
		Block c = new Block.Builder("C").onTable().build();
		Block d = new Block.Builder("D").onTable().build();
		
		initialState.
			addBlock(a).
			addBlock(b).
			addBlock(c).
			addBlock(d);

		initialState.
			addPredicate(new On(b,a)).
			addPredicate(new OnTable(a)).
			addPredicate(new OnTable(c)).
			addPredicate(new OnTable(d)).
			addPredicate(new ArmEmpty(robot));
		
		
		
		State beliefState = new State();
	
		Block a1 = new Block.Builder("A").onTable().build();
		Block b1 = new Block.Builder("B").below(a1).build();
		a1.setAboveBlock(b1);
		Block c1 = new Block.Builder("C").onTable().build();
		Block d1 = new Block.Builder("D").onTable().build();
		
		beliefState.
			addBlock(a1).
			addBlock(b1).
			addBlock(c1).
			addBlock(d1);
		
		beliefState.
			addPredicate(new On(b1,a1)).
			addPredicate(new OnTable(a1)).
			addPredicate(new OnTable(c1)).
			addPredicate(new OnTable(d1)).
			addPredicate(new ArmEmpty(robot));
		
		// goal state does not have the real state, so we add only predicates,
		// even if they are false,
		// we don't change the real world state, as reflected in Block objects.
		State goalState = new State();
		
		goalState.
			addBlock(a1).
			addBlock(b1).
			addBlock(c1).
			addBlock(d1);
		
		goalState.
			addPredicate(new On(c1,a1)).
			addPredicate(new On(b1,d1)).
			addPredicate(new OnTable(a1)).
			addPredicate(new OnTable(d1)).
			addPredicate(new ArmEmpty(robot));
		
		System.out.println(initialState.getStateInfo());
		System.out.println(goalState.getStateInfo());
		
		
		robot.initialState = initialState;
		robot.beliefState = beliefState;
		robot.goalState = goalState;
		
		
		List<Operator> ops = robot.plan();
		
		
	}

	

	
}
