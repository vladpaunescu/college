package lab04.predicates;

import lab04.Block;

public class On implements Predicate{
	
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
	
	@Override
	public void invalidate() {
		above.setOnBlock(null);
		below.setClear();
		
	}

	@Override
	public void makeTrue() {
		above.setOnBlock(below);
		below.setAboveBlock(above);
	}

	@Override
	public String printInfo() {
		if (isValid()) return "On(" + above.name + ", " + below.name +  ")";
		return "NOT On(" + above.name + ", " + below.name +  ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((above == null) ? 0 : above.hashCode());
		result = prime * result + ((below == null) ? 0 : below.hashCode());
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
		On other = (On) obj;
		if (above == null) {
			if (other.above != null)
				return false;
		} else if (!above.equals(other.above))
			return false;
		if (below == null) {
			if (other.below != null)
				return false;
		} else if (!below.equals(other.below))
			return false;
		return true;
	}

}
