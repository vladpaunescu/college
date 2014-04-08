package lab04;

public class Block {

	public String name;
	public boolean onTable;
	public Block below;
	public Block above;
	public boolean shouldClear;
	
	public static class Builder{
		private String name;
		private boolean onTable;
		private Block below;
		private Block above;
		
		public Builder(String n){
			name = n;
			onTable = false;
			above = null;
			below = null;
		}
		
		public Builder onTable(){
			onTable = true;
			return this;
		}
		
		public Builder below(Block b){
			below = b;
			return this;
		}
		
		public Builder above(Block b){
			above = b;
			return this;
		}
		
		public Block build(){
			return new Block(this);
		}
	}
	
	private Block(Builder b){
		this.name = b.name;
		this. onTable = b.onTable;
		this.below = b.below;
		this.above = b.above;
	}
	
	
	public boolean isOnBlock(Block bl){
		if (below != null)	return below.equals(bl);
		return false;
	}
	
	public boolean isBelowBlock(Block b){
		if (above != null) return above.equals(b);
		return false;
	}
	
	public boolean isClear(){
		return above == null;
	}
	
	public boolean onTable(){
		return onTable;
	}
	
	
	public void setOnTable(){
		onTable = true;
	}
	
	public void setOnBlock(Block b){
		below = b;
	}
	
	public void setAboveBlock(Block b){
		above = b;
	}
	
	public void setClear(){
		above = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((above == null) ? 0 : above.hashCode());
		result = prime * result + ((below == null) ? 0 : below.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (onTable ? 1231 : 1237);
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
		Block other = (Block) obj;
		if (above == null) {
			if (other.above != null)
				return false;
		} 
		if (below == null) {
			if (other.below != null)
				return false;
		} 
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (onTable != other.onTable)
			return false;
		return true;
	}
}