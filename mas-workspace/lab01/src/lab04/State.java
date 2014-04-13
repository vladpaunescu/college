package lab04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lab04.predicates.ArmEmpty;
import lab04.predicates.On;
import lab04.predicates.OnTable;
import lab04.predicates.Predicate;

public class State {
	public List<Predicate> predicates;
	public Map<String, Block> blockMap;
	public List<Block> blocks;
	public Robot robot;
	
	
	public State(){
		predicates = new ArrayList<>();
		blocks = new ArrayList<>();
		blockMap = new HashMap<>();
	}

	public State(State state) {
		this();
		for(Block block : state.blocks){
			addBlock(new Block.Builder(block.name).build());
		}
		
		for(Block block: state.blocks){
			Block cloneBlock = getBlockByName(block.name);
			cloneBlock.onTable = block.onTable;
			if (block.above != null){
				cloneBlock.setAboveBlock(getBlockByName(block.above.name));
			}
			if (block.below != null){
				cloneBlock.setOnBlock(getBlockByName(block.below.name));
			}
		}
		
		// clone predicates
		for (Predicate predicate : state.predicates){
			predicates.add(predicate.clone(blockMap));
		}
		
		// clone robot
		robot = new Robot(state.robot.name);
		robot.armEmpty = state.robot.armEmpty;
		if (!robot.armEmpty){
			robot.arm = getBlockByName(state.robot.arm.name);
		}
	}

	public State addBlock(Block block){
		blocks.add(block);
		blockMap.put(block.name, block);
		return this;
	}
	
	public State addPredicate(Predicate predicate){
		predicates.add(predicate);
		return this;
	}
	
	public void removePredicate(Predicate predicate) {
		predicates.remove(predicate);
	}
	
	public void setRobot(Robot r){
		robot = r;
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
	
	public Block getBlockByName(String name){
		return blockMap.get(name);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blockMap == null) ? 0 : blockMap.hashCode());
		result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
		result = prime * result
				+ ((predicates == null) ? 0 : predicates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (blockMap == null) {
			if (other.blockMap != null)
				return false;
		} 
		if (blocks == null) {
			if (other.blocks != null)
				return false;
		} 
		if (predicates == null) {
			if (other.predicates != null)
				return false;
		}
		
		for(Block otherBlock: other.blocks){
			Block block = getBlockByName(otherBlock.name);
			if (block.onTable != otherBlock.onTable){
				return false;
			}
			if (otherBlock.above != null){
				Block above = getBlockByName(otherBlock.above.name);
				if (!above.isOnBlock(block)){
					return false;
				}
			}
			if (otherBlock.below != null){
				Block below = getBlockByName(otherBlock.below.name);
				if (!below.isBelowBlock(block)){
					return false;
				}
			}
		}
		
		// predicates
		for (Predicate predicate : other.predicates){
			if(!predicates.contains(predicate)){
				return false;
			}
		}
		
		if(!robot.equals(other.robot)){
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

	public List<Block> getBlocksOnTable() {
		List<Block> blocks = new ArrayList<>();
		for ()
		
		return blocks;
	}

	

	
}
