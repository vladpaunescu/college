package lab04.predicates;

import lab04.Robot;

public class ArmEmpty implements Predicate{

	public Robot robot;
	
	public  ArmEmpty(Robot robot){
		this.robot = robot;
	}
	
	@Override
	public boolean isValid(){
		if(robot != null){
			return robot.armEmpty();
		}
		return false;
	}

	@Override
	public String printInfo() {
		if (isValid()){
			return "ArmEmpty(" + robot.name + ")";
		}
		return "NOT ArmEmpty(" + robot.name + ")";
	}
	
	@Override
	public void invalidate() {
		robot.armEmpty = false;
		
	}

	@Override
	public void makeTrue() {
		robot.armEmpty = true;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ArmEmpty other = (ArmEmpty) obj;
		if (robot == null) {
			if (other.robot != null)
				return false;
		} else if (!robot.equals(other.robot))
			return false;
		return true;
	}

	
	
	
}
