package lab04;

public class Block {

	public String name;
	public boolean onTable;
	public Block below;
	public Block above;
	public boolean inRobotArm;
	
	public static class Builder{
		private String name;
		private boolean onTable;
		private Block below;
		private Block above;
		
		public Builder(String n){
			name = n;
		}
		
		public Builder onTable(boolean b){
			onTable = b;
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
		return below.equals(bl);
	}
	
	public boolean isBelowBlock(Block b){
		return above.equals(b);
	}
	
	public boolean isClear(){
		return above == null;
	}
	
	public boolean onTable(){
		return onTable;
	}
	
	public boolean inRobotArm(){
		return inRobotArm;
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

	
}