package lab04;


public class Robot {

	public boolean armEmpty;
	public Block arm;
	public String name;

	

	public Robot(String n){
		name = n;
		armEmpty = true;
		arm = null;
	}

	public void setArm(Block block){
		if (armEmpty){
			arm = block;
			armEmpty = false;
		}
		else {
			System.err.println("Robot arm not EMPTY!!!");
		}
	}

	public void clearArm(){
		if(!armEmpty){
			arm = null;
			armEmpty = true;
		}
		else {
			System.err.println("Robot arm already EMPTY!!!");
		}
	}

	public boolean armEmpty(){
		return armEmpty;
	}

	

	public boolean holds(Block block) {
		return block.equals(arm);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arm == null) ? 0 : arm.hashCode());
		result = prime * result + (armEmpty ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Robot other = (Robot) obj;
		if (arm == null) {
			if (other.arm != null)
				return false;
		} 
		if (armEmpty != other.armEmpty)
			return false;
		if(arm != null && other.arm != null)
			if (!arm.name.equals(other.arm.name))
				return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}


