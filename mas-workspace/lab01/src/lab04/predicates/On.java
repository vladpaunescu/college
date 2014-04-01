package lab04.predicates;

import lab04.Block;

public class On extends Predicate{
	
	public Block above;
	public Block below;
	
	public On(Block a, Block b){
		above = a;
		below = b;
	}
	
	@Override
	public boolean isValid(){
		if(above != null && below != null){
			return above.isOnBlock(below);
		}
		return false;
	}
	
}
