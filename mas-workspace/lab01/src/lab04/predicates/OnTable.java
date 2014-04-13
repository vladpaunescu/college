package lab04.predicates;

import java.util.Map;

import lab04.Block;

public class OnTable implements Predicate {
	
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
	
	@Override
	public void invalidate() {
		block.onTable = false;
	}

	@Override
	public void makeTrue() {
		block.setOnTable();
	}
	
	@Override
	public Predicate clone(Map<String, Block> blockMap) {
		return new OnTable(blockMap.get(block.name));
	}

	@Override
	public String printInfo() {
		if (isValid()) return "OnTable(" + block.name + ")";
		return "NOT OnTable(" + block.name + ")";
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
		OnTable other = (OnTable) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.name.equals(other.block.name))
			return false;
		return true;
	}
}
