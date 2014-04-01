package lab04.predicates;

import lab04.Block;

public class Clear extends Predicate{
	
	public Block block;
	
	public  Clear(Block b){
		block = b;
	}
	
	@Override
	public boolean isValid(){
		if(block != null){
			return block.isClear();
		}
		return false;
	}

}
