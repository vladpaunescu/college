package lab04;

import java.util.LinkedList;
import java.util.List;

import lab04.operators.PickUp;
import lab04.operators.PutDown;
import lab04.operators.StackOp;
import lab04.operators.Unstack;
import lab04.predicates.ArmEmpty;
import lab04.predicates.On;
import lab04.predicates.OnTable;

public class Planner {
	
	public State initialState;
	public State goalState;
	public State beliefState;
	List<ActionState> robotPlan;


	private List<ActionState> plan() {
		// TODO Auto-generated method stub
		if (stateForwardSearch(beliefState, new LinkedList<State>(), new LinkedList<ActionState>())){
			System.out.println("Plan succeeded");
			for(ActionState actionState : robotPlan){
				System.out.println(actionState.state.getStateInfo());
				System.out.println(actionState.operator);
			}
		}
		return robotPlan;
	}

	private boolean stateForwardSearch(State currentState, List<State> visitedStates, List<ActionState> plan){

		if(currentState.satisfies(goalState)){
			robotPlan = plan;
			return true;
		}

		List<ActionState> allowedActions = getAllowedActions(currentState);

		for(ActionState actionState : allowedActions){
			State newState = actionState.applyAction();
			System.out.println(newState.getStateInfo());
			if(!visitedStates.contains(newState)){
				List<State> newVisitedStates = new LinkedList<>(visitedStates);
				newVisitedStates.add(newState);
				List<ActionState> newPlan = new LinkedList<>(plan);
				newPlan.add(actionState);
				boolean success = stateForwardSearch(newState, newVisitedStates, newPlan);
				if (success){
					return true;
				}
			} else{ System.out.println("State already visited"); }
		}
		return false;
	}

	private List<ActionState> getAllowedActions(State currentState) {
		List<ActionState> actions = new LinkedList<>();

		for (Block block : beliefState.blocks){
			State pickupState = new State(currentState);
			PickUp pickup = new PickUp(pickupState.robot, pickupState.getBlockByName(block.name));
			if(pickup.canApply()){
				actions.add(new ActionState(pickup, pickupState));
			}

			State putDownState = new State(currentState);
			PutDown putDown = new PutDown(putDownState.robot, putDownState.getBlockByName(block.name));
			if(putDown.canApply()){
				actions.add(new ActionState(putDown, putDownState));
			}

			State stackState = new State(currentState);
			StackOp stack = new StackOp(stackState.robot, stackState.robot.arm, stackState.getBlockByName(block.name));
			if(stack.canApply()){
				actions.add(new ActionState(stack, stackState));
			}
		}

		for(int i = 0; i < beliefState.blocks.size(); ++i){
			for (int j = 0; j < beliefState.blocks.size(); ++j){
				if (i != j){
					State unstackState = new State(currentState);
					Unstack unstack = new Unstack(unstackState.robot,
							unstackState.getBlockByName(currentState.blocks.get(i).name),
							unstackState.getBlockByName(currentState.blocks.get(j).name));

					if (unstack.canApply()){
						actions.add(new ActionState(unstack, unstackState));
					}
				}
			}
		}

		return actions;
	}
	
	public static void main(String[] args){
		Planner planner = new Planner();
		Robot robot = new Robot("R1");

		State initialState = new State();	

		Block a = new Block.Builder("A").onTable().build();
		Block b = new Block.Builder("B").below(a).build();
		a.setAboveBlock(b);
		Block c = new Block.Builder("C").onTable().build();
		Block d = new Block.Builder("D").onTable().build();
		
		initialState.setRobot(robot);

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
		
		Robot robot1 = new Robot("R1");

		Block a1 = new Block.Builder("A").onTable().build();
		Block b1 = new Block.Builder("B").below(a1).build();
		a1.setAboveBlock(b1);
		Block c1 = new Block.Builder("C").onTable().build();
		Block d1 = new Block.Builder("D").onTable().build();

		beliefState.setRobot(robot1);
		
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
		addPredicate(new ArmEmpty(robot1));

		// goal state does not have the real state, so we add only predicates,
		// even if they are false,
		// we don't change the real world state, as reflected in Block objects.
		State goalState = new State();
		

		Robot robot2 = new Robot("R1");
		
		goalState.setRobot(robot2);

		goalState.
		addBlock(a1).
		addBlock(b1).
		addBlock(c1).
		addBlock(d1);

//		goalState.addPredicate(new On(b1,a1)).
//		addPredicate(new OnTable(a1)).
//		addPredicate(new On(c1, b1)).
//		addPredicate(new On(d1,c1)).
//		addPredicate(new ArmEmpty(robot1));
		goalState.
		addPredicate(new On(c1,a1)).
		addPredicate(new On(b1,d1)).
		addPredicate(new OnTable(a1)).
		addPredicate(new OnTable(d1)).
		addPredicate(new ArmEmpty(robot2));

		System.out.println(initialState.getStateInfo());
		System.out.println(goalState.getStateInfo());


		planner.initialState = initialState;
		planner.beliefState = beliefState;
		planner.goalState = goalState;

		List<ActionState> ops = planner.plan();
		System.out.println("Done");
	}
}
