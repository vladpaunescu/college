package lab04.predicates;

import lab04.Block;
import lab04.Robot;

public class Hold implements Predicate {

	public Block block;
	public Robot robot;
	
	public  Hold(Robot r, Block b){
		robot = r;
		block = b;
	}
	
	@Override
	public boolean isValid(){
		if(block != null){
			return robot.holds(block);
		}
		return false;
	}
	
	@Override
	public void invalidate() {
		robot.clearArm();
		
	}

	@Override
	public void makeTrue() {
		robot.setArm(block);
	}

	@Override
	public String printInfo() {
		if (isValid()) return "Hold(" + robot.name + ", " + block.name + ")";
		return "NOT Hold(" + robot.name + ", " + block.name + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + ((robot == null) ? 0 : robot.hashCode());
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
		Hold other = (Hold) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (robot == null) {
			if (other.robot != null)
				return false;
		} else if (!robot.equals(other.robot))
			return false;
		return true;
	}

	
}
