package lab04.predicates;

import lab04.Block;

public class Hold extends Predicate {

	public Block block;
	
	public  Hold(Block b){
		block = b;
	}
	
	@Override
	public boolean isValid(){
		if(block != null){
			return block.inRobotArm();
		}
		return false;
	}
}
