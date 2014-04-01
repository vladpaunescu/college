package lab04.predicates;

import lab04.Block;

public class OnTable extends Predicate {
	
	public Block block;
	
	public  OnTable(Block b){
		block = b;
	}
	
	@Override
	public boolean isValid(){
		if(block != null){
			return block.onTable();
		}
		return false;
	}

}
