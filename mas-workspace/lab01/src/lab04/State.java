package lab04;

import java.util.LinkedList;
import java.util.List;

import lab04.predicates.ArmEmpty;
import lab04.predicates.On;
import lab04.predicates.OnTable;
import lab04.predicates.Predicate;

public class State {
	public List<Predicate> predicates;
	public List<Block> blocks;
	
	
	public State(){
		predicates = new LinkedList<>();
		blocks = new LinkedList<>();
	}

	public State(State state) {
		this();
		for(Block block : state.blocks){
			blocks.add(new Block.Builder(block.name).build());
		}
		
		//TODO
		//for(int i = 0; i < state.blocks.size(); i++)
	}

	public State addBlock(Block block){
		blocks.add(block);
		return this;
	}
	
	public State addPredicate(Predicate predicate){
		predicates.add(predicate);
		return this;
	}
	
	public String getStateInfo(){
		StringBuilder sb = new StringBuilder();
		sb.append("=== State ===").append("\n");
		for(Predicate pred : predicates){
			sb.append(pred.printInfo()).append("\n");
		}
		return sb.toString();
	}
	

	public boolean satisfies(State goalState) {
		List<Predicate> goalPredicates = goalState.predicates;
		for (Predicate predicate : goalPredicates){
			if(!predicates.contains(predicate))
				return false;
		}
		return true;
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
			addPredicate(new On(b,a)).
			addPredicate(new OnTable(a)).
			addPredicate(new OnTable(c)).
			addPredicate(new OnTable(d)).
			addPredicate( new ArmEmpty(robot));
		
		System.out.println(initialState.getStateInfo());
	}

	
}
