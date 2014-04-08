package lab04.predicates;

import java.util.Map;

import lab04.Block;

public class Clear implements Predicate{
	
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
	
	@Override
	public void invalidate() {
		// no op
	}

	@Override
	public void makeTrue() {
		block.setClear();
	}
	
	@Override
	public Predicate clone(Map<String, Block> blockMap) {
		return new Clear(blockMap.get(block.name));
	}

	@Override
	public String printInfo() {
		if (isValid()) return "Clear(" + block.name + ")";
		return "NOT Clear(" + block.name + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
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
		Clear other = (Clear) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		return true;
	}

	
	
}
